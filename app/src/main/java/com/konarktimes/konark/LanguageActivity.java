package com.konarktimes.konark;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.konarktimes.konark.Model.Languages;
import com.konarktimes.konark.ServerConvig.ServerHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LanguageActivity extends AppCompatActivity {
   // Spinner spinner;
    //Button confirm;
    //ArrayList<Languages> lang;
    Button od,en;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
       // spinner = findViewById(R.id.spinner);
        //confirm=findViewById(R.id.confirm);
        od=findViewById(R.id.od);
        en=findViewById(R.id.en);

        //lang = new ArrayList<>();
        //lang.add(new Languages("Odia","od",4));
        //lang.add(new Languages("English","en",1));

        //loadLang();
        /*ArrayAdapter<Languages> adapter=new ArrayAdapter<>(LanguageActivity.this,android.R.layout.simple_spinner_item,lang);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/


        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Languages obj = lang.get(position);
                SharedPreferences sharedPreferences = getSharedPreferences("lang_id",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("lang_id", obj.getId());
                    editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent(LanguageActivity.this,SplashScreen.class));
                    LanguageActivity.this.finish();
            }
        });*/

        od.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("lang_id",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("lang_id",4);
                editor.commit();
                startActivity(new Intent(LanguageActivity.this,SplashScreen.class));
                LanguageActivity.this.finish();
            }
        });

        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("lang_id",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("lang_id",1);
                editor.commit();
                startActivity(new Intent(LanguageActivity.this,SplashScreen.class));
                LanguageActivity.this.finish();
            }
        });
    }



   /* public void loadLang(){
        String url= ServerHelper.getUrl()+ServerHelper.getLangUrl();
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       /* try {

                            JSONArray jsonArray = new JSONArray();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Toast.makeText(LanguageActivity.this, "Reached", Toast.LENGTH_SHORT).show();
                                String name = jsonObject.getString("name");
                                String code = jsonObject.getString("language_code");
                                int id = jsonObject.getInt("id");

                                lang.add(new Languages(name, code, id));
                                //Toast.makeText(LanguageActivity.this, lang.get(i).getName(), Toast.LENGTH_SHORT).show();
                            }
                            ArrayAdapter<Languages> adapter=new ArrayAdapter<>(LanguageActivity.this,android.R.layout.simple_spinner_item,lang);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);

                        }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }*/
//Remove from below
                      /* Gson gson=new Gson();
                       Type type=new TypeToken<ArrayList<Languages>>(){}.getType();
                       lang=gson.fromJson(response,type);

                        ArrayAdapter<Languages> adapter=new ArrayAdapter<>(LanguageActivity.this,android.R.layout.simple_spinner_item,lang);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LanguageActivity.this, "Error\n"+error, Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue queue= Volley.newRequestQueue(LanguageActivity.this);
        queue.add(request);


    }*/

}
