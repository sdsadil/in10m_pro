package com.in10mServiceMan.ui.custom_views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.in10mServiceMan.R
import java.util.*

class MyDatePicker() {


    companion object {

        fun show(context: Context, dateChangedListener: DatePickerDialog.OnDateSetListener) {

            val calendar = Calendar.getInstance()
            val mYear = calendar.get(Calendar.YEAR)
            val mMonth = calendar.get(Calendar.MONTH)
            val mDay = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(context, R.style.CalendarTheme,
                    dateChangedListener, mYear, mMonth, mDay)

            datePickerDialog.show()

        }
    }

}

class MyTimePicker() {

    companion object {

        fun show(context: Context, timeSetListener: TimePickerDialog.OnTimeSetListener) {

            val calendar = Calendar.getInstance()

            val timePickerDialog = TimePickerDialog(context, R.style.CalendarTheme,
                    timeSetListener, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false)

            timePickerDialog.show()

        }
    }
}