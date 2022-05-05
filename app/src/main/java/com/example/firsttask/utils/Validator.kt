package com.example.firsttask.utils

abstract class Validator {
    companion object {
        fun validateEmail(email: String): Boolean {
            return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        }

        fun validateInputs(
            username: String,
            email: String,
        ): Boolean {
            return !(username.isEmpty() || email.isEmpty())
        }
    }
}