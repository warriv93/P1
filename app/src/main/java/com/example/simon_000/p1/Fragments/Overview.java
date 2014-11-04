package com.example.simon_000.p1.Fragments;



import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simon_000.p1.Customs.CustomList;
import com.example.simon_000.p1.Customs.Info;

import com.example.simon_000.p1.MainActivity;
import com.example.simon_000.p1.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Overview extends Fragment {
    private TextView tr, te, bal;
    private ListView rl, el;
    private int mYear;
    private int mMonth;
    private int mDay;
    private EditText fromDate, toDate;
    private Integer[] exImageId = {
            R.drawable.groceriesimg_2724,
            R.drawable.travelicon512,
            R.drawable.renthomeicon,
            R.drawable.hobbies,
            R.drawable.other
    };
    private Integer[] revImageId = {
            R.drawable.salary,
            R.drawable.other
    };

    public Overview() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        startStuff(view);
        return view;
    }
    private void setCurrentDate() {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        String res = mDay + "-"
                + (mMonth + 1) + "-" + mYear;

        String from = mDay + "-" + mMonth + "-" +mYear;
       fromDate.setText(from);
       toDate.setText(res);

    }



    //skapar referenser till allting och hämtar info från listorna till ListViewsen
    private void startStuff(View view) {
        tr = (TextView)view.findViewById(R.id.textRev);
        te = (TextView)view.findViewById(R.id.textEx);
        tr.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        te.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        bal = (TextView)view.findViewById(R.id.tvBalance);

        //algoritm för att räkna ut balance inkomster - utgifter.
        Double sum = ((MainActivity)getActivity()).getSumValues();

        //formaterar så att det bara visas 2 decimaler
        NumberFormat df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(2);
        String finalsum = df.format(sum);

        bal.setText("Balance " + finalsum + " SEK");
        bal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        //DATEPICKER
        fromDate = (EditText)view.findViewById(R.id.fromDate);
        toDate = (EditText)view.findViewById(R.id.toDate);
        setCurrentDate();
        fromDate.setOnClickListener(new datePickerFrom());
        toDate.setOnClickListener(new datePickerTo());

        //listvew!
        rl = (ListView)view.findViewById(R.id.RevList);
        el = (ListView)view.findViewById(R.id.ExList);

        //set Revenue names in rl listview
        CustomList revadapter = new
                CustomList(getActivity(), ((MainActivity)getActivity()).getRevenuelist(), revImageId);

        //update list before run
        rl.destroyDrawingCache();
        rl.setVisibility(ListView.INVISIBLE);
        rl.setVisibility(ListView.VISIBLE);
        revadapter.notifyDataSetChanged();

        rl.setAdapter(revadapter);

        //set Expense names in listview
        CustomList exadapter = new
                CustomList(getActivity(), ((MainActivity)getActivity()).getExpensesList(), exImageId);


        //update list before run
        el.destroyDrawingCache();
        el.setVisibility(ListView.INVISIBLE);
        el.setVisibility(ListView.VISIBLE);
        exadapter.notifyDataSetChanged();

        el.setAdapter(exadapter);

        // ListView Item Click Listener
        rl.setOnItemClickListener(new rlListener());
        el.setOnItemClickListener(new elListener());

    }
    private class datePickerFrom implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            fromDate.setText(dayOfMonth + "-"
                                    + (monthOfYear) + "-" + year);
                            String fromDate = (dayOfMonth + "-"
                                    + (monthOfYear +1) + "-" + year);
                            ((MainActivity)getActivity()).setfromDate(fromDate);

                           //om den inte är större än datepickerTo

                            CustomList revadapter = new
                                    CustomList(getActivity(), ((MainActivity)getActivity()).getRevenuelist(), revImageId);
                            rl.setAdapter(revadapter);

                            CustomList exadapter = new
                                    CustomList(getActivity(), ((MainActivity)getActivity()).getExpensesList(), exImageId);

                            el.setAdapter(exadapter);
                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }

    }
    private class datePickerTo implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            toDate.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);
                            String toDate = (dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);
                            ((MainActivity)getActivity()).settoDate(toDate);

                            CustomList revadapter = new
                                    CustomList(getActivity(), ((MainActivity)getActivity()).getRevenuelist(), revImageId);
                            rl.setAdapter(revadapter);

                            CustomList exadapter = new
                                    CustomList(getActivity(), ((MainActivity)getActivity()).getExpensesList(), exImageId);

                            el.setAdapter(exadapter);
                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }
    }


    //REVENUES list onClick LISTENER
    private class rlListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            // ListView Clicked item index
            int itemPosition     = position;
            //open selected finanse and show more information
                    Dialog d = new Dialog(getActivity());
                    d.setTitle("Transaction information");
                    d.setCanceledOnTouchOutside(true);
                    //inserting xml file in Dialog
                    LayoutInflater factory = LayoutInflater.from(getActivity());
                    View infoLayout = factory.inflate(R.layout.dialog, null);
                    TextView tv = (TextView) infoLayout.findViewById(R.id.textViewDialog);
                    ArrayAdapter<Info> mArrayAdapter = new ArrayAdapter<Info>(
                            getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, ((MainActivity)getActivity()).getRevenuelist());

                    //formaterar så att det bara visas 2 decimaler
                    NumberFormat df = DecimalFormat.getInstance();
                     df.setMaximumFractionDigits(2);
                     String Valueex = df.format(mArrayAdapter.getItem(itemPosition).get_value());

                    //Hämtar den angivna inmatade transaktionen från databasen
                    String text = "Transaction: "+mArrayAdapter.getItem(itemPosition).get_title().toString()+"\n"+
                            "Description: "+mArrayAdapter.getItem(itemPosition).get_desc().toString() +"\n"+
                            "Category: "+mArrayAdapter.getItem(itemPosition).get_cate().toString() +"\n"+
                            "Value: "+ Valueex +" SEK\n"+
                            "Date: "+mArrayAdapter.getItem(itemPosition).get_date().toString() +"\n";


                    tv.setText(text);
                    d.setContentView(infoLayout);

                    d.show();



        }
    }


    //EXPENSES list onClick LISTENER
    private class elListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            // ListView Clicked item index
            int itemPosition     = position;

                    Dialog d = new Dialog(getActivity());
                    d.setTitle("Transaction information");
                    d.setCanceledOnTouchOutside(true);

                    //inserting xml file in Dialog
                    LayoutInflater factory = LayoutInflater.from(getActivity());
                    View infoLayout = factory.inflate(R.layout.dialog, null);
                    TextView tv = (TextView) infoLayout.findViewById(R.id.textViewDialog);
                    ArrayAdapter<Info> mArrayAdapter = new ArrayAdapter<Info>(
                            getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, ((MainActivity)getActivity()).getExpensesList());

                    //formaterar så att det bara visas 2 decimaler
                     NumberFormat df = DecimalFormat.getInstance();
                      df.setMinimumFractionDigits(0);
                     df.setMaximumFractionDigits(2);
                     String Valuerev = df.format(mArrayAdapter.getItem(itemPosition).get_value());

                    //Hämtar den angivna inmatade transaktionen från databasen
                    String text = "Transaction: "+mArrayAdapter.getItem(itemPosition).get_title().toString()+"\n"+
                            "Description: "+mArrayAdapter.getItem(itemPosition).get_desc().toString() +"\n"+
                            "Category: "+mArrayAdapter.getItem(itemPosition).get_cate().toString() +"\n"+
                            "Value: "+Valuerev +" SEK\n"+
                            "Date: "+mArrayAdapter.getItem(itemPosition).get_date().toString() +"\n";
                    tv.setText(text);
                    d.setContentView(infoLayout);

                    d.show();


        }
    }


}
