package com.example.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.*;
import android.widget.AdapterView;
import java.io.*;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Home1 extends AppCompatActivity {

    String name,pass;
    TextView t1;

    String url1="http://192.168.31.210/web/home.php";
    String result=null;
    ListView listView;
    BufferedInputStream bufferedInputStream;

    BufferedInputStream is;
    String line=null;
    String[] hname;
    String[] hbook_name;
    String[] himage;
    String[] hatr_name;
    String[] hprice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home1);

        name = getIntent().getStringExtra("UNAME");
        pass=getIntent().getStringExtra("UPASS");

        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
        t1=(TextView)findViewById(R.id.user);
        t1.setText(name);

        listView=(ListView)findViewById(R.id.list);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork()).build());
        colect();
        CustomListView customListView=new CustomListView(this,hname,hbook_name,himage,hatr_name,hprice);
        listView.setAdapter(customListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s=himage[i];
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        });


    }

    public void colect()
    {
        try
        {
            URL url=new URL(url1);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is=new BufferedInputStream(con.getInputStream());


        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();
            while ((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();

        }

        try{
            JSONArray ja=new JSONArray(result);
            JSONObject jo=null;
            hname=new String[ja.length()];
            hbook_name=new String[ja.length()];
            himage=new String[ja.length()];
            hatr_name=new String[ja.length()];
            hprice=new String[ja.length()];

            for(int i=0;i<=ja.length();i++){
                jo=ja.getJSONObject(i);
                hname[i]=jo.getString("slr_name");
                hbook_name[i]=jo.getString("book_name");
                himage[i]=jo.getString("image");
                hatr_name[i]=jo.getString("atr_name");
                hprice[i]=jo.getString("price");
            }
        }
        catch (Exception ex)
        {

            ex.printStackTrace();
        }
    }


    public void sell_p(View view)
    {
        Intent i=new Intent(Home1.this,Sell.class);
        i.putExtra("UNAME",name);
        startActivity(i);

    }

    public void user(View view)
    {
        Intent i=new Intent(Home1.this,User_info.class);
        i.putExtra("user",name);
        i.putExtra("pass",pass);
        startActivity(i);
    }



}