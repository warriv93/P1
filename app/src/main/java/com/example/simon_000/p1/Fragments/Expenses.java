package com.example.simon_000.p1.Fragments;



import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon_000.p1.MainActivity;
import com.example.simon_000.p1.R;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Expenses extends Fragment {
    private TextView ti, td;
    private Spinner cate;
    private EditText name, desc, value, date;
    private Button btclear, btsave;
    private int mYear;
    private int mMonth;
    private int mDay;


    public Expenses() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        startStuff(view);
        return view;
    }

    private void startStuff(View view) {
        ti = (TextView)view.findViewById(R.id.textInfoex);
        td = (TextView)view.findViewById(R.id.textDateex);
        ti.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        td.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        name = (EditText)view.findViewById(R.id.ExName);
        cate = (Spinner)view.findViewById(R.id.ExCat);
        desc = (EditText)view.findViewById(R.id.ExDesc);
        value = (EditText)view.findViewById(R.id.ExVal);
        date = (EditText)view.findViewById(R.id.ExDate);
        setCurrentDate();
        date.setOnClickListener(new datePicker());


        btclear = (Button)view.findViewById(R.id.btCancelex);
        btsave = (Button)view.findViewById(R.id.btSaveex);

        //String array to hold
        String[] RevSpinnerValues = new String[] {
                "Groceries",
                "Travel",
                "Rent",
                "Hobby",
                "Other"
        };
        ArrayAdapter<String> ExSpinneradapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, RevSpinnerValues);

        cate.setAdapter(ExSpinneradapter);

        btclear.setOnClickListener(new BlClearex());
        btsave.setOnClickListener(new BlSaveex());
    }

    private void setCurrentDate() {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        String res = mDay + "-"
                + (mMonth + 1) + "-" + mYear;

        date.setText(res);

}

    private class BlSaveex implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String Name = name.getText().toString();
            String Desc = desc.getText().toString();
            String Value = value.getText().toString();
            if(Name.isEmpty() || Desc.isEmpty() || Value.isEmpty()){
                Toast.makeText(getActivity(), "You need to fill in all the fields.", Toast.LENGTH_LONG).show();
            }else {
                ((MainActivity) getActivity()).saveExpensesToDB(name.getText().toString(), cate.getSelectedItem().toString(),
                        value.getText().toString(), desc.getText().toString(), date.getText().toString());
                name.setText("");
                desc.setText("");
                value.setText("");
            }
        }
    }


    private class BlClearex implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            name.setText("");
            desc.setText("");
            value.setText("");
        }
    }

    private class datePicker implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            date.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }
    }
}