package com.example.firsttask.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMember(member: Member)

    @Query("SELECT * FROM members_list ORDER BY id ASC")
    fun getAllMembers() : LiveData<List<Member>>
}