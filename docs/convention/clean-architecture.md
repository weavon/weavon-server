# Clean Architecture

### Architecture Diagram

![clean-architecture.png](../images/clean-architecture.png)

### Context Architecture

```
└─ context
   ├─ organization
   ├─ summary
   └─ status
      ├─ application
      │  ├─ model
      │  │  ├─ command
      │  │  │  ├─ StatusSearchCommand.java
      │  │  │  └─ StatusOperateCommand.java
      │  │  └─ result
      │  │     ├─ EmployeeStatusResult.java
      │  │     ├─ DepartmentStatusResult.java
      │  │     └─ CollaboratorStatusResult.java
      │  ├─ service
      │  │  ├─ StatusService.java
      │  │  └─ StatusRestService.java implements StatusService
      │  ├─ adapter
      │  │  └─ StatusSummaryAdapter.java (external context access interface)
      │  └─ repository
      │     └─ StatusRepository.java (database access interface)
      ├─ domain
      │  ├─ model
      │  │  ├─ DailyStatus.java
      │  │  └─ PeriodStatus.java
      │  └─ service
      │     ├─ StatusGenerator.java
      │     └─ StatusCalculator.java
      ├─ infrastructure
      │  ├─ model
      │  │  └─ StatusEntity.java
      │  ├─ adapter
      │  │  └─ StatusSummaryRestAdapter.java implements StatusSummaryAdapter
      │  └─ repository
      │     └─ StatusRestRepository.java implements StatusRepository
      └─ presentattion
         ├─ model
         │  ├─ request
         │  │  ├─ SearchMonthlyStatusRequest.java
         │  │  └─ SearchDailyStatusRequest.java
         │  └─ response
         │     ├─ MonthlyStatusResponse.java
         │     └─ DailyStatusResponse.java 
         └─ controller
            ├─ EmployeeStatusController.java
            ├─ DepartmentStatusController.java
            └─ CollaboratorStatusController.java
```
