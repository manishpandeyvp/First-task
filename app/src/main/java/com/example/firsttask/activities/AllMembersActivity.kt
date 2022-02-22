package com.example.firsttask.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firsttask.R
import com.example.firsttask.adapters.MembersAdapter
import com.example.firsttask.data.MemberDB
import com.example.firsttask.data.MemberRepository
import kotlinx.android.synthetic.main.activity_all_members.*

class AllMembersActivity : AppCompatActivity() {

    private lateinit var repository: MemberRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_members)

        val memberDao = MemberDB.getDatabase(applicationContext).memberDao()
        repository = MemberRepository(memberDao)
    }

    override fun onResume() {
        super.onResume()

        rv_teammates.layoutManager = LinearLayoutManager(this)
        val adapter = MembersAdapter(this)
        rv_teammates.adapter = adapter

        repository.getAllMembers.observe(this) { user ->
            if (user.isNotEmpty()) {
                tv_number_of_people.text = user.size.toString()
                tv_nothing_to_show.visibility = View.GONE
                rv_teammates.visibility = View.VISIBLE
            }
            adapter.setData(user)
        }

        iv_add.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}