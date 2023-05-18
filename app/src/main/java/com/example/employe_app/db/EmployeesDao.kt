package com.example.employe_app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EmployeesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employees: Employees)

    @Delete
    suspend fun deleteEmployee(employees: Employees)

    @Update
    suspend fun updateEmployee(employees: Employees)

    @Query("SELECT * FROM employees_table WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchEmployees(searchQuery: String): LiveData<List<Employees>>

    @Query("SELECT * from employees_table ORDER BY name ASC")
    fun getAllEmployee(): LiveData<List<Employees>>
}