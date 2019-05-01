package com.example.androidappv2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import android.provider.ContactsContract;

import static android.provider.ContactsContract.Intents.Insert.NAME;
import static android.view.View.VISIBLE;


public class sendLoginActivity extends AppCompatActivity {
//com.example.smstest
    private static final String CHANNEL_ID = "5";
    int i = 0;
    ImageChangeBroadcastReceiver imageChangeBroadcastReceiver;
    SMSChangeBroadcastReceiver smsRecv;
    String username = "";
    String password = "";
    String lastContact = "";
    Boolean muted = false;
    ArrayList<String> recNotifs = new ArrayList();
    String number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_login);
        createNotificationChannel();
        ToggleButton toggle = (ToggleButton) findViewById(R.id.muteToggle);
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        Boolean defaultValue = false;
        muted = sharedPref.getBoolean(getString(R.string.muted), defaultValue);
        final SharedPreferences.Editor editor = sharedPref.edit();
        if(muted == true) {
            toggle.setChecked(true);
        }
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d("Toggle", "On");
                    muted = true;
                    editor.putBoolean(getString(R.string.muted), muted);
                    editor.commit();
                } else {
                    Log.d("Toggle", "Off");
                    muted = false;
                    editor.putBoolean(getString(R.string.muted), muted);
                    editor.commit();
                }
            }
        });


        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.UNAMEEXTRAMSG);
        password = intent.getStringExtra(MainActivity.PWORDEXTRAMSG);
        Intent serviceIntent = new Intent(this,NotificationService.class);
        startService(serviceIntent);
        imageChangeBroadcastReceiver = new ImageChangeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.firstnotificationtest");
        registerReceiver(imageChangeBroadcastReceiver,intentFilter);

        sendBroadcast(intent);

        smsRecv = new SMSChangeBroadcastReceiver();
        IntentFilter intentF = new IntentFilter();
        intentF.addAction("com.example.smstest");
        registerReceiver(smsRecv,intentF);


        Thread t = new Thread() {
            public void run() {

                DataInputStream in = socketClass.getDIS();

                while (true) {
                    if(muted == false) {
                        try {
                            int size = in.readInt();
                            byte[] buffer = new byte[size];
                            in.read(buffer);

                            String received = new String(buffer, "UTF8");
                            Log.d("Received:", received);
                            String[] spl = received.split(Character.toString((char) 31));
                            //spl[0] is username@devicename
                            String dev = spl[1];
                            //spl[1] is typ
                            String source = spl[2];
                            //spl[2] is tit
                            String title = spl[3];
                            String tex = spl[4];
                            //spl[3] is tex

                            if (received.contains("RESMS")) {
                                sendSMSNoView(title, tex);
                            } else if (received.contains("SMS")) {
                                lastContact = title;
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        TextView txt = (TextView) findViewById(R.id.responseText);
                                        txt.setVisibility(VISIBLE);
                                        Button bttn = (Button) findViewById(R.id.sendButton);
                                        bttn.setVisibility(VISIBLE);

                                    }
                                });


                            }



                            if(spl.length >= 4) {
                                sendNotification(title, tex, source);
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        t.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imageChangeBroadcastReceiver);
        unregisterReceiver(smsRecv);
    }

    public void sendSMSNoView(String cont, String toSend) throws SecurityException{
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , cont);
        smsIntent.putExtra("sms_body"  , toSend);
        startActivity(smsIntent);
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("+15059188014", null, toSend, null, null);
    }

    public void sendSMS(View view) {
        //SmsManager smsManager = SmsManager.getDefault();
        TextView txt = (TextView) findViewById(R.id.responseText);
        String toSend = txt.getText().toString();
        //theoretically will send te contents of toSend
        //smsManager.sendTextMessage("+15059188014", null, toSend, null, null);
        sendNotif mt = new sendNotif();
        mt.message = "RESMS" + Character.toString((char) 31) + username + "@" + Build.MODEL + Character.toString((char) 31) + "response" + Character.toString((char) 31) + lastContact + Character.toString((char) 31) + toSend;
        mt.execute();
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                TextView txt = (TextView) findViewById(R.id.responseText);
                txt.setVisibility(View.INVISIBLE);
                Button bttn = (Button) findViewById(R.id.sendButton);
                bttn.setVisibility(View.INVISIBLE);

            }
        });

    }

    public class SMSChangeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Sampletest","test");
            number = intent.getStringExtra("Phone");
            Log.d("Number", number);
        }
    }

    public class ImageChangeBroadcastReceiver extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {
            String tit = intent.getStringExtra("Notification Title");
            CharSequence tex = intent.getCharSequenceExtra("Notification Text");
            String typ = intent.getStringExtra("Notification Type");

            if(tex != null & tit != null & typ != null) {
                if(muted == false & (typ.compareTo("com.example.androidappv2") != 0) & (!typ.contains("dialer")) & (!typ.contains("screenshot")) & (!typ.contains("battery")) & (!typ.contains("android"))) {

                        sendNotif mt = new sendNotif();
                        if(typ.contains("text") || typ.contains("sms")) {

                            while(number.equals("")) {
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                            Log.d("Number After sleep?",number);
                            mt.message = "SMS" + Character.toString((char) 31) + username + "@" + Build.MODEL + Character.toString((char) 31) + typ + Character.toString((char) 31) + number + Character.toString((char) 31) + tex.toString();
                        }
                        else {
                            mt.message = "NOTIF" + Character.toString((char) 31) + username + "@" + Build.MODEL + Character.toString((char) 31) + typ + Character.toString((char) 31) + tit + Character.toString((char) 31) + tex.toString();

                        }

                        if(recNotifs.size() < 6 & !recNotifs.contains(mt.message)) {
                            recNotifs.add(mt.message);
                            mt.execute();
                        }
                        else {
                            if(recNotifs.contains(mt.message)) {

                            }
                            else {
                                recNotifs.remove(0);
                                recNotifs.add(mt.message);
                                mt.execute();
                            }
                        }

//                        mt.execute();
                        i++;
                        i = i % 15;
                }
            }
        }
    }

    class sendNotif extends AsyncTask<Void,Void,Void> {
        String message = "Notification not transmitted properly...";
        @Override
        protected Void doInBackground(Void... Params) {

            String receivedMessage = "not what i want";
            try {
                String toSend = message;
                Socket s = socketClass.getSocket();

//                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                DataOutputStream out = socketClass.getDOS();

                out.writeInt(toSend.length());
                out.write(toSend.getBytes());

                Log.d("Message", toSend);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;

        }
    }

    public void sendNotification(final String title, final String content, final String src) {

        //Get an instance of NotificationManager//
        Log.d("NOTIF", "sent system notificaiton");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);


// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                TextView app = (TextView) findViewById(R.id.textView2);
                TextView tit = (TextView) findViewById(R.id.textView3);
                TextView cont = (TextView) findViewById(R.id.textView4);
                app.setText(src);
                tit.setText(title);
                cont.setText(content);
            }
        });
        /*
        */

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static byte [] encrypt(byte [] data, SecretKey sec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher ciph = Cipher.getInstance("ES/ECB/PKCS7Padding");
        ciph.init(Cipher.ENCRYPT_MODE, sec);

        return ciph.doFinal(data);
    }

    private static byte [] decrypt(byte [] data, SecretKey sec) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher ciph = Cipher.getInstance("ES/ECB/PKCS7 Padding");
        ciph.init(Cipher.DECRYPT_MODE, sec);

        return ciph.doFinal(data);
    }


}
