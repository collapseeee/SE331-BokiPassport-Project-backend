# Frontend Integration Plan — Server-Scored Exams

**For:** the frontend team (this is a separate repo and a separate branch)
**Backend branch:** `feature/entity-DTO` → `develop`
**Status of this document:** the contract is final. The backend implements it; this file is what you implement against.

---

## ⚠️ Read this first: merging the backend branch breaks the exam flow

The backend stops sending `isCorrect` to the browser. Your current `ExamQuestionView.calculateScore()` reads
`answerChoice.isCorrect` to score the exam — after this backend lands, that value is `undefined` and **every
exam scores 0**.

This is intentional: the answer key must not be in the browser, because a student can open devtools and read
it before answering. Scoring moves to the server.

**Consequence:** the frontend branch must land **before or together with** the backend branch. If the backend
lands alone, the exam feature is broken until the frontend catches up.

**Also note:** the backend now awards the level server-side on a pass, so the frontend must stop doing it
client-side (see Change 6).

---

## 1. The three endpoints that changed

### 1.1 `GET /v1/questions?examId={examId}` — candidate view (**shape changed**)

Same URL as before. Level-gated: a user below the exam's level gets **403**.

**Before**
```json
{"id":1,"questionText":"What is BokiAI?","examId":1,
 "answerChoices":[{"id":1,"answerText":"The real boki","isCorrect":false,"questionId":1},
                  {"id":2,"answerText":"The Agentic Artificial Inteligence","isCorrect":true,"questionId":1}]}
```

**After** — `isCorrect` is gone
```json
{"id":1,"questionText":"What is BokiAI?","examId":1,
 "answerChoices":[{"id":1,"answerText":"The real boki","questionId":1},
                  {"id":2,"answerText":"The Agentic Artificial Inteligence","questionId":1}]}
```

`answerChoices[].id` and `.questionId` are unchanged, so the radio-button rendering and the `selectedAnswers`
record keep working. Only `isCorrect` disappears.

### 1.2 `GET /v1/admin/exams/{examId}/questions` — admin view (**new**)

The answer key, for the exam editor only. `ROLE_ADMIN` required; a non-admin gets **403**.

```json
[{"id":1,"questionText":"What is BokiAI?","examId":1,
  "answerChoices":[{"id":1,"answerText":"The real boki","isCorrect":false,"questionId":1},
                   {"id":2,"answerText":"The Agentic Artificial Inteligence","isCorrect":true,"questionId":1}]}]}
```

### 1.3 `POST /v1/exams/{examId}/submit` — submit the exam (**new**)

Level-gated, same rule as 1.1: **403** if the caller is below the exam's level.

**Request**
```json
{ "answers": { "1": 2, "2": 6, "3": 9, "4": 15, "5": 18 } }
```
`answers` maps **question id → chosen answer id**. A question you did not answer is simply omitted and counts
as wrong (the timer can expire mid-exam, so partial submissions are valid — they are not rejected).

**Response 200**
```json
{ "examId": 1, "totalScore": 3, "maxScore": 5, "isPassed": true, "awardedLevelId": 2 }
```

| Field | Type | Meaning |
|---|---|---|
| `examId` | number | the exam that was submitted |
| `totalScore` | number | number of correct answers |
| `maxScore` | number | number of questions in the exam |
| `isPassed` | boolean | `totalScore >= round(passCriteria * maxScore)` — the same formula the frontend used |
| `awardedLevelId` | number \| null | the level the user just unlocked, `null` if they did not pass or there is no next level |

**Error responses**
| Status | When |
|---|---|
| 403 | the caller's level is below the exam's level |
| 404 | no such exam |

---

## 2. Every affected endpoint — field list

