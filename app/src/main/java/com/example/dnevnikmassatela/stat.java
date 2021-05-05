package com.example.dnevnikmassatela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class stat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        Bundle arguments = getIntent().getExtras();
        String email = arguments.get("EMAI").toString();







        //инициализировать и назначить переменную
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set imt selected
        bottomNavigationView.setSelectedItemId(R.id.nav_stat);

        //выполнить ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_imt:
                        Intent intent = new Intent(stat.this, UserActivityList.class);
                        intent.putExtra("EMAIL", email);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_food:
                        Intent intent1 = new Intent(stat.this, Foods.class);
                        intent1.putExtra("EMAI", email);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_upr:
                        Intent intent3 = new Intent(stat.this, Upr.class);
                        intent3.putExtra("EMAI", email);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_sovet:
                        Intent intent4 = new Intent(stat.this, Sovet.class);
                        intent4.putExtra("EMAI", email);
                        startActivity(intent4);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_stat:
                        Intent intent5 = new Intent(stat.this, stat.class);
                        intent5.putExtra("EMAI", email);
                        startActivity(intent5);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}