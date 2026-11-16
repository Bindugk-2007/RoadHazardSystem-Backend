ROAD HAZARD SYSTEM - BACKEND STARTER

Requirements:
- JDK 25 (latest LTS)
- Internet connection for the first run (to download Maven and project libraries)
- VS Code or another code editor

HOW TO OPEN:
1. Extract this ZIP file.
2. In VS Code choose File > Open Folder.
3. Select the extracted RoadHazardSystem folder.
4. Open Terminal > New Terminal.
5. Make sure the terminal is in the folder containing pom.xml and mvnw.cmd.
6. Run:
   .\mvnw.cmd spring-boot:run

The first run downloads Maven and the required Java libraries. Keep the internet connected.
When you see "Started RoadHazardApplication", the backend is running.

API ENDPOINTS:
POST   http://localhost:8080/api/hazards
GET    http://localhost:8080/api/hazards
PATCH  http://localhost:8080/api/hazards/1/status
DELETE http://localhost:8080/api/hazards/1

EXAMPLE POST JSON:
{
  "title": "Large pothole",
  "description": "Pothole on the main road",
  "location": "Bengaluru",
  "hazardType": "Pothole",
  "severity": "High"
}

EXAMPLE PATCH JSON:
{
  "status": "In Progress"
}

DATABASE:
This starter uses a local H2 database so you do not need to install MySQL yet.
H2 console: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:./data/roadhazarddb
Username: sa
Password: (leave blank)

NOTE:
Priority is currently based on the citizen-selected severity (High=100,
Medium=60, Low=30). This starter does not yet include AI image recognition,
image uploads, GPS validation, or user/admin authentication.
