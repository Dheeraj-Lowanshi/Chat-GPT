# Votezy Lite – Online Election Management System

Full-stack production-style monorepo with:
- `backend/`: Spring Boot REST API (no Spring Security, manual role logic).
- `frontend/`: React + Vite + Tailwind application.

## 1) Backend Project Structure

```text
backend/
  src/main/java/com/votezylite/
    config/
    controller/
    dto/
    entity/
    enums/
    exception/
    repository/
    service/
      impl/
    util/
  src/main/resources/
    application.yml
    schema.sql
    data.sql
```

## 2) Frontend Project Structure

```text
frontend/
  src/
    api/
    components/
    context/
    pages/
    routes/
    main.jsx
    App.jsx
```

## 3) Core API Endpoints

- Auth: `POST /api/auth/login`
- Admin Dashboard: `GET /api/admin/dashboard/{electionId}`
- Elections:
  - `POST /api/elections`
  - `GET /api/elections`
  - `GET /api/elections/active`
  - `PATCH /api/elections/{id}/close`
  - `GET /api/elections/{id}/results`
  - `GET /api/elections/{id}/results/export`
- Candidates:
  - `POST /api/candidates` (multipart)
  - `PUT /api/candidates/{id}` (multipart)
  - `PATCH /api/candidates/{id}/approval?status=APPROVED|REJECTED`
  - `GET /api/candidates/election/{electionId}?search=`
- Voters:
  - `POST /api/voters`
  - `GET /api/voters?search=&page=0&size=10`
- Votes:
  - `POST /api/votes`

## 4) Run Backend

1. Create MySQL DB credentials if different from default in `application.yml`.
2. Run:
   ```bash
   cd backend
   mvn spring-boot:run
   ```
3. Default server: `http://localhost:8080`

Default admin:
- Email: `admin@votezy.com`
- Password: `admin123`

## 5) Run Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on Vite default `http://localhost:5173`.

Optionally set API URL:
```bash
VITE_API_URL=http://localhost:8080/api
```

## 6) Production Readiness Notes

- Layered architecture + DTO pattern + ModelMapper.
- Validation with Bean Validation (`@NotNull`, `@Email`, `@Size`).
- Global exception handling with `@RestControllerAdvice`.
- Image storage via local uploads folder and persisted file path.
- Manual role-based auth through login role checks.
- Vote lock: unique voter-election constraint and transactional checks.
- Election status auto-updated by date/time when listing elections.
- Candidate search/filter, voter pagination, CSV export, and real-time result polling (frontend every 5 seconds).

