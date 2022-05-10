package com.example.firsttask.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class InputValidatorTest{

//    Test for Inputs Validator
    @Test
    fun `validate Inputs returns true`(){
        val res = Validator.validateInputs("manishp", "abc@cde.com")
        assertThat(res).isTrue()
    }

    @Test
    fun `validate inputs 1 returns false`(){
        val res = Validator.validateInputs("", "abc@cde.com")
        assertThat(res).isFalse()
    }

    @Test
    fun `validate inputs 2 returns false`(){
        val res = Validator.validateInputs("manishp", "")
        assertThat(res).isFalse()
    }

    @Test
    fun `validate inputs 3 returns false`(){
        val res = Validator.validateInputs("", "")
        assertThat(res).isFalse()
    }
}