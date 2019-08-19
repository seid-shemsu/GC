package com.example.seid.gc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by seid on 8/17/2019.
 */

public class Login extends AppCompatActivity {
    Button login,register;
    TextInputLayout inputId,inputPassword;
    EditText id,password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initialize();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Registration.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Users users = new Users(Login.this);
                    SQLiteDatabase data = users.getReadableDatabase();
                    String datas[] = {"userId","password","name","department"};
                    Cursor cursor = data.query("user",datas,null,null,null,null,null);
                    boolean valid = false;
                    if(cursor.moveToFirst()){
                        do{
                            if(cursor.getString(0).toString().equals(id.getText().toString()) && cursor.getString(1).toString().equals(password.getText().toString())){
                                valid = true;
                                break;
                            }
                        }while(cursor.moveToNext());
                    }
                    if (valid){
                        Intent i = new Intent(Login.this,MainActivity.class);
                        i.putExtra("name",cursor.getString(2).toString());
                        i.putExtra("dept",cursor.getString(3).toString());
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(Login.this, "invalid id or password", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(Login.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void initialize(){
        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        inputId = (TextInputLayout) findViewById(R.id.inputUsername);
        inputPassword = (TextInputLayout) findViewById(R.id.inputPassword);
    }
}
