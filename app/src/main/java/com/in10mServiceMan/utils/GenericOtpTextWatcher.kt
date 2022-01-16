package com.in10mServiceMan.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class GenericOtpTextWatcher {
    companion object{
        fun handleOtpTextWatcher(otpET1:EditText,otpET2:EditText,otpET3:EditText,otpET4:EditText){

            otpET1.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(otpET1.text.toString().length > 0){
                        otpET2.requestFocus()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

            })
            otpET2.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(otpET2.text.toString().isNotEmpty()){
                        otpET3.requestFocus()
                    }else if(otpET2.text.toString().isEmpty()){
                        otpET1.requestFocus()
                        otpET1.setSelection(otpET1.text.length)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

            })
            otpET3.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(otpET3.text.toString().isNotEmpty()){
                        otpET4.requestFocus()
                    }else if(otpET3.text.toString().isEmpty()){
                        otpET2.requestFocus()
                        otpET2.setSelection(otpET2.text.length)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

            })
            otpET4.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(otpET4.text.toString().isEmpty()){
                        otpET3.requestFocus()
                        otpET3.setSelection(otpET3.text.length)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

            })
        }
    }
}