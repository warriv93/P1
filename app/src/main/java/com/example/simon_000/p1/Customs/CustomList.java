package com.example.simon_000.p1.Customs;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simon_000.p1.MainActivity;
import com.example.simon_000.p1.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CustomList extends ArrayAdapter<Info>{
    private final Activity context;
    private final List<Info> list;
    private final Integer[] imageId;


    public CustomList(Activity context, List<Info> list, Integer[] imageId) {

        super(context, R.layout.list_single, list);
        this.context = context;
        this.list = list;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtValues = (TextView) rowView.findViewById(R.id.txtvalue);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        //formaterar så att det bara visas 2 decimaler
        NumberFormat df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(2);
        String Value = df.format(list.get(position).get_value());
        //Hämtar den angivna inmatade transaktionen från databasen
        txtTitle.setText(list.get(position).get_title().toString());
        txtValues.setText(Value +" SEK");
        imageView.setImageResource(imageId[position]);

        return rowView;
    }
}