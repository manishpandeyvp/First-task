package com.example.firsttask.ui.fragments.add_member

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.firsttask.R
import com.example.firsttask.data.Member
import com.example.firsttask.ui.activities.UpdateMemberActivity
import com.example.firsttask.utils.Validator
import kotlinx.android.synthetic.main.add_member_fragment.*

class AddMemberFragment : Fragment() {

    private lateinit var viewModel: AddMemberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_member_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddMemberViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        val lang = resources.getStringArray(R.array.dept)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, lang)
        atcv_dept.setAdapter(arrayAdapter)

        btn_save.setOnClickListener {
            val username = et_name.text.toString()
            val email = et_email.text.toString()
            val gender = if (rg_gender.checkedRadioButtonId == R.id.rb_male) "Male" else "Female"
            val empType = if (toggler_emp.isChecked) "Full Time" else "Part Time"
            val dept = atcv_dept.text.toString()

            if (Validator.validateInputs(username, email)) {
                if (Validator.validateEmail(email)) {
                    addOrUpdate(username, email, gender, dept, empType)
                } else {
                    Toast.makeText(context, "Email format is incorrect!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "It seems some fields are empty!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showUpdateDialog(member: Member) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Email ID already exists in DB.")
        builder.setMessage("Do you want to update the data?")

        builder.setPositiveButton("YES") { dialog, _ ->
            val intent = Intent(context, UpdateMemberActivity::class.java)
            intent.putExtra("Member", member)
            dialog.dismiss()
            resetFields()
            startActivity(intent)
        }

        builder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun resetFields() {
        et_name.setText("")
        et_email.setText("")
        rg_gender.check(R.id.rb_female)
        toggler_emp.isChecked = false
    }

    private fun addOrUpdate(
        username: String,
        email: String,
        gender: String,
        dept: String,
        empType: String
    ) {
        viewModel.getUserWithEmail(email).observe(viewLifecycleOwner) {
            if (it == null) {
                val member = Member(0, username, email, gender, empType, dept)
                viewModel.addMember(member)
                Toast.makeText(context, "Data Saved!", Toast.LENGTH_LONG).show()
                resetFields()
            } else if (et_email.text.toString() != "") {
                showUpdateDialog(it)
            }
        }
    }
}