package com.example.firsttask.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Member::class], version = 1)
abstract class MemberDB : RoomDatabase() {

    abstract fun memberDao(): MemberDao

    companion object {
        @Volatile
        private var INSTANCE: MemberDB? = null

        fun getDatabase(context: Context): MemberDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, MemberDB::class.java, "member_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}