package com.example.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sell_info extends AppCompatActivity {
    String urladdress = "http://192.168.31.210/web/sell_image.php";

    String[] name;
    String[] book_name;
    String[] image;
    String[] atr_name;
    String[] price;
    ListView listView;
    TextView t1;

    BufferedInputStream is;
    String line=null;
    String result=null;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sell_info);

        listView=(ListView)findViewById(R.id.listv);

        user_name=getIntent().getStringExtra("user_name");
        Toast.makeText(getApplicationContext(),user_name,Toast.LENGTH_LONG).show();

        t1=(TextView)findViewById(R.id.mnusr);
        t1.setText(user_name);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();

        CustomListView customListView=new CustomListView(this,name,book_name,image,atr_name,price);
        listView.setAdapter(customListView);

    }




    public void collectData()
    {
        try
        {
            URL url=new URL(urladdress);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();

            //String name="q";
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            //httpURLConnection.setDoInput(true);
            OutputStream OS = con.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("slr_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

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
            name=new String[ja.length()];
            book_name=new String[ja.length()];
            image=new String[ja.length()];
            atr_name=new String[ja.length()];
            price=new String[ja.length()];

            for(int i=0;i<=ja.length();i++){
                jo=ja.getJSONObject(i);
                name[i]=jo.getString("slr_name");
                book_name[i]=jo.getString("book_name");
                image[i]=jo.getString("image");
                atr_name[i]=jo.getString("atr_name");
                price[i]=jo.getString("price");
            }
        }
        catch (Exception ex)
        {

            ex.printStackTrace();
        }
    }
}