# Smart Disaster Management System (SDS) — Antigravity Master Replication Prompt

> **Shareable Antigravity Prompt Link:**  
> `https://raw.githubusercontent.com/Soulofghost/smart-disaster-management/main/PROMPT.md`
>
> **GitHub Repository:**  
> `https://github.com/Soulofghost/smart-disaster-management`

---

## 🤖 Master Prompt for Any AI Agent / Antigravity Instance

Copy and paste the prompt below into any AI coding assistant or Antigravity prompt window to replicate this project from scratch:

```markdown
SYSTEM PROMPT: You are a Senior Full-Stack Engineer, Java Spring Boot Expert, UI/UX Designer, and Security Architect.
TASK: Completely create, build, and deploy the Smart Disaster Management System (SDS) for Kerala using Java 21, Spring Boot 3.2.3, Spring Security 6, Spring Data JPA, Thymeleaf, Bootstrap 5, Leaflet GIS, and PostgreSQL (Supabase).

================================================================================
1. DATABASE & SECURITY SCHEMAS:
================================================================================
- Separate Admin accounts into `sds_admins` table:
  (id, full_name, email, password, department, phone, role='ADMIN', enabled, created_at).
- Standard users reside in `sds_users`:
  (id, full_name, email, password, phone, role ENUM['CITIZEN', 'VOLUNTEER'], enabled, created_at).
- Volunteer profile data resides in `sds_volunteers`:
  (id, user_id FK -> sds_users, skills, district, availability['AVAILABLE', 'BUSY', 'OFFLINE'], verification_status['APPROVED', 'PENDING'], has_vehicle, vehicle_type, vehicle_number).
- Disaster Incidents reside in `sds_incidents`:
  (id, user_id, title, description, latitude, longitude, disaster_type, severity, status, priority, recommended_authority).
- Relief Camps reside in `sds_relief_camps`:
  (id, name, address, capacity, available_beds, latitude, longitude, phone).
- Government Offices reside in `sds_government_offices`:
  (id, name, office_type, district, phone, address, latitude, longitude) covering all 14 districts.

- SECURITY CONFIGURATION:
  * Implement `CustomUserDetailsService` to check `sds_admins` first, then `sds_users`.
  * Implement `CustomAuthenticationSuccessHandler` routing:
    ADMIN -> `/dashboard/admin`
    VOLUNTEER -> `/dashboard/volunteer`
    CITIZEN -> `/dashboard/citizen`
  * Passwords MUST be encrypted using `BCryptPasswordEncoder`.

================================================================================
2. VOLUNTEER REGISTRATION & ATOMIC SAVING:
================================================================================
- When a user submits registration at `/register/save` with role=VOLUNTEER, `UserServiceImpl.registerUser()` MUST automatically create both the `User` record in `sds_users` AND a `Volunteer` entity record in `sds_volunteers` with status 'APPROVED' and availability 'AVAILABLE' in a single `@Transactional` method.

================================================================================
3. GEOSPATIAL & MAP ROUTES:
================================================================================
- Route `/camps/map` MUST return HTTP 200 OK rendering `camps-map.html` with interactive Leaflet markers for relief camps and bed capacity.
- Route `/alerts/map` MUST render `alerts-map.html` with live disaster alerts, weather layers, and emergency contacts directory.
- `GlobalExceptionHandler` MUST return clean 404 headers for static asset requests (`.ico`, `.css`, `.js`) without rendering HTML error pages.

================================================================================
4. DATA INITIALIZER (DATA SEEDING):
================================================================================
- Create `DataInitializer.java` running on startup to seed:
  * Admin: admin@ksdma.gov.in / admin123 (in sds_admins)
  * Volunteer: volunteer@ksdma.gov.in / volunteer123 (in sds_users + sds_volunteers)
  * Citizen: citizen@ksdma.gov.in / citizen123 (in sds_users)
  * Seed relief camps in Kochi, Thiruvananthapuram, Alappuzha, and Wayanad.
  * Seed 50+ Kerala government emergency contacts.

================================================================================
5. CLOUD DEPLOYMENT BLUEPRINTS:
================================================================================
- Create multi-stage `Dockerfile` (Maven 3.9 + Eclipse Temurin 21 JRE).
- Create `render.yaml` specifying `runtime: docker` and `SPRING_PROFILES_ACTIVE=default`.
- Create `netlify.toml` specifying publish folder `src/main/resources/static` and reverse proxy redirects.
```
