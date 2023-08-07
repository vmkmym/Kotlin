package com.example.roomsecond.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    // 값을 입력하지 않을 땐 "", ?= null
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo("name") val name: String ?= null,
    @ColumnInfo("phone") val phone: String?= null,
    @ColumnInfo("email") val email: String?= null,
    @ColumnInfo("age") val age: String?= null,
    )

// Primarykey는 필수