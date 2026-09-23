Implementation plan for task_002_be_employee_entity

- Add Flyway migration V1__create_employees.sql creating employees table with email unique index
- Add Employee JPA entity mapping to employees table
- Add EmployeeRepository extending JpaRepository with existsByEmail and findByEmail
- Enable Flyway and set hibernate ddl-auto to validate in application.yml
- Tests rely on @DataJpaTest to run migrations against H2; existing tests in backend/src/test/java/.../EmployeeRepositoryTest.java will verify AC-1/AC-2/AC-3
