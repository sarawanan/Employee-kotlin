package com.example.empkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class EmpKotlinApplication

fun main(args: Array<String>) {
    runApplication<EmpKotlinApplication>(*args)
}

@Configuration
class DbInitialize(val employeeRepo: EmployeeRepo) {
    @Bean
    fun init() {
        employeeRepo.saveAll(
            listOf(
                Employee(firstName = "Saro", lastName = "M", salary = 15000.00),
                Employee(firstName = "Kirthika", lastName = "S", salary = 10000.00)
            )
        )
    }

}