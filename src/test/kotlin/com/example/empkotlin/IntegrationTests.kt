package com.example.empkotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests @Autowired constructor(
    val restTemplate: TestRestTemplate
) {
    @Test
    fun `create employee`() {
        val employee = Employee(firstName = "Saro", lastName = "M", salary = 15000.00)
        val entity = restTemplate.postForEntity(
            "/api/employee", employee, Employee::class.java)
        assertEquals(HttpStatus.CREATED, entity.statusCode)
        assertEquals(employee.firstName, entity.body?.firstName)
        assertEquals(3, entity.body?.id)
    }

    @Test
    fun `get all employees`() {
        val entity = restTemplate.getForEntity("/api/employee", List::class.java)
        assertEquals(2, entity.body?.size)
    }
}