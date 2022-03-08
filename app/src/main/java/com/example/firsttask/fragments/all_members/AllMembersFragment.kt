package com.example.firsttask.fragments.all_members

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firsttask.R
import com.example.firsttask.activities.UpdateMemberActivity
import com.example.firsttask.adapters.MembersAdapter
import com.example.firsttask.data.Member
import kotlinx.android.synthetic.main.all_members_fragment.*

class AllMembersFragment : Fragment() {

    private lateinit var viewModel: AllMembersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.all_members_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AllMembersViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        rv_teammates.layoutManager = LinearLayoutManager(context)
        val adapter = MembersAdapter(requireContext())
        rv_teammates.adapter = adapter

        adapter.setOnClickListener(object : MembersAdapter.OnClickListener {
            override fun onClickEdit(member: Member) {
                val intent = Intent(context, UpdateMemberActivity::class.java)
                intent.putExtra("Member", member)
                startActivity(intent)
            }

            override fun onClickDelete(member: Member) {
                showDeleteAlertDialog(member, adapter)
            }
        })

        getAndSetMemberList(adapter)
    }

    private fun getAndSetMemberList(adapter: MembersAdapter) {
        viewModel.getAllMembers().observe(this) { user ->
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
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are you sure?")
        builder.setMessage("All your data of this member will be deleted.")

        builder.setPositiveButton("YES") { dialog, _ ->
            viewModel.deleteMember(member).also {
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