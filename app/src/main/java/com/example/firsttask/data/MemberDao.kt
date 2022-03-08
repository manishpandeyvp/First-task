package com.example.firsttask.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMember(member: Member)

    @Query("SELECT * FROM members_list ORDER BY id ASC")
    fun getAllMembers(): LiveData<List<Member>>

    @Update
    suspend fun updateMember(member: Member)

    @Delete
    suspend fun deleteMember(member: Member)

    @Query("SELECT * FROM members_list WHERE email = :email")
    fun getUserWithEmail(email: String): LiveData<Member>

}