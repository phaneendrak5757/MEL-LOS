package com.fincare.mel_los;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fincare.mel_los.R;

/**
 * Created by Phaneendra on 02-Aug-16.
 */
public class Home extends AppCompatActivity {
    private Button leadgen,cro;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        leadgen=(Button) findViewById(R.id.lead_gen);
        cro=(Button) findViewById(R.id.cro);
        cro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Cro_Main.class);
                startActivity(i);
            }
        });
        leadgen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i=new Intent(getApplicationContext(),LeadGen.class);
                startActivity(i);
            }
        });

    }



    private boolean hasNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
