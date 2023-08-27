package com.example.empkotlin

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class EmployeeControllerTests @Autowired constructor(
    private val mvc: MockMvc
) {
    @MockBean
    private lateinit var employeeRepo: EmployeeRepo

    private val employee = Employee(id = 1, firstName = "Saro", lastName = "M", salary = 15000.00)

    @Test
    fun `create employee and test findby methods`() {
        Mockito.`when`(employeeRepo.save(any())).thenReturn(employee)
        mvc.perform(
            post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(ObjectMapper().writeValueAsString(employee))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.firstName").value(employee.firstName))
    }

    @Test
    fun `get all employees`() {
        Mockito.`when`(employeeRepo.findAll()).thenReturn(listOf(employee))
        mvc.perform(get("/api/employee"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].firstName").value(employee.firstName))
    }

    @Test
    fun `employee findById`() {
        Mockito.`when`(employeeRepo.getReferenceById(1)).thenReturn(employee)
        mvc.perform(get("/api/employee/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.firstName").value(employee.firstName))
    }

    @Test
    fun `employee findById not found`() {
        Mockito.`when`(employeeRepo.getReferenceById(2)).thenThrow(EntityNotFoundException::class.java)
        mvc.perform(get("/api/employee/2"))
            .andExpect(jsonPath("$.detail").value("Not Found"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `update employee`() {
        val updatedEmployee = employee.apply { salary = 20000.00 }
        Mockito.`when`(employeeRepo.getReferenceById(any())).thenReturn(employee)
        Mockito.`when`(employeeRepo.save(any())).thenReturn(updatedEmployee)
        mvc.perform(
            put("/api/employee/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(ObjectMapper().writeValueAsString(updatedEmployee))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.salary").value(updatedEmployee.salary))
    }

    @Test
    fun `delete employee by id`() {
        mvc.perform(delete("/api/employee/1"))
            .andExpect(status().isOk)
    }
}