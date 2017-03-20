package com.fincare.mel_los.data;

import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentValues;
import android.content.Context;

import android.accounts.Account;
import android.content.ContentProviderClient;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.fincare.mel_los.AddNewAccountActivity;
import com.fincare.mel_los.R;
import com.fincare.mel_los.util.DbHelper_Old;
import com.fincare.mel_los.util.Globals;
import com.fincare.mel_los.util.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
/**
 * Created by Phaneendra on 06-Sep-16.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private static final int NET_CONNECT_TIMEOUT_MILLIS = 1500000;  // 1500 seconds

    /**
     * Network read timeout, in milliseconds.
     */
    private static final int NET_READ_TIMEOUT_MILLIS = 1000000;  // 1000 seconds

    public static final String TAG = "SyncAdapter";

    public Context c;
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        c=context;
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Log.i(TAG, "Beginning network synchronization");

        try {
            final URL location = new URL(Globals.syncService);
            InputStream stream = null;

            try {
                Log.i(TAG, "Streaming data from network: " + location);
                //stream = downloadUrl(location);
                dataSync(getUserName());

                //String resource = getStr(stream);
                //return resource;
                Log.d(TAG+"-stream","Sync Completed");


                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Feed URL is malformed", e);
            syncResult.stats.numParseExceptions++;
            return;
        } catch (IOException e) {
            Log.e(TAG, "Error reading from network: " + e.toString());
            syncResult.stats.numIoExceptions++;
            return;
        }
        Log.i(TAG, "Network synchronization complete");
    }


    public String getUserName() {
        AccountManager accountManager = AccountManager.get(getContext());
        Account[] accounts = accountManager.getAccountsByType(getContext().getResources().getString(R.string.account_type));
        String a="";
        for (Account account : accounts) {
            a=account.name.toString();
        }

        return a;
    }


    private String getDataToSync()
    {
        JSONObject data=new JSONObject();

        Cursor cursor = getContext().getContentResolver().query(MelProvider.CONTENT_URI3, null, "uploaded = 'Pending'", null, null);

        JSONArray leads=new JSONArray();
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    JSONObject leadgen = new JSONObject();
                    try {
                        leadgen.put(cursor.getColumnName(0).toString(),cursor.getString(0));
                        leadgen.put(cursor.getColumnName(1).toString(),cursor.getString(1));
                        leadgen.put(cursor.getColumnName(2).toString(),cursor.getString(2));
                        leadgen.put(cursor.getColumnName(3).toString(),cursor.getString(3));
                        leadgen.put(cursor.getColumnName(4).toString(),cursor.getString(4));
                        leadgen.put(cursor.getColumnName(5).toString(),cursor.getString(5));
                        leadgen.put(cursor.getColumnName(6).toString(),cursor.getString(6));
                        leadgen.put(cursor.getColumnName(7).toString(),cursor.getString(7));
                        leadgen.put(cursor.getColumnName(8).toString(),cursor.getString(8));
                        leadgen.put(cursor.getColumnName(9).toString(),cursor.getString(9));
                        leadgen.put(cursor.getColumnName(10).toString(),cursor.getString(10));
                        leadgen.put(cursor.getColumnName(11).toString(),cursor.getString(11));
                        leadgen.put(cursor.getColumnName(12).toString(),cursor.getString(12));
                        leadgen.put(cursor.getColumnName(13).toString(),cursor.getString(13));
                        leadgen.put(cursor.getColumnName(14).toString(),cursor.getString(14));
                        leadgen.put(cursor.getColumnName(15).toString(),cursor.getString(15));
                        leadgen.put(cursor.getColumnName(16).toString(),cursor.getString(16));
                        leadgen.put(cursor.getColumnName(17).toString(),cursor.getString(17));
                        leadgen.put(cursor.getColumnName(18).toString(),cursor.getString(18));
                        leadgen.put(cursor.getColumnName(19).toString(),cursor.getString(19));
                        leadgen.put(cursor.getColumnName(20).toString(),cursor.getString(20));
                        leads.put(leadgen);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }while (cursor.moveToNext());
            }
        }
        try{
            data.put("leads",leads);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        Cursor c1 = getContext().getContentResolver().query(MelProvider.CONTENT_URI4, null, "uploaded='coo-upload-Pending'", null, null);

        JSONArray coo=new JSONArray();
        if(c1!=null)
        {
            if (c1.moveToFirst())
            {
                do {
                    JSONObject coo1 = new JSONObject();
                    try {
                        coo1.put(c1.getColumnName(1).toString(),c1.getString(1));
                        coo1.put(c1.getColumnName(17).toString(),c1.getString(17));
                        coo1.put(c1.getColumnName(26).toString(),c1.getString(26));
                        coo1.put(c1.getColumnName(34).toString(),c1.getString(34));
                        coo1.put(c1.getColumnName(50).toString(),c1.getString(50));
                        coo1.put(c1.getColumnName(52).toString(),c1.getString(52));
                        coo1.put(c1.getColumnName(53).toString(),c1.getString(53));
                        coo1.put(c1.getColumnName(54).toString(),c1.getString(54));
                        coo1.put(c1.getColumnName(55).toString(),c1.getString(55));



                        coo.put(coo1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }while (c1.moveToNext());
            }
        }
        try{
            data.put("coo-pending",coo);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        Cursor c2 = getContext().getContentResolver().query(MelProvider.CONTENT_URI6, null, null, null, null);

        JSONArray sync=new JSONArray();
        if(c2!=null)
        {
            if (c2.moveToFirst())
            {
                do {
                    JSONObject sync1 = new JSONObject();
                    try {
                        sync1.put(c2.getColumnName(1).toString(),c2.getString(1));
                        sync1.put(c2.getColumnName(2).toString(),c2.getString(2));




                        sync.put(sync1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }while (c1.moveToNext());
            }
        }
        try{
            data.put("sync",sync);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return data.toString();
    }



    private void dataSync(final String user) {
        class UploadImage extends AsyncTask<String, Void, String> {

            //ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(getApplicationContext(), "Uploading Image", "Please wait...",true,true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONObject jsonResp = null;
                try{
                    jsonResp = new JSONObject(s);
                    String result = jsonResp.getString("result").toString();

                    if(result.equals("success"))
                    {
                        ContentValues c=new ContentValues();
                        c.put("uploaded","Uploaded");
                        getContext().getContentResolver().update(MelProvider.CONTENT_URI3,c,"uploaded='Pending'",null);

                        ContentValues c1=new ContentValues();
                        c1.put("uploaded","uploaded");
                        getContext().getContentResolver().update(MelProvider.CONTENT_URI4,c,"uploaded='coo-upload-Pending'",null);


                        JSONObject maindat=new JSONObject(jsonResp.getString("data"));
                        JSONArray branch=new JSONArray(maindat.getString("branch"));
                        if(branch.length()>0) {
                            getContext().getContentResolver().delete(MelProvider.CONTENT_URI1,null,null);
                            for (int i = 0; i < branch.length(); i++) {

                                ContentValues values = new ContentValues();
                                JSONObject b1 = branch.getJSONObject(i);
                                values.put("id",b1.getInt("id"));
                                values.put("branch_name",b1.getString("branch_name"));
                                values.put("division_name",b1.getString("division_name"));
                                values.put("zone_name",b1.getString("zone_name"));
                                Uri uri = getContext().getContentResolver().insert(MelProvider.CONTENT_URI1, values);

                                Log.d("INSERT",uri.toString());
                            }
                        }


                        JSONArray sync=new JSONArray(maindat.getString("sync"));
                        if(sync.length()>0) {
                            getContext().getContentResolver().delete(MelProvider.CONTENT_URI6,null,null);
                            for (int i = 0; i < sync.length(); i++) {

                                ContentValues values = new ContentValues();
                                JSONObject s1 = sync.getJSONObject(i);
                                values.put("master_vertion",s1.getString("master_vertion"));
                                Date d=new Date();
                                String pattern = "yyyy-MM-dd HH:mm:ss";
                                SimpleDateFormat format = new SimpleDateFormat(pattern);
                                values.put("lastsync_at",format.format(d).toString());
                                Uri uri = getContext().getContentResolver().insert(MelProvider.CONTENT_URI6, values);
                                Log.d("INSERT",uri.toString());
                            }
                        }



                        JSONArray master=new JSONArray(maindat.getString("master"));
                        if(master.length()>0) {
                            getContext().getContentResolver().delete(MelProvider.CONTENT_URI2,null,null);
                            for (int i = 0; i < master.length(); i++) {

                                ContentValues values = new ContentValues();
                                JSONObject b1 = master.getJSONObject(i);
                                values.put("id",b1.getInt("id"));
                                values.put("name",b1.getString("name"));
                                values.put("type",b1.getString("type"));
                                Uri uri = getContext().getContentResolver().insert(MelProvider.CONTENT_URI2, values);

                                Log.d("INSERT",uri.toString());
                            }
                        }

                        //-------------member------------------------------------

                        JSONArray member=new JSONArray(maindat.getString("members"));
                        //System.out.println(member.toString());
                        //Toast.makeText(getContext(),member.toString(),Toast.LENGTH_LONG).show();
                        if(member.length()>0) {

                            for (int i = 0; i < member.length(); i++) {
                                ContentValues m = new ContentValues();
                                JSONObject k1 = member.getJSONObject(i);

                                getContext().getContentResolver().delete(MelProvider.CONTENT_URI4,"Unique_Lead_No='"+k1.getString("Unique_Lead_No")+"'",null);


                                m.put("branch",k1.getString("branch_name"));
                                m.put("Unique_Lead_No",k1.getString("Unique_Lead_No"));
                                m.put("c_first_name",k1.getString("c_first_name"));
                                m.put("c_dob",k1.getString("c_dob"));
                                m.put("c_contact_mobile",k1.getString("c_contact_mobile"));
                                m.put("c_pancard",k1.getString("c_pancard"));
                                m.put("c_voter_id",k1.getString("c_voter_id"));
                                m.put("c_aadhar_card",k1.getString("c_aadhar_card"));
                                m.put("g_first_name",k1.getString("g_first_name"));
                                m.put("g_dob",k1.getString("g_dob"));
                                m.put("g_contact_mobile",k1.getString("g_contact_mobile"));
                                m.put("g_pancard",k1.getString("g_pancard"));
                                m.put("g_voter_id",k1.getString("g_voter_id"));
                                m.put("g_aadhar_card",k1.getString("g_aadhar_card"));
                                m.put("tenure",k1.getString("tenure"));
                                m.put("roi",k1.getString("roi"));
                                m.put("applied_amt",k1.getString("applied_amt"));
                                m.put("rec_amt",k1.getString("rec_amt"));
                                m.put("cibil_score",k1.getString("cibil_score"));
                                m.put("score",k1.getString("score"));
                                m.put("ltv",k1.getString("ltv"));
                                m.put("foir",k1.getString("foir"));
                                m.put("pdcam_remarks",k1.getString("pdcam_remarks"));
                                m.put("dcm_remarks",k1.getString("dcm_remarks"));
                                m.put("zcm_remarks",k1.getString("zcm_remarks"));
                                m.put("ncm_remarks",k1.getString("ncm_remarks"));
                                m.put("coo_remarks",k1.getString("coo_remarks"));
                                m.put("added_at",k1.getString("added_at"));
                                m.put("product",k1.getString("product"));
                                m.put("loan_purpose",k1.getString("loan_purpose"));
                                m.put("cm_amt",k1.getString("cm_amt"));
                                m.put("dcm_amt",k1.getString("dcm_amt"));
                                m.put("zcm_amt",k1.getString("zcm_amt"));
                                m.put("ncm_amt",k1.getString("ncm_amt"));
                                m.put("cro_amt",k1.getString("cro_amt"));
                                m.put("dcm_done_at",k1.getString("dcm_done_at"));
                                m.put("dcm_done_by",k1.getString("dcm_done_by"));
                                m.put("dcm_qry",k1.getString("dcm_qry"));
                                m.put("dcm_res_msg",k1.getString("dcm_res_msg"));
                                m.put("dcm_status",k1.getString("dcm_status"));
                                m.put("zcm_done_at",k1.getString("zcm_done_at"));
                                m.put("zcm_done_by",k1.getString("zcm_done_by"));
                                m.put("zcm_qry",k1.getString("zcm_qry"));
                                m.put("zcm_res_msg",k1.getString("zcm_res_msg"));
                                m.put("zcm_sts",k1.getString("zcm_sts"));
                                m.put("ncm_done_at",k1.getString("ncm_done_at"));
                                m.put("ncm_done_by",k1.getString("ncm_done_by"));
                                m.put("ncm_qry",k1.getString("ncm_qry"));
                                m.put("ncm_res_msg",k1.getString("ncm_res_msg"));
                                m.put("ncm_msg",k1.getString("ncm_msg"));
                                m.put("coo_done_at",k1.getString("coo_done_at"));
                                m.put("coo_done_by",k1.getString("coo_done_by"));
                                m.put("coo_qry",k1.getString("coo_qry"));
                                m.put("coo_res_msg",k1.getString("coo_res_msg"));
                                m.put("coo_sts",k1.getString("coo_sts"));
                                m.put("main_sts",k1.getString("main_sts"));
                                m.put("property_photo_file_name",k1.getString("property_photo_file_name"));
                                m.put("residency_photo_file_name",k1.getString("residency_photo_file_name"));
                                m.put("collateral_photo_file_name",k1.getString("collateral_photo_file_name"));
                                m.put("uploaded","uploaded");


                                Uri uri = getContext().getContentResolver().insert(MelProvider.CONTENT_URI4, m);
                                Log.d("INSERT",uri.toString());
                                //System.out.println(String.valueOf(b1.getInt("id"))+"--"+b1.getString("branch_name"));
                            }
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.d(TAG,"Branch Not Found. "+e.getMessage().toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                //String bitmap = params[0];
                HashMap<String,String> data = new HashMap<>();

                data.put("tag", "register");
                data.put("uname",  params[0]);
                data.put("uploads",getDataToSync());


                String result = rh.sendPostRequest(Globals.syncService,data);

                return result;
            }
        }
        UploadImage ui = new UploadImage();
        String[] s={user};
        ui.execute(s);
    }
}

