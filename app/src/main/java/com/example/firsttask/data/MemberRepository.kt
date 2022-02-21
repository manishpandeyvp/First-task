package com.example.firsttask.data

import androidx.lifecycle.LiveData

class MemberRepository(
    private val memberDao: MemberDao
) {
    val getAllMembers: LiveData<List<Member>> = memberDao.getAllMembers()

    suspend fun addMember(member: Member) {
        memberDao.addMember(member)
    }
}