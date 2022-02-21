package com.example.firsttask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members_list")
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    var name : String,
    var email : String,
    var gender : String,
    var empType : String,
    var dept  :String
)
