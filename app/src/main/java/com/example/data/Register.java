package com.example.data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    TextView t1,t2,t3,t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        t1=(TextView)findViewById(R.id.name);
        t2=(TextView)findViewById(R.id.new_user_name);
        t3=(TextView)findViewById(R.id.new_user_pass);
        t4=(TextView)findViewById(R.id.phone);

    }

    public void userReg(View view)
    {

        String name=t1.getText().toString();
        String user_name=t2.getText().toString();
        String user_pass=t3.getText().toString();
        String phone=t4.getText().toString();
        BackgroundTask b=new BackgroundTask(this);
        String mtd="register";
        b.execute(mtd,name,user_name,user_pass,phone);
    }
}
