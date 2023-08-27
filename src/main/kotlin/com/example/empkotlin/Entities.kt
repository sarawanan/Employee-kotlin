package com.example.empkotlin

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Entity
class Employee(
    @GeneratedValue(strategy = GenerationType.AUTO) @Id val id: Long? = null,
    var firstName: String,
    var lastName: String,
    var salary: Double
)

@Repository
interface EmployeeRepo : JpaRepository<Employee, Long>