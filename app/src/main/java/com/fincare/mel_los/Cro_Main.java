package com.fincare.mel_los;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fincare.mel_los.data.MelProvider;

/**
 * Created by Phaneendra on 16-Sep-16.
 */
public class Cro_Main  extends AppCompatActivity {
    private GridLayout datatoadd;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cro_main);
        datatoadd=(GridLayout) findViewById(R.id.datatoadd);
        String[] req_cols={"Unique_Lead_No","c_first_name","branch","rec_amt"};
        final Cursor c=getContentResolver().query(MelProvider.CONTENT_URI4,req_cols,"main_sts='NCM Completed'",null,null);
        int i=2;
        if (c.moveToFirst()) {
            do {
                //TableRow tableRow2=new TableRow(getApplicationContext());

                GridLayout.LayoutParams perms= new GridLayout.LayoutParams(GridLayout.spec(i, GridLayout.CENTER),GridLayout.spec(0, GridLayout.CENTER));
                perms.width= TableRow.LayoutParams.WRAP_CONTENT;
                perms.height= TableRow.LayoutParams.WRAP_CONTENT;


                String bid=c.getString(2);
                String[] bname={"branch_name"};


                Cursor c1=getContentResolver().query(MelProvider.CONTENT_URI1,bname,"id="+bid,null,null);

                c1.moveToFirst();
                TextView tv=new TextView(getApplicationContext());
                tv.setText(c1.getString(0));
                tv.setTextColor(Color.parseColor("black"));



                GridLayout.LayoutParams perms1= new GridLayout.LayoutParams(GridLayout.spec(i, GridLayout.CENTER),GridLayout.spec(1, GridLayout.CENTER));
                perms1.width= TableRow.LayoutParams.WRAP_CONTENT;
                perms1.height= TableRow.LayoutParams.WRAP_CONTENT;


                //tableRow2.setLayoutParams(perms);
                final String unq=c.getString(0);

                TextView tv1=new TextView(getApplicationContext());
                tv1.setText(unq);
                tv1.setTextColor(Color.parseColor("black"));



                GridLayout.LayoutParams perms2= new GridLayout.LayoutParams(GridLayout.spec(i, GridLayout.CENTER),GridLayout.spec(2, GridLayout.CENTER));
                perms2.width= TableRow.LayoutParams.WRAP_CONTENT;
                perms2.height= TableRow.LayoutParams.WRAP_CONTENT;

                TextView tv2=new TextView(getApplicationContext());
                tv2.setText(c.getString(1));
                tv2.setTextColor(Color.parseColor("black"));


                //tableRow2.addView(tv2);

                GridLayout.LayoutParams perms3= new GridLayout.LayoutParams(GridLayout.spec(i, GridLayout.CENTER),GridLayout.spec(3, GridLayout.CENTER));
                perms3.width= TableRow.LayoutParams.WRAP_CONTENT;
                perms3.height= TableRow.LayoutParams.WRAP_CONTENT;

                TextView tv3=new TextView(getApplicationContext());
                tv3.setText(c.getString(3));
                tv3.setTextColor(Color.parseColor("black"));



                GridLayout.LayoutParams perms4= new GridLayout.LayoutParams(GridLayout.spec(i++, GridLayout.CENTER),GridLayout.spec(4, GridLayout.CENTER));
                perms4.width= TableRow.LayoutParams.WRAP_CONTENT;
                perms4.height= TableRow.LayoutParams.WRAP_CONTENT;



                Button tv4=new Button(getApplicationContext());
                tv4.setText("Update");
                tv4.setTextColor(Color.parseColor("black"));
                tv4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i= new Intent(getApplicationContext(),Cro_update.class);
                        i.putExtra("unq",unq);
                        startActivity(i);
                    }
                });


                datatoadd.addView(tv,perms);
                datatoadd.addView(tv1,perms1);
                datatoadd.addView(tv2,perms2);
                datatoadd.addView(tv3,perms3);
                datatoadd.addView(tv4,perms4);

            } while (c.moveToNext());
        }
    }
}
