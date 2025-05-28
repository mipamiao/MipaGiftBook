package com.example.holleworld.DATA.Repository;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "peoples")
public class People {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name,money,memo,arc;

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

    public People(){};
    public People(String Name,String Arc,String Money,String Memo){
        name=Name;money=Money;memo=Memo;arc=Arc;
    }
}
