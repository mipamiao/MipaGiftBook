package com.example.holleworld.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.holleworld.Utils.IndirectClass;
import com.example.holleworld.DATA.Person;
import com.example.holleworld.R;

import java.util.List;

public class SingleItem {
    private LinearLayout LL_SingleItem;
    private TextView name,money,memo;
    private View[] spanviews;
    private Context context;
    private IndirectClass IC;
    public int Index;
    public static final int NumOfSpanView=4;
    public SingleItem(Context context0,IndirectClass ic){
        IC=ic;
        Index=-1;
        context=context0;
        LL_SingleItem=new LinearLayout(context);
        LL_SingleItem.setLayoutParams(new LinearLayout.LayoutParams(10,ViewGroup.LayoutParams.MATCH_PARENT,1));
        LL_SingleItem.setOrientation(LinearLayout.VERTICAL);
        InitialTV(context);
        InitialSV(context);
        AddViewToSI();
    }

    private void AddViewToSI() {
        LL_SingleItem.addView(spanviews[0]);
        LL_SingleItem.addView(name);
        LL_SingleItem.addView(spanviews[1]);
        LL_SingleItem.addView(money);
        LL_SingleItem.addView(spanviews[2]);
        LL_SingleItem.addView(memo);
        LL_SingleItem.addView(spanviews[3]);
    }

    private void InitialSV(Context context) {
        spanviews=new View[NumOfSpanView];
        for(int i=0;i<NumOfSpanView;i++){
            spanviews[i]=new View(context);
            spanviews[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,Page.LineWidth));
            spanviews[i].setBackgroundColor(context.getResources().getColor(R.color.divide_line));
        }
    }

    private  void InitialTV(Context context){
        name=new TextView(context);money=new TextView(context);memo=new TextView(context);
        SetTV(name);SetTV(money);SetTV(memo);
        name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((MainActivity)(IC.getActivity())).Alter(Index);
                return false;
            }
        });
        name.setText("杨\n麒\n麟\n");
        money.setText("三\n百\n元");
        memo.setText("无");
    }
    private void SetTV(TextView TV){
        TV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20,1));
        TV.setGravity(Gravity.CENTER);
        TV.setTextSize(25);
    }

    public LinearLayout getLL_SingleItem() {
        return LL_SingleItem;
    }
    public void  SetTypeface(Typeface tf){
        name.setTypeface(tf);
        money.setTypeface(tf);
        memo.setTypeface(tf);
    }
    public void ShowData(List<Person> LP, int index){
        Person person=LP.get(index);
        //if(index==Index)return ;
        Index = index;
        person.isFixed=false;
        name.setText(person.name);
        money.setText(person.money);
        memo.setText(person.memo);
    }
    public void SetSpace(){
        Index=-1;
        name.setText("");
        money.setText("");
        memo.setText("");
    }
    public void HighLight(){
        LL_SingleItem.setBackgroundColor(context.getResources().getColor(R.color.divide_line));
    }
    public void CancelHighLight(){
        LL_SingleItem.setBackgroundColor(context.getResources().getColor(R.color.noVi));
    }
    public void ChangeTextColor(){

        name.setTextColor(IC.getActivity().getResources().getColor(R.color.white));
    }
}
