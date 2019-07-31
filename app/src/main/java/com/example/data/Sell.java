package com.example.data;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Sell extends AppCompatActivity {
    TextView t1;
    String name;
    EditText e1,e2,e3;
    Button b1,b2;
    String book,price;
    ImageView i1;
    String im;
    String atr_name;
    private static int IMG_RQU=1;
    private Bitmap bitmap;
    private String upload="http://192.168.31.210/web/sell.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sell);

        name=getIntent().getStringExtra("UNAME");
        t1=(TextView)findViewById(R.id.user);
        t1.setText(name);
        e2=(EditText) findViewById(R.id.price);


        e1=(EditText)findViewById(R.id.b_name);
        b1=(Button)findViewById(R.id.b_sell);
        b2=(Button)findViewById(R.id.sel_image);
        i1=(ImageView)findViewById(R.id.image);
        e3=(EditText)findViewById(R.id.atr_name);



    }

    public void book(View view)
    {
        book=e1.getText().toString();
        price=e2.getText().toString();
        atr_name=e3.getText().toString();
        String method = "book";
        Toast.makeText(this,name + im,Toast.LENGTH_LONG).show();
        BackgroundTask backgroundTask = new BackgroundTask(this);
        //backgroundTask.execute(method,book,name,im);
        uploadImage();

    }

    public void image(View view)
    {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, IMG_RQU);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_RQU && resultCode == RESULT_OK)
        {
            try {
                /*Uri path = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                i1.setImageBitmap(bitmap);
                i1.setVisibility(View.VISIBLE);*/
                assert data != null;
                Uri imageUri = data.getData();
                i1.setImageURI(imageUri);
                i1.setVisibility(View.VISIBLE);
                assert imageUri != null;
                im=imageUri.toString();


            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private String imageIntoString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        String temp=Base64.encodeToString(imgByte,Base64.DEFAULT);
        return temp;
    }

    private void uploadImage()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, upload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String res=jsonObject.getString("response");
                    Toast.makeText(Sell.this,res,Toast.LENGTH_LONG).show();
                    Toast.makeText(Sell.this,im,Toast.LENGTH_LONG).show();
                    i1.setImageResource(0);
                    i1.setVisibility(View.GONE);
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
                params.put("book_name",book.trim());
                params.put("slr_name",name.trim());
                params.put("price",price.trim());
                params.put("atr_name",atr_name.trim());
                params.put("image",im);
                return params;
            }
        };
        MySingle.getInstance(Sell.this).addToRequest(stringRequest);
    }


}