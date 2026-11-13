# Module 4 — Employee Management System (React + Spring Boot integration)

A React 19 + Vite employee-management frontend designed to connect to the Spring Boot REST backend. It retains the classroom module's required dashboard, employee CRUD, department management, login, role-based access, search, sorting and pagination.

## Added improvements
- Export the currently filtered employee directory to CSV (Excel-compatible).
- Refreshed blue/teal visual theme, responsive controls and updated component styling.
- Employee directory search, department-aware sorting and pagination remain available.

## Project structure
- `src/` — React pages, components, context and API client.
- `backend-integration/` — Java controller and security integration files to merge into the Spring Boot backend.

## Run frontend
Requires Node.js 20.19+ or 22.12+.

```bash
npm install
npm run dev
```

Vite runs at `http://localhost:5173`. The app calls `/api`; configure the Vite proxy in `vite.config.js` to forward `/api` to `http://localhost:8080` while developing.

## Build
```bash
npm run build
```
Copy the contents of `dist/` to the backend's `src/main/resources/static/` directory and start Spring Boot. Open `http://localhost:8080`.

## Backend integration
The Java files in `backend-integration/` are integration/replacement examples, not a standalone backend. Merge them with the matching model/service classes in your Module 3 Spring Boot project. Follow `backend-integration/README.md` and retain your existing `pom.xml` and data models.

## Demo roles
`hr / 1234`, `manager / 1234`, `employee / 1234` (as provided by the classroom backend integration).

> Educational project. Replace demo credentials and review authentication/CSRF before any public deployment.
