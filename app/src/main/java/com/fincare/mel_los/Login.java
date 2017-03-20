package com.fincare.mel_los;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.fincare.mel_los.data.LoginData;
import com.fincare.mel_los.data.MelProvider;
import com.fincare.mel_los.util.DbHelper_Old;
import com.fincare.mel_los.util.Globals;
import com.fincare.mel_los.util.RequestHandler;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.HashMap;

/**
 * Created by Phaneendra on 28-Jul-16.
 */
public class Login  extends AppCompatActivity {
    private EditText password,username;
    private Button loginbutton;
    private AccountManager accountManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        password=(EditText) findViewById(R.id.password);
        username=(EditText) findViewById(R.id.username);
        loginbutton=(Button) findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),username.getText(),Toast.LENGTH_LONG).show();
                if(hasNetworkConnection())
                {
                    try {
                        byte[] b_pass = password.getText().toString().getBytes("UTF-8");
                        String pass = Base64.encodeToString(b_pass, Base64.DEFAULT);
                        checkLogin(username.getText().toString(),pass);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    try {
                        byte[] b_pass = password.getText().toString().getBytes("UTF-8");
                        DbHelper_Old db = new DbHelper_Old(getApplicationContext());
                        Toast.makeText(Login.this, Base64.encodeToString(b_pass, Base64.DEFAULT), Toast.LENGTH_SHORT).show();
                        if(db.checklogin(username.getText().toString(), Base64.encodeToString(b_pass, Base64.DEFAULT)))
                        {
                            Intent i=new Intent(getApplicationContext(),Home.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Credentials!!",Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        /*try {

            System.out.println(Base64.decode("U3lzdGVtQDEyMw==",Base64.DEFAULT).toString());
            String result = new String(Base64.decode("U3lzdGVtQDEyMw==",Base64.DEFAULT));
            System.out.println(result);
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
    }

    private void checkLogin(final String name, String pass){
        class UploadImage extends AsyncTask<String,Void,String> {

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
                //loading.dismiss();

                JSONObject jsonResp = null;
                try{
                    jsonResp = new JSONObject(s);
                    String result = jsonResp.getString("result").toString();
                    if(result.equals("success"))
                    {

                        JSONObject maindat=new JSONObject(jsonResp.getString("data"));
                        //LoginData ld=new LoginData();
                        ContentValues ld= new ContentValues();
                        ld.put("main_screen_access",maindat.getString("main_screen_access"));
                        ld.put("mfi_emp_active",maindat.getString("mfi_emp_active"));

                        ld.put("mfi_emp_name",maindat.getString("mfi_emp_name"));
                        ld.put("mfi_emp_user_name",name);
                        ld.put("mfi_emp_user_pass",maindat.getString("mfi_emp_user_pass"));
                        ld.put("route_access",maindat.getString("route_access"));
                        ld.put("sub_l1_screen_access",maindat.getString("sub_l1_screen_access"));

                        Uri uri = getContentResolver().insert(MelProvider.CONTENT_URI5, ld);
                        //DbHelper db=new DbHelper(getApplicationContext());
                        if(uri==null)
                        {
                            Log.e("LoginProb","user data not inserted");
                            Intent i=new Intent(getApplicationContext(),Home.class);
                            startActivity(i);
                        }
                        else
                        {

                            if(!AddNewAccountActivity.chkUserAccount(getApplicationContext(),username.getText().toString())) {
                                accountManager = AccountManager.get(getApplicationContext());
                                Account newUserAccount = new Account(username.getText().toString(), getResources().getString(R.string.account_type));

                                try {
                                    String encryptedPassword = password.getText().toString();
                                    boolean accountCreated = accountManager.addAccountExplicitly(newUserAccount, encryptedPassword, null);

                                    if (accountCreated) {

                                        Bundle result1 = new Bundle();
                                        result1.putString(AccountManager.KEY_ACCOUNT_NAME, username.getText().toString());
                                        result1.putString(AccountManager.KEY_ACCOUNT_TYPE, getString(R.string.account_type));
                                        //response.onResult(result);
                                        Toast.makeText(getApplicationContext(), "new account Added", Toast.LENGTH_LONG).show();
                                        finish();
                                        getContentResolver().setSyncAutomatically (newUserAccount,MelProvider.PROVIDER_NAME,true);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "error in creating account1", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    Log.e("authentate", e.getLocalizedMessage(), e);
                                    Toast.makeText(getApplicationContext(), "error in creating account2", Toast.LENGTH_LONG).show();
                                }
                            }
                            Toast.makeText(getApplicationContext(),"successfully inserted",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(getApplicationContext(),Home.class);
                            startActivity(i);

                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),jsonResp.getString("result").toString(),Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();



            }

            @Override
            protected String doInBackground(String... params) {
                String bitmap = params[0];
                HashMap<String,String> data = new HashMap<>();
                data.put("token", bitmap);
                String fcm_id = FirebaseInstanceId.getInstance().getToken();

                data.put("tag", "register");
                data.put("uname",  params[0]);
                data.put("pass",  params[1]);

                data.put("fcm_id", fcm_id);


                String result = rh.sendPostRequest(Globals.loginService,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        String[] s={name,pass};
        ui.execute(s);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
