package com.example.okul.myapplicationu1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity{
    SharedPreferences preferences;
    public static String URL = "http://turulay.com/isim4.php";//Bilgisayarýn IP adresi
    boolean internetBaglantisiVarMi() {

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() != null

                && conMgr.getActiveNetworkInfo().isAvailable()

                && conMgr.getActiveNetworkInfo().isConnected()) {

            return true;

        } else {

            return false;

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id){
            case R.id.item1:
                Toast.makeText(this,"ayarlar sayfasý gelecek",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),ayarlarfrm.class);
                startActivity(i);
                break;

            case R.id.item2:
                Toast.makeText(this,"Program Sonlanacak",Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button ac_butonu=(Button)findViewById(R.id.ac_button);
        Button kapat_butonu=(Button) findViewById(R.id.kapat_button);
        final EditText editText=(EditText)findViewById(R.id.editText1);
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String CihazID=preferences.getString("CihazID", "Cihaz ID Gir");
        editText.setText(CihazID);
        URL="http://turulay.com/isim4.php?did="+CihazID+"&durumu=";
        Thread t1=new Thread()
        {
            public  void run()
            {

                    try {
                        sleep(5000);
                        fetchJsonTask a = new fetchJsonTask();
                        a.execute(URL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                    //finish();
                }

            }
        };
        if(!internetBaglantisiVarMi())
        {
            Toast.makeText(getApplicationContext(), "Internet Baðlantýnýz yok", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Internet Baðlantýnýz var", Toast.LENGTH_LONG).show();
            t1.start();
        }


        kapat_butonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String isim="0";
                //fetchJsonTask a = new fetchJsonTask();
                //a.execute(URL + isim);
                Thread t2=new Thread()
                {
                    public  void run()
                    {

                        try {
                           //sleep(5000);
                            String isim="0";
                            fetchJsonTask a = new fetchJsonTask();
                            fetchJsonTask b = new fetchJsonTask();
                            b.execute(URL + isim);
                            sleep(15000);
                            a.execute(URL + isim);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            //finish();
                        }

                    }
                };
                if(!internetBaglantisiVarMi())
                {
                    Toast.makeText(getApplicationContext(), "Internet Baðlantýnýz yok", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Internet Baðlantýnýz var", Toast.LENGTH_LONG).show();
                    t2.start();
                }


            }
        });

        ac_butonu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //String isim=editText.getText().toString();
                //String isim = "1";
                //fetchJsonTask a = new fetchJsonTask();
                //a.execute(URL + isim);
                Thread t3 = new Thread() {
                    public void run() {

                        try {
                            //sleep(5000);
                            String isim = "1";
                            fetchJsonTask a = new fetchJsonTask();
                            fetchJsonTask b = new fetchJsonTask();
                            b.execute(URL + isim);
                            sleep(15000);
                            a.execute(URL + isim);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            //finish();
                        }

                    }
                };
                if (!internetBaglantisiVarMi()) {
                    Toast.makeText(getApplicationContext(), "Internet Baðlantýnýz yok", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Internet Baðlantýnýz var", Toast.LENGTH_LONG).show();
                    t3.start();
                }


            }
        });



    }
    public static String connect(String url){
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response;
        try {
            response=httpClient.execute(httpget);
            HttpEntity entity=response.getEntity();
            if(entity!=null){
                InputStream instream=entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                StringBuilder sb = new StringBuilder();
                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return sb.toString();
            }
        } catch (Exception e) {
        }
        return null;
    }




    class fetchJsonTask extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                String ret = connect(params[0]);
                ret = ret.trim();
                JSONObject jsonObj = new JSONObject(ret);
                return jsonObj;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result != null) {
                parseJson(result);
            } else {
                TextView tv = (TextView) findViewById(R.id.textView1);
                tv.setText("Kayýt Bulunamadý");
                tv.setTextColor(Color.RED);
                Toast.makeText(getApplicationContext(), "Sistemden Herhangi bir bilgi gelmedi.",
                        Toast.LENGTH_LONG).show();
            }

        }
    }

    public void parseJson(JSONObject ogrenciJson) {
        TextView Durum1 = (TextView) findViewById(R.id.Durum1);
        TextView Durum2 = (TextView) findViewById(R.id.Durum2);
        TextView Durum3 = (TextView) findViewById(R.id.Durum3);
        TextView Durum4 = (TextView) findViewById(R.id.Durum4);
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText("");
        System.out.println(ogrenciJson);
        try {
            Durum1.setText("Durum :"+ ogrenciJson.getString("Durum1"));
            Durum2.setText("Tarih :"+ ogrenciJson.getString("Durum2"));
            Durum3.setText("IP Adresi :"+ ogrenciJson.getString("Durum3"));
            Durum4.setText("Cihaz ID :"+ ogrenciJson.getString("Durum4"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}