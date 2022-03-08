package com.example.firsttask.fragments.add_member

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.firsttask.data.Member
import com.example.firsttask.data.MemberDB
import com.example.firsttask.data.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMemberViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MemberRepository
    private val allMembersData: LiveData<List<Member>>
    private val userWithEmail: MutableLiveData<Member> = MutableLiveData()
    var tMember : MutableLiveData<Member> ?= MutableLiveData()

    init {
        val memberDao = MemberDB.getDatabase(application).memberDao()
        repository = MemberRepository(memberDao)
        allMembersData = repository.getAllMembers
    }

    fun addMember(member: Member) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMember(member)
            println(member)
        }
    }

    suspend fun getUserWithEmail(email: String): MutableLiveData<Member>? {
//        Log.d("MANISH", repository.getUserWithEmail(email).value.toString())
        val job = viewModelScope.launch {
            tMember?.postValue(repository.getUserWithEmail(email).value)
        }
        job.join()
        return tMember
    }

    fun getUserWithEmail1(email: String) : LiveData<Member> {
        return repository.getUserWithEmail(email)
    }
}