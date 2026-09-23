package com.example.empmng.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Spring Data repository for Employee entities. Provides basic CRUD operations and a couple of useful query methods.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * Check existence of an employee by email.
     *
     * @param email the email to check
     * @return true if an employee with the given email exists
     */
    boolean existsByEmail(String email);

    /**
     * Find an employee by email.
     *
     * @param email the email to search for
     * @return an Optional with the Employee if found
     */
    Optional<Employee> findByEmail(String email);

    // Run saveAndFlush in a new transaction so a DB constraint failure does not taint the caller's EntityManager
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    <S extends Employee> S saveAndFlush(S entity);

    // Run count in a separate transaction to avoid automatic flushing of a possibly tainted persistence context
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    long count();
}
