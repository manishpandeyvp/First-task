package com.example.firsttask.data

import androidx.lifecycle.LiveData

class MemberRepository(
    private val memberDao: MemberDao
) {
    val getAllMembers: LiveData<List<Member>> = memberDao.getAllMembers()

    suspend fun addMember(member: Member) {
        memberDao.addMember(member)
    }

    fun getUserWithEmail(email: String): LiveData<Member> {
        return memberDao.getUserWithEmail(email)
    }

    suspend fun updateUser(member: Member) {
        memberDao.updateMember(member)
    }

    suspend fun deleteMember(member: Member) {
        memberDao.deleteMember(member)
    }
}