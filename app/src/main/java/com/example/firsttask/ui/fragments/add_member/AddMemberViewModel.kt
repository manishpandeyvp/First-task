package com.example.firsttask.ui.fragments.add_member

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.firsttask.data.Member
import com.example.firsttask.data.MemberDB
import com.example.firsttask.data.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMemberViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MemberRepository
    private val allMembersData: LiveData<List<Member>>

    init {
        val memberDao = MemberDB.getDatabase(application).memberDao()
        repository = MemberRepository(memberDao)
        allMembersData = repository.getAllMembers
    }


    fun addMember(member: Member) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMember(member)
        }
    }

    fun getUserWithEmail(email: String): LiveData<Member> {
        return repository.getUserWithEmail(email)
    }

}