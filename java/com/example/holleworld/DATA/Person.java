package com.example.holleworld.DATA;

import com.example.holleworld.DATA.Repository.People;
import com.example.holleworld.DATA.Repository.PeopleRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Person implements Serializable {
    public String name,money,memo,arc;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFixed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getArc() {
        return arc;
    }

    public void setArc(String arc) {
        this.arc = arc;
    }

    public static final String[] CT={"壹","贰","叁","肆","伍","陆","柒","捌","玖","拾"};
    public static final String[] NT={"个","十","百","千","万"};
    public static Map<String,Integer>UnitMap=new HashMap<String, Integer>(){{put("个",1);put("十",10);put("百",100);put("千",1000);put("万",10000);}};
    public static Map<String,Integer>NumMap=new HashMap<String, Integer>(){{put("壹",1);put("贰",2);put("叁",3);put("肆",4);put("伍",5);put("陆",6);
        put("柒",7);put("捌",8);put("玖",9);put("拾",10);}};
    public Person(String Name,String Arc,String Money,String Memo){
        name=Name;money=MoneyTrans(Money);memo=Memo;arc=Arc;
        isFixed=true;
    }
    public Person(String Name,String Arc,String Money,String Memo,int kind){
        name=Name;money=MoneyTrans(Money);memo=Memo;arc=Arc;
        isFixed=true;
    }
    public void Change(String Name,String Arc,String Money,String Memo){
        name=Name;money=MoneyTrans(Money);memo=Memo;arc=Arc;
        PeopleRepository.getInstance().updatePerson(this);
        isFixed=true;
    }
    public String MoneyTrans(String str){
        if(str.length()==0)return str;
        if(!isNum(str))return "错误数据";
        String Re = "";
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='0')continue;
            Re += CT[str.charAt(i)-49]+NT[str.length()-1-i];
        }
        return Re+"元";
    }
    public static String ReMoneyTrans(String str){
        int sum=0;
        for(int i=0;i<str.length()-1;i+=2){
            sum+=NumMap.get(str.substring(i,i+1))*UnitMap.get(str.substring(i+1,i+2));
        }
        if(sum==0)return str;
        return Integer.toString(sum);
    }
    public static boolean isNum(String str){
        for (int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i)))return false;
        }
        return true;
    }
    public Person(JSONObject jO)  {
        try{
            name = (String) jO.get("name");
            money = (String) jO.get("money");
            memo = (String) jO.get("memo");
            arc = (String) jO.get("arc");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public JSONObject GetJsonObject(){
        JSONObject jO   = new JSONObject();
        try {
            jO.put("name",name);
            jO.put("money",money);
            jO.put("memo",memo);
            jO.put("arc",arc);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jO;
    }
    public static Person fromPeople(People people){
        Person person = new Person(people.getName(),people.getArc(), people.getMoney(), people.getMemo());
        person.setId(people.getId());
        person.setMoney(people.getMoney());
        return person;
    }
    public static People toPeople(Person person){
        People people = new People(person.getName(),person.getArc(), person.getMoney(), person.getMemo());
        people.setId(person.getId());
        return people;
    }
}
