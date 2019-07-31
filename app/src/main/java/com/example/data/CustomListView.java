package com.example.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;

public class CustomListView extends ArrayAdapter<String>{


    private String[] name;
    private String[] book_name;
    private String[] image;
    private String[] atr_name;
    private String[] price;
    private Activity context;
    Bitmap bitmap;
    Uri uri;

    public CustomListView(Activity context,String[] name,String[] book_name, String[] image, String[] atr_name,String[] price) {
        super(context, R.layout.display,name);
        this.context=context;
        this.name=name;
        this.book_name=book_name;
        this.image=image;
        this.atr_name=atr_name;
        this.price=price;
    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r=convertView;
        ViewHolder viewHolder=null;

        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.display,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)r.getTag();

        }

        viewHolder.t1.setText(name[position]);
        viewHolder.t2.setText(book_name[position]);
        viewHolder.t3.setText(atr_name[position]);
        viewHolder.t4.setText(price[position]);
        //new GetImageFromURL(viewHolder.i1).execute(image[position]);
        String im=image[position];
        Uri uri= Uri.parse(im);
        viewHolder.i1.setImageURI(uri);


        return r;
    }

    class ViewHolder
    {
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        ImageView i1;

        ViewHolder(View v)
        {
            t1=(TextView)v.findViewById(R.id.tvprofilename);
            t2=(TextView)v.findViewById(R.id.tvemail);
            t3=(TextView)v.findViewById(R.id.atr_name);
            t4=(TextView)v.findViewById(R.id.price);
            i1=(ImageView)v.findViewById(R.id.imageView);

        }
    }

    public class GetImageFromURL extends AsyncTask<String,Void,Bitmap>
    {
        ImageView imageView;
        public GetImageFromURL(ImageView imgv)
        {
            this.imageView=imgv;
        }


        @Override
        protected Bitmap doInBackground(String... url) {

            bitmap=null;
            String urldisplay=url[0];
            try{
                InputStream ist=new java.net.URL(urldisplay).openStream();
                bitmap= BitmapFactory.decodeStream(ist);
                //uri=Uri.parse(urldisplay);



            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){

            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}

