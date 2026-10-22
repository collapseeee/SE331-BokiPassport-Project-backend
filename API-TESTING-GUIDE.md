# API Testing Guide — BokiPassport entity DTOs

Branch: `feature/entity-DTO` → target: `develop`
Reviewer: please work through this top to bottom. Every command is copy-pasteable in **PowerShell**.

---

## 0. Before you start (5 min)

### 0.1 Start the database

```powershell
cd "D:\Work\UNI_Year3\Component Base\Project AI-passport\se331-2026-project02-bokinarak\953331-2026-component-based-software-dev-project-2-group-2"
docker compose up -d
```

### 0.2 Start the backend

```powershell
.\mvnw.cmd spring-boot:run
```

Wait for `Started BokipassportApplication`. The schema is rebuilt on every boot
(`spring.jpa.jpa.ddl-auto: create`), so the seed data in `InitApp` is regenerated each time.
**Your local database is wiped on every start** — that is expected, not a bug.

### 0.3 Get a token — you need this for EVERY request

```powershell
$body = '{"username":"boki","password":"boki123"}'
$token = (Invoke-RestMethod -Uri http://localhost:8080/v1/auth/authenticate -Method Post -ContentType 'application/json' -Body $body).accessToken
```

> **Two gotchas that cause a confusing `403 Forbidden`:**
>
> 1. Use `curl.exe` **with the `.exe`**. In PowerShell 5.1, `curl` is an alias for
>    `Invoke-WebRequest`, which has no `-H` flag — your header silently never gets sent.
> 2. `$token` only lives in the current terminal window. If you open a new one, get a new token.
>
> Every endpoint except `/v1/auth/**` requires `Authorization: Bearer <token>`.
> The token lasts 24 hours and re-logging in does **not** invalidate the old one.

**Seeded logins**

| Username | Password | Level | Notes |
|---|---|---|---|
| `admin` | `123` | 4 | ROLE_ADMIN + ROLE_USER |
| `boki` | `boki123` | 1 | |
| `gerry` | `123` | 3 | disabled account |
| `collapsee` | `123` | 2 | |
| `somchai` | `123` | 2 | |
| `mali` | `123` | 1 | |
| `arthit` | `123` | 3 | disabled account |
| `nicha` | `123` | 3 | |
| `krittin` | `123` | 3 | |
| `pimchanok` | `123` | 4 | |
| `tanapat` | `123` | 3 | |
| `supansa` | `123` | 1 | disabled account |
| `thianlnwza007` | `123` | 1 | |

---

## 1. Level — `GET /v1/levels`

```powershell
curl.exe http://localhost:8080/v1/levels -H "Authorization: Bearer $token"
curl.exe http://localhost:8080/v1/levels/1 -H "Authorization: Bearer $token"
```

**Expect** — 4 levels, each with its own `benefits` array already populated:

```json
[{"id":1,"name":"Level 1","description":"Beginner","passCriteria":0.5,
  "benefits":[{"id":1,"description":"You can use BokiAI up to 1M Tokens!","levelId":1},
              {"id":2,"description":"You can generate image using BokiAI!","levelId":1}]},
 {"id":2,"name":"Level 2","description":"Intermediate","passCriteria":0.55,"benefits":[...3 items...]},
 {"id":3,"name":"Level 3","description":"Advanced","passCriteria":0.75,"benefits":[...4 items...]},
 {"id":4,"name":"Level 4","description":"Master","passCriteria":0.8,"benefits":[...5 items...]}]
```

**Check**
- [ ] 4 levels, ids 1–4
- [ ] `passCriteria` is a fraction: `0.5`, `0.55`, `0.75`, `0.8` — the frontend multiplies by 100
- [ ] every benefit has the right `levelId` (level 1 → 1, level 2 → 2, …)
- [ ] `GET /v1/levels/1` returns **only** Level 1's 2 benefits

---

## 2. Badge — `GET /v1/badges`

```powershell
curl.exe http://localhost:8080/v1/badges -H "Authorization: Bearer $token"
curl.exe http://localhost:8080/v1/badges/1 -H "Authorization: Bearer $token"
```

**Expect** — 7 badges, shape `{id, name, description}` and **no `users` key**:

