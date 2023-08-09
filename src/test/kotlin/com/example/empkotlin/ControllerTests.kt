package com.example.empkotlin

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class EmployeeControllerTests @Autowired constructor(
    val mvc: MockMvc
) {
    @MockBean
    private lateinit var employeeRepo: EmployeeRepo

    @Test
    fun `create employee`() {
        val employee = Employee(id = 1, firstName = "Saro", lastName = "M", salary = 15000.00)
        Mockito.`when`(employeeRepo.save(any())).thenReturn(employee)
        mvc.perform(post("/api/employee")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(ObjectMapper().writeValueAsString(employee)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.firstName").value(employee.firstName))
    }

    @Test
    fun `get all employees`() {
        val employee = Employee(id = 1, firstName = "Saro", lastName = "M", salary = 15000.00)
        Mockito.`when`(employeeRepo.findAll()).thenReturn(listOf(employee))
        mvc.perform(get("/api/employee"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].firstName").value(employee.firstName))
    }
}