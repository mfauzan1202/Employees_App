package com.example.employe_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employe_app.db.Employees
import com.example.employe_app.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val employeeRepository: EmployeeRepository
): ViewModel() {

    fun insertEmployee(employee: Employees) = viewModelScope.launch {
        employeeRepository.insertEmployee(employee)
    }

    fun deleteEmployee(employee: Employees) = viewModelScope.launch {
        employeeRepository.deleteEmployee(employee)
    }

    fun updateEmployee(employee: Employees) = viewModelScope.launch {
        employeeRepository.updateEmployee(employee)
    }

    fun searchEmployees(searchQuery: String) = employeeRepository.searchEmployees(searchQuery)

    val allEmployee = employeeRepository.getAllEmployee()
}