package com.example.empkotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests @Autowired constructor(
    private val restTemplate: TestRestTemplate
) {
    private val employee = Employee(firstName = "Saro", lastName = "M", salary = 15000.00)

    @Test
    fun `create employee`() {
        val entity = restTemplate.postForEntity(
            "/api/employee", employee, Employee::class.java
        )
        assertEquals(HttpStatus.CREATED, entity.statusCode)
        assertEquals(employee.firstName, entity.body?.firstName)
        assertEquals(3, entity.body?.id)
    }

    @Test
    fun `get all employees`() {
        val entity = restTemplate.getForEntity("/api/employee", List::class.java)
        assertEquals(2, entity.body?.size)
    }

    @Test
    fun `get employee by id`() {
        val entity = restTemplate.getForEntity("/api/employee/1", Employee::class.java)
        assertEquals(HttpStatus.OK, entity.statusCode)
        assertEquals("M", entity.body?.lastName)
    }

    @Test
    fun `update employee`() {
        val updatedEmp = employee.apply { salary = 20000.00 }
        restTemplate.put("/api/employee/1", updatedEmp)
        val entity = restTemplate.getForEntity("/api/employee/1", Employee::class.java)
        assertEquals(HttpStatus.OK, entity.statusCode)
        assertEquals(updatedEmp.salary, entity.body?.salary)
    }

    @Test
    fun `delete employee`() {
        restTemplate.delete("/api/employee/1")
        val entity = restTemplate.getForEntity("/api/employee/1", String::class.java)
        assertEquals(HttpStatus.NOT_FOUND, entity.statusCode)
    }
}