# CabProject - Proper Package Structure Guide

## Current Issues with Package Structure:
1. Mixed naming conventions (CamelCase in packages)
2. Services and repositories inside controller package
3. Non-standard package names

## Recommended Package Structure:

```
com.cts.cabproject/
├── Application.java                              # Main application class
├── controller/                                   # REST Controllers
│   ├── AuthController.java                      # Authentication endpoints
│   ├── DriverController.java                    # Driver CRUD operations
│   └── DriverReviewController.java              # Driver review operations
├── service/                                     # Business Logic Layer
│   ├── DriverService.java                      # Interface
│   ├── DriverServiceImpl.java                  # Implementation
│   ├── DriverReviewService.java                # Interface
│   └── DriverReviewServiceImpl.java            # Implementation  
├── repository/                                  # Data Access Layer
│   ├── DriverRepository.java                   # Driver data access
│   └── DriverReviewRepository.java             # Review data access
├── entity/                                      # JPA Entities
│   ├── Driver.java                             # Driver entity (renamed from DriverInfo)
│   └── DriverReview.java                       # Review entity
├── config/                                      # Configuration classes
│   └── SecurityConfig.java                     # Security configuration
├── security/                                    # Security related classes
│   ├── JwtUtil.java                            # JWT utility
│   └── JwtFilter.java                          # JWT filter
└── dto/                                         # Data Transfer Objects (optional)
    ├── DriverDto.java
    └── LoginRequest.java
```

## Java Naming Conventions Applied:

### 1. Package Names:
- **Before**: `com.cts.CabProject.CabController.Service1`
- **After**: `com.cts.cabproject.service`
- **Rule**: All lowercase, no camelCase

### 2. Class Names:
- **Before**: `DriverInfo`, `CabSevice`, `Controls`
- **After**: `Driver`, `DriverService`, `DriverController`
- **Rule**: PascalCase, descriptive names

### 3. Interface Names:
- **Before**: `CabSevice` (typo + unclear)
- **After**: `DriverService`
- **Rule**: Clear, descriptive interface names

### 4. Method Names:
- **Before**: Mixed conventions
- **After**: camelCase, verb-based names

## Migration Steps:

### Step 1: Create New Package Structure
```bash
mkdir -p src/main/java/com/cts/cabproject/{controller,service,repository,entity,config,security,dto}
```

### Step 2: Move and Rename Files
1. Move `CabProjectApplication.java` → `Application.java`
2. Move `CabEntity/DriverInfo.java` → `entity/Driver.java`
3. Move `CabEntity/DriverReview.java` → `entity/DriverReview.java`
4. Move `CabController/Controls.java` → `controller/DriverController.java`
5. Move `CabController/Repository1/AuthController.java` → `controller/AuthController.java`
6. Move `CabController/DriverReviewController.java` → `controller/DriverReviewController.java`
7. Move `CabController/Service1/CabSevice.java` → `service/DriverService.java`
8. Move `CabController/Service1/CabServiceImpl.java` → `service/DriverServiceImpl.java`
9. Move `CabController/Repository1/CabRepo.java` → `repository/DriverRepository.java`
10. Move `Config/SecurityConfig.java` → `config/SecurityConfig.java`
11. Move `JwtUtil.java` → `security/JwtUtil.java`
12. Move `filter/JwtFilter.java` → `security/JwtFilter.java`

### Step 3: Update Package Declarations
Update all package declarations to use the new structure:
```java
// Old
package com.cts.CabProject.CabController.Service1;
// New  
package com.cts.cabproject.service;
```

### Step 4: Update Import Statements
Update all import statements throughout the project:
```java
// Old
import com.cts.CabProject.CabEntity.DriverInfo;
// New
import com.cts.cabproject.entity.Driver;
```

### Step 5: Update Class Names
- `DriverInfo` → `Driver`
- `CabSevice` → `DriverService` (fix typo)
- `CabServiceImpl` → `DriverServiceImpl`
- `Controls` → `DriverController`
- `CabRepo` → `DriverRepository`

### Step 6: Update References
Update all class references, field names, and method parameters.

## Benefits of This Structure:

1. **Industry Standard**: Follows Java and Spring Boot conventions
2. **Clear Separation**: Each layer has its own package
3. **Maintainable**: Easy to find and modify code
4. **Scalable**: Easy to add new features
5. **Professional**: Looks professional for presentations

## Current Working Endpoints (After Restructure):

### Public Endpoints:
- `POST /auth/login` - Login with JWT
- `POST /register` - Driver registration

### Protected Endpoints (JWT Required):
- `GET /drivers` - Get all drivers
- `GET /drivers/{id}` - Get driver by ID
- `PUT /drivers/{id}` - Update driver
- `DELETE /drivers/{id}` - Delete driver
- `PATCH /drivers/{id}/availability` - Toggle availability

## Next Steps:
1. Create the new structure gradually
2. Test each module after moving
3. Update unit tests
4. Update documentation
5. Clean up old structure
