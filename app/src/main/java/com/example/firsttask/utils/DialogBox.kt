package com.example.firsttask.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.example.firsttask.activities.UpdateMemberActivity
import com.example.firsttask.data.Member

abstract class DialogBox {

    companion object{
        fun showAlertDialog(context: Context, activity: Activity) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Do you really want to quit?")
            builder.setMessage("All your data you filled will be lost.")

            builder.setPositiveButton("YES") { dialog, _ ->
                dialog.dismiss()
                activity.finish()
            }

            builder.setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
        }

//        TODO : use extension functions
        fun showUpdateDialog(member: Member, context: Context, activity: Activity) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Email ID already exists in DB.")
            builder.setMessage("Do you want to update the data?")

            builder.setPositiveButton("YES") { dialog, _ ->
                val intent = Intent(context, UpdateMemberActivity::class.java)
                intent.putExtra("Member", member)
                dialog.dismiss()
                startActivity(context, intent, null)
                activity.finish()
            }

            builder.setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
        }
    }
}