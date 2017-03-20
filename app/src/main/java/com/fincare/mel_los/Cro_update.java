package com.fincare.mel_los;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fincare.mel_los.data.MelProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Phaneendra on 21-Sep-16.
 */
public class Cro_update extends AppCompatActivity {

    private TextView zone,division,branch,unqledno,apliedamt,cmamt,dcmamt,zcmamt,ncmamt,cooamt,product,loanpurpose,tenure,roi,ltv,foir,
            c_name,g_name,c_dob,g_dob,c_mobile,g_mobile,c_pan,g_pan,c_vid,g_vid,c_aid,g_aid,
            crocmts,ncmcmts,zcmcmts,dcmcmts,cmcmts,
            cro_qry_res_remarks;
    private Spinner status;
    private EditText app_amt,cro_remarks,cro_query;
    private Button home,submit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cro_update);
        final String unq=(String) getIntent().getSerializableExtra("unq");

        Cursor m= getContentResolver().query(MelProvider.CONTENT_URI4,null,"Unique_Lead_No='"+ unq +"'",null,null);
        if(m.moveToFirst())
        {
            String[] bname={"branch_name","division_name","zone_name"};


            Cursor c1=getContentResolver().query(MelProvider.CONTENT_URI1,bname," id="+m.getString(0),null,null);

            c1.moveToFirst();

            branch=(TextView) findViewById(R.id.branch);
            branch.setText(c1.getString(0));

            division=(TextView) findViewById(R.id.division);
            division.setText(c1.getString(1));

            zone=(TextView) findViewById(R.id.zone);
            zone.setText(c1.getString(2));

            unqledno=(TextView) findViewById(R.id.unqledno);
            unqledno.setText(unq);

            apliedamt=(TextView) findViewById(R.id.apliedamt);
            apliedamt.setText(m.getString(16));

            cmamt=(TextView) findViewById(R.id.cmamt);
            cmamt.setText(m.getString(30));

            dcmamt=(TextView) findViewById(R.id.dcmamt);
            dcmamt.setText(m.getString(31));

            zcmamt=(TextView) findViewById(R.id.zcmamt);
            zcmamt.setText(m.getString(32));

            ncmamt=(TextView) findViewById(R.id.ncmamt);
            ncmamt.setText(m.getString(33));

            cooamt=(TextView) findViewById(R.id.cooamt);
            cooamt.setText(m.getString(34));

            product=(TextView) findViewById(R.id.product);
            product.setText(m.getString(28));

            loanpurpose=(TextView) findViewById(R.id.loanpurpose);
            loanpurpose.setText(m.getString(29));

            tenure=(TextView) findViewById(R.id.tenure);
            tenure.setText(m.getString(14));

            roi=(TextView) findViewById(R.id.roi);
            roi.setText(m.getString(15));

            ltv=(TextView) findViewById(R.id.ltv);
            ltv.setText(m.getString(20));

            foir=(TextView) findViewById(R.id.foir);
            foir.setText(m.getString(21));

            c_name=(TextView) findViewById(R.id.c_name);
            c_name.setText(m.getString(2));

            g_name=(TextView) findViewById(R.id.g_name);
            g_name.setText(m.getString(8));

            c_dob=(TextView) findViewById(R.id.c_dob);
            c_dob.setText(m.getString(3));

            g_dob=(TextView) findViewById(R.id.g_dob);
            g_dob.setText(m.getString(9));

            c_mobile=(TextView) findViewById(R.id.c_mobile);
            c_mobile.setText(m.getString(4));

            g_mobile=(TextView) findViewById(R.id.g_mobile);
            g_mobile.setText(m.getString(10));

            c_pan=(TextView) findViewById(R.id.c_pan);
            c_pan.setText(m.getString(5));

            g_pan=(TextView) findViewById(R.id.g_pan);
            g_pan.setText(m.getString(11));

            c_vid=(TextView) findViewById(R.id.c_vid);
            c_vid.setText(m.getString(6));

            g_vid=(TextView) findViewById(R.id.g_vid);
            g_vid.setText(m.getString(12));

            c_aid=(TextView) findViewById(R.id.c_aid);
            c_aid.setText(m.getString(7));

            g_aid=(TextView) findViewById(R.id.g_aid);
            g_aid.setText(m.getString(13));

            dcmcmts=(TextView) findViewById(R.id.dcmcmts);
            dcmcmts.setText(Html.fromHtml(m.getString(23)));

            zcmcmts=(TextView) findViewById(R.id.zcmcmts);
            zcmcmts.setText(Html.fromHtml(m.getString(24)));

            ncmcmts=(TextView) findViewById(R.id.ncmcmts);
            ncmcmts.setText(Html.fromHtml(m.getString(25)));

            cmcmts=(TextView) findViewById(R.id.cmcmts);
            cmcmts.setText(Html.fromHtml(m.getString(22)));

            crocmts=(TextView) findViewById(R.id.crocmts);
            crocmts.setText(Html.fromHtml(m.getString(26)));

            cro_qry_res_remarks=(TextView) findViewById(R.id.cro_qry_res_remarks);
            cro_qry_res_remarks.setText(Html.fromHtml(m.getString(26)));

            app_amt=(EditText) findViewById(R.id.app_amt);
            app_amt.setText(Html.fromHtml(m.getString(16)));
        }

        home=(Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
            }
        });

        submit=(Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status=(Spinner) findViewById(R.id.status);
                app_amt=(EditText) findViewById(R.id.app_amt);
                cro_remarks=(EditText) findViewById(R.id.cro_remarks);
                cro_query=(EditText) findViewById(R.id.cro_query);

                if(status.getSelectedItem().toString()=="Query Raised" && cro_query.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please give Query Remarks",Toast.LENGTH_LONG).show();
                }
                else if(status.getSelectedItem().toString()!="Query Raised" && cro_remarks.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please give Remarks",Toast.LENGTH_LONG).show();
                }
                else if(status.getSelectedItem().toString()=="Approved" && app_amt.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please give approved Amount",Toast.LENGTH_LONG).show();
                }
                else
                {
                    ContentValues c=new ContentValues();
                    c.put("uploaded","coo-upload-Pending");
                    if(status.getSelectedItem().toString()=="Approved")
                    {
                        c.put("main_sts","COO Completed");
                        c.put("coo_sts","Recommended");
                    }
                    else
                    {
                        c.put("coo_sts",status.getSelectedItem().toString());
                        if(status.getSelectedItem().toString()=="Query Raised")
                            c.put("main_sts","COO Query Raised");
                        else
                            c.put("main_sts","COO Completed");
                    }
                    Date d=new Date();
                    String pattern = "yyyy-MM-dd HH:mm:ss";
                    SimpleDateFormat format = new SimpleDateFormat(pattern);
                    c.put("coo_done_at",format.format(d).toString());

                    c.put("coo_remarks",cro_remarks.getText().toString());
                    if(cro_query.getText().toString().length()>0)
                        c.put("coo_qry",cro_remarks.getText().toString());
                    c.put("cro_amt",app_amt.getText().toString());
                    c.put("rec_amt",app_amt.getText().toString());
                    int ins=getApplicationContext().getContentResolver().update(MelProvider.CONTENT_URI4,c,"Unique_Lead_No='"+unq+"'",null);
                    if(ins>0)
                    {
                        Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),Cro_Main.class);
                        startActivity(i);
                    }
                }
            }
        });




    }
}
