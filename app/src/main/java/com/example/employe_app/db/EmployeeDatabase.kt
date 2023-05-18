package com.example.employe_app.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Employees::class],
    version = 1
)
abstract class EmployeeDatabase: RoomDatabase() {

    abstract fun getEmployeeSDao(): EmployeesDao
}