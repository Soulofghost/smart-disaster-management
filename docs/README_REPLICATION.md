# Smart Disaster Management System (SDS) — Antigravity Agent Replication Guide

> **Shareable Link for Antigravity AI Agents**:  
> `https://github.com/Soulofghost/smart-disaster-management.git`

This document contains everything an Antigravity AI agent (or human developer) needs to load, run, or replicate this exact project 1:1 in any environment.

---

## ⚡ 1-Line Agent Import Command
To load and run this project in any Antigravity AI session, give the agent this exact instruction:

```markdown
Clone and initialize the Smart Disaster Management System from https://github.com/Soulofghost/smart-disaster-management.git, run tests (`mvn test`), and start the server connected to Supabase PostgreSQL database.
```

---

## 🏗️ Architecture & Core Components

1. **Spring Boot 3.2.3 Web Application**:
   - Controller Layer: `AuthController`, `CitizenController`, `VolunteerController`, `AdminController`, `ReliefCampController`, `IncidentController`, `GovernmentOfficeController`.
   - Security Layer: `SecurityConfig` (Spring Security 6), `CustomUserDetailsService`, `CustomAuthenticationSuccessHandler`.
   - Persistence Layer: `sds_users`, `sds_volunteers`, `sds_admins`, `sds_incidents`, `sds_relief_camps`, `sds_government_offices`.

2. **Supabase PostgreSQL Cloud Database**:
   - Connection URL: `jdbc:postgresql://db.rnpqtflhbxuuzxfxwqni.supabase.co:5432/postgres?sslmode=require`
   - Data Seeding: Auto-initialized on startup via `DataInitializer.java`.

3. **Deployments**:
   - 1-Click Render Cloud: `https://render.com/deploy?repo=https://github.com/Soulofghost/smart-disaster-management`
   - Netlify Edge CDN: `netlify.toml` pre-configured for static publish & reverse proxy.

---

## 🔑 Pre-Configured Accounts
- **State Control Admin**: `admin@ksdma.gov.in` / `admin123`
- **Rescue Volunteer**: `volunteer@ksdma.gov.in` / `volunteer123`
- **Citizen**: `citizen@ksdma.gov.in` / `citizen123`
