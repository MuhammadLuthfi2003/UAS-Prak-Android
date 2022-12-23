package com.example.uas_firebase;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatePicker2} factory method to
 * create an instance of this fragment.
 */
public class DatePicker2 extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        // Set the activity to the Main Activity.
        EditComplaint activity = (EditComplaint) getActivity();
        // Invoke Main Activity's processDatePickerResult() method.
        if (activity != null) {
            activity.processDatePicker(year, month, day);
        }
    }
}