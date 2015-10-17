package com.tas.jsonreaderpoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ParsingActivity extends AppCompatActivity {

    private ListView listView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsing);

        String s = getJSONFile();
        String myDataArray[] = {};

//        Try to parse some jsonfile and display it in listview

        try{
            JSONObject rootJSON = new JSONObject(s);
            JSONArray toppingJSON = rootJSON.getJSONArray("topping");

            myDataArray = new String[toppingJSON.length()];
            for (int i = 0; i < toppingJSON.length(); i++) {
                JSONObject jsonObject = toppingJSON.getJSONObject(i);
                myDataArray[i] = jsonObject.getString("type");
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

//        Now we need to display the array in a listview
        listView = (ListView) findViewById(R.id.myListView);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.row,myDataArray);

        if(listView != null){
            listView.setAdapter(stringArrayAdapter);
        }
    }

    private static final String DATA_FILE="samplejson";

//    reading and writing

    public String getJSONFile(){
        String fileData = null;
        InputStream ins = getResources().openRawResource(
                getResources().getIdentifier("raw/samplejson",
                        "raw", getPackageName()));
        int size = 0;
        try {
            size = ins.available();
            byte [] buffer = new byte[size];
            ins.read(buffer);
            fileData = new String(buffer,"UTF-8");
        } catch (IOException e) {
            Log.e("FILE", "Error");
            e.printStackTrace();
        }
        return fileData;
    }

    public String getJSONFile2(){
        FileInputStream fileInputStream = null;
        String fileData = null;
        try {
            fileInputStream = openFileInput(DATA_FILE);
            int size = fileInputStream.available();
            byte [] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();
            fileData = new String(buffer,"UTF-8");
        } catch (FileNotFoundException e){
            Log.e("FILE", "Could not find that file");
            e.printStackTrace();;
        } catch (IOException e){
            Log.e("FILE", "Error");
            e.printStackTrace();;
        } finally {
            try {
                if(fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }

        }
        return fileData;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parsing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
