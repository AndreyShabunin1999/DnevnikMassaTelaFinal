package com.example.dnevnikmassatela;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegistrationActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout lay_Name;
    private TextInputLayout lay_Email;
    private TextInputLayout lay_Password;
    private TextInputLayout lay_ConfPassword;
    private TextInputLayout lay_weight;
    private TextInputLayout lay_height;
    private CheckBox checkboxm;
    private CheckBox checkboxw;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private EditText et_Name;
    private EditText et_Email;
    private EditText et_Password;
    private EditText et_ConfPassword;
    private EditText et_weight;
    private EditText et_height;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();

        initDatePicker();
        dateButton.setText(getTodaysDate());
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String date;

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String dr;
    public void setdr(String dr) {
        this.date = dr;
    }
    public void setD(String date) {
        this.date = date;
    }

    private String makeDateString(int day, int month, int year)
    {
        dr = year + "-" + month + "-" + day;
        setdr(dr);
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String makeDRString(int day, int month, int year)
    {
        return year + "-" + month + "-" + day;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Янв";
        if(month == 2)
            return "Фев";
        if(month == 3)
            return "Март";
        if(month == 4)
            return "АПР";
        if(month == 5)
            return "МАЙ";
        if(month == 6)
            return "Июнь";
        if(month == 7)
            return "Июль";
        if(month == 8)
            return "Авг";
        if(month == 9)
            return "Сен";
        if(month == 10)
            return "Окт";
        if(month == 11)
            return "Ноябрь";
        if(month == 12)
            return "Дек";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    private void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        checkboxm = findViewById(R.id.checkBoxMan);
        dateButton = findViewById(R.id.datePickerButton);
        checkboxw = findViewById(R.id.checkBoxWoman);
        lay_Name = findViewById(R.id.lay_Name);
        lay_Email = findViewById(R.id.lay_Email);
        lay_Password = findViewById(R.id.lay_Password);
        lay_ConfPassword = findViewById(R.id.lay_ConfPassword);
        lay_weight = findViewById(R.id.lay_weight);
        lay_height = findViewById(R.id.lay_height);
        et_Name = findViewById(R.id.et_Name);
        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        et_ConfPassword = findViewById(R.id.et_ConfPassword);
        et_weight = findViewById(R.id.et_weight);
        et_height = findViewById(R.id.et_height);
        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister);
        appCompatTextViewLoginLink = findViewById(R.id.appCompatTextViewLoginLink);
    }


    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }


    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }
    private String pol;


    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(et_Name, lay_Name, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(et_Email, lay_Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(et_Email, lay_Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(et_Password, lay_Password, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(et_weight, lay_weight, getString(R.string.error_message_weight))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(et_height, lay_height, getString(R.string.error_message_height))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(et_Password, et_ConfPassword,
                lay_ConfPassword, getString(R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkUser(et_Email.getText().toString().trim())) {

            user.setName(et_Name.getText().toString().trim());
            user.setEmail(et_Email.getText().toString().trim());
            user.setPassword(et_Password.getText().toString().trim());
            user.setData(dr.trim());
            user.setWeight(et_weight.getText().toString().trim());
            user.setHeight(et_height.getText().toString().trim());

            if((checkboxm.isChecked()==true ) & (checkboxw.isChecked() == false)) {
                pol = "M";
                setPol(pol);
            }
            else if((checkboxm.isChecked()==false ) & (checkboxw.isChecked() == true)) {
                pol = "Ж";
                setPol(pol);
            }
            else if((checkboxm.isChecked()==false ) & (checkboxw.isChecked() == false)) {
                Snackbar.make(nestedScrollView, getString(R.string.error_message_gender), Snackbar.LENGTH_LONG).show();
            }

            user.setGender(pol.trim());
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date date = new Date();
            String dat = dateFormat.format(date);
            user.setDatareg(dat);

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }
    public void setPol(String pol) {
        this.pol = pol;
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        et_Name.setText(null);
        et_Email.setText(null);
        et_Password.setText(null);
        et_ConfPassword.setText(null);
        et_weight.setText(null);
        et_height.setText(null);
    }
}