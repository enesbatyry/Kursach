package com.example.enes.materialdesignfromgoogle;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Enes on 01.03.2018.
 */

public class TimePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new android.app.TimePickerDialog(getActivity(), (android.app.TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
    }
}
