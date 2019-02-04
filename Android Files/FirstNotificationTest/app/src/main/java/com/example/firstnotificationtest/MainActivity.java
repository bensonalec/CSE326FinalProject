package com.example.firstnotificationtest;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)

public class MainActivity extends AppCompatActivity {

    int i = 0;
    TextView title,text,notType;
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

    }
    notificationN[] nots = new notificationN[15];
    public class ImageChangeBroadcastReceiver extends BroadcastReceiver {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {
            String tit = intent.getStringExtra("Notification Title");
            CharSequence tex = intent.getCharSequenceExtra("Notification Text");
            String typ = intent.getStringExtra("Notification Type");
            nots[i] = new notificationN();
            nots[i].title = tit;
            nots[i].text = tex;
            nots[i].type = typ;
            //title.setText(nots[i].title);
            //text.setText(nots[i].text.toString());
            //notType.setText(nots[i].type);

            i++;
            i = i % 15;
        }
    }

    public class notificationN {
        String title;
        CharSequence text;
        String type;
    }
}
