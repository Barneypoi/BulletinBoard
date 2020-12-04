package com.example.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class JsonTest extends AsyncTask<Void,Void,Void> {
    private List<News> list;
    private Context context;

    public JsonTest(List<News> list, Context context){
        super();
        this.list=list;
        this.context=context;
    }
    @Override
    protected Void doInBackground(Void... params) {
        Gson gson = new Gson();
//        String str = readJsonFile("./app/src/main/assets/metadata.json");
        String str = readJsonFile("metadata.json");
        JsonArray jsonElements = JsonParser.parseString(str).getAsJsonArray();
        for(JsonElement news : jsonElements){
            News news1 = gson.fromJson(news,News.class);
            list.add(news1);
        }
        return null;
    }


    private String readJsonFile(String fileName) {

        String str = null;
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            str  = new String(buffer, "utf8").trim();
            return str;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    //将json文件转为字符串
//    private String readJsonFile(String fileName) {
//        String jsonStr = "";
//        try {
//            File jsonFile = new File(fileName);
//            FileReader fileReader = new FileReader(jsonFile);
//            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
//            int ch = 0;
//            StringBuffer sb = new StringBuffer();
//            while ((ch = reader.read()) != -1) {
//                sb.append((char) ch);
//            }
//            fileReader.close();
//            reader.close();
//            jsonStr = sb.toString();
//            return jsonStr;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    public static void main(String[] args) {
//        Gson gson = new Gson();
//        String str = readJsonFile("./app/src/main/assets/metadata.json");
//        JsonArray jsonElements = JsonParser.parseString(str).getAsJsonArray();
//    }
}
