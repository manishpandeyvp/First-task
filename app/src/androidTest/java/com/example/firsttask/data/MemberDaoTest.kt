package com.example.firsttask.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.firsttask.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MemberDaoTest {
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var memberDao: MemberDao
    private lateinit var memberDb: MemberDB

    @Before
    fun setup() {
        memberDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MemberDB::class.java
        ).allowMainThreadQueries().build()
        memberDao = memberDb.memberDao()
    }

    @After
    fun teardown(){
        memberDb.close()
    }

    @Test
    fun addMember() = runBlockingTest {
        val member = Member(1, "Manish", "abc@gmail.com", "Male", "Full Time", "ECE")
        memberDao.addMember(member)

        val memberList = memberDao.getAllMembers().getOrAwaitValue()
        assertThat(memberList).contains(member)
    }

    @Test
    fun updateMember() = runBlockingTest {
        val member = Member(1, "Manish", "abc@gmail.com", "Male", "Full Time", "ECE")
        memberDao.addMember(member)

        val member1 = Member(1, "Manish", "abcdef@gmail.com", "Male", "Full Time", "ECE")
        memberDao.updateMember(member1)

        val memberList = memberDao.getAllMembers().getOrAwaitValue()
        assertThat(memberList).contains(member1)
    }

    @Test
    fun deleteMember() = runBlockingTest {
        val member = Member(1, "Manish", "abc@gmail.com", "Male", "Full Time", "ECE")
        val member1 = Member(2, "Vijay", "def@gmail.com", "Male", "Full Time", "CSE")

        memberDao.addMember(member)
        memberDao.addMember(member1)

        memberDao.deleteMember(member)

        val memberList = memberDao.getAllMembers().getOrAwaitValue()
        assertThat(memberList).doesNotContain(member)
    }

    @Test
    fun getUserWithEmail() = runBlockingTest {
        val member1 = Member(1, "Manish", "abc@gmail.com", "Male", "Full Time", "ECE")
        val member2 = Member(2, "Vijay", "def@gmail.com", "Male", "Full Time", "CSE")

        memberDao.addMember(member1)
        memberDao.addMember(member2)

        val usersWithEmail = memberDao.getUserWithEmail("def@gmail.com").getOrAwaitValue()
        assertThat(usersWithEmail).isEqualTo(member2)
    }

}