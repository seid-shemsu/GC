package com.example.seid.gc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by seid on 8/17/2019.
 */

public class Registration extends AppCompatActivity {

    TextView name,id,email,password,phone;
    Spinner year,department;
    TextInputLayout inputName,inputId,inputEmail,inputPassword,inputPhone;
    Button register;
    String syear, dept;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initialize();
        setArray();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (checkInput()){
                        Users newUser = new Users(Registration.this);
                        SQLiteDatabase data = newUser.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("userId",id.getText().toString());
                        contentValues.put("name",name.getText().toString());
                        contentValues.put("email",email.getText().toString());
                        contentValues.put("phone",phone.getText().toString());
                        contentValues.put("year",syear);
                        contentValues.put("department",dept);
                        contentValues.put("password",password.getText().toString());
                        long row = data.insert("user", null, contentValues);
                        if (row == -1)
                            Toast.makeText(Registration.this, "u registered", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(Registration.this, "successfully registered", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Registration.this, Login.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(Registration.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                syear = year.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                syear = "";
            }
        });
        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept = department.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dept = "";
            }
        });
    }
    public boolean checkInput(){
        boolean isValid = true;
        if(name.getText().toString().isEmpty()){
            inputName.setError("full name is required");
            isValid = false;
        }
        if(id.getText().toString().isEmpty()){
            inputId.setError("id is required");
            isValid = false;
        }
        if(password.getText().toString().length()<6){
            inputPassword.setError("minimum 6 character is required");
            isValid = false;
        }
        if(year.getSelectedItem().toString().isEmpty() || syear == ""){
            Toast.makeText(this, "please select your year", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if(department.getSelectedItem().toString().isEmpty() || dept == ""){
            Toast.makeText(this, "please select your department", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

    public void setArray(){
        ArrayAdapter<CharSequence> departmentArray = ArrayAdapter.createFromResource(this,R.array.department,
                android.R.layout.simple_spinner_item);
        departmentArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(departmentArray);

        ArrayAdapter<CharSequence> yearArray = ArrayAdapter.createFromResource(this, R.array.year,
                android.R.layout.simple_spinner_item);
        yearArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearArray);
    }

    public void initialize(){
        name = (TextView) findViewById(R.id.name);
        id = (TextView) findViewById(R.id.id);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        password = (TextView) findViewById(R.id.password);
        year = (Spinner) findViewById(R.id.year);
        department = (Spinner) findViewById(R.id.department);
        inputName = (TextInputLayout) findViewById(R.id.inputName);
        inputPassword = (TextInputLayout) findViewById(R.id.inputPassword);
        inputEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        inputId= (TextInputLayout) findViewById(R.id.inputID);
        inputPhone = (TextInputLayout) findViewById(R.id.inputPhone);
        register = (Button) findViewById(R.id.register);
    }

}