| Endpoint | Fields |
|---|---|
| `GET /v1/questions?examId=` | `id, questionText, examId, answerChoices[{id, answerText, questionId}]` — **no `isCorrect`** |
| `GET /v1/admin/exams/{id}/questions` | `id, questionText, examId, answerChoices[{id, answerText, isCorrect, questionId}]` |
| `POST /v1/exams/{id}/submit` | request `{answers: {questionId: answerId}}` → response `{examId, totalScore, maxScore, isPassed, awardedLevelId}` |
| `GET /v1/exams` | `id, name, description, levelId, questionCount` (unchanged) |
| `GET /v1/exams/{id}` | `id, name, description, levelId, questionCount` (unchanged) |
| `GET /v1/users/{id}` | now also carries `isActive` and `levelId` (unchanged by this work) |
| `GET /v1/exams/users/{id}` | now also carries `examId` (unchanged by this work) |

Unchanged by this work: `/v1/levels`, `/v1/badges`, `/v1/courses`, `/v1/benefits`, `/v1/services`, `/v1/users`.

---

## 3. The frontend changes

### Change 1 — stop scoring client-side

`src/views/user/exam/ExamQuestionView.vue`

Delete `calculateScore()` and the `submit` event's score payload. The component should now emit **the selected
answers**, not a number:

```ts
// before
const emit = defineEmits<{ (e: 'submit', score: number): void }>();
const calculateScore = () => { /* reads answerChoice.isCorrect */ };

// after
const emit = defineEmits<{ (e: 'submit', answers: Record<number, number>): void }>();
// selectedAnswers is already Record<number, AnswerChoice> — reduce it to id -> answerId
const submitAnswers = () => {
    const answers: Record<number, number> = {};
    for (const [questionId, choice] of Object.entries(selectedAnswers.value)) {
        answers[Number(questionId)] = choice.id;
    }
    emit('submit', answers);
};
```

The "you must answer every question" check can stay as a UI guard, but the server does **not** require it —
an unanswered question simply counts as wrong.

### Change 2 — call the submit endpoint

`src/views/user/exam/ExamView.vue` — replace the `handleSubmit(score)` handler:

```ts
const examAttemptService = ... // new call to POST /v1/exams/{id}/submit

const handleSubmit = async (answers: Record<number, number>) => {
    stopTimer();
    const { data } = await examAttemptService.submit(exam.value!.id, answers);
    userScore.value = data.totalScore;
    questionLength.value = data.maxScore;
    isPassed.value = data.isPassed;
    if (data.awardedLevelId) {
        // the server owns the level now — refetch the profile
        await refreshProfile();
    }
    isModalOpen.value = true;
};
```

`isPassed` must now come from the **server's** answer, not from the local `passCriteria` maths. The local
`levelStore.getLevelById(...)` lookup is still fine for *displaying* the pass criteria percentage, but the
pass/fail verdict is the server's.

### Change 3 — the exam editor needs the key

`src/views/admin/ExamManagementEditView.vue` and `src/services/QuestionService.ts`

The editor is the only place that legitimately needs `isCorrect` (to show which answer is right). Point it at
the new admin endpoint:

```ts
// a new method, used only by the admin editor
getQuestionsWithAnswerKey(examId: number) {
    return apiClient.get(`/v1/admin/exams/${examId}/questions`);
}
```

The candidate flow keeps using `getQuestionsByExamId(examId)`, which now returns the key-free shape.

### Change 4 — handle the 403

`src/router/index.ts`

The router currently blocks the route client-side:

```ts
if (examRes.levelId > Number(userLevel)) {
    return { name: 'NoPermission', query: { role: 'User with lower level' } }
}
```

Keep it — it gives a nicer experience than waiting for the server to say no. But the server is now the
authority, so also handle a 403 from the questions call by routing to `NoPermission` rather than showing an
empty exam. The `NoPermissionView` route already exists for this.

### Change 5 — the exam list is still public

`GET /v1/exams` and `GET /v1/exams/{id}` are **not** level-gated — the list screen shows locked exams with a
lock icon, so the metadata has to be visible to everyone. No change needed there.

### Change 6 — the server owns the level award

`src/stores/auth.ts`

`updateUserLevel()` was appending a level to the local profile after a pass. The server now does that, so
**remove the local `updateUserLevel` call** and refetch the profile after a successful submit, so the new
level comes from the backend:

```ts
// after a submit that returned awardedLevelId
const { data } = await UserService.getUser(authStore.id);
authStore.user = data;   // now includes the new level
```

