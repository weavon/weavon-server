# Clean Architecture

### Package Structure of Clean Architecture

```
├─ config
├─ exception
├─ helper
├─ util
├─ validation
└─ core
   ├─ shared
   ├─ user
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
      │  │  └─ StatusRestService.java
      │  ├─ repository
      │  │  └─ StatusRepository.java
      │  ├─ adapter
      │  │  └─ StatusEmployeeAdapter.java
      │  └─ client
      │     └─ EmployeeClient.java 
      ├─ domain
      │  ├─ model
      │  │  ├─ DailyStatus.java
      │  │  └─ MonthlyStatus.java
      │  └─ service
      │     ├─ StatusGenerator.java
      │     └─ StatusCalculator.java
      ├─ infrastructure
      │  ├─ model
      │  │  └─ StatusEntity.java
      │  ├─ repository
      │  │  └─ StatusRestRepository.java
      │  ├─ adapter
      │  │  └─ StatusEmployeeAdapter.java
      │  └─ client
      │     └─ EmployeeClient.java
      └─ presentation
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



### Diagram of Clean Architecture

![architecture.png](../images/architecture.png)
