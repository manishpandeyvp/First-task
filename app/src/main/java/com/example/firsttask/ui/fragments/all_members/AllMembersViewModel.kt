package com.example.firsttask.ui.fragments.all_members

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.firsttask.data.Member
import com.example.firsttask.data.MemberDB
import com.example.firsttask.data.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllMembersViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MemberRepository
    private val allMembersData: LiveData<List<Member>>

    init {
        val memberDao = MemberDB.getDatabase(application).memberDao()
        repository = MemberRepository(memberDao)
        allMembersData = repository.getAllMembers
    }

    fun getAllMembers(): LiveData<List<Member>> {
        return repository.getAllMembers
    }

    fun deleteMember(member: Member) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMember(member)
        }
    }
}