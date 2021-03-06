package org.jaku8ka.companionAnimal;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);

        return dpd;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day, 0, 0, 0);
        Date chosenDate = cal.getTime();

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        String formattedDate = df.format(chosenDate);

        switch (getTag()) {

            case"Date Picker Brth":
                TextView tvDateOfBirth = getActivity().findViewById(R.id.date_of_birth);
                tvDateOfBirth.setText(formattedDate);
                break;
            case "Date Picker Odc":
                TextView tvDateOdc = getActivity().findViewById(R.id.date_of_odc);
                tvDateOdc.setText(formattedDate);

                break;
            case "Date Picker Vac":
                TextView tvDateVac = getActivity().findViewById(R.id.date_of_vac);
                tvDateVac.setText(formattedDate);
                break;
        }
    }
}
