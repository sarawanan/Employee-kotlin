package com.example.empkotlin

import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class EmployeeRepositoryTests @Autowired constructor(
    val employeeRepo: EmployeeRepo,
    val testEntityManager: TestEntityManager
) {
    @Test
    fun `save and find employees`() {
        testEntityManager.persistAndFlush(Employee(firstName = "Saro", lastName = "M", salary = 15000.00))
        assertEquals("Saro", employeeRepo.findByIdOrNull(1)?.firstName)
        assertEquals("M", employeeRepo.findByIdOrNull(1)?.lastName)
        assertEquals(15000.00, employeeRepo.findByIdOrNull(1)?.salary)
        assertEquals(1, employeeRepo.findAll().size)
    }
}