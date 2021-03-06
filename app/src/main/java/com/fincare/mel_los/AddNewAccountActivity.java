package com.fincare.mel_los;

/**
 * Created by Phaneendra on 25-Aug-16.
 */
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * This class is called when the user want to create an account in the configuration of Android.
 */
public class AddNewAccountActivity extends Activity {

    /**
     * The tag utilized for the log.
     */
    private static final String LOG_TAG = AddNewAccountActivity.class.getSimpleName();

    /**
     * The context of the program.
     */
    private Context context;

    /**
     * The user name input by the user.
     */
    private EditText usernameET;

    /**
     * The password input by the user.
     */
    private EditText passwordET;

    /**
     * The button to add a new account.
     */
    private Button addNewAccountButton;

    /**
     * The response passed by the service.
     * It is used to give the user name and the password to the account manager
     */
    private AccountAuthenticatorResponse response;

    /**
     * The account manager used to request and add account.
     */
    private AccountManager accountManager;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState The state saved previously
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Lock the screen orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.add_new_account_layout);

        context = this;

        usernameET = (EditText) findViewById(R.id.username);

        passwordET = (EditText) findViewById(R.id.password);

        addNewAccountButton = (Button) findViewById(R.id.createNewAccountButton);
        addNewAccountButton.setOnClickListener(onClickListener);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            /*
             * Pass the new account back to the account manager
             */
            response = extras.getParcelable(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);
        }
    }

    /**
     * The listener for the button pressed.
     */
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final String username = usernameET.getText().toString();
            final String password = passwordET.getText().toString();

            // Check the contents
            // Check the user name
            if (username == null || username.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please give User Name", Toast.LENGTH_LONG).show();
                return;
            }

            // Check the password
            if (password == null || password.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please Give Password", Toast.LENGTH_LONG).show();
                return;
            }

            accountManager = AccountManager.get(context);

            /*
             * Check if the account already exists.
             */
            if (getUserAccount(context, username) != null) {
                Toast.makeText(context, "user account already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            /*
             * Check the user name and the password against the server.
             */
            Account newUserAccount = new Account(username, getResources().getString(R.string.account_type));

            try {
                String encryptedPassword = password;
                boolean accountCreated = accountManager.addAccountExplicitly(newUserAccount, encryptedPassword, null);

                if (accountCreated) {
                    if (response != null) {
                        Bundle result = new Bundle();
                        result.putString(AccountManager.KEY_ACCOUNT_NAME, username);
                        result.putString(AccountManager.KEY_ACCOUNT_TYPE, getString(R.string.account_type));
                        response.onResult(result);
                        Toast.makeText(context, "new account Added", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(context, "error in creating account", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context,"error in creating account", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getLocalizedMessage(), e);
                Toast.makeText(context, "error in creating account", Toast.LENGTH_LONG).show();
            }
        }
    };

    public static Account getUserAccount(Context context, String username) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(context.getResources().getString(R.string.account_type));
        for (Account account : accounts) {
            if (account.name.equalsIgnoreCase(username)) {
                return account;
            }
        }

        return null;
    }


    public static boolean chkUserAccount(Context context, String username) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(context.getResources().getString(R.string.account_type));
        for (Account account : accounts) {
            if (account.name.equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }

    public static void syncAllAccountsPeriodically(Context contextAct, long seconds) throws Exception {
        AccountManager manager = AccountManager.get(contextAct);
        Account[] accounts = manager.getAccountsByType(contextAct.getResources().getString(R.string.account_type));
        String accountName = "";
        String accountType = "";
        for (Account account : accounts) {
            accountName = account.name;
            accountType = account.type;
            break;
        }

        Account a = new Account(accountName, accountType);
        ContentResolver.addPeriodicSync(a, "com.fincare.mellos.provider", new Bundle(), seconds*1000);
    }

}