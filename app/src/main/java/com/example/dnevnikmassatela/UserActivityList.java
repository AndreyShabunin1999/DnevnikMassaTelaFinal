package com.example.dnevnikmassatela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.graphics.Paint;

import static com.example.dnevnikmassatela.SpeedView.*;


public class UserActivityList extends AppCompatActivity {

    TextView textView;
    TextView textView2;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView4;
    com.example.dnevnikmassatela.SpeedView sv;
    Button insert;
    private EditText et_zamer;
    private final AppCompatActivity activity = UserActivityList.this;
    private AppCompatButton appCompatButtonLogin;
    private DatabaseHelper db = new DatabaseHelper(this);
    private String em;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Long imt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        initViews();

        //инициализировать и назначить переменную
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set imt selected
        bottomNavigationView.setSelectedItemId(R.id.nav_imt);

        //выполнить ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_food:
                        Intent intent = new Intent(UserActivityList.this, Foods.class);
                        intent.putExtra("EMAI", em);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_imt:
                        Intent intent5 = new Intent(UserActivityList.this, UserActivityList.class);
                        intent5.putExtra("EMAIL", em);
                        startActivity(intent5);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_stat:
                        Intent intent2 = new Intent(UserActivityList.this, stat.class);
                        intent2.putExtra("EMAI", em);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_upr:
                        Intent intent3 = new Intent(UserActivityList.this, Upr.class);
                        intent3.putExtra("EMAI", em);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_sovet:
                        Intent intent4 = new Intent(UserActivityList.this, Sovet.class);
                        intent4.putExtra("EMAI", em);
                        startActivity(intent4);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        sv = findViewById(R.id.indicator);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView4 = findViewById(R.id.textView4);
        et_zamer=findViewById(R.id.et_Zamer);
        insert = findViewById(R.id.btn_insert);

        Integer w =0;

        //Переменная для хранения email пользователя введенная в форме входа
        em = getIntent().getStringExtra("EMAIL");
        db.checkWeight(em);


        Cursor vt = db.getV();
        if(vt.getCount()==0){
            db.checkWeight(em);
            db.checkdatareg(em);
            String dar = db.getDareg();
            w = db.getVes();
            textView4.setText("Дата: "+ dar);
            textView.setText(w + " КГ");
        } else {
            StringBuffer bufferV = new StringBuffer();
            StringBuffer buffer = new StringBuffer();
            while (vt.moveToNext()) {
                bufferV.append(vt.getString(0));
                buffer.append("Дата: "+vt.getString(1)+"\n");
            }
            textView.setText(bufferV + " КГ");
            textView4.setText(buffer);
            String sh = bufferV.toString();
            w = Integer.parseInt(sh);
        }

        Integer h = db.getVusota();
        //Формулы Поля Брока для нормы веса
        if (h >= 155 && h <= 165) {
            textView7.setText("Лишний вес: " + (w-(h-100)) + " КГ");
            textView6.setText(h-100 + " КГ");
        }
        if (h > 165 && h <= 175) {
            textView7.setText("Лишний вес: " + (w-(h-105)) + " КГ");
            textView6.setText(h-105 + " КГ");
        }
        if (h > 176) {
            textView7.setText("Лишний вес: " + (w-(h-110)) + " КГ");
            textView6.setText(h-110 + " КГ");
        }

        //расчёт Индекса Массы тела
        Double h0= (double)h;
        Double h1 = (Math.pow((h0/100),2));
        Double w0 = (double)w;
        Double imt0 = w0 / h1;
        imt = Math.round(imt0);
        sv.setImt(imt);
        String strimt= Long.toString(imt);
        textView8.setText(strimt);

        if(imt<16.00){
            textView8.setText("Ваш Индекс Массы Тела: " + strimt + " Выраженный дефицит массы тела");
            textView.setTextColor(Color.parseColor("#8B0000"));
            textView7.setTextColor(Color.parseColor("#8B0000"));
        }
        if(imt>=17.00 && imt<=18.50){
            textView8.setText("Ваш Индекс Массы Тела: " + strimt + " Недостаточная масса тела");
            textView.setTextColor(Color.parseColor("#ff8800"));
            textView7.setTextColor(Color.parseColor("#ff8800"));
        }
        if(imt>=18.60 && imt<=25.00){
            textView8.setText("Ваш Индекс Массы Тела: " + strimt + " Норма");
            textView.setTextColor(Color.parseColor("#7FFF00"));
            textView7.setTextColor(Color.parseColor("#7FFF00"));
        }
        if(imt>=26.00 && imt<=30.00){
            textView8.setText("Ваш Индекс Массы Тела: " + strimt + " Избыточная масса тела (предожирение)");
            textView.setTextColor(Color.parseColor("#ff8800"));
            textView7.setTextColor(Color.parseColor("#ff8800"));
        }
        if(imt>=31.00 && imt<=35.00){
            textView8.setText("Ваш Индекс Массы Тела: " + strimt + " Ожирение 1 степени");
            textView.setTextColor(Color.parseColor("#ff2400"));
            textView7.setTextColor(Color.parseColor("#ff2400"));
        }
        if(imt>=36.00 && imt<=40.00){
            textView8.setText("Ваш Индекс Массы Тела: " + strimt + " Ожирение 2 степени");
            textView.setTextColor(Color.parseColor("#FF0000"));
            textView7.setTextColor(Color.parseColor("#FF0000"));
        }
        if(imt>40.00){
            textView8.setText("Ваш Индекс Массы Тела: " + strimt + " Ожирение 3 степени");
            textView.setTextColor(Color.parseColor("#8B0000"));
            textView7.setTextColor(Color.parseColor("#8B0000"));
        }
    }

    public Long getImt() {
        return imt;
    }

    private void initViews() {
        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);
        et_zamer = findViewById(R.id.et_Zamer);
    }

    public void onMyButtonClick(View view)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date();
        Integer id_user = db.getuId();
        String ves = et_zamer.getText().toString().trim();
        String dat = dateFormat.format(date);
        Boolean checkinsertdata = db.insertuserdata(id_user, ves, dat);
        if(checkinsertdata==true)
            Toast.makeText(UserActivityList.this, "Вставка успешно выполнена", Toast.LENGTH_SHORT).show();

        Cursor res = db.getdata();
        if(res.getCount()==0){
            Toast.makeText(UserActivityList.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("Дата: "+res.getString(0)+"\n");}
        textView4.setText(buffer);

        Cursor vt = db.getV();
        if(vt.getCount()==0){
            Toast.makeText(UserActivityList.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer bufferV = new StringBuffer();
        while(vt.moveToNext()){
            bufferV.append(vt.getString(0));}
        textView.setText(bufferV + " КГ");
        Intent intent2 = new Intent(UserActivityList.this, UserActivityList.class);
        intent2.putExtra("EMAIL", em);
        startActivity(intent2);
        overridePendingTransition(0,0);
    }
}