package com.example.holleworld.DATA;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.holleworld.DATA.Repository.PeopleRepository;
import com.example.holleworld.Utils.IndirectClass;
import com.example.holleworld.MyApp;
import com.example.holleworld.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Data {
    ArrayList<Person>LP;
    Context context;
    IndirectClass ic ;

    public ArrayList<Person> getLP() {
        return LP;
    }

    public void setLP(ArrayList<Person> LP) {
        this.LP = LP;
    }

    public static String SaveLpPath="LP";
    public static String VariationSavePath = "VS";

    public  Data(Context context1,IndirectClass IC){
        LP=new ArrayList<Person>();
        LP.add(new Person("","","","",1));
        context=context1;
        ic=IC;
    }
    public void FillLPByFile(){
        context= MyApp.getInstance().getApplicationContext();
        try {
            InputStream in = context.getResources().openRawResource(R.raw.data);
            int len = in.available();
            byte [] buffer = new byte[len];
            in.read(buffer);
            String data = new String(buffer,"utf-8");
            String [] peoples = data.split("\r\n");
            for(int i = 0;i< peoples.length;i++){
               // System.out.println(peoples[i]);
                String []temp = peoples[i].split("\t");
                AddToLP(new Person(TryGet(temp,0),TryGet(temp,1),TryGet(temp,2),TryGet(temp,3)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ic.getActivity().VS.Server_GetLiBu = "http://124.71.204.38:8080/GetLiBu";
        ic.getActivity().VS.Server_PostLibu = "http://124.71.204.38:8080/PostLiBu";
        PeopleRepository.getInstance().reCreat(LP);
    }
    private String TryGet(String [] SS,int index){
        if(index>=SS.length)return "";
        return SS[index];
    }
    public int SearchPeople(String in,int start,boolean isReverse){
        for(int i=start;i< LP.size()&&i>=0;i+=isReverse?-1:1){
            if(LP.get(i).name.contains(in)|| LP.get(i).arc.contains(in))return i;
        }
        return -1;
    }
    public void Save() {
//        try {
//            Log.e("printf size","size"+LP.size());
//            MinLP();
//            SaveObject(SaveLpPath,LP);
//            SaveObject(VariationSavePath,ic.getActivity().VS);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
    public void SaveObject(String path,Object obj) throws IOException {
        File file=new File(context.getFilesDir().getPath()+"/"+path);
        if(!file.exists()){
            file.createNewFile();
            Log.e("save not exist", "Save: "+obj.toString());
        }
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(obj);
        //objectOutputStream.flush();
        objectOutputStream.close();
        Log.e("save", "Save: "+obj.toString());
        System.out.println("save      "+ "Save: "+obj.toString());
    }
//    public boolean canLoad(){
//        File file=new File(context.getFilesDir().getPath()+"/"+SaveLpPath);
//
//        Log.e("load ", "load: "+context.getFilesDir().getPath());
//        if(file.exists())return true;
//        return  false;
//    }
    public void Load() {
//        try {
//            LP = (ArrayList<Person>) LoadObject(SaveLpPath);
//            ic.getActivity().VS = (VariationSave)LoadObject(VariationSavePath);
//        } catch (IOException e) {
//            //throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        LP = new ArrayList<>( PeopleRepository.getInstance().getAll());
        //Log.e("load", "Load: "+LP.size());
    }
    public  Object LoadObject(String path) throws IOException, ClassNotFoundException {
        File file=new File(context.getFilesDir().getPath()+"/"+path);
        if(!file.exists()) {
            Log.e("load path not exist", "load: "+path);
            return null;
        }
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(file));
        Object obj = objectInputStream.readObject();
        objectInputStream.close();
        Log.e("load ", "load: "+context.getFilesDir().getPath());
        return obj;
    }
    public void AddToLP(Person person){
        if(LP.isEmpty())LP.add(new Person("","","","",1));
        LP.set(LP.size()-1,person);
        PeopleRepository.getInstance().addPerson(person);
        LP.add(new Person("","","","",1));
    }
    public void SaveToSDCard(){
        MinLP();
        File exFile = Environment.getExternalStorageDirectory();
        Log.e("SAVE TO SDC", "onClick: sd file dir =====" + exFile);
        try {
            // 写入数据
            File file = new File(exFile,"mipalibu.txt");
            if(!file.exists())file.createNewFile();
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(LP);objectOutputStream.close();
            Toast.makeText(context,"导出完成，导出文件放在根目录！",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void LoadBySDCard(){
        File exFile = Environment.getExternalStorageDirectory();
        Log.e("Load BY SDC", "onClick: sd file dir =====" + exFile);
        try {
            // 写入数据
            File file = new File(exFile,"mipalibu.txt");
            if(!file.exists()){
                Log.e("Load by sdc","file not exist");
                Toast.makeText(context,"请把导入文件放在根目录！",Toast.LENGTH_LONG).show();
                return;
            }
            LP.clear();
            ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(file));
            LP=(ArrayList<Person>) objectInputStream.readObject();
            objectInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        ic.getActivity().UpdataAfterLoad();
        Save();
        PeopleRepository.getInstance().reCreat(LP);
    }
    public void LoadByNet(String spec ){

        Thread thread = new Thread("12"){
            @Override
            public  void run(){
                try {
                    URL url = new URL(spec);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    InputStream is = urlConnection.getInputStream();
                    int len = urlConnection.getContentLength();
                    byte buf[] = new byte[len+3];
                    int len2=0,count=0,len3=0;
                    while((len2 =is.read(buf,len3,len-len3))!=-1)len3+=len2;
                    System.out.println("LoadByNet   "+"len = "+len+"len2 ="+len2+"len3 ="+len3);
                    Log.e("LoadByNet","len = "+len+"len2 ="+len2+"len3 ="+len3);
                    is.close();
                    JSONArray jA = new JSONArray(new String(buf,0,len3,"utf-8"));
                    LP.clear();
                    for(int i=0;i<jA.length();i++)AddToLP(new Person((JSONObject) jA.get(i)));
                    //Log.e("LoadByNet","lplength="+LP.size()+new String(buf,0,len,"utf-8"));
                    ic.getActivity().UpdataAfterLoad();
                    //Toast.makeText(context,"导入完成！",Toast.LENGTH_LONG).show();
                    ic.getActivity().VS.Server_GetLiBu = spec;
                    PeopleRepository.getInstance().reCreat(LP);
                    Save();
                } catch (MalformedURLException e) {
                    Log.e("LoadByNet","Wrong1");
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    Log.e("LoadByNet","Wrong2");
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    Log.e("LoadByNet","wrong3");
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
    }
    public void SaveToNet(String spec){
        Log.e("SAve",spec);
        MinLP();
        JSONArray jA = new JSONArray();
        for(int i=0;i< LP.size();i++){
            jA.put(LP.get(i).GetJsonObject());
        }
        Log.e("SaveToNet",jA.toString());

        Thread SaveThread = new Thread(){
            @Override
            public void run(){
                try {
                    URL url = new URL(spec);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    os.write(jA.toString().getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    os.close();
                    InputStream is = urlConnection.getInputStream();
                    byte buf[] = new byte[1024];
                    int len = is.read(buf);
                    is.close();
                    //Log.e("SaveToNet",new String(buf,0,len,"utf-8"));
                    //Toast.makeText(context,"已上传至服务器!！",Toast.LENGTH_LONG).show();
                    ic.getActivity().VS.Server_PostLibu = spec;
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        SaveThread.start();
    }
    public void MinLP(){
        for(int i = LP.size()-1;i>0;i--){
            if(LP.get(i).name.equals("")&&LP.get(i-1).name.equals("")){
                PeopleRepository.getInstance().deletePerson(LP.get(i));
                LP.remove(i);
            }
        }
    }
}
