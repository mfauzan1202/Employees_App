package com.example.employe_app.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "employees_table")
@Parcelize
data class Employees(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    var name: String,

    var birthDate: String,

    var address: String,

    var email: String,

    var gender: String,

    var phoneNumber: String

): Parcelable
