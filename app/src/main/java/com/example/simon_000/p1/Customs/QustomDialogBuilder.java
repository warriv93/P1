package com.example.simon_000.p1.Customs;

/**
 * Created by simon_000 on 2014-10-01.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon_000.p1.MainActivity;
import com.example.simon_000.p1.R;

public class QustomDialogBuilder extends AlertDialog.Builder{

    private EditText pw, un;
    private CheckBox cb;

    /** The custom_body layout */
    private View mDialogView;

    /** optional dialog title layout */
    private TextView mTitle;
    /** optional alert dialog image */
    private ImageView mIcon;
    /** optional message displayed below title if title exists*/
    private TextView mMessage;
    /** The colored holo divider. You can set its color with the setDividerColor method */
    private View mDivider;

    public QustomDialogBuilder(Context context) {
        super(context);

        mDialogView = View.inflate(context, R.layout.qustom_dialog_layout, null);
        setView(mDialogView);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);

        un = (EditText) mDialogView.findViewById(R.id.un);
        pw = (EditText) mDialogView.findViewById(R.id.pw);
        cb = (CheckBox) mDialogView.findViewById(R.id.cb);



        //TODO

        //lägg till clicka bak en gång till för att gå ut ur appen //9gaga har ett exempel



    }
    public Boolean getcbisChecked() {
        cb.isChecked();
        return cb.isChecked();
    }
    public QustomDialogBuilder getunText() {
        un.getText();
        return this;
    }

    public QustomDialogBuilder getpwText() {
        pw.getText();
        return this;
    }
    public QustomDialogBuilder setpwText(CharSequence text) {
        pw.setText(text);
        return this;
    }
    public QustomDialogBuilder setunText(CharSequence text){
        un.setText(text);
        return this;
    }

    /**
     * Use this method to color the divider between the title and content.
     * Will not display if no title is set.
     *
     * @param colorString for passing "#ffffff"
     */
    public QustomDialogBuilder setDividerColor(String colorString) {
        mDivider.setBackgroundColor(Color.parseColor(colorString));
        return this;
    }

    @Override
    public QustomDialogBuilder setTitle(CharSequence text) {
        mTitle.setText(text);
        return this;
    }

    public QustomDialogBuilder setTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        return this;
    }

    @Override
    public QustomDialogBuilder setMessage(int textResId) {
        mMessage.setText(textResId);
        return this;
    }

    @Override
    public QustomDialogBuilder setMessage(CharSequence text) {
        mMessage.setText(text);
        return this;
    }

    @Override
    public QustomDialogBuilder setIcon(int drawableResId) {
        mIcon.setImageResource(drawableResId);
        return this;
    }

    @Override
    public QustomDialogBuilder setIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }

    /**
     * This allows you to specify a custom layout for the area below the title divider bar
     * in the dialog. As an example you can look at example_ip_address_layout.xml and how
     * I added it in TestDialogActivity.java
     *
     * @param resId  of the layout you would like to add
     * @param context
     */
    public QustomDialogBuilder setCustomView(int resId, Context context) {
        View customView = View.inflate(context, resId, null);
        ((FrameLayout)mDialogView.findViewById(R.id.customPanel)).addView(customView);
        return this;
    }

    @Override
    public AlertDialog show() {
        if (mTitle.getText().equals("")) mDialogView.findViewById(R.id.topPanel).setVisibility(View.GONE);
        return super.show();
    }

//    public void runPosLogic() {
//        String user = un.getText().toString();
//        String pass = pw.getText().toString();
//        if(user.isEmpty() || pass.isEmpty()) {
//            Toast.makeText(getContext(), "You need to enter a Name and Lastname.", Toast.LENGTH_LONG).show();
//
////         getContext().startLoginDialog();
//
//
//        }else {
//            Toast.makeText(getContext(), "Welcome: "+ un.getText().toString() + " " + pw.getText().toString(), Toast.LENGTH_LONG).show();
//
//        }
//        if(cb.isChecked()) {
//            //                Log.d(controller.sharedpreferences.getString("pw", ""), controller.sharedpreferences.getString("pw", ""));
//
//
//
////            editor.putString("un", un.getText().toString());
////            editor.putString("pw", pw.getText().toString());
////            editor.commit();
//        }else{
////            SharedPreferences.Editor editor = sharedpreferences.edit();
//
////            editor.clear();
////            editor.commit();
//
//            un.setText("");
//            pw.setText("");
//        }
//    }


}