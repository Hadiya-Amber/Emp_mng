package com.example.empmng.employee;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * JPA entity representing an employee.
 *
 * Maps to the database table `employees` created by Flyway migration V1__create_employees.sql.
 */
@Entity
@Table(name = "employees", uniqueConstraints = @UniqueConstraint(name = "uk_employees_email", columnNames = "email"))
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_id_seq")
    @SequenceGenerator(name = "employees_id_seq", sequenceName = "employees_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "department")
    private String department;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    /**
     * No-arg constructor required by JPA.
     */
    public Employee() {
    }

    /**
     * Returns the database identifier for this employee.
     *
     * @return the id or null if the entity is not yet persisted
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the database identifier for this employee. Typically managed by JPA.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the employee's first name.
     *
     * @return first name or null
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the employee's first name.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the employee's last name.
     *
     * @return last name or null
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the employee's last name.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the employee's email address.
     *
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the employee's email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the employee's department.
     *
     * @return department or null
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the employee's department.
     *
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Returns the employee's job title.
     *
     * @return job title or null
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets the employee's job title.
     *
     * @param jobTitle the job title to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Returns the employee's hire date.
     *
     * @return hire date or null
     */
    public LocalDate getHireDate() {
        return hireDate;
    }

    /**
     * Sets the employee's hire date.
     *
     * @param hireDate the hire date to set
     */
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
