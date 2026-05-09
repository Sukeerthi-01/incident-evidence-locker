# SECURITY REPORT — Incident Evidence Locker

## 1. Authentication Testing
- With Basic Auth → 200 OK (allowed)
- Without Auth → 401 Unauthorized (blocked)
- Wrong password → 401 Unauthorized (blocked)

## 2. Authorization
- No role-based access (all authenticated users can access endpoints)
- Improvement: add roles (ADMIN/USER) and restrict endpoints

## 3. Input Testing
- Tried XSS payload: <script>alert(1)</script> → no execution observed
- Tried SQL injection: ' OR '1'='1 → no bypass observed
- Note: limited endpoints tested

## 4. Findings
- Default Spring Security login is used
- No RBAC (role-based access control)
- Basic validation present

## 5. Recommendations
- Implement JWT authentication
- Add RBAC with @PreAuthorize
- Validate/sanitize all inputs
- Add rate limiting (e.g., Redis or gateway)

## 6. Conclusion
Basic security controls are working (auth enforced). Further improvements needed for production readiness.





## Screenshots

### Auth Success
![Auth Success](screenshots/auth_success.png)

### Incident Success
![Incident Success](screenshots/incident_success.png)

### Unauthorized Access
![Unauthorized](screenshots/no_auth_401.png)

### Wrong Password
![Wrong Password](screenshots/wrong_password_401.png)




