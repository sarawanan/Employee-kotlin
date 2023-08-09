package com.example.empkotlin

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController(val employeeRepo: EmployeeRepo) {
    @PostMapping("/api/employee")
    @ResponseStatus(HttpStatus.CREATED)
    fun createEmployee(@RequestBody employee: Employee) = employeeRepo.save(employee)

    @GetMapping("/api/employee")
    fun getAllEmployees(): MutableList<Employee> = employeeRepo.findAll()

}