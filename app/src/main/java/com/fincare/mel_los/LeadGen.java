package com.fincare.mel_los;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.fincare.mel_los.data.Aadhar;
import com.fincare.mel_los.data.LeadGeneration;
import com.fincare.mel_los.data.MelProvider;
import com.fincare.mel_los.util.AadharStirngReading;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * Created by Phaneendra on 17-Aug-16.
 */
public class LeadGen extends AppCompatActivity {
    private ImageButton qr, dt, dt1;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private EditText edd, follw_date, borrower_name, rec_address, prop_address, kyc_id, mobile, business_decription, business_address, loan_amount;
    private Spinner kyc_type, prop_location, property_type, business_type, loan_purpose, source_of_lead, lead_type;
    private Aadhar a;
    private Button home,save;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leadgen);
        property_type = (Spinner) findViewById(R.id.property_type);
        business_type = (Spinner) findViewById(R.id.business_type);
        loan_purpose = (Spinner) findViewById(R.id.loan_purpose);
        source_of_lead = (Spinner) findViewById(R.id.source_of_lead);
        lead_type = (Spinner) findViewById(R.id.lead_type);


        kyc_type = (Spinner) findViewById(R.id.kyc_type);
        prop_location = (Spinner) findViewById(R.id.prop_location);
        edd = (EditText) findViewById(R.id.edd);
        follw_date = (EditText) findViewById(R.id.follw_date);
        qr = (ImageButton) findViewById(R.id.qr);
        borrower_name = (EditText) findViewById(R.id.borrower_name);
        rec_address = (EditText) findViewById(R.id.rec_address);
        prop_address = (EditText) findViewById(R.id.prop_address);
        kyc_id = (EditText) findViewById(R.id.kyc_id);

        //mobile,business_decription,business_address,loan_amount
        mobile = (EditText) findViewById(R.id.mobile);
        business_decription = (EditText) findViewById(R.id.business_decription);
        business_address = (EditText) findViewById(R.id.business_address);
        loan_amount = (EditText) findViewById(R.id.loan_amount);


        addItemsOnkyc_type();
        addItemsOnprop_location();

        addItemsOnproperty_type();
        addItemsOnbusiness_type();
        addItemsOnloan_purpose();
        addItemsOnsource_of_lead();
        addItemsOnlead_type();


        dt = (ImageButton) findViewById(R.id.dt);
        dt1 = (ImageButton) findViewById(R.id.dt1);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
            }
        });
        dt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(998);
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(LeadGen.this);
                integrator.initiateScan();
            }
        });

        save =(Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LeadGeneration l=new LeadGeneration();
                ContentValues l = new ContentValues();

                //DbHelper db=new DbHelper(getApplicationContext());
                String err="";
                if(borrower_name!=null && borrower_name.getText().toString().trim().length()>0)
                    l.put("borrower_name",borrower_name.getText().toString().trim());
                else
                    err+="Please give Name, ";
                if(mobile!=null && mobile.getText().toString().trim().length()>0)
                    l.put("mobile",mobile.getText().toString().trim());
                else
                    err+="Please give Mobile No.,";
                if(rec_address!=null && rec_address.getText().toString().trim().length()>0)
                    l.put("rec_address",rec_address.getText().toString().trim());
                else
                    err+="Please give Residency Address, ";
                if(prop_address!=null && prop_address.getText().toString().trim().length()>0)
                    l.put("prop_address",prop_address.getText().toString().trim());
                else
                    err+="Please give Property Address, ";
                if(kyc_type!=null && kyc_type.getSelectedItem().toString().trim().length()>0)
                    l.put("kyc_type",kyc_type.getSelectedItem().toString().trim());
                else
                    err+="Please select KYC Type, ";
                if(kyc_id!=null && kyc_id.getText().toString().trim().length()>0)
                    l.put("kyc_id",kyc_id.getText().toString().trim());
                else
                    err+="Please give KYC ID";
                if(property_type!=null && property_type.getSelectedItem().toString().trim().length()>0)
                    l.put("property_type",property_type.getSelectedItem().toString().trim());
                else
                    err+="Please select Property Type, ";
                if(prop_location!=null && prop_location.getSelectedItem().toString().trim().length()>0)
                    l.put("property_localtion",prop_location.getSelectedItem().toString().trim());
                else
                    err+="Please select Property Location, ";
                if(business_type!=null && business_type.getSelectedItem().toString().trim().length()>0)
                    l.put("business_type",business_type.getSelectedItem().toString().trim());
                else
                    err+="Please select business type, ";
                if(business_decription!=null && business_decription.getText().toString().trim().length()>0)
                    l.put("business_discription",business_decription.getText().toString().trim());
                else
                    err+="Please give Business Discription, ";
                if(business_address!=null && business_address.getText().toString().trim().length()>0)
                    l.put("business_address",business_address.getText().toString().trim());
                else
                    err+="Please give Business address, ";
                if(loan_purpose!=null && loan_purpose.getSelectedItem().toString().trim().length()>0)
                    l.put("loan_purpose",loan_purpose.getSelectedItem().toString().trim());
                else
                    err+="Please select Loan Purpose, ";
                if(source_of_lead!=null && source_of_lead.getSelectedItem().toString().trim().length()>0)
                    l.put("source_of_lead",source_of_lead.getSelectedItem().toString().trim());
                else
                    err+="Please select Source of Lead, ";
                if(lead_type!=null && lead_type.getSelectedItem().toString().trim().length()>0)
                    l.put("lead_type",lead_type.getSelectedItem().toString().trim());
                else
                    err+="Please select lead type, ";
                if(edd!=null ||edd.getText().toString().trim().length()>0)
                    l.put("edd",edd.getText().toString().trim());
                else
                    err+="Please select Loan Required Date, ";
                if(follw_date!=null && follw_date.getText().toString().trim().length()>0)
                    l.put("next_followup",follw_date.getText().toString().trim());
                else
                    err+="Please select next follow up date, ";
                if(loan_amount!=null && loan_amount.getText().toString().trim().length()>0)
                    l.put("loan_amt",loan_amount.getText().toString().trim());
                else
                    err+="Please give Loan Amount";

                Date d=new Date();
                String pattern = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String pattern1 = "yyyyMMddHHmmss";
                SimpleDateFormat format1 = new SimpleDateFormat(pattern1);
                    l.put("uniqno",format1.format(d).toString());
                    l.put("uploaded","Pending");
                    l.put("inserted_at",format.format(d).toString());
                if(err.length()==0) {
                    Uri uri = getContentResolver().insert(
                            MelProvider.CONTENT_URI3, l);
                    if (uri!=null) {
                        Toast.makeText(getApplicationContext(),"Succussfully Saved "+uri.toString(),Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),Home.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Failed.. Try Again!!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),err.toString().trim(),Toast.LENGTH_LONG).show();
                }

            }
        });

        home =(Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x0000c0de) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            String contents = "";
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {

                    //Toast.makeText(this, requestCode + "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    Log.d("aadhar",result.getContents());
                    AadharStirngReading as=new AadharStirngReading();
                    a=as.getAadhar(result.getContents().replace("\"", "'").toString(),getApplicationContext());

                }


                borrower_name.setText(a.getName());
                rec_address.setText(a.getCo() + " " + a.getHouse() + " " + a.getStreet() + " " + a.getLoc() + " " + a.getVtc() + " " + a.getPo() + " " + a.getDist() + " " + a.getState() + " " + a.getPc());
                prop_address.setText(a.getCo() + " " + a.getHouse() + " " + a.getStreet() + " " + a.getLoc() + " " + a.getVtc() + " " + a.getPo() + " " + a.getDist() + " " + a.getState() + " " + a.getPc());
                business_address.setText(a.getCo() + " " + a.getHouse() + " " + a.getStreet() + " " + a.getLoc() + " " + a.getVtc() + " " + a.getPo() + " " + a.getDist() + " " + a.getState() + " " + a.getPc());
                kyc_id.setText(a.getUid());

               kyc_type.setSelection(( (ArrayAdapter) kyc_type.getAdapter() ).getPosition("Aadhar card"));
                //Intent intent1=new Intent(MainActivity.this, Register1.class).putExtra("aadhar", ad);

                //startActivity(intent1);

            }


        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }

}


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        if (id == 998) {
            return new DatePickerDialog(this, myDateListener1, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            //Toast.makeText(getApplicationContext(),String.valueOf(arg1).toString(),Toast.LENGTH_LONG).show();
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day

            edd.setText(arg1+"-"+String.format("%02d", (arg2+1))+"-"+String.format("%02d", (arg3)));
        }
    };
    private DatePickerDialog.OnDateSetListener myDateListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            //Toast.makeText(getApplicationContext(),String.valueOf(arg1).toString(),Toast.LENGTH_LONG).show();
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day

            follw_date.setText(arg1+"-"+String.format("%02d", (arg2+1))+"-"+String.format("%02d", (arg3)));
        }
    };



    public void addItemsOnkyc_type() {
        kyc_type = (Spinner) findViewById(R.id.kyc_type);
        List<String> list=new ArrayList<String>();
        Uri Master = MelProvider.CONTENT_URI2;
        Cursor cursor = getContentResolver().query(Master, null, "type='ID TYPE'", null, "name");
        Log.d("data-sync",String.valueOf(cursor.getCount()));
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(1).toString().trim());
                }while (cursor.moveToNext());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kyc_type.setAdapter(dataAdapter);
    }

    public void addItemsOnprop_location() {
        prop_location = (Spinner) findViewById(R.id.prop_location);
        List<String> list=new ArrayList<String>();
        Uri Branch = MelProvider.CONTENT_URI1;
        Cursor cursor = getContentResolver().query(Branch, null, null, null, "branch_name");
        //Log.d("data-sync",String.valueOf(cursor.getCount()));
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(1).toString().trim());
                }while (cursor.moveToNext());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prop_location.setAdapter(dataAdapter);
    }

    public void addItemsOnproperty_type() {
        property_type=(Spinner) findViewById(R.id.property_type);
        List<String> list=new ArrayList<String>();
        Uri Master = MelProvider.CONTENT_URI2;
        Cursor cursor = getContentResolver().query(Master, null, "type='PROPERTY TYPE'", null, "name");
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(1).toString().trim());
                }while (cursor.moveToNext());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        property_type.setAdapter(dataAdapter);
    }

    public void addItemsOnbusiness_type() {
        business_type=(Spinner) findViewById(R.id.business_type);
        List<String> list=new ArrayList<String>();
        Uri Master = MelProvider.CONTENT_URI2;
        Cursor cursor = getContentResolver().query(Master, null, "type='BUSINESS TYPE'", null, "name");
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(1).toString().trim());
                }while (cursor.moveToNext());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        business_type.setAdapter(dataAdapter);
    }
    public void addItemsOnloan_purpose() {
        loan_purpose=(Spinner) findViewById(R.id.loan_purpose);
        List<String> list=new ArrayList<String>();
        Uri Master = MelProvider.CONTENT_URI2;
        Cursor cursor = getContentResolver().query(Master, null, "type='LOAN PURPOSE'", null, "name");
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(1).toString().trim());
                }while (cursor.moveToNext());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loan_purpose.setAdapter(dataAdapter);
    }
    public void addItemsOnsource_of_lead() {
        source_of_lead=(Spinner) findViewById(R.id.source_of_lead);
        List<String> list=new ArrayList<String>();
        Uri Master = MelProvider.CONTENT_URI2;
        Cursor cursor = getContentResolver().query(Master, null, "type='SOURCE LEADS'", null, "name");
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(1).toString().trim());
                }while (cursor.moveToNext());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        source_of_lead.setAdapter(dataAdapter);
    }
    public void addItemsOnlead_type() {
        lead_type=(Spinner) findViewById(R.id.lead_type);
        List<String> list=new ArrayList<String>();
        Uri Master = MelProvider.CONTENT_URI2;
        Cursor cursor = getContentResolver().query(Master, null, "type='LEAD TYPE'", null, "name");
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(1).toString().trim());
                }while (cursor.moveToNext());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lead_type.setAdapter(dataAdapter);
    }

}

