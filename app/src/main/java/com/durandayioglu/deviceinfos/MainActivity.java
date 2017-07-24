package com.durandayioglu.deviceinfos;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button btnBilgi;
    TextView txtImsi, txtImei, txtIccd, txtApn, txtSignal, txtBatarya, txtKonum, txtAygitAdi, txtAcikOlanSure, txtOs, txtCpu;
    String imsi, imei, iccd, apn, signal, batarya, konum, aygitAdi, acikOlanSure, os, cpu;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private TelephonyManager telephonyManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DeviceInfos();

    }

    private BroadcastReceiver batteryInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batarya = "% " + String.valueOf(level);
        }
    };

    public void DeviceInfos() {

        txtImsi = (TextView) findViewById(R.id.txtImsi);
        txtImei = (TextView) findViewById(R.id.txtImei);
        txtIccd = (TextView) findViewById(R.id.txtICCD);
        txtApn = (TextView) findViewById(R.id.txtApn);
        txtSignal = (TextView) findViewById(R.id.txtSignal);
        txtBatarya = (TextView) findViewById(R.id.txtBattery);
        txtKonum = (TextView) findViewById(R.id.txtLocation);
        txtAygitAdi = (TextView) findViewById(R.id.txtDeviceName);
        txtAcikOlanSure = (TextView) findViewById(R.id.txtTime);
        txtOs = (TextView) findViewById(R.id.txtOs);
        txtCpu = (TextView) findViewById(R.id.txtCpu);
        btnBilgi = (Button) findViewById(R.id.btnGoster);


        os = Build.VERSION.RELEASE + " - " + "Api : " + Build.VERSION.SDK_INT;
        aygitAdi = Build.MANUFACTURER + " - " + Build.MODEL;

        this.registerReceiver(this.batteryInfo, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            getDeviceInfo();
        }


        btnBilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtImsi.setText("IMSI : " + imsi);
                txtImei.setText("IMEI : " + imei);
                txtIccd.setText("ICCD : " + iccd);
                txtApn.setText("APN : " + apn);
                txtSignal.setText("Sinyal Seviyesi : " + signal);
                txtKonum.setText("Konum : " + konum);
                txtAygitAdi.setText("Cihaz : " + aygitAdi);
                txtAcikOlanSure.setText("Açık Kalma Süresi : " + acikOlanSure);
                txtOs.setText("Android Sürümü : " + os);
                txtCpu.setText("Cpu : " + cpu);
                txtBatarya.setText("Batarya Seviyesi : " + batarya);

            }
        });


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceInfo();
        }
    }

    public void getDeviceInfo() {
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
        imsi = telephonyManager.getSubscriberId();
        iccd = telephonyManager.getSimSerialNumber();
    }


}
