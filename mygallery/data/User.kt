package com.example.mygallery.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    // 값을 입력하지 않을 땐 "", ?= null
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo("name") var name: String ?= null,
//    @ColumnInfo("phone") var phone: String?= null,
//    @ColumnInfo("email") var email: String?= null,
//    @ColumnInfo("age") var age: String?= null,
)