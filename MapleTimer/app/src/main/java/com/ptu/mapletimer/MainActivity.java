package com.ptu.mapletimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    int id = 1;
    EditText input;
    Button showBtn;
    int userTime;
    int userTime2;

    String userTimeStr;
    String userTimeStr2;

    boolean actTwoHour;
    boolean actOneHour;
    boolean actThiryMin;
    boolean actFifteenMin;
    boolean actUserTimer;
    boolean actUserTimer2;

    TextView twoHour;
    TextView oneHour;
    TextView thiryMin;
    TextView fifteenMin;
    TextView userTimerTxt;
    TextView userTimerTxt2;

    CheckBox loops;
    CheckBox pushCheck;

    CountDownTimer twoHourTimer = null;
    CountDownTimer oneHourTimer = null;
    CountDownTimer thirtyMinTimer = null;
    CountDownTimer fifteenMinTimer = null;
    CountDownTimer userTimer = null;
    CountDownTimer userTimer2 = null;

    private long time= 0;
    View dummy = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actTwoHour = false;
        actOneHour = false;
        actThiryMin = false;
        actFifteenMin = false;
        actUserTimer = false;
        actUserTimer2 = false;

        twoHour = (TextView)findViewById(R.id.twoHour);
        oneHour = (TextView)findViewById(R.id.oneHour);
        thiryMin = (TextView)findViewById(R.id.thiryMin);
        fifteenMin = (TextView)findViewById(R.id.fifteenMin);
        userTimerTxt = (TextView)findViewById(R.id.userTimer);
        userTimerTxt2 = (TextView)findViewById(R.id.userTimer2);

        loops = (CheckBox)findViewById(R.id.loops);
        pushCheck = (CheckBox)findViewById(R.id.pushCheck);

        userTimeStr = "00:00:00";
        userTimeStr2 = "00:00:00";


    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로 가기 버튼을 한번 더 누르면 타이머와 알림이 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            allCancel(dummy);
            finish();
        }
    }

    public void pushAlarm(String title,String text){
        if(pushCheck.isChecked()) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

            //PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, new Intent(getApplicationContext(),MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
            builder.setSmallIcon(R.drawable.smallicon);
            builder.setContentTitle(title);
            builder.setContentText(text);
            builder.setDefaults(Notification.DEFAULT_ALL);
            builder.setColor(Color.RED);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            notificationManager.notify(id, builder.build());
            id++;
        }
    }

    public void showDialog(final View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("원하는 시간을 입력하세요. (초)");
        builder.setMessage("시간 (초), 숫자만 입력해주세요.");

        input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userTime = Integer.parseInt(input.getText().toString());
                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"숫자를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                stopUserTimer(v);
                userTimerTxt.setTextColor(Color.BLACK);
                String Hour = (userTime/(60*60)<10)?"0"+Integer.toString(userTime/(60*60)):Integer.toString(userTime/(60*60));
                String Min = (userTime/60%60<10)?"0"+Integer.toString(userTime/60%60):Integer.toString(userTime/60%60);
                String Sec = (userTime%60<10)?"0"+Integer.toString(userTime%60):Integer.toString(userTime%60);
                userTimeStr = Hour + ":" + Min + ":" + Sec;
                userTimerTxt.setText(userTimeStr);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = builder.create();

        showBtn = findViewById(R.id.timeSetting);
        ad.show();
    }

    public void showDialog2(final View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("원하는 시간을 입력하세요. (초)");
        builder.setMessage("시간 (초), 숫자만 입력해주세요.");

        input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userTime2 = Integer.parseInt(input.getText().toString());
                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"숫자를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                stopUserTimer2(v);
                userTimerTxt2.setTextColor(Color.BLACK);
                String Hour = (userTime2/(60*60)<10)?"0"+Integer.toString(userTime2/(60*60)):Integer.toString(userTime2/(60*60));
                String Min = (userTime2/60%60<10)?"0"+Integer.toString(userTime2/60%60):Integer.toString(userTime2/60%60);
                String Sec = (userTime2%60<10)?"0"+Integer.toString(userTime2%60):Integer.toString(userTime2%60);
                userTimeStr2 = Hour + ":" + Min + ":" + Sec;
                userTimerTxt2.setText(userTimeStr2);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = builder.create();

        showBtn = findViewById(R.id.timeSetting);
        ad.show();
    }

    public void twoHourCountDownTimer(final View v) {
        if(actTwoHour == false) {
            twoHour.setTextColor(Color.BLACK);
            actTwoHour = true;
            int time = 7200000;

            twoHourTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    twoHour.setText(
                            String.format("%02d:%02d:%02d",
                                    (int) (millisUntilFinished / (1000 * 60 * 60)) % 24,
                                    (int) (millisUntilFinished / (1000 * 60)) % 60,
                                    (int) (millisUntilFinished / 1000) % 60
                            ));
                    if (millisUntilFinished <= 10000) {
                        twoHour.setTextColor(Color.RED);
                    }
                }

                @Override
                public void onFinish() {
                    actTwoHour = false;
                    if (loops.isChecked()) {
                        twoHourCountDownTimer(v);
                        pushAlarm("2시간 타이머가 종료되었습니다.","자동 반복이 시작됩니다.");
                    }
                    else{
                        pushAlarm("2시간 타이머가 종료되었습니다.","도핑 후 끝난 타이머 재시작 버튼을 눌러주세요.");
                    }
                    twoHour.setText("버프 종료");

                }
            }.start();
        }
    }

    public void oneHourCountDownTimer(final View v) {
        if(actOneHour == false) {
            oneHour.setTextColor(Color.BLACK);
            actOneHour = true;
            int time = 3600000;

            oneHourTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    oneHour.setText(
                            String.format("%02d:%02d:%02d",
                                    (int) (millisUntilFinished / (1000 * 60 * 60)) % 24,
                                    (int) (millisUntilFinished / (1000 * 60)) % 60,
                                    (int) (millisUntilFinished / 1000) % 60
                            ));
                    if (millisUntilFinished <= 10000) {
                        oneHour.setTextColor(Color.RED);
                    }
                }

                @Override
                public void onFinish() {
                    actOneHour = false;
                    if (loops.isChecked()) {
                        oneHourCountDownTimer(v);
                        pushAlarm("1시간 타이머가 종료되었습니다.","자동 반복이 시작됩니다.");
                    }
                    else{
                        pushAlarm("1시간 타이머가 종료되었습니다.","도핑 후 종료된 타이머 재시작 버튼을 눌러주세요.");
                    }
                    oneHour.setText("버프 종료");

                }
            }.start();
        }
    }

    public void thirtyMinCountDownTimer(final View v) {
        if(actThiryMin == false) {
            thiryMin.setTextColor(Color.BLACK);
            actThiryMin = true;
            int time = 1800000;

            thirtyMinTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    thiryMin.setText(
                            String.format("%02d:%02d:%02d",
                                    (int) (millisUntilFinished / (1000 * 60 * 60)) % 24,
                                    (int) (millisUntilFinished / (1000 * 60)) % 60,
                                    (int) (millisUntilFinished / 1000) % 60
                            ));
                    if (millisUntilFinished <= 10000) {
                        thiryMin.setTextColor(Color.RED);
                    }
                }

                @Override
                public void onFinish() {
                    actThiryMin = false;
                    if (loops.isChecked()) {
                        thirtyMinCountDownTimer(v);
                        pushAlarm("30분 타이머가 종료되었습니다.","자동 반복이 시작됩니다.");
                    }
                    else{
                        pushAlarm("30분 타이머가 종료되었습니다.","도핑 후 종료된 타이머 재시작 버튼을 눌러주세요.");
                    }
                    thiryMin.setText("버프 종료");

                }
            }.start();
        }
    }

    public void fifteenMinCountDownTimer(final View v) {
        if(actFifteenMin == false) {
            fifteenMin.setTextColor(Color.BLACK);
            actFifteenMin = true;
            int time = 900000;

            fifteenMinTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    fifteenMin.setText(
                            String.format("%02d:%02d:%02d",
                                    (int) (millisUntilFinished / (1000 * 60 * 60)) % 24,
                                    (int) (millisUntilFinished / (1000 * 60)) % 60,
                                    (int) (millisUntilFinished / 1000) % 60
                            ));
                    if (millisUntilFinished <= 10000) {
                        fifteenMin.setTextColor(Color.RED);
                    }
                }

                @Override
                public void onFinish() {
                    actFifteenMin = false;
                    if (loops.isChecked()) {
                        fifteenMinCountDownTimer(v);
                        pushAlarm("15분 타이머가 종료되었습니다.","자동 반복이 시작됩니다.");
                    }
                    else{
                        pushAlarm("15분 타이머가 종료되었습니다.","도핑 후 종료된 타이머 재시작 버튼을 눌러주세요.");
                    }
                    fifteenMin.setText("버프 종료");

                }
            }.start();
        }
    }

    public void userCountDownTimer(final View v) {
        if(actUserTimer == false && !userTimerTxt.getText().toString().equals("00:00:00")) {
            userTimerTxt.setTextColor(Color.BLACK);
            actUserTimer = true;
            String[] temp = userTimeStr.split(":");
            int time = (Integer.parseInt(temp[0])*1000*60*60) + (Integer.parseInt(temp[1])*1000*60) + (Integer.parseInt(temp[2])*1000);

            userTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    userTimerTxt.setText(
                            String.format("%02d:%02d:%02d",
                                    (int) (millisUntilFinished / (1000 * 60 * 60)) % 24,
                                    (int) (millisUntilFinished / (1000 * 60)) % 60,
                                    (int) (millisUntilFinished / 1000) % 60
                            ));
                    if (millisUntilFinished <= 10000) {
                        userTimerTxt.setTextColor(Color.RED);
                    }
                }

                @Override
                public void onFinish() {
                    actUserTimer = false;
                    userTimerTxt.setText("버프 종료");
                    if (loops.isChecked()) {
                        userCountDownTimer(v);
                        pushAlarm("사용자 지정 타이머1이 종료되었습니다.","자동 반복이 시작됩니다.");
                    }
                    else{
                        pushAlarm("사용자 지정 타이머1이 종료되었습니다.","도핑 후 종료된 타이머 재시작 버튼을 눌러주세요.");
                    }


                }
            }.start();
        }
    }

    public void userCountDownTimer2(final View v) {
        if(actUserTimer2 == false && !userTimerTxt2.getText().toString().equals("00:00:00")) {
            userTimerTxt2.setTextColor(Color.BLACK);
            actUserTimer2 = true;
            String[] temp = userTimeStr2.split(":");
            int time = (Integer.parseInt(temp[0])*1000*60*60) + (Integer.parseInt(temp[1])*1000*60) + (Integer.parseInt(temp[2])*1000);

            userTimer2 = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    userTimerTxt2.setText(
                            String.format("%02d:%02d:%02d",
                                    (int) (millisUntilFinished / (1000 * 60 * 60)) % 24,
                                    (int) (millisUntilFinished / (1000 * 60)) % 60,
                                    (int) (millisUntilFinished / 1000) % 60
                            ));
                    if (millisUntilFinished <= 10000) {
                        userTimerTxt2.setTextColor(Color.RED);
                    }
                }

                @Override
                public void onFinish() {
                    actUserTimer2 = false;
                    userTimerTxt2.setText("버프 종료");
                    if (loops.isChecked()) {
                        userCountDownTimer2(v);
                        pushAlarm("사용자 지정 타이머2가 종료되었습니다.","자동 반복이 시작됩니다.");
                    }
                    else{
                        pushAlarm("사용자 지정 타이머2가 종료되었습니다.","도핑 후 종료된 타이머 재시작 버튼을 눌러주세요.");
                    }


                }
            }.start();
        }
    }

    public void stopTwoHourTimer(View v){
        if(actTwoHour == true) {
            twoHourTimer.cancel();
            twoHour.setText("02:00:00");
            actTwoHour = false;
        }
    }

    public void stopOneHourTimer(View v){
        if(actOneHour == true) {
            oneHourTimer.cancel();
            oneHour.setText("01:00:00");
            actOneHour = false;
        }
    }
    public void stopThiryMinTimer(View v){
        if(actThiryMin == true) {
            thirtyMinTimer.cancel();
            thiryMin.setText("00:30:00");
            actThiryMin = false;
        }
    }
    public void stopFifteenTimer(View v){
        if(actFifteenMin == true) {
            fifteenMinTimer.cancel();
            fifteenMin.setText("00:15:00");
            actFifteenMin = false;
        }
    }

    public void stopUserTimer(View v){
        if(actUserTimer == true) {
            userTimer.cancel();
            userTimerTxt.setText(userTimeStr);
            actUserTimer = false;
        }
    }

    public void stopUserTimer2(View v){
        if(actUserTimer2 == true) {
            userTimer2.cancel();
            userTimerTxt2.setText(userTimeStr2);
            actUserTimer2 = false;
        }
    }

    public void allStart(View v){
        twoHourCountDownTimer(v);
        oneHourCountDownTimer(v);
        thirtyMinCountDownTimer(v);
        fifteenMinCountDownTimer(v);
        userCountDownTimer(v);
        userCountDownTimer2(v);
    }

    public void allCancel(View v){
        stopTwoHourTimer(v);
        stopOneHourTimer(v);
        stopThiryMinTimer(v);
        stopFifteenTimer(v);
        stopUserTimer(v);
        stopUserTimer2(v);
    }

    public void finishBufStart(View v){
        if(twoHour.getText().toString().equals("버프 종료")){
            twoHourCountDownTimer(v);
        }
        if(oneHour.getText().toString().equals("버프 종료")){
            oneHourCountDownTimer(v);
        }
        if(thiryMin.getText().toString().equals("버프 종료")){
            thirtyMinCountDownTimer(v);
        }
        if(fifteenMin.getText().toString().equals("버프 종료")){
            fifteenMinCountDownTimer(v);
        }
        if(userTimerTxt.getText().toString().equals("버프 종료")){
            userCountDownTimer(v);
        }
        if(userTimerTxt2.getText().toString().equals("버프 종료")){
            userCountDownTimer2(v);
        }
    }


}
