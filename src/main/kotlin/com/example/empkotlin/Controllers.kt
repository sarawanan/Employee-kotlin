package com.example.empkotlin

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class EmployeeController(private val employeeRepo: EmployeeRepo) {
    @PostMapping("/api/employee")
    @ResponseStatus(HttpStatus.CREATED)
    fun createEmployee(@RequestBody employee: Employee) = employeeRepo.save(employee)

    @GetMapping("/api/employee")
    fun getAllEmployees(): List<Employee> = employeeRepo.findAll()

    @GetMapping("/api/employee/{id}")
    fun getEmployeeById(@PathVariable id: Long): Employee =
        employeeRepo.getReferenceById(id)

    @PutMapping("/api/employee/{id}")
    fun updateEmployee(@PathVariable id: Long, @RequestBody employee: Employee): Employee =
        employeeRepo.save(employeeRepo.getReferenceById(id)
            .apply {
                firstName = employee.firstName
                lastName = employee.lastName
                salary = employee.salary
            })

    @DeleteMapping("/api/employee/{id}")
    fun deleteEmployee(@PathVariable id: Long) = employeeRepo.deleteById(id)
}