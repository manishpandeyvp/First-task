package com.example.firsttask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firsttask.R
import com.example.firsttask.data.Member
import kotlinx.android.synthetic.main.item_member.view.*

class MembersAdapter : RecyclerView.Adapter<MembersAdapter.ViewHolder>() {

    private var membersList = emptyList<Member>()
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("Adapter : On Bind View Holder : position - $position")
        val member = membersList[position]

        val view = holder.itemView
        view.tv_name.text = member.name
        view.tv_email.text = member.email
        view.tv_gender.text = member.gender
        view.tv_dept.text = member.dept
        view.tv_emp_type.text = member.empType

        val demo: Demo = holder.itemView as Demo
        demo.demoFun()

        view.iv_edit.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClickEdit(member)
            }
        }

        view.iv_delete.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClickDelete(member)
            }
        }
    }

    override fun getItemCount(): Int {
        return membersList.size
    }

    fun setData(members: List<Member>) {
        this.membersList = members
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClickEdit(member: Member)
        fun onClickDelete(member: Member)
    }

    interface Demo {
        fun demoFun()
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}