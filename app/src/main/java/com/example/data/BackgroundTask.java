package com.example.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.os.*;
import android.view.accessibility.AccessibilityManager;
import android.widget.*;

import java.io.*;
import java.net.*;

public class BackgroundTask extends AsyncTask<String,Void,String>{
    public AlertDialog alertDialog;
    public String reg_url;
    public String login_url;
    public String user;
    public Context ctx;
    public String login_name,login_pass,book_url;
    public String sell_info;

    BackgroundTask(Context ctx)
    {
        this.ctx =ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information....");
        reg_url = "http://192.168.31.210/web/register.php";
        login_url = "http://192.168.31.210/web/login.php";
        book_url = "http://192.168.31.210/web/sell.php";
        user="http://192.168.31.210/web/user.php";
        sell_info="http://192.168.31.210/web/user.php";

    }

    @Override
    protected String doInBackground(String... params) {

        String method = params[0];

        if(method.equals("sell_info"))
        {
            String uname=params[1];
            try {
                URL url = new URL(sell_info);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                Toast.makeText(ctx,"Soumo"+uname,Toast.LENGTH_LONG).show();
                String data = URLEncoder.encode("slr_name", "UTF-8") + "=" + URLEncoder.encode(uname, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                return "send to sell_info";
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        else if(method.equals("user"))
        {
            String uname=params[1];
            String pass=params[2];
            try
            {
                URL url = new URL(user);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(uname, "UTF-8") + "&" +
                        URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                return "send to query";
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(method.equals("book"))
        {
            String book_name=params[1];
            String slr_name=params[2];
            String image=params[3];
            try
            {
                URL url=new URL(book_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("book_name", "UTF-8") + "=" + URLEncoder.encode(book_name, "UTF-8") + "&" +
                        URLEncoder.encode("slr_name", "UTF-8") + "=" + URLEncoder.encode(slr_name, "UTF-8")+ "&" +
                        URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                return "Ready to sell";
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (method.equals("register")) {
            String name = params[1];
            String user_name = params[2];
            String user_pass = params[3];
            String phone=params[4];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8") +
                        "&" +URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                return "Registration Success...";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("login"))
        {
            login_name = params[1];
            login_pass = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Registration Success..."))
        {

            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

        }
        else if(result.equals("send to sell_info"))
        {
            Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
        }
        else if(result.equals("Login Failed.......Try Again.."))
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
        else if(result.equals("Login Success..Welcome"))
        {

                /*alertDialog.setMessage(result + " Your user name is: " + login_name+" ");
                alertDialog.show();*/
            Intent intent=new Intent(BackgroundTask.this.ctx,Home1.class);
            intent.putExtra("UNAME",login_name);
            intent.putExtra("UPASS",login_pass);
            BackgroundTask.this.ctx.startActivity(intent);
        }
        else if(result.equals("Ready to sell"))
        {
            Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
        }
        else if(result.equals("send to query"))
        {
            Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();

        }
    }
}



