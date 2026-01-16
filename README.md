# HealthAera Test Automation Project

## Overview

This repository contains automated test scripts for the HealthAera application using Java, Selenium WebDriver, and TestNG framework. The automation suite covers various modules including chatbot functionality, health information management, planner features, and document upload workflows.

## Project Structure

```
Task/
├── src/
│   ├── main/java/
│   │   ├── pages/          # Page Object Model classes
│   │   └── utils/          # Utility classes (BaseTest, YopmailService)
│   └── test/java/
│       └── tests/          # TestNG test classes
├── pom.xml                 # Maven dependencies
├── testng.xml             # TestNG configuration
└── README.md              # This file
```

## Technologies Used

- **Java** - Programming language
- **Selenium WebDriver** - Browser automation
- **TestNG** - Testing framework
- **Maven** - Build and dependency management
- **WebDriverManager** - Automatic driver management
- **Page Object Model** - Design pattern for maintainable tests

## Test Environment

- **QA Environment URL**: `https://qa-frontend.sierradimensions.com/onboarding?path=signin`
- **Browser**: Chrome (auto-managed by WebDriverManager)

## How to Run Tests

### Prerequisites
- Java 8 or higher installed
- Maven installed
- Chrome browser installed

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=AddSubtaskFlowTest
mvn test -Dtest=AddPlanFlowTest
mvn test -Dtest=LoginAndTermsVerificationTest
```

### Run via TestNG XML
```bash
mvn test -DsuiteXmlFile=testng.xml
```

## Available Test Flows

| Test Class | Description |
|------------|-------------|
| `AddSubtaskFlowTest` | Tests adding tasks/subtasks to existing plans |
| `AddPlanFlowTest` | Tests creating new plans in My Planner |
| `LoginAndTermsVerificationTest` | Tests login flow and terms verification |
| `DocumentUploadFlowTest` | Tests PHI document upload process |
| `MyHealthInformationFlowTest` | Tests My Health Bio and History features |
| `ExploringSoloFlowTest` | Tests exploring solo navigation flow |
| `ViewBlogFlowTest` | Tests viewing blogs from Health Menu |
| `CreateArticleFlowTest` | Tests creating folders and articles in My Library |
| `AddTaskToPlanFlowTest` | Tests adding tasks directly from Plans Listing |
| `LogoutFlowTest` | Tests logout functionality |

## Page Objects

- `LoginPage` - Login page interactions
- `OTPVerificationPage` - OTP verification handling
- `WelcomePage` - Welcome page (Show me around / Exploring solo)
- `HealthMenuPage` - Main health menu navigation
- `PlannerPage` - My Planner page
- `PlansListPage` - Plans listing and management
- `PlanDetailPage` - Individual plan details
- `AddPlanPage` - Plan creation form
- `AddTaskPage` - Task creation form
- `MyHealthInformationPage` - Health information management
- `DocumentProcessingModal` - Document upload modal
- `BlogsPage` - Blogs listing interactions
- `BlogDetailPage` - Individual blog view interactions
- `MyLibraryPage` - My Library folder management
- `AddFolderModal` - Folder creation modal
- `AddArticlePage` - Article creation form
- And more...

---

## Known Bugs & Test Coverage

Below is the list of identified bugs and their test coverage status:

| Bug ID | Title | Module | Severity | Priority | Status | Test Coverage |
|--------|-------|--------|----------|----------|--------|---------------|
| BUG-01 | Chatbot Terms and Condition verification | Chatbot | Critical | P0 | Open | `LoginAndTermsVerificationTest` |
| BUG-02 | My Health Bio and My Health History is displayed | My Health Information | Critical | P0 | Open | `MyHealthInformationFlowTest` |
| BUG-03 | Importance notice appears during PHI upload | My Health Information >> PHI Upload | High | P1 | Open | `DocumentUploadFlowTest` |
| BUG-04 | User redirects to the Navigation Page | Welcome Hailey | Critical | P0 | Open | `ExploringSoloFlowTest` |
| BUG-05 | Verify plan creation | My Planner | Critical | P1 | Open | `AddPlanFlowTest` |
| BUG-06 | Verify task creation | My Planner | Critical | P2 | Open | `AddSubtaskFlowTest` |
| BUG-07 | Verify blog viewing | Health Menu >> Blogs | Medium | P2 | Open | `ViewBlogFlowTest` |
| BUG-08 | Verify article creation | My Library | High | P1 | Open | `CreateArticleFlowTest` |
| BUG-09 | Verify task creation from list | My Planner >> Plans List | High | P2 | Open | `AddTaskToPlanFlowTest` |
| BUG-10 | Verify logout functionality | Health Menu | Critical | P1 | Open | `LogoutFlowTest` |

### Detailed Bug Information

#### BUG-01: Chatbot Terms and Condition verification
- **Module**: Chatbot
- **Severity**: Critical
- **Priority**: P0
- **Environment**: QA
- **Preconditions**: User must be logged in and on Welcome Hailey Page
- **Steps to Reproduce**:
  1. Click on the "Show me around Hailey!" option
- **Expected Result**: The chatbot should open with a Terms and Conditions modal (if not accepted)
- **Actual Result**: _[To be updated]_

---

#### BUG-02: My Health Bio and My Health History is displayed
- **Module**: My Health Information
- **Severity**: Critical
- **Priority**: P0
- **Environment**: QA
- **Preconditions**: User have already uploaded at least 1 of their medical records on the platform
- **Steps to Reproduce**:
  1. Navigation menu → My Health Information
  2. View My Health Bio and View My Health History
- **Test Data**: Health Record of the User
- **Expected Result**: My Health Bio and My Health History should be displayed correctly
- **Actual Result**: _[To be updated]_

---

#### BUG-03: Importance notice appears to the user during PHI upload
- **Module**: My Health Information >> PHI Upload
- **Severity**: High
- **Priority**: P1
- **Environment**: QA
- **Preconditions**: User is logged in and Condition already added to the My Health Information
- **Steps to Reproduce**:
  1. Navigation menu → My Health Information
  2. Open condition → Records
  3. Add → Choose Record Type
  4. Choose upload method → Browse
  5. Select Record → Confirm & Continue
  6. Observe the "Almost Ready..." modal page
- **Test Data**: Health Record of the User
- **Expected Result**: The Important Notice message should be displayed to the user
- **Actual Result**: _[To be updated]_

---

#### BUG-04: User redirects to the Navigation Page
- **Module**: Welcome Hailey
- **Severity**: Critical
- **Priority**: P0
- **Environment**: QA
- **Preconditions**: User is already logged in and on Welcome Hailey page
- **Steps to Reproduce**:
  1. Click on "I've got this, exploring solo" option
- **Expected Result**: The Navigation page opens up successfully
- **Actual Result**: _[To be updated]_

---

#### BUG-05: Verify plan creation
- **Module**: My Planner
- **Severity**: Critical
- **Priority**: P1
- **Environment**: QA
- **Preconditions**: User is already logged in and on Welcome Hailey page
- **Steps to Reproduce**:
  1. Navigation menu → My planner
  2. Build a plan → Fill form
  3. Submit
- **Expected Result**: Plan creation should be done successfully
- **Actual Result**: _[To be updated]_
- **Test Coverage**: ✅ Automated in `AddPlanFlowTest`

---

#### BUG-06: Verify task creation
- **Module**: My Planner
- **Severity**: Critical
- **Priority**: P2
- **Environment**: QA
- **Preconditions**: User have an already created plan and is on plans listing page
- **Steps to Reproduce**:
  1. Plans → Add task
  2. Fill form → Submit
- **Expected Result**: Task should be added successfully to the plan
- **Actual Result**: _[To be updated]_
- **Test Coverage**: ✅ Automated in `AddSubtaskFlowTest`

---

#### BUG-07: Verify blog viewing
- **Module**: Health Menu >> Blogs
- **Severity**: Medium
- **Priority**: P2
- **Environment**: QA
- **Preconditions**: User is logged in
- **Steps to Reproduce**:
  1. Health Menu → Blogs and Articles
  2. Select a blog
- **Expected Result**: Blog details page should display correctly
- **Actual Result**: _[To be updated]_
- **Test Coverage**: ✅ Automated in `ViewBlogFlowTest`

---

#### BUG-08: Verify article creation
- **Module**: My Library
- **Severity**: High
- **Priority**: P1
- **Environment**: QA
- **Preconditions**: User is logged in
- **Steps to Reproduce**:
  1. Blogs and Articles → My Library tab
  2. Create Folder → Open Folder
  3. Create Manual Article
- **Expected Result**: Article should be created and visible in the folder
- **Actual Result**: _[To be updated]_
- **Test Coverage**: ✅ Automated in `CreateArticleFlowTest`

---

#### BUG-09: Verify task creation from list
- **Module**: My Planner >> Plans List
- **Severity**: High
- **Priority**: P2
- **Environment**: QA
- **Preconditions**: User is logged in and on Plans Listing
- **Steps to Reproduce**:
  1. Click (+) icon on Plans Listing
  2. Fill task details → Save
- **Expected Result**: Task should be added successfully
- **Actual Result**: _[To be updated]_
- **Test Coverage**: ✅ Automated in `AddTaskToPlanFlowTest`

---

#### BUG-10: Verify logout functionality
- **Module**: Health Menu
- **Severity**: Critical
- **Priority**: P1
- **Environment**: QA
- **Preconditions**: User is logged in
- **Steps to Reproduce**:
  1. Health Menu → Hamburger Menu
  2. Logout → Confirm
- **Expected Result**: User should be redirected to Sign In page
- **Actual Result**: _[To be updated]_
- **Test Coverage**: ✅ Automated in `LogoutFlowTest`

---

## Bug Severity & Priority Legend

### Severity Levels
- **Critical**: System crash, data loss, or major functionality broken
- **High**: Major functionality affected, but workaround exists
- **Medium**: Minor functionality affected
- **Low**: Cosmetic issues, minor inconveniences

### Priority Levels
- **P0**: Must fix immediately - blocks testing/release
- **P1**: Fix before release
- **P2**: Fix if time permits before release
- **P3**: Fix in future release

---

## Contributing

When adding new test cases:
1. Follow the Page Object Model pattern
2. Extend `BaseTest` for setup/teardown
3. Use meaningful test names and descriptions
4. Add appropriate assertions
5. Include logging for debugging
6. Update this README with test coverage information

## Test Reporting

Test results are generated in:
- `target/surefire-reports/` - Maven Surefire reports
- `test-output/` - TestNG HTML reports

## Contact

For questions or issues, please contact the QA team.

---

**Last Updated**: January 2026  
**Maintained By**: QA Automation Team
