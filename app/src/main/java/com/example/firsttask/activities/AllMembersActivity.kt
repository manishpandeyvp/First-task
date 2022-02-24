package com.example.firsttask.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firsttask.R
import com.example.firsttask.adapters.MembersAdapter
import com.example.firsttask.data.Member
import com.example.firsttask.data.MemberDB
import com.example.firsttask.data.MemberRepository
import kotlinx.android.synthetic.main.activity_all_members.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        adapter.setOnClickListener(object : MembersAdapter.OnClickListener {
            override fun onClickEdit(member: Member) {
                val intent = Intent(applicationContext, UpdateMemberActivity::class.java)
                intent.putExtra("Member", member)
                startActivity(intent)
            }

            override fun onClickDelete(member: Member) {
                showDeleteAlertDialog(member, adapter)
            }
        })

        getAndSetMemberList(adapter)

        iv_add.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getAndSetMemberList(adapter: MembersAdapter) {
        repository.getAllMembers.observe(this) { user ->
            if (user.isNotEmpty()) {
                tv_number_of_people.text = user.size.toString()
                tv_nothing_to_show.visibility = View.GONE
                rv_teammates.visibility = View.VISIBLE
            } else {
                tv_number_of_people.text = "0"
                tv_nothing_to_show.visibility = View.VISIBLE
                rv_teammates.visibility = View.GONE
            }
            adapter.setData(user)
        }
    }

    private fun showDeleteAlertDialog(member: Member, adapter: MembersAdapter) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure?")
        builder.setMessage("All your data of this member will be deleted.")

        builder.setPositiveButton("YES") { dialog, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                repository.deleteMember(member)
            }.also {
                getAndSetMemberList(adapter)
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

}