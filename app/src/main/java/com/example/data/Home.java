package com.example.data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    String address="http://192.168.31.210/web/all_data.php";
    List <Product>productList;
    RecyclerView recyclerView;
    TextView t;

    private String gname,pass;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gname=getIntent().getStringExtra("UNAME");
        pass=getIntent().getStringExtra("UPASS");


        t=(TextView)findViewById(R.id.txt);
        t.setText(gname);
        Button b1=(Button)findViewById(R.id.sell);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Home.this,Sell.class);
                in.putExtra("N",gname);
                startActivity(in);
            }
        });
        //ProgressBar progressBarSubject = (ProgressBar)findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.lest_h);

        //initializing the productlist
         productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();

    }
    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Product(
                                        //product.getInt("id"),
                                        product.getString("name"),
                                        product.getString("user_name"),
                                        product.getString("password")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            ProductsAdapter adapter = new ProductsAdapter(Home.this, productList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}






 class Product {
    private int id;
    private String name;
    private String user_name;
    private String password;


    public Product( String name, String user_name, String password) {
        this.id = id;
        this.name = name;
        this.user_name =user_name;
        this.password = password;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }


    }







class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.lest, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        //loading the image

        holder.tname.setText(product.getName());
        holder.tuser_name.setText(product.getUser_name());
        holder.tuser_pass.setText(String.valueOf(product.getPassword()));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tname, tuser_name, tuser_pass;

        public ProductViewHolder(View itemView) {
            super(itemView);

            tname = itemView.findViewById(R.id.tname);
            tuser_name = itemView.findViewById(R.id.tuser_name);
            tuser_pass = itemView.findViewById(R.id.tpassword);

        }
    }
}

