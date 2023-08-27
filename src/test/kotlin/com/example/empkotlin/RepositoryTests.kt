package com.example.empkotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException

@DataJpaTest
class EmployeeRepositoryTests @Autowired constructor(
    val employeeRepo: EmployeeRepo,
    val testEntityManager: TestEntityManager
) {
    @Test
    fun `find employees`() {
        testEntityManager.persistAndFlush(Employee(firstName = "Saro", lastName = "M", salary = 15000.00))
        assertEquals("Saro", employeeRepo.findByIdOrNull(1)?.firstName)
        assertEquals("M", employeeRepo.findByIdOrNull(1)?.lastName)
        assertEquals(15000.00, employeeRepo.findByIdOrNull(1)?.salary)
        assertEquals(1, employeeRepo.findAll().size)
    }

    @Test
    fun `get employee by reference not found`() {
        assertThrows<JpaObjectRetrievalFailureException> { employeeRepo.getReferenceById(4) }
    }

    @Test
    fun `update employee`() {
        testEntityManager.persistAndFlush(Employee(firstName = "Saro", lastName = "M", salary = 15000.00))
        employeeRepo.save(employeeRepo.getReferenceById(1).apply { salary = 20000.00 })
        assertEquals(20000.00, employeeRepo.getReferenceById(1).salary)
    }
}