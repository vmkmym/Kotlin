package com.example.roomsecond.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null // 캐시가 아니라 메모리를 바로 점유 (비효율적이지만 어차피 점유할거라)
        // Volatile과 synchronized의 동작원리 검색해보셈!!!
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // 멀티쓰레드를 위한 synchronized
                val db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "my.db"
                ).build()
                db
            }
        }
    }
}
// 우리는 버전이 1에서 2로 갔기 때문에 값도, 인자도 바꾸기 (추가한 항목 중괄호 안에 적기)
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE User ADD COLUMN phone TEXT")
        database.execSQL("ALTER TABLE User ADD COLUMN email TEXT")
        database.execSQL("ALTER TABLE User ADD COLUMN age TEXT")
    }
}