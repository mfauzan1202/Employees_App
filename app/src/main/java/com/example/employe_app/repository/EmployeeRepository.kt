package com.example.employe_app.repository

import com.example.employe_app.db.Employees
import com.example.employe_app.db.EmployeesDao
import javax.inject.Inject


class EmployeeRepository @Inject constructor(val employeeDao: EmployeesDao) {

    suspend fun insertEmployee(employees: Employees) = employeeDao.insertEmployee(employees)

    suspend fun deleteEmployee(employees: Employees) = employeeDao.deleteEmployee(employees)

    suspend fun updateEmployee(employees: Employees) = employeeDao.updateEmployee(employees)

    fun searchEmployees(searchQuery: String) = employeeDao.searchEmployees(searchQuery)

    fun getAllEmployee() = employeeDao.getAllEmployee()
}