package com.example.androidappv2;

import android.Manifest;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)

public class MainActivity extends AppCompatActivity {

    public static final String UNAMEEXTRAMSG = "com.example.AndroidAppV2.USERNAME";
    public static final String PWORDEXTRAMSG = "com.example.AndroidAppV2.PASSWORD";
    public static final String SOCKEXTRAMSG = "com.example.AndroidAppV2.SOCKET";
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] PERMISSIONS = {
                android.Manifest.permission.SEND_SMS,
                Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
                android.Manifest.permission.INTERNET,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_CONTACTS
        };
        ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        socketClass sockC = new socketClass();
        sockC.execute();

    }

    public void login(View view) throws NoSuchAlgorithmException, UnsupportedEncodingException, InterruptedException {
        String frame = "";


        EditText unameBox = (EditText) findViewById(R.id.userNameBox);
        EditText passBox = (EditText) findViewById(R.id.passwordBox);
        TextView errorBox = (TextView) findViewById(R.id.errorView);
        String username = unameBox.getText().toString();
        String password = passBox.getText().toString();



        byte [] passBytes = password.getBytes("UTF8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte [] encBytes = md.digest(passBytes);
        String enc = new String(encBytes);

        frame = "LOGIN" + Character.toString((char) 31) + username + "@" + Build.MODEL + Character.toString((char) 31) + password;
        sendNotif mt = new sendNotif();
        mt.message = frame;
        mt.execute();

        Thread.sleep(5000);
        if(!mt.received.equals("") & (mt.fin == true)) {
            if(mt.received.contains("SUCCESS")) {

//                Credential credential = new Credential.Builder(username).setPassword(password).build();

                Intent intent = new Intent(this, sendLoginActivity.class);
                intent.putExtra(UNAMEEXTRAMSG, username);
                intent.putExtra(PWORDEXTRAMSG,password);
//                intent.putExtra(SOCKEXTRAMSG, (Serializable) mt.socket);
                startActivity(intent);

            } else if(mt.received.contains("FAILURE")) {
                errorBox.setText("Incorrect Username or Password");
                //display message "Incorrect username or password"
                //raise on screen error message using alert library
            } else {
                errorBox.setText(mt.received);
                //something went very wrong
            }

        } else {
            errorBox.setText("Timed out!");
        }

    }

    public void regActivity(View view) {
        Intent intent = new Intent(this, registerActivity.class);
        startActivity(intent);

    }


}