```json
[{"id":1,"name":"Welcome to Boki Passport!","description":"You've logged into Boki Passport for the first time!"},
 {"id":2,"name":"Hello World!","description":"You've learned the first course!"},
 {"id":3,"name":"\"Easy peasy lemon squeezy!\"","description":"Passed the level 1 exam."},
 {"id":4,"name":"\"Showtime!\"","description":"Passed the level 2 exam."},
 {"id":5,"name":"Dance with the BokiAI","description":"Passed the level 3 exam."},
 {"id":6,"name":"Stairway to BokiAI expert","description":"Passed the level 4 exam."},
 {"id":7,"name":"Better luck next time!","description":"Failed the exam for the first time."}]
```

**Check**
- [ ] 7 badges
- [ ] no `users` array leaking out of the entity

---

## 3. Course — `GET /v1/courses`

```powershell
curl.exe http://localhost:8080/v1/courses -H "Authorization: Bearer $token"
curl.exe http://localhost:8080/v1/courses/1 -H "Authorization: Bearer $token"
```

**Expect** — 4 courses:

```json
[{"id":1,"createdAt":"2026-10-22T05:05:52.100142Z","name":"Introduction to Agentic AI",
  "description":"A comprehensive overview of AI agent capabilities, ...",
  "videoUrl":"https://youtu.be/ZZ2QUCePgYw?si=4lXuVGfxJoDP-pAm","levelId":1}, ...]
```

**Check**
- [ ] field is `videoUrl` (**not** `video` — the entity field is renamed in the DTO)
- [ ] `levelId` 1→4
- [ ] `createdAt` is present
- [ ] `GET /v1/courses/1` has `levelId: 1`

---

## 4. Benefit — `GET /v1/benefits`

```powershell
curl.exe http://localhost:8080/v1/benefits -H "Authorization: Bearer $token"
curl.exe "http://localhost:8080/v1/benefits?levelId=2" -H "Authorization: Bearer $token"
```

**Expect** — 14 benefits in total; the filtered call returns exactly 3:

```json
[{"id":3,"description":"You can use BokiAI up to 5M Tokens!","levelId":2},
 {"id":4,"description":"You can generate image using BokiAI!","levelId":2},
 {"id":5,"description":"You can talk with Boki!","levelId":2}]
```

**Check**
- [ ] 14 benefits unfiltered (2 + 3 + 4 + 5 across the 4 levels)
- [ ] `?levelId=2` returns exactly 3, all with `levelId: 2`
- [ ] `?levelId=1` returns 2, `?levelId=4` returns 5

---

## 5. Service — `GET /v1/services`

```powershell
curl.exe http://localhost:8080/v1/services -H "Authorization: Bearer $token"
```

**Expect** — 4 services:

```json
[{"id":1,"name":"BokiAI Chat","levelId":1},
 {"id":2,"name":"BokiAI Image Generation","levelId":2},
 {"id":3,"name":"BokiAI File Assistant","levelId":3},
 {"id":4,"name":"BokiAI Model Customizer","levelId":4}]
```

**Check**
- [ ] 4 services, `levelId` 1→4
- [ ] no nested `level` object
- [ ] ⚠️ **the names are placeholders I chose** — if the team has canonical BokiAI service names,
      edit them in `config/InitApp.java` and re-run. Flag it if they look wrong to you.

---

## 6. Question + Answer — `GET /v1/questions?examId=`

```powershell
curl.exe "http://localhost:8080/v1/questions?examId=1" -H "Authorization: Bearer $token"
curl.exe "http://localhost:8080/v1/questions?examId=2" -H "Authorization: Bearer $token"
curl.exe "http://localhost:8080/v1/questions?examId=99" -H "Authorization: Bearer $token"
curl.exe "http://localhost:8080/v1/questions" -H "Authorization: Bearer $token"
```

**Expect** — 5 questions per exam, each with 4 answer choices:

```json
[{"id":1,"questionText":"What is BokiAI?","examId":1,
  "answerChoices":[{"id":1,"answerText":"The real boki","isCorrect":false,"questionId":1},
                   {"id":2,"answerText":"The Agentic Artificial Inteligence","isCorrect":true,"questionId":1}, ...]},
 ...]
```

