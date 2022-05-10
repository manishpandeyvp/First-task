package com.example.firsttask.utils

import com.google.common.truth.Truth
import org.junit.Test

class EmailValidatorTest{
    @Test
    fun validateEmail_ReturnsTrue(){
        val res = Validator.validateEmail("abc@gmail.com")
        Truth.assertThat(res).isTrue()
    }

    @Test
    fun validateEmail_ReturnsFalse(){
        val res = Validator.validateEmail("abc")
        Truth.assertThat(res).isFalse()
    }

    @Test
    fun validateEmail2_ReturnsFalse(){
        val res = Validator.validateEmail("")
        Truth.assertThat(res).isFalse()
    }
}