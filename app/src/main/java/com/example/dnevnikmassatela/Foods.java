package com.example.dnevnikmassatela;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.ArrayList;

import static java.lang.Math.round;

/**
 * Этот класс помогает вызывать запросы к FatSecret REST API для поиска продуктов питания и рецептов
 */

public class Foods extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
   TextView tvost, tvuglznach, tvbelokznach,tvzhirznach,tveating, tvkal,tvkal2,tvkal3,tvkal4, tvreckal,tvreckal2,tvreckal3,tvreckal4;
   ProgressBar progressugl, progressprot, progressfat;
   FrameLayout fr;
   private DatabaseHelper databaseHelper;
   private double kkal_br, prot_br, carbs_br, fat_br, kkal_din, prot_din, carbs_din, fat_din, kkal_uzh, prot_uzh, carbs_uzh, fat_uzh, kkal_per, prot_per, carbs_per, fat_per;
   private Integer w, h, vozr, belki, zhir, uglevod, znachpol, id, rez_kkal;
   private int kkal;
   private Double koef;
   private String gender, dr, email, LastClick, rez_carbs, rez_fat, rez_prot;
   SQLiteDatabase db;
   Spinner spinner;
   ImageView imageView, imageView1, imageView2, imageView3;
   SharedPreferences LastSelect, ko;
   SharedPreferences.Editor editor;
   FrameLayout zavtrak, obed, uzhin, perekus;
   Cursor brfCursor, dinCursor, uzhCursor, perCursor;
   ArrayList<Product> products = new ArrayList<Product>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        initViews();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        // из ресурсов выводим фото в framelayout
        Picasso.with(this).load(R.drawable.zavtrac).resize(600, 380).into(imageView);
        Picasso.with(this).load(R.drawable.obed).resize(600, 380).into(imageView1);
        Picasso.with(this).load(R.drawable.uzhin).resize(600, 380).into(imageView2);
        Picasso.with(this).load(R.drawable.perekus).resize(600, 380).into(imageView3);
        // получаем файл с ссылками LastSettings
        LastSelect = getSharedPreferences("LastSetting", Context.MODE_PRIVATE);
        String LastClick = LastSelect.getString("LastClick","Сидячий образ жизни");
        setLC(LastClick);

        String LK = LastSelect.getString("KO","1.2");
        double lastkoef = Double.parseDouble(LK);
        if (lastkoef == 0){
            setKoef(1.2);
        }
        setKoef(lastkoef);

        //получаем email
        Bundle arguments = getIntent().getExtras();
        email = arguments.get("EMAI").toString();
        setEmai(email);
        databaseHelper.checkWeight(email);
        databaseHelper.DataAndPol(email);
        //Получаем из бд высоту
        h = databaseHelper.getVusota();
        //Получаем из бд пол пользователя
        gender = databaseHelper.getPol();
        if(gender.equals("M")){
            znachpol = 5;
            setZnachpol(znachpol);
        } else if (gender.equals("Ж")){
            znachpol = 161;
            setZnachpol(znachpol);
        }
        //Получаем из бд др пользователя
        dr = databaseHelper.getDR();
        //Расчёт возраста пользователя
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            Date dateDR = dateFormat.parse(dr);
            vozr = date.getYear() - dateDR.getYear();
            setVozr(vozr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Получаем из бд высоту
        h = databaseHelper.getVusota();
        setH(h);

        //получаем вес
        Cursor vt = databaseHelper.getV();
        if(vt.getCount()==0){
            databaseHelper.checkWeight(email);
            w = databaseHelper.getVes();
            setW(w);
        } else {
            StringBuffer bufferV = new StringBuffer();
            while (vt.moveToNext()) {
                bufferV.append(vt.getString(0));
            }
            String sh = bufferV.toString();
            w = Integer.parseInt(sh);
            setW(w);
        }

        id = databaseHelper.getuId();
        setId(id);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.AcrivnostName, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //Задём значение из памяти телефона
        spinner.setSelection(adapter.getPosition(LastClick));

        zavtrak.setOnClickListener(myOnClickListener);
        obed.setOnClickListener(myOnClickListener);
        uzhin.setOnClickListener(myOnClickListener);
        perekus.setOnClickListener(myOnClickListener);

        //инициализировать и назначить переменную
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set imt selected
        bottomNavigationView.setSelectedItemId(R.id.nav_food);

        //выполнить ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_imt:
                        Intent intent = new Intent(Foods.this, UserActivityList.class);
                        intent.putExtra("EMAIL", email);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_stat:
                        Intent intent2 = new Intent(Foods.this, stat.class);
                        intent2.putExtra("EMAI", email);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_upr:
                        Intent intent3 = new Intent(Foods.this, Upr.class);
                        intent3.putExtra("EMAI", email);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_sovet:
                        Intent intent4 = new Intent(Foods.this, Sovet.class);
                        intent4.putExtra("EMAI", email);
                        startActivity(intent4);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_food:
                        Intent intent5 = new Intent(Foods.this, Foods.class);
                        intent5.putExtra("EMAI", email);
                        startActivity(intent5);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        UpdateTV();
    }

    private void initViews() {
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        tvuglznach = findViewById(R.id.tvuglznach);
        tveating = findViewById(R.id.tveating);
        fr = findViewById(R.id.fr);
        tvkal = findViewById(R.id.tvkal);
        tvkal2 = findViewById(R.id.tvkal2);
        tvkal3 = findViewById(R.id.tvkal3);
        tvkal4 = findViewById(R.id.tvkal4);
        tvost = findViewById(R.id.tvost);
        tvreckal = findViewById(R.id.tvreckal);
        tvreckal2 = findViewById(R.id.tvreckal2);
        tvreckal3 = findViewById(R.id.tvreckal3);
        tvreckal4 = findViewById(R.id.tvreckal4);
        tvuglznach = findViewById(R.id.tvuglznach);
        tvbelokznach = findViewById(R.id.tvbelokznach);
        tvzhirznach = findViewById(R.id.tvzhirznach);
        zavtrak = findViewById(R.id.zavtrak);
        obed = findViewById(R.id.obed);
        uzhin = findViewById(R.id.uzhin);
        perekus = findViewById(R.id.perekus);
        spinner = findViewById(R.id.spActivnost);
        progressfat = findViewById(R.id.progressfat);
        progressugl = findViewById(R.id.progressugl);
        progressprot = findViewById(R.id.progressprot);
    }


    @Override
    public void onResume() {
        super.onResume();
        UpdateTV();
    }

    public void UpdateTV(){
        // открываем подключение
        db = databaseHelper.getReadableDatabase();
        //получаем данные из бд (таблица Завтрак) в виде курсора
        brfCursor =  db.rawQuery("Select SUM(kkal), SUM(prot), SUM(carbs), SUM(fat) from TABLE_BREAKFAST where data = date('now','localtime') AND br_user = " + id, null);
        if(brfCursor.moveToFirst()){
            double kkal_br = brfCursor.getDouble(0);
            double prot_br = brfCursor.getDouble(1);
            double carbs_br = brfCursor.getDouble(2);
            double fat_br = brfCursor.getDouble(3);
            setBr(kkal_br,prot_br,carbs_br,fat_br);
        }
        //получаем данные из бд (таблица Обед) в виде курсора
        dinCursor =  db.rawQuery("Select SUM(kkal), SUM(prot), SUM(carbs), SUM(fat) from TABLE_DINNER where data = date('now','localtime') AND din_user = " + id, null);
        if(dinCursor.moveToFirst()){
            double kkal_din = dinCursor.getDouble(0);
            double prot_din = dinCursor.getDouble(1);
            double carbs_din = dinCursor.getDouble(2);
            double fat_din = dinCursor.getDouble(3);
            setDin(kkal_din,prot_din,carbs_din,fat_din);
        }
        //получаем данные из бд (таблица Ужин) в виде курсора
        uzhCursor =  db.rawQuery("Select SUM(kkal), SUM(prot), SUM(carbs), SUM(fat) from TABLE_UZHIN where data = date('now','localtime') AND uzh_user = " + id, null);
        if(uzhCursor.moveToFirst()){
            double kkal_uzh = uzhCursor.getDouble(0);
            double prot_uzh = uzhCursor.getDouble(1);
            double carbs_uzh = uzhCursor.getDouble(2);
            double fat_uzh = uzhCursor.getDouble(3);
            setUzh(kkal_uzh,prot_uzh,carbs_uzh,fat_uzh);
        }
        //получаем данные из бд (таблица Ужин) в виде курсора
        perCursor =  db.rawQuery("Select SUM(kkal), SUM(prot), SUM(carbs), SUM(fat) from TABLE_PEREKUS where data = date('now','localtime') AND per_user = " + id, null);
        if(perCursor.moveToFirst()){
            double kkal_per = perCursor.getDouble(0);
            double prot_per = perCursor.getDouble(1);
            double carbs_per = perCursor.getDouble(2);
            double fat_per = perCursor.getDouble(3);
            setPer(kkal_per,prot_per,carbs_per,fat_per);
        }
        //Общее количесвто каллорий за день
        Integer kkal_rez = (int) (kkal_br+kkal_din+kkal_per+kkal_uzh);
        String kkal_eat= kkal_rez.toString();
        tveating.setText(kkal_eat);
        //общее количество углеводов за день
        Integer carbs_rez = (int) (carbs_br+carbs_din+carbs_per+carbs_uzh);
        String carbs_eat= carbs_rez.toString();
        tvuglznach.setText(carbs_eat);
        //общее количество жиров за день
        Integer fat_rez = (int) (fat_br+fat_din+fat_per+fat_uzh);
        String fat_eat= fat_rez.toString();
        tvzhirznach.setText(fat_eat);
        //общее количество белков за день
        Integer prot_rez = (int) (prot_br+prot_din+prot_per+prot_uzh);
        String prot_eat= prot_rez.toString();
        tvbelokznach.setText(prot_eat);

        //вывод текущих каллорий за день по завтраку
        int kb = (int) (kkal_br);
        tvkal.setText(String.valueOf(kb) + " ккал");
        //вывод текущих каллорий за день по обеду
        int kd = (int) (kkal_din);
        tvkal2.setText(String.valueOf(kd) + " ккал");
        //вывод текущих каллорий за день по ужину
        int ku = (int) (kkal_uzh);
        tvkal3.setText(String.valueOf(ku) + " ккал");
        //вывод текущих каллорий за день по перекусу
        int kp = (int) (kkal_per);
        tvkal4.setText(String.valueOf(kp) + " ккал");
        //Получаем коэффициент активности для расчёта каллорий
        double koefic = koef;
        kkal = (int) ((((10 * w) + (6.25 * h) - (5 * vozr)-znachpol) * koefic)*0.8);
        int weight = databaseHelper.getVes();
        belki = (int) (weight * 0.8);
        zhir = (int) (weight * 0.5);
        uglevod = (int) (weight * 1.9);
        //расчитываем оставшиеся каллории
        Integer KAL = kkal - kkal_rez;
        //если кончились каллории меняем цвет на красный
        if (KAL < 0){
            fr.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        //вывод ост каллорий
        tvost.setText(String.valueOf(KAL));
        //завтрак
        int kalbr = (int) (kkal * 0.3);
        tvreckal.setText("Рекомендуется: " + String.valueOf(kalbr) + " ккал");
        //обед
        int kaldin = (int) (kkal * 0.4);
        tvreckal2.setText("Рекомендуется: " + String.valueOf(kaldin) + " ккал");
        //ужин
        int kaluzh = (int) (kkal * 0.25);
        tvreckal3.setText("Рекомендуется: " + String.valueOf(kaluzh) + " ккал");
        //перекус
        int kalper = (int) (kkal * 0.05);
        tvreckal4.setText("Рекомендуется: " + String.valueOf(kalper) + " ккал");
        tvuglznach.setText(carbs_rez + " / " + uglevod + "г");
        tvbelokznach.setText(prot_rez + " / " + belki + "г");
        tvzhirznach.setText(fat_rez + " / " + zhir + "г");
        Integer procent_ugl = Procent(uglevod,carbs_rez);
        progressugl.setProgress(procent_ugl);
        Integer procent_prot = Procent(belki,prot_rez);
        progressprot.setProgress(procent_prot);
        Integer procent_fat = Procent(zhir,fat_rez);
        progressfat.setProgress(procent_fat);
    }

    public Integer Procent(Integer a, Integer b){
        if (b==0){
            return 0;
        }
        double first = (double) a;
        double two = (double) b;
        double c = 100 + (((two - first) / first) * 100);
        Log.d("MyLog", "C : " + c);
        Integer procent = (int) round(c);
        return procent;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        editor = LastSelect.edit();
        String text = ((TextView) spinner.getSelectedView()).getText().toString();
        editor.putString("LastClick",text).apply();
        switch(position) {
            case 0:
                koef = 1.2;
                editor.putString("KO","1.2").apply();
                setKoef(koef);
                UpdateTV();
                break;
            case 1:
                koef = 1.375;
                editor.putString("KO","1.375").apply();
                setKoef(koef);
                UpdateTV();
                break;
            case 2:
                koef = 1.55;
                editor.putString("KO","1.55").apply();
                setKoef(koef);
                UpdateTV();
                break;
            case 3:
                koef = 1.725;
                editor.putString("KO","1.725").apply();
                setKoef(koef);
                UpdateTV();
                break;
            case 4:
                koef = 1.9;
                editor.putString("KO","1.9").apply();
                setKoef(koef);
                UpdateTV();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.zavtrak:
                    Toast.makeText(Foods.this ,"Вы нажали на завтрак", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Foods.this, Zavtrak.class);
                    intent.putExtra("EMAIL", getEmail());
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.obed:
                    Toast.makeText(Foods.this ,"Вы нажали на обед", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(Foods.this, Obed.class);
                    intent2.putExtra("EMAIL", email);
                    startActivity(intent2);
                    overridePendingTransition(0,0);
                    break;
                case R.id.uzhin:
                    Toast.makeText(Foods.this ,"Вы нажали на ужин", Toast.LENGTH_SHORT).show();
                    Intent intent3 = new Intent(Foods.this, Uzhin.class);
                    intent3.putExtra("EMAIL", email);
                    startActivity(intent3);
                    overridePendingTransition(0,0);
                    break;
                case R.id.perekus:
                    Toast.makeText(Foods.this ,"Вы нажали на перекус", Toast.LENGTH_SHORT).show();
                    Intent intent4 = new Intent(Foods.this, Perekus.class);
                    intent4.putExtra("EMAIL", email);
                    startActivity(intent4);
                    overridePendingTransition(0,0);
                    break;
            }
        }
    };
    public String getEmail() {
        return email;
    }
    public void setW(Integer w) {
        this.w = w;
    }
    public void setEmai(String email) {
        this.email = email;
    }
    public void setH(Integer h) {
        this.h = h;
    }
    public void setZnachpol(Integer znachpol) {
        this.znachpol = znachpol;
    }
    public void setVozr(Integer vozr) {
        this.vozr = vozr;
    }
    public void setLC(String LastClick) {
        this.LastClick = LastClick;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setKoef(double koef) {
        this.koef = koef;
    }
    public void setBr(double kkal, double prot, double carbs, double fat){
        this.kkal_br = kkal;
        this.prot_br = prot;
        this.carbs_br = carbs;
        this.fat_br = fat;
    }
    public void setDin(double kkal, double prot, double carbs, double fat){
        this.kkal_din = kkal;
        this.prot_din = prot;
        this.carbs_din = carbs;
        this.fat_din = fat;
    }
    public void setUzh(double kkal, double prot, double carbs, double fat){
        this.kkal_uzh = kkal;
        this.prot_uzh = prot;
        this.carbs_uzh = carbs;
        this.fat_uzh = fat;
    }
    public void setPer(double kkal, double prot, double carbs, double fat){
        this.kkal_per = kkal;
        this.prot_per = prot;
        this.carbs_per = carbs;
        this.fat_per = fat;
    }
}
