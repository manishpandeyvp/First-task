package com.example.firsttask.ui.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.firsttask.R
import com.example.firsttask.data.Member
import com.example.firsttask.data.MemberDB
import com.example.firsttask.data.MemberRepository
import com.example.firsttask.utils.Validator
import kotlinx.android.synthetic.main.activity_update_member.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateMemberActivity : AppCompatActivity() {

    private lateinit var repository: MemberRepository
    private lateinit var tMember: Member

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_member)

        val memberDao = MemberDB.getDatabase(applicationContext).memberDao()
        repository = MemberRepository(memberDao)

        tMember = intent.getParcelableExtra("Member")!!
    }

    override fun onResume() {
        super.onResume()
        val lang = resources.getStringArray(R.array.dept)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, lang)
        atcv_dept.setAdapter(arrayAdapter)

        setDataInFields()

        btn_update.setOnClickListener {
            val username = et_name.text.toString()
            val email = et_email.text.toString()
            val gender = if (rg_gender.checkedRadioButtonId == R.id.rb_male) "Male" else "Female"
            val empType = if (toggler_emp.isChecked) "Full Time" else "Part Time"
            val dept = atcv_dept.text.toString()

            if (Validator.validateInputs(username, email)) {
                if (Validator.validateEmail(email)) {
                    val member = Member(tMember.id, username, email, gender, empType, dept)
                    updateMember(member)
                } else {
                    Toast.makeText(this, "Email format is incorrect!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "It seems some fields are empty!", Toast.LENGTH_LONG).show()
            }
        }

        iv_back.setOnClickListener {
            val username = et_name.text.toString()
            val email = et_email.text.toString()

            if (username.isEmpty() && email.isEmpty()) {
                finish()
            } else {
                showAlertDialog()
            }
        }
    }

    private fun setDataInFields() {
        et_name.setText(tMember.name)
        et_email.setText(tMember.email)
        toggler_emp.isChecked = tMember.empType == "Full Time"
        atcv_dept.setText(tMember.dept)
        if (tMember.gender == "Male") {
            rb_male.isChecked = true
            rb_female.isChecked = false
        } else {
            rb_male.isChecked = false
            rb_female.isChecked = true
        }
    }

    override fun onBackPressed() {
        if (et_name.text.toString().isEmpty() && et_email.text.toString().isEmpty()) {
            super.onBackPressed()
        } else {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Do you really want to quit?")
        builder.setMessage("All your data you filled will be lost.")

        builder.setPositiveButton("YES") { dialog, _ ->
            dialog.dismiss()
            finish()
        }

        builder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun updateMember(member: Member) {
        lifecycleScope.launch(Dispatchers.IO) {
            repository.updateUser(member)
            println(member)
        }.also {
            Toast.makeText(this, "Successfully updated!", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}