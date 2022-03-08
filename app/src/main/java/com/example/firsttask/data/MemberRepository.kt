package com.example.firsttask.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MemberRepository(
    private val memberDao: MemberDao
) {
    val getAllMembers: LiveData<List<Member>> = memberDao.getAllMembers()

    suspend fun addMember(member: Member) {
        memberDao.addMember(member)
    }

    fun getUserWithEmail(email: String): LiveData<Member> {
        Log.d("MANISH", memberDao.getUserWithEmail(email).value.toString())
        return memberDao.getUserWithEmail(email)
    }

    suspend fun updateUser(member: Member) {
        memberDao.updateMember(member)
    }

    suspend fun deleteMember(member: Member){
        memberDao.deleteMember(member)
    }
}