package com.example.data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {

    protected String name,user_name,phone;
    protected String ename,ephone;
    EditText e1,e2;
    private String url="http://192.168.31.210/web/update.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name=getIntent().getStringExtra("name");
        user_name=getIntent().getStringExtra("user_name");
        phone=getIntent().getStringExtra("phone");


        e1=(EditText)findViewById(R.id.upname);
        e2=(EditText)findViewById(R.id.upphone);

        ename=name;
        ephone=phone;

        e1.setText(ename);
        e2.setText(ephone);

    }

    public void update_info(View view)
    {
        ename=e1.getText().toString();
        ephone=e2.getText().toString();
            Toast.makeText(getApplicationContext(),ename+"_"+user_name+"_"+ephone,Toast.LENGTH_LONG).show();
        update();

    }

    private void update()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String res=jsonObject.getString("response");
                    Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
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
            protected Map<String ,String > getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("user",name.trim());
                params.put("user_name",ename.trim());
                params.put("phone",ephone.trim());
                return params;
            }
        };
        MySingle.getInstance(Update.this).addToRequest(stringRequest);
    }
}
