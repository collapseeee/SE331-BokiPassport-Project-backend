# TODO List - CA?T Passport (Project 2)

## Backend (Spring Boot)

### 1. Entity Design & JPA (Criteria 30)
- [ ] User entity (id, name, surname, email, password, profileImageUrl, level, province, role, isActive)
- [ ] Level entity (id, levelNumber, name, passCriteria)
- [ ] Benefit entity (id, name, description, level)
- [ ] Service entity (id, name, description, level)
- [ ] Question entity (id, text, choices, correctAnswer, level)
- [ ] ELearning entity (id, title, videoUrl, level)
- [ ] ExamHistory entity (id, user, level, score, result, attemptDate)
- [ ] Badge entity (id, name, description, imageUrl, level)
- [ ] Map relationships properly (User-Level, Level-Benefit, Level-Service, Level-Question, User-ExamHistory)
- [ ] DTOs + Mapstruct mappers for all entities
- [ ] JPA query methods for search and filter
- [ ] Pagination with Pageable

### 2. Mock Data (Criteria 1)
- [ ] DataInitializer on startup
- [ ] 4 levels with benefits and services
- [ ] Multiple users (enough for pagination testing)
- [ ] Questions per level (multiple choice with choices)
- [ ] ELearning content per level
- [ ] Exam history for users
- [ ] Badges per level

### 3. Auth & Security (Criteria 5)
- [x] POST /api/v1/auth/register
- [x] POST /api/v1/auth/authenticate
- [x] POST /api/v1/auth/refresh-token
- [ ] JWT interceptor (frontend axios)
- [ ] Spring Security roles (ADMIN, USER)
- [ ] Deactivated user cannot login
- [ ] Route guards (frontend)

### 4. Admin APIs (Criteria 7-18)
- [ ] GET /api/v1/admin/users (paginated, filterable by name/surname/level/province)
- [ ] GET /api/v1/admin/users/{id} (detail with exam history)
- [ ] PUT /api/v1/admin/users/{id} (edit user)
- [ ] PATCH /api/v1/admin/users/{id}/deactivate (deactivate/reactivate)
- [ ] GET /api/v1/admin/levels (list with benefits and services)
- [ ] GET /api/v1/admin/levels/{id} (detail)
- [ ] PUT /api/v1/admin/levels/{id} (edit benefits, services, pass criteria)
- [ ] POST /api/v1/admin/levels/{id}/benefits (add benefit)
- [ ] POST /api/v1/admin/levels/{id}/services (add service)
- [ ] POST /api/v1/admin/questions (create question)
- [ ] PUT /api/v1/admin/questions/{id} (edit question)
- [ ] DELETE /api/v1/admin/questions/{id} (remove question)
- [ ] GET /api/v1/admin/questions?levelId={id} (list by level)

### 5. User APIs (Criteria 19-26)
- [ ] GET /api/v1/users/me (current user profile with level)
- [ ] PUT /api/v1/users/me (edit own details)
- [ ] GET /api/v1/users/me/exam-history (paginated)
- [ ] GET /api/v1/users/me/badges
- [ ] GET /api/v1/users/me/level/benefits
- [ ] GET /api/v1/users/me/level/services
- [ ] GET /api/v1/users/me/elearning (content matching level)
- [ ] GET /api/v1/users/me/exam (questions for current level)
- [ ] POST /api/v1/users/me/exam/submit (submit answers, get result, upgrade if pass)
- [ ] POST /api/v1/users/me/profile-image (upload to S3)

### 6. S3 Integration (Criteria 3)
- [ ] AWS S3 config for profile image upload
- [ ] Presigned URL or direct upload

### 7. Backend Structure (Criteria 29, 35)
- [ ] Controller / Service / DAO layers separated
- [ ] DTOs used, entities not returned directly
- [ ] Proper HTTP methods and status codes
- [ ] CORS configured
- [ ] Input validation

## Frontend (Vue 3)

### 8. Replace json-server with Real API
- [ ] Update apiClient.ts baseURL to backend URL
- [ ] Update all service files to match new API
- [ ] Add JWT token interceptor

### 9. Auth Pages (Criteria 3-6)
- [ ] Register page with profile image upload
- [ ] Login page
- [ ] Route guards (admin vs user)
- [ ] Logout

### 10. Admin Pages (Criteria 7-18)
- [ ] User list with pagination (JPA Pageable)
- [ ] User filter (name, surname, level, province) + combined filters
- [ ] User detail page with sub-routes (info, level+exam history, edit)
- [ ] Deactivate/reactivate user
- [ ] Level list + detail page
- [ ] Level edit (benefits, services, pass criteria)
- [ ] Question CRUD (create, edit, remove)

### 11. User Pages (Criteria 19-26)
- [ ] Level + benefits shown on first login
- [ ] Profile area (image, name, level)
- [ ] Profile sub-pages (details, exam history, badges, services)
- [ ] Edit own details (reusable components from admin)
- [ ] eLearning page (video matching level)
- [ ] Exam page (multiple choice, matching level)
- [ ] Exam submit + result display
- [ ] Level upgrade on pass (immediate, no re-login)

### 12. Frontend Techniques (Criteria 31-32)
- [ ] Reusable components with props/emit
- [ ] Pinia for shared state (user, token)
- [ ] Named routes, nested routes, 404 route
- [ ] Tailwind CSS for responsive design
- [ ] VeeValidate for form validation (Criteria 27-28)

## Deployment (Criteria 33)
- [ ] Dockerfile for backend
- [ ] Dockerfile for frontend
- [ ] docker-compose.yml
- [ ] Deploy to VM/cloud
- [ ] Automated CD (GitHub Actions)

## Documentation
- [ ] README.md (team info, repo URLs, deployment URLs)

## Total: ~70+ tasks across backend, frontend, and deployment