**Check**
- [ ] `answerChoices` (not `answerList` — renamed in the DTO)
- [ ] each answer's `questionId` matches the question it belongs to
- [ ] `examId=1/2/3` → 5 questions each; `examId=99` → `[]` with HTTP 200
- [ ] missing `examId` → **HTTP 400** (the param is required)

> ⚠️ **`isCorrect` is exposed on purpose** — the frontend scores the exam client-side, so the
> answer key has to reach the browser. There is a comment on the field saying so.
> This is a known limitation, not an oversight. If the exam must not leak answers, it needs a
> server-scored `POST /v1/exams/{id}/submit` — that is future work.

---

## 7. Exam — `GET /v1/exams`

```powershell
curl.exe http://localhost:8080/v1/exams -H "Authorization: Bearer $token"
curl.exe http://localhost:8080/v1/exams/1 -H "Authorization: Bearer $token"
```

**Expect** — 3 exams, with a **count** and no questions:

```json
[{"id":1,"name":"Upgrade to Level 2 examination","description":"Introduction to BokiAI.","levelId":1,"questionCount":5},
 {"id":2,"name":"Upgrade to Level 3 examination","description":"BokiAI design patterns.","levelId":2,"questionCount":5},
 {"id":3,"name":"Upgrade to Level 4 examination","description":"3 Advanced BokiAI design.","levelId":3,"questionCount":5}]
```

**Check**
- [ ] `questionCount` present and equal to 5
- [ ] **no `questions` array** — deliberate decision, so `GET /v1/exams` can never leak an answer key.
      The frontend reads `exam.questionCount` instead of `exam.questions.length`.
- [ ] `levelId` 1→3 (exam *n* belongs to level *n*; there is no exam for level 4)

---

## 8. User — `GET /v1/users`

```powershell
curl.exe http://localhost:8080/v1/users -H "Authorization: Bearer $token"
curl.exe "http://localhost:8080/v1/users?_limit=3&_page=1" -H "Authorization: Bearer $token"
curl.exe http://localhost:8080/v1/users/2 -H "Authorization: Bearer $token"
curl.exe "http://localhost:8080/v1/users?level=4" -H "Authorization: Bearer $token"
curl.exe "http://localhost:8080/v1/users?search=Boki" -H "Authorization: Bearer $token"
curl.exe "http://localhost:8080/v1/users?province=Chiang%20Mai" -H "Authorization: Bearer $token"
```

**Expect** — a user now carries `isActive` and `levelId`:

```json
{"id":2,"firstName":"Boki","lastName":"Chan","province":"Chiang Rai",
 "image":"https://avatars.githubusercontent.com/u/316038766?s=200&v=4","username":"boki",
 "isActive":true,"levelId":1,
 "roles":["ROLE_USER"],
 "badges":[{"id":1,"name":"Welcome to Boki Passport!","description":"..."}],
 "levels":[{"id":1,"name":"Level 1"}]}
```

**Check**
- [ ] `isActive` is `true`/`false` and matches the account state — `gerry` (id 3),
      `arthit` (id 7) and `supansa` (id 12) are seeded **disabled**, everyone else is active
- [ ] `levelId` is the **highest** level the user holds, not the first:
      - `boki` (id 2) → `levelId: 1`
      - `admin` (id 1, holds all 4) → `levelId: 4`
      - `pimchanok` (id 10, holds all 4) → `levelId: 4`
- [ ] `levelId` agrees with the `?level=` filter — the filter uses the same MAX rule
- [ ] `X-Total-Count` response header is present on the list endpoint (13 users)
- [ ] `roles` is an **array** (`["ROLE_USER"]`, `["ROLE_ADMIN","ROLE_USER"]` for admin)

**Also worth testing** (pre-existing, not from this branch):

```powershell
curl.exe -X PATCH http://localhost:8080/v1/users/3/deactivate -H "Authorization: Bearer $token"
curl.exe http://localhost:8080/v1/users/3 -H "Authorization: Bearer $token"   # isActive should now be false
curl.exe -X PATCH http://localhost:8080/v1/users/3/reactivate -H "Authorization: Bearer $token"
```

---

## 9. Exam history — `GET /v1/exams/users/{id}`

```powershell
curl.exe -D - http://localhost:8080/v1/exams/users/2 -H "Authorization: Bearer $token"
curl.exe "http://localhost:8080/v1/exams/users/2?_page=1&_limit=5" -H "Authorization: Bearer $token"
```

