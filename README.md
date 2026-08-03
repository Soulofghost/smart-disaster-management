# Smart Disaster Management System (SDMS) - Kerala

![Kerala SDMS](https://img.shields.io/badge/Status-Production_Ready-success?style=for-the-badge) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.3-green?style=for-the-badge&logo=springboot) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)

A real-time, unified response platform connecting citizens, registered rescue volunteers, and district disaster management authorities across Kerala. Built with Spring Boot, Thymeleaf, and PostgreSQL (Supabase).

## Features & Modules

1. **User & Authentication Module**
   - Role-based access control (`ADMIN`, `CITIZEN`, `VOLUNTEER`) using Spring Security.
   - Secure login & registration with BCrypt password hashing.
   
2. **Citizen Dashboard & Disaster Reporting**
   - Submit geotagged disaster reports (Flood, Landslide, Fire, etc.).
   - Track report status and view historical incidents.
   - One-tap **Emergency SOS** button to instantly alert authorities with GPS coordinates.

3. **Volunteer & Resource Management**
   - Volunteer registration and verification workflow.
   - Volunteers can update availability and accept assigned rescue missions.

4. **GIS & Emergency Services (Live Map)**
   - Interactive Leaflet map displaying active Relief Camps, capacity, and available resources.
   - Directory of nearby Government Offices (Police Stations, Hospitals, Fire & Rescue).

5. **AI Priority Engine & Analytics (Admin Dashboard)**
   - **AI Priority Prototype:** A rule-based engine that automatically analyzes incoming disaster reports to assign priority (LOW/MEDIUM/HIGH/CRITICAL) and recommend responding authorities.
   - **Visual Analytics:** Chart.js integration for real-time graphs of incident severities and types.
   - **Broadcasts:** System-wide broadcast alerts sent from the Admin command centre.
   - **Exports:** 1-click CSV data export for offline reporting.

---

## Quick Start
```bash
mvn clean install
mvn spring-boot:run
```
Open http://localhost:8080
