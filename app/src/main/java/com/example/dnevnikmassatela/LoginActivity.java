package com.example.dnevnikmassatela;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    SharedPreferences sPref;
    private CheckBox checkbox;
    final String SAVED_TEXT = "saved_text";
    final String SAVED_TEXT2 = "saved_text2";

    private NestedScrollView nestedScrollView;
    private ImageView myImageView;

    private TextInputLayout lay_Email;
    private TextInputLayout lay_Password;

    private EditText et_Email;
    private EditText et_Password;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        myImageView = (ImageView) findViewById(R.id.myImage);
        myImageView.setImageResource(R.drawable.logo);
        initViews();
        initListeners();
        initObjects();
        loadText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }

    private void saveText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor em = sPref.edit();
        SharedPreferences.Editor pa = sPref.edit();
        em.putString(SAVED_TEXT, et_Email.getText().toString());
        pa.putString(SAVED_TEXT2, et_Password.getText().toString());
        em.apply();
        pa.apply();
    }


    private void loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText1 = sPref.getString(SAVED_TEXT, "");
        String savedText2 = sPref.getString(SAVED_TEXT2, "");
        Log.d("MyLog", "email : " + savedText1 + "pas: " + savedText2);
        et_Email.setText(savedText1);
        et_Password.setText(savedText2);
    }

    private void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        lay_Email = findViewById(R.id.lay_Email);
        lay_Password = findViewById(R.id.lay_Password);
        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);
        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);
        checkbox = findViewById(R.id.checkBoxColor);
    }


    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                if(checkbox.isChecked()) {
                    saveText();
                    verifyFromSQLite();
                }
                else {
                verifyFromSQLite();
                }
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intentRegister);
                break;
        }
    }



    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(et_Email, lay_Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(et_Email, lay_Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(et_Password, lay_Password, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(et_Email.getText().toString().trim()
                , et_Password.getText().toString().trim())) {
            Intent accountsIntent = new Intent(activity,UserActivityList.class);
            accountsIntent.putExtra("EMAIL", et_Email.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);

        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }


    private void emptyInputEditText() {
        et_Email.setText(null);
        et_Password.setText(null);
    }
}