package com.example.dnevnikmassatela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;


public class Sovet extends AppCompatActivity {
    FrameLayout state1, state2, state3, state4, state5, state6, state7, state8;
    ImageView imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sovet);

        Bundle arguments = getIntent().getExtras();
        String email = arguments.get("EMAI").toString();

        state1 = findViewById(R.id.state1);
        state2 = findViewById(R.id.state2);
        state3 = findViewById(R.id.state3);
        state4 = findViewById(R.id.state4);
        state5 = findViewById(R.id.state5);
        state6 = findViewById(R.id.state6);
        state7 = findViewById(R.id.state7);
        state8 = findViewById(R.id.state8);
        imageView=findViewById(R.id.imageView);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        imageView6=findViewById(R.id.imageView6);
        imageView7=findViewById(R.id.imageView7);
        imageView8=findViewById(R.id.imageView8);

        // из ресурсов
        Picasso.with(this).load(R.drawable.state1).resize(600, 400).into(imageView);
        Picasso.with(this).load(R.drawable.state2).resize(600, 400).into(imageView2);
        Picasso.with(this).load(R.drawable.state3).resize(600, 400).into(imageView3);
        Picasso.with(this).load(R.drawable.state4).resize(600, 400).into(imageView4);
        Picasso.with(this).load(R.drawable.state5).resize(600, 400).into(imageView5);
        Picasso.with(this).load(R.drawable.state6).resize(600, 400).into(imageView6);
        Picasso.with(this).load(R.drawable.state7).resize(600, 400).into(imageView7);
        Picasso.with(this).load(R.drawable.state8).resize(600, 400).into(imageView8);

        state1.setOnClickListener(myOnClickListener);
        state2.setOnClickListener(myOnClickListener);
        state3.setOnClickListener(myOnClickListener);
        state4.setOnClickListener(myOnClickListener);
        state5.setOnClickListener(myOnClickListener);
        state6.setOnClickListener(myOnClickListener);
        state7.setOnClickListener(myOnClickListener);
        state8.setOnClickListener(myOnClickListener);

        //инициализировать и назначить переменную
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set imt selected
        bottomNavigationView.setSelectedItemId(R.id.nav_sovet);

        //выполнить ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_imt:
                        Intent intent = new Intent(Sovet.this, UserActivityList.class);
                        intent.putExtra("EMAIL", email);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_food:
                        Intent intent1 = new Intent(Sovet.this, Foods.class);
                        intent1.putExtra("EMAI", email);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_stat:
                        Intent intent2 = new Intent(Sovet.this, stat.class);
                        intent2.putExtra("EMAI", email);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_upr:
                        Intent intent3 = new Intent(Sovet.this, Upr.class);
                        intent3.putExtra("EMAI", email);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_sovet:
                        Intent intent5 = new Intent(Sovet.this, Sovet.class);
                        intent5.putExtra("EMAI", email);
                        startActivity(intent5);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


    View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.state1:
                            WebView webView = new WebView(getApplicationContext());
                            webView.loadUrl("file:///android_asset/state1/State.html");
                            AlertDialog.Builder builder = new AlertDialog.Builder(Sovet.this);
                            builder.setTitle("Как худеть? Мнения экспертов с мировым именем")
                                    .setView(webView)
                                    .setNeutralButton("Закрыть", null)
                                    .show();
                            break;
                        case R.id.state2:
                            WebView webView2 = new WebView(Sovet.this);
                            webView2.loadUrl("file:///android_asset/state2/State.html");
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(Sovet.this);
                            builder2.setTitle("Самые действенные и эффективные диеты")
                                    .setView(webView2)
                                    .setNeutralButton("Закрыть", null)
                                    .show();
                            break;
                        case R.id.state3:
                            WebView webView3 = new WebView(Sovet.this);
                            webView3.loadUrl("file:///android_asset/state3/State.html");
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(Sovet.this);
                            builder3.setTitle("Самые действенные и эффективные диеты")
                                    .setView(webView3)
                                    .setNeutralButton("Закрыть", null)
                                    .show();
                            break;
                        case R.id.state4:
                            WebView webView4 = new WebView(Sovet.this);
                            webView4.loadUrl("file:///android_asset/state4/State.html");
                            AlertDialog.Builder builder4 = new AlertDialog.Builder(Sovet.this);
                            builder4.setTitle("6 лучших упражнений для всего тела")
                                    .setView(webView4)
                                    .setNeutralButton("Закрыть", null)
                                    .show();
                            break;
                        case R.id.state5:
                            WebView webView5 = new WebView(Sovet.this);
                            webView5.loadUrl("file:///android_asset/state5/State.html");
                            AlertDialog.Builder builder5 = new AlertDialog.Builder(Sovet.this);
                            builder5.setTitle("Вода и как она помогает в борьбе с лишним весом?")
                                    .setView(webView5)
                                    .setNeutralButton("Закрыть", null)
                                    .show();
                            break;
                        case R.id.state6:
                            WebView webView6 = new WebView(Sovet.this);
                            webView6.loadUrl("file:///android_asset/state6/State.html");
                            AlertDialog.Builder builder6 = new AlertDialog.Builder(Sovet.this);
                            builder6.setTitle("Избыточный вес: причины, последствия, способы избавления")
                                    .setView(webView6)
                                    .setNeutralButton("Закрыть", null)
                                    .show();
                            break;
                        case R.id.state7:
                            WebView webView7 = new WebView(Sovet.this);
                            webView7.loadUrl("file:///android_asset/state7/State.html");
                            AlertDialog.Builder builder7 = new AlertDialog.Builder(Sovet.this);
                            builder7.setTitle("20 продуктов, которые помогут сбросить вес")
                                    .setView(webView7)
                                    .setNeutralButton("Закрыть", null)
                                    .show();
                            break;
                        case R.id.state8:
                            WebView webView8 = new WebView(Sovet.this);
                            webView8.loadUrl("file:///android_asset/state8/State.html");
                            AlertDialog.Builder builder8 = new AlertDialog.Builder(Sovet.this);
                            builder8.setTitle("Как пешие прогулки помогают худеть. Эффект интервальной ходьбы")
                                    .setView(webView8)
                                    .setNeutralButton("Закрыть", null)
                                    .show();
                            break;
                    }
        }
    };

}