`authStore.level` already derives from `user.levels`, so nothing else changes.

### Change 7 — the token interceptor (still outstanding)

`src/services/apiClient.ts` has no request interceptor, so it never sends `Authorization` and every `/v1`
call returns 403. Add one:

```ts
apiClient.interceptors.request.use((config) => {
    const raw = localStorage.getItem('AUTH_TOKEN');
    if (raw) {
        const { accessToken } = JSON.parse(raw);
        config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
});
```

The token is stored under the `AUTH_TOKEN` key by `src/stores/auth.ts`.

---

## 4. Types to update in `src/types.ts`

```ts
export interface AnswerChoice {
    id: number
    answerText: string
    questionId: number
    // isCorrect is intentionally absent from the candidate endpoint.
    // The admin endpoint returns a separate type that includes it.
}

export interface AnswerChoiceWithKey extends AnswerChoice {
    isCorrect: boolean
}

export interface ExamSubmitResponse {
    examId: number
    totalScore: number
    maxScore: number
    isPassed: boolean
    awardedLevelId: number | null
}
```

`src/types.ts` currently declares `User` and `UserExamHistory` **twice** with different shapes. That needs
cleaning up at some point — the backend contract is now fixed, so the duplicate definitions can be deleted in
favour of the real ones.

---

## 5. How to test the backend side of this yourself

```powershell
cd "D:\Work\UNI_Year3\Component Base\Project AI-passport\se331-2026-project02-bokinarak\953331-2026-component-based-software-dev-project-2-group-2"
docker compose up -d
.\mvnw.cmd spring-boot:run
```

In another terminal:

```powershell
$body = '{"username":"boki","password":"boki123"}'
$token = (Invoke-RestMethod -Uri http://localhost:8080/v1/auth/authenticate -Method Post -ContentType 'application/json' -Body $body).accessToken

# boki is level 1. Exam 3 is a level-3 exam -> must be 403
curl.exe -o NUL -w "%{http_code}" "http://localhost:8080/v1/questions?examId=3" -H "Authorization: Bearer $token"

# exam 1 is a level-1 exam -> 200, and no isCorrect anywhere in the body
curl.exe "http://localhost:8080/v1/questions?examId=1" -H "Authorization: Bearer $token"

# the admin key endpoint, as a non-admin -> 403
curl.exe -o NUL -w "%{http_code}" "http://localhost:8080/v1/admin/exams/1/questions" -H "Authorization: Bearer $token"

# the admin key endpoint, as the admin -> 200, with isCorrect
$admin = (Invoke-RestMethod -Uri http://localhost:8080/v1/auth/authenticate -Method Post -ContentType 'application/json' -Body '{"username":"admin","password":"123"}').accessToken
curl.exe "http://localhost:8080/v1/admin/exams/1/questions" -H "Authorization: Bearer $admin"

# submit an exam -> 200 with the score
curl.exe -X POST "http://localhost:8080/v1/exams/1/submit" -H "Authorization: Bearer $token" -H "Content-Type: application/json" -d '{\"answers\":{\"1\":2,\"2\":6,\"3\":9,\"4\":15,\"5\":18}}'
```

> Use `curl.exe`, not `curl`. In PowerShell 5.1 `curl` is an alias for `Invoke-WebRequest`, which has no `-H`
> flag, so the header silently never gets sent and everything comes back 403.

Note that submitting an exam as `boki` writes a real exam-history row and may award `boki` level 2. The
database is rebuilt on every backend restart (`ddl-auto: create`), so the seed data comes back.

---

## 6. Open questions for the frontend team

1. **Should the submit be blocked before all questions are answered?** The server allows partial submissions
   (the timer can expire). The frontend currently blocks it with an alert. Keep the UI guard, or let the
   server be the only judge?
2. **Do you want a "review your mistakes" screen after submitting?** The backend does not return the correct
   answers. Adding that means returning the key after a submit, which is a further decision.
3. **Should the exam list show only unlocked exams, or all of them with locks?** The backend currently returns
   every exam, and the existing `ExamCard` already has a locked state.
