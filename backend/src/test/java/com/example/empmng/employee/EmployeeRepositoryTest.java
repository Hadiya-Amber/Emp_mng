package com.example.empmng.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void repositoryIsPresent() {
        // Basic sanity: repository bean must be present
        assertNotNull(repository, "EmployeeRepository should be autowired and not null");

        // AC-1 (observable): verify Flyway migrations created the employees table with expected columns
        // This queries H2's INFORMATION_SCHEMA; table and column names are upper-cased in H2 when unquoted.
        String tableName = "EMPLOYEES";
        String[] expectedColumns = new String[]{"ID", "FIRST_NAME", "LAST_NAME", "EMAIL", "DEPARTMENT", "JOB_TITLE", "HIRE_DATE"};

        // Ensure the table exists
        Integer tableCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?",
                Integer.class,
                tableName
        );
        assertNotNull(tableCount, "Should be able to query INFORMATION_SCHEMA.TABLES");
        assertTrue(tableCount > 0, "employees table should exist (expected Flyway migration to have created it)");

        // Ensure each expected column exists
        for (String col : expectedColumns) {
            Integer colCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = ?",
                    Integer.class,
                    tableName,
                    col
            );
            assertNotNull(colCount, "Should be able to query INFORMATION_SCHEMA.COLUMNS");
            assertTrue(colCount > 0, () -> "Expected column '" + col + "' to exist on table '" + tableName + "'");
        }
    }

    @Test
    void saveAndFindEmployee() {
        // Build an Employee with all fields set and save it
        Employee e = new Employee();
        e.setFirstName("John");
        e.setLastName("Doe");
        e.setEmail("john.doe@example.com");
        e.setDepartment("Engineering");
        e.setJobTitle("Developer");
        e.setHireDate(LocalDate.of(2020, 1, 15));

        Employee saved = repository.save(e);
        assertNotNull(saved, "Saved employee should not be null");
        assertNotNull(saved.getId(), "Saved employee should have a generated id");

        Optional<Employee> foundOpt = repository.findById(saved.getId());
        assertTrue(foundOpt.isPresent(), "Should find the saved employee by id");

        Employee found = foundOpt.get();
        assertEquals("John", found.getFirstName(), "firstName should round-trip unchanged");
        assertEquals("Doe", found.getLastName(), "lastName should round-trip unchanged");
        assertEquals("john.doe@example.com", found.getEmail(), "email should round-trip unchanged");
        assertEquals("Engineering", found.getDepartment(), "department should round-trip unchanged");
        assertEquals("Developer", found.getJobTitle(), "jobTitle should round-trip unchanged");
        assertEquals(LocalDate.of(2020, 1, 15), found.getHireDate(), "hireDate should round-trip unchanged");
    }

    @Test
    void uniqueEmailConstraint() {
        // AC-2: saving two employees with the same email should fail with a DB-level unique constraint
        String dupEmail = "duplicate@example.com";

        Employee first = new Employee();
        first.setFirstName("A");
        first.setLastName("One");
        first.setEmail(dupEmail);
        first.setDepartment("Dept");
        first.setJobTitle("Role");
        first.setHireDate(LocalDate.of(2021, 6, 1));

        // Persist the first entity and flush to ensure DB constraint state
        repository.saveAndFlush(first);

        long countBefore = repository.count();
        assertTrue(countBefore >= 1, "There should be at least one employee after saving the first record");

        Employee second = new Employee();
        second.setFirstName("B");
        second.setLastName("Two");
        second.setEmail(dupEmail); // same email
        second.setDepartment("Dept");
        second.setJobTitle("Role");
        second.setHireDate(LocalDate.of(2022, 7, 2));

        // Attempting to save a second employee with the same email should throw a DataIntegrityViolationException
        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.saveAndFlush(second);
        }, "Saving a second employee with duplicate email should raise a DataIntegrityViolationException (DB-level unique constraint)");

        // Ensure no second row was committed: count remains unchanged
        long countAfter = repository.count();
        assertEquals(countBefore, countAfter, "Failed save should not have created a new employee row");
    }
}
