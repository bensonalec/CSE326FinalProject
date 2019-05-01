package com.example.androidappv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class registerActivity extends AppCompatActivity {

    public static EditText unameBox;
    public static EditText passBox;
    public static EditText emailBox;
    public static EditText pass2Box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unameBox = (EditText) findViewById(R.id.unameField);
        passBox = (EditText) findViewById(R.id.pwordField);
        emailBox = (EditText) findViewById(R.id.emailField);
        pass2Box = (EditText) findViewById(R.id.pass2Field);


    }

    public void registerClick(View view) throws IOException, NoSuchAlgorithmException, InterruptedException {
        String userName = unameBox.getText().toString();
        String emailAddress = emailBox.getText().toString();
        String passwordInit = passBox.getText().toString();
        String passwordConfirm = pass2Box.getText().toString();
        Log.d("In reg","Reg");
        if(passwordInit.equals(passwordConfirm)) {
            byte [] passBytes = passwordInit.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte [] encBytes = md.digest(passBytes);
            String enc = new String(encBytes);

            String frame = "REGISTER" + Character.toString((char) 31) + userName + "@" + Build.MODEL + Character.toString((char) 31) + passwordInit + Character.toString((char) 31) + emailAddress;
            sendNotif mt = new sendNotif();
            mt.message = frame;
            Log.d("Sending frame...","send");
            mt.execute();
            Thread.sleep(5000);
            if(!mt.received.equals("")) {
                Log.d("Received","skrrt");
                if(mt.received.contains("SUCCESS")) {
                    //route back to initial screen, programatically return to parent page
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                } else if(mt.received.contains("FAILURE")) {
                    TextView err = (TextView) findViewById(R.id.errorView);
                    err.setText("Unable to register");
                } else {
                    TextView err = (TextView) findViewById(R.id.errorView);
                    err.setText("Unable to register");
                    //something went very wrong
                }

            }

        }


    }
}

class sendNotif extends AsyncTask<Void,Void,Void> {

    String message = "Notification not transmitted properly...";
    Boolean fin = false;
    String received = "";
    Socket socket = null;



    @Override
    protected Void doInBackground(Void... Params) {
        String receivedMessage = "not what i want";
        try {
            Log.d("Info",message);
            Socket s = null;
            Log.d("Inner","skrrt");
            s = socketClass.getSocket();
//            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            DataOutputStream out = socketClass.getDOS();
            //           DataInputStream in = new DataInputStream(s.getInputStream());
            DataInputStream in = socketClass.getDIS();
            out.writeInt(message.length());
            out.write(message.getBytes());
            Log.d("SIZE", Integer.toString(message.getBytes().length));

            int sz = in.readInt();
            byte [] rec = new byte[sz];
            in.read(rec);
            received = new String(rec, "UTF8");
            fin = true;

        } catch (UnknownHostException e) {
            fin = true;
        } catch (IOException e) {
            fin = true;
        }


        return null;

    }
}

