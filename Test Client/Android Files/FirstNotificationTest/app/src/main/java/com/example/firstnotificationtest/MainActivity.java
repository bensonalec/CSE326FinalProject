package com.example.firstnotificationtest;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.Stack;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)

public class MainActivity extends AppCompatActivity {

    int i = 0;
    TextView title,text,notType,iter;
    String username = "bensonalec";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this,NotificationService.class);
        startService(serviceIntent);

        ImageChangeBroadcastReceiver imageChangeBroadcastReceiver = new ImageChangeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.firstnotificationtest");
        registerReceiver(imageChangeBroadcastReceiver,intentFilter);
        title = (TextView) this.findViewById(R.id.toChange);
        text = (TextView) this.findViewById(R.id.textLoc);
        notType = (TextView) this.findViewById(R.id.typeContainer);
        iter = (TextView) this.findViewById(R.id.iter);
        title.setText(Build.MODEL);

    }
    notificationN[] nots = new notificationN[15];
    public class ImageChangeBroadcastReceiver extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {
            HelloThread test = new HelloThread();
            test.intent = intent;
            test.f = i;
            test.start();
            i++;
            i = i % 15;

        }
    }
    public Stack<notificationN> stk = new Stack<notificationN>();
    public class HelloThread extends Thread {
        Intent intent = null;
        int f = 0;
        public void run() {
            if (intent != null) {
                String tit = intent.getStringExtra("Notification Title");
                CharSequence tex = intent.getCharSequenceExtra("Notification Text");
                String typ = intent.getStringExtra("Notification Type");

                /*
                notificationN notTemp = new notificationN();
                notTemp.title = tit;
                notTemp.text = tex;
                notTemp.type = typ;
                stk.push(notTemp);
                */

                myTask mt = new myTask();
                mt.message = Build.MODEL+":"+username + "\t" + typ + "\t" + tit + "\t" + tex.toString();
                mt.execute();
            }
        }

    }
    public class notificationN {
        String title;
        CharSequence text;
        String type;
    }

    class myTask extends AsyncTask<Void,Void,Void> {
        String message = "Notification not transmitted properly...";
        @Override
        protected Void doInBackground(Void... Params) {
            try {
                Socket s = new Socket("67.0.195.29", 5000);

                PrintWriter output = new PrintWriter(s.getOutputStream());
                output.write(message);
                output.flush();
                output.close();

                s.close();

            } catch (UnknownHostException e) {
                text.setText(e.toString());
            } catch (IOException e) {
                text.setText(e.toString());
            }
            return null;

        }

        /*
       */
    }
}
