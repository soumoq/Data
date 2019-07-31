package com.example.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_info extends AppCompatActivity {

    private TextView t1,t2,t3;
    RequestQueue rq;
    String name,user_name,phone;
    String uname,pass;
    String url="http://192.168.31.210/web/user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        uname=getIntent().getStringExtra("user");
        pass=getIntent().getStringExtra("pass");

        String task="user";
        BackgroundTask backgroundTask=new BackgroundTask(this);
        backgroundTask.execute(task,uname,pass);
        Toast.makeText(getApplicationContext(),uname+" "+pass,Toast.LENGTH_LONG).show();

        t1=(TextView)findViewById(R.id.name);
        t2=(TextView)findViewById(R.id.user_name);
        t3=(TextView)findViewById(R.id.phone);

        rq= Volley.newRequestQueue(this);
        fatchJson();
    }

    public void update(View view)
    {
        Intent intent=new Intent(User_info.this,Update.class);
        intent.putExtra("name",name);
        intent.putExtra("user_name",user_name);
        intent.putExtra("phone",phone);
        startActivity(intent);

    }

    public void sell_info(View view)
    {
        Intent intent=new Intent(User_info.this, Sell_info.class);
        intent.putExtra("user_name",uname);
        startActivity(intent);
    }

    public void fatchJson()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    name=jsonObject.getString("name");
                    user_name=jsonObject.getString("user_name");
                    phone=jsonObject.getString("phone");


                    Toast.makeText(getApplicationContext(),phone +" Res",Toast.LENGTH_LONG).show();
                    t1.setText(name);
                    t2.setText(user_name);
                    t3.setText(phone);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String ,String > getParams() throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("user",uname.trim());
                params.put("pass",pass.trim());
                return params;
            }
        };
        MySingle.getInstance(User_info.this).addToRequest(stringRequest);
    }
}

