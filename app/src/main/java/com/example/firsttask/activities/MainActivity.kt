package com.example.firsttask.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.firsttask.R
import com.example.firsttask.data.Member
import com.example.firsttask.data.MemberDB
import com.example.firsttask.data.MemberRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var allMembersList: LiveData<List<Member>>
    private lateinit var repository: MemberRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val memberDao = MemberDB.getDatabase(applicationContext).memberDao()
        repository = MemberRepository(memberDao)
        allMembersList = repository.getAllMembers
    }

    override fun onResume() {
        super.onResume()
        val lang = resources.getStringArray(R.array.dept)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, lang)
        atcv_dept.setAdapter(arrayAdapter)

        btn_save.setOnClickListener {
            val username = et_name.text.toString()
            val email = et_email.text.toString()
            val gender = if (rg_gender.checkedRadioButtonId == R.id.rb_male) "Male" else "Female"
            val empType = if (toggler_emp.isChecked) "Full Time" else "Part Time"
            val dept = atcv_dept.text.toString()

            if (validateInputs(username, email)) {
                if (emailValidate(email)) {
                    val member = Member(0, username, email, gender, empType, dept)
                    addMember(member)
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

            if(username.isEmpty() && email.isEmpty()){
                finish()
            }else{
                showAlertDialog()
            }
        }
    }

    override fun onBackPressed() {
        if(et_name.text.toString().isEmpty() && et_email.text.toString().isEmpty()){
            super.onBackPressed()
        }else{
            showAlertDialog()
        }
    }

    private fun showAlertDialog(){
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

    private fun addMember(member: Member) {
        lifecycleScope.launch(Dispatchers.IO) {
            repository.addMember(member)
            println(member)
        }.also {
            Toast.makeText(this, "Successfully saved!", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun emailValidate(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validateInputs(
        username: String,
        email: String,
    ): Boolean {
        return !(username.isEmpty() || email.isEmpty())
    }
}