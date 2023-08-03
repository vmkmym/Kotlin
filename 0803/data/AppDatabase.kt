package com.example.roominfo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// 어노테이션을 넣어놓으면 어노테이션이 알아서 코드를 자동 생성해준다.
@Database(entities = [User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    // 대문자로 바꾸기 그리고 실행 해보기 (에러 없는지)
    abstract fun userDao(): UserDAO
}
// 우리는 버전이 1에서 2로 갔기 때문에 값도, 인자도 바꾸기 (추가한 항목 중괄호 안에 적기)
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE User ADD COLUMN phone TEXT")
        database.execSQL("ALTER TABLE User ADD COLUMN email TEXT")
//        database.execSQL("ALTER TABLE User ADD COLUMN age INTEGER")
    }
}