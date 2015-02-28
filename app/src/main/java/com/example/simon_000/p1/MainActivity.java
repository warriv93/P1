package com.example.simon_000.p1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simon_000.p1.Customs.DataBase;
import com.example.simon_000.p1.Customs.Info;
import com.example.simon_000.p1.Customs.QustomDialogBuilder;
import com.example.simon_000.p1.Fragments.Expenses;
import com.example.simon_000.p1.Fragments.IncomeFragment;
import com.example.simon_000.p1.Fragments.Overview;


public class MainActivity extends Activity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    private EditText un, pw;
    private CheckBox cb;
    private int mYear;
    private int mMonth;
    private int mDay;

//    private String RevList;
    public SharedPreferences sharedpreferences;
    private DataBase db;
    private Overview ov;
    private boolean doubleBackToExitPressedOnce = false;
    private String fromDate ;
    private String toDate;
    private Date dateTo = null;
    private Date dateFrom = null;
    private Date infoDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startstuff();


        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }
    //initierar databas klassen & sharedPreferences
    private void startstuff() {
        fromDate = setCurrentDateFrom();
        toDate = setCurrentDateTo();

        db = new DataBase(this);
//        db.setUpDb();
        FragmentManager fm = getFragmentManager();
//        If = (IncomeFragment)fm.findFragmentById(R.layout.fragment_income);
//        Ef = (Expenses)fm.findFragmentById(R.layout.fragment_expenses);
        ov = (Overview)fm.findFragmentById(R.layout.fragment_overview);
//        If.setController(controller);
//        Ef.setController(controller);
        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        if(sharedpreferences.contains("un")){
            startLoginDialog();
        }
    }
    private String setCurrentDateFrom() {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);



        String from = mDay + "-" + mMonth + "-" +mYear;

        return from;

    }
    private String setCurrentDateTo() {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);



        String to = mDay + "-" +(mMonth +1) + "-" +mYear;

        return to;
        //jhjhjh

    }

//info från inmatningsfragmentet sparas till databasen
    public void saveRevenueToDB(String RevName, String RevCate, String RevValue, String RevDesc, String RevDate) {
        db.open();
        db.saveRevenues(RevName, RevCate, Double.parseDouble(RevValue), RevDesc, RevDate);
        db.close();
        Toast.makeText(this, "Revenue saved", Toast.LENGTH_SHORT).show();

    }
    public Double getSumValues(){
        db.open();
        Double sum = db.getValueSum();
        db.close();
        return sum;
    }

    //info från inmatningsfragmentet sparas till databasen
    public void saveExpensesToDB(String RevName, String RevCate, String RevValue, String RevDesc, String RevDate) {
        db.open();
        db.saveExpenses(RevName, RevCate, Double.parseDouble(RevValue), RevDesc, RevDate);
        db.close();
        Toast.makeText(this, "Expense saved", Toast.LENGTH_SHORT).show();

    }


//hämtar ut info från databasen och sätter det i ett objekt som i sintur sparas i en Arraylista som retuneras

    public List<Info> getRevenuelist(){
        List<Info> RevenueList = new ArrayList<Info>();
        db.open();
        Cursor c =  db.getAllRevenues();
        if(c.moveToFirst()){
            do{
                Info i = new Info();
                i.set_id(Integer.parseInt(c.getString(0)));
                i.set_title((c.getString(1)));
                i.set_cate(c.getString(2));
                i.set_value(Double.parseDouble(c.getString(3)));
                i.set_desc(c.getString(4));
                i.set_date(c.getString(5));

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                try {
                    dateFrom = sdf.parse(fromDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    dateTo = sdf.parse(toDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                    try {
                        infoDate = sdf.parse(i.get_date());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(infoDate.compareTo(dateFrom)>=0 && infoDate.compareTo(dateTo)<=0){
                        RevenueList.add(i);
                    }
                if(dateFrom.compareTo(dateTo)>=0){
                    Toast.makeText(this, "From date is bigger than To date!",Toast.LENGTH_SHORT).show();
                }
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return RevenueList;
    }


//hämtar ut info från databasen och sätter det i ett objekt som i sintur sparas i en Arraylista som retuneras
        public List<Info> getExpensesList(){
            List<Info> ExpensesList = new ArrayList<Info>();
            db.open();
            Cursor c =  db.getAllExpenses();
            if(c.moveToFirst()){
                do{
                    Info i = new Info();
                    i.set_id(Integer.parseInt(c.getString(0)));
                    i.set_title((c.getString(1)));
                    i.set_cate(c.getString(2));
                    i.set_value(Double.parseDouble(c.getString(3)));
                    i.set_desc(c.getString(4));
                    i.set_date(c.getString(5));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                    try {
                        dateFrom = sdf.parse(fromDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        dateTo = sdf.parse(toDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        infoDate = sdf.parse(i.get_date());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if(infoDate.compareTo(dateFrom)>=0 && infoDate.compareTo(dateTo)<=0){

                        ExpensesList.add(i);
                    }
                    if(dateFrom.compareTo(dateTo)>=0){
                        Toast.makeText(this, "From date is bigger than To date!",Toast.LENGTH_SHORT).show();
                    }
                } while(c.moveToNext());
            }
            c.close();
            db.close();
            return ExpensesList;
        }

//all kod för inloggnings alertDialog
    public void startLoginDialog() {
       AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        View loginlayout = factory.inflate(R.layout.fragment_login, null);

        un = (EditText) loginlayout.findViewById(R.id.un);
        pw = (EditText) loginlayout.findViewById(R.id.pw);
        cb = (CheckBox) loginlayout.findViewById(R.id.cb);

        un.setText(sharedpreferences.getString("un", ""));
        pw.setText(sharedpreferences.getString("pw", ""));

        alert.setView(loginlayout);
        alert.setTitle("Manage your finances");
        alert.setIcon(getResources().getDrawable(R.drawable.money_bag2));
        alert.setCancelable(false);

        alert.setPositiveButton("Continue", new PB());
        alert.setNegativeButton("Cancel", new NB());

        AlertDialog ad = alert.create();
        ad.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.Logout){
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public void setfromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void settoDate(String toDate) {
        this.toDate = toDate;
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    //HÄR SÄTTS FRAGMENTEN IN I TABSEN
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


            //HÄR SÄTTS FRAGMENTEN IN I TABSEN
            switch (position) {
                case 0:
                    return new IncomeFragment();
                case 1:
                    return new Expenses();
                case 2:
                    return new Overview();
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    //OM DU KLICKAR PÅ BACK SÅ KOMMER BARA EN TOAST UPP OM DU KLICKAR 2 ggr SÅ HOPPAR DU UR APPEN
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    //LoginDialogRutans "OK" knapp logik, sparar info i sharedpreferences
    private class PB implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            String user = un.getText().toString();
            String pass = pw.getText().toString();
            if(user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getApplication(), "You need to enter a Name and Lastname.", Toast.LENGTH_LONG).show();
                startLoginDialog();
            }else {
                Toast.makeText(getApplication(), "Welcome: "+ un.getText().toString() + " " + pw.getText().toString(), Toast.LENGTH_LONG).show();

            }
            if(cb.isChecked()) {
                //                Log.d(controller.sharedpreferences.getString("pw", ""), controller.sharedpreferences.getString("pw", ""));
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("un", un.getText().toString());
                editor.putString("pw", pw.getText().toString());
                editor.commit();
            }else{
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.commit();

                un.setText("");
                pw.setText("");
            }
            dialogInterface.dismiss();
        }

    }
    //LoginRutans Cancelknapp - Stänger av appen.
    private class NB implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            System.exit(0);
        }
    }
}