**Expect** — `examId` is now included, and `X-Total-Count` comes back in the headers:

```json
[{"id":1,"createdAt":"2026-10-22T05:59:05.527422Z","totalScore":1,"isPassed":false,"examId":1,
  "name":"Upgrade to Level 2 examination","description":"Introduction to BokiAI.","level":"Level 1"}]
```

**Check**
- [ ] `examId` present — the frontend uses it to look the exam up by id
- [ ] `X-Total-Count: 7` for user 2 (boki)
- [ ] `?_page=1&_limit=5` returns 5 rows and the header still says 7
- [ ] ⚠️ **this route shares the `/v1/exams` prefix with the exam endpoints.** Spring resolves
      `/v1/exams/users/2` to the exam-history handler (the literal `users` wins over the `{id}`
      template) — confirmed working, but if you ever add a new `/v1/exams/{something}` route,
      check this one still resolves.

---

## 10. Negative cases (please actually try these)

| Request | Expected |
|---|---|
| any endpoint **without** the `Authorization` header | **403**, empty body |
| any endpoint with a **garbage** token | **403**, empty body |
| `GET /v1/questions` (no `examId`) | **400** |
| `GET /v1/questions?examId=99` | **200**, `[]` |
| `GET /v1/exams/999` | **200**, empty body |
| `GET /v1/levels/999` | **200**, empty body |
| `GET /v1/auth/authenticate` with GET | **405** |

A missing id returning `200` with an empty body is consistent across every controller in this
branch — it is not a regression from these changes, but flag it if you want proper `404`s
(it wants one `@ControllerAdvice` for the whole app).

---

## 11. Run the automated tests

```powershell
.\mvnw.cmd test
```

**Expect** — 6 tests, 0 failures:

```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0 -- in se331.bokipassport.BokipassportApplicationTests
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0 -- in se331.bokipassport.util.UserDTOMappingTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0 -- in se331.bokipassport.util.UserExamHistoryDTOMappingTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
```

`BokipassportApplicationTests` boots the whole app against the local Postgres, so **the database
must be running** or that one test fails. The two mapping tests are plain unit tests — no DB needed.

---

## 12. The frontend contract (for the frontend teammate)

The frontend lives in a separate repo and has its own branch. These are the field names the
backend now returns:

| Endpoint | Fields |
|---|---|
| `/v1/levels` | `id, name, description, passCriteria, benefits[]` |
| `/v1/badges` | `id, name, description` |
| `/v1/courses` | `id, createdAt, name, description, videoUrl, levelId` |
| `/v1/benefits` | `id, description, levelId` |
| `/v1/services` | `id, name, levelId` |
| `/v1/questions?examId=` | `id, questionText, examId, answerChoices[{id, answerText, isCorrect, questionId}]` |
| `/v1/exams` | `id, name, description, levelId, questionCount` |
| `/v1/users` | `id, firstName, lastName, province, image, username, isActive, levelId, roles[], badges[], levels[]` |
| `/v1/exams/users/{id}` | `id, createdAt, totalScore, isPassed, examId, name, description, level` |

Two things the frontend **must** do before it can call any of this:

1. **Attach the token.** `apiClient` currently has no axios request interceptor, so it never
   sends `Authorization` and every `/v1` call will 403. Read `AUTH_TOKEN` from localStorage
   and set the header.
2. **Read `questionCount`, not `questions.length`.** `ExamDTO` deliberately does not embed
   questions. One line in `views/user/exam/ExamView.vue`.

---

## What this branch does NOT do

Worth knowing so you don't go looking for it:

- **No `POST`/`PUT`/`DELETE`** on levels, courses, exams, questions, services, benefits.
  The admin edit buttons in the frontend are still no-ops. Creating/editing content is future work.
- **No stats endpoint.** The frontend dashboard counts users client-side, so it needs either
  `X-Total-Count` (already sent) or a dedicated endpoint.
- **No level-based authorization.** Any authenticated user can read any exam's questions and
  answers, including exams above their level. The frontend blocks this in the router, but that is
  client-side only. **This is the biggest known gap in the branch** — flagged for the team.
- **No 404s.** Missing ids return 200 with an empty body.
