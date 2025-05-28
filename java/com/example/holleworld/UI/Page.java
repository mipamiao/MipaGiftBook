package com.example.holleworld.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.holleworld.DATA.Data;
import com.example.holleworld.Utils.IndirectClass;
import com.example.holleworld.R;

public class Page {
    public SingleItem [] SIs;
    public LinearLayout LL;
    public static final int  NumOfOnePage = 10,LineWidth=10;
    public int PageCode;
    public View [] colsviews;
    public Page(LinearLayout ll, Context context, Typeface tf, IndirectClass IC){
        LL=ll;
        SIs=new SingleItem[NumOfOnePage];
        InitialCView(context);
        for(int i=0;i<NumOfOnePage;i++){
            SIs[i]=new SingleItem(context,IC);
            SIs[i].SetTypeface(tf);
            LL.addView(colsviews[i]);
            LL.addView(SIs[i].getLL_SingleItem());
        }
        LL.addView(colsviews[NumOfOnePage]);
        PageCode=-2;
    }

    private void InitialCView(Context context) {
        colsviews=new View[NumOfOnePage+1];
        for(int i=0;i<NumOfOnePage+1;i++){
            colsviews[i]=new View(context);
            colsviews[i].setLayoutParams(new LinearLayout.LayoutParams(Page.LineWidth,ViewGroup.LayoutParams.MATCH_PARENT));
            colsviews[i].setBackgroundColor(context.getResources().getColor(R.color.divide_line));
        }
    }

    public void ShowData(Data data , int pageCode){
        if(pageCode==-1||pageCode>(int)(data.getLP().size()/NumOfOnePage))return ;
        PageCode=pageCode;
        for(int i=0;i<NumOfOnePage;i++){
            if(PageCode*NumOfOnePage+i<data.getLP().size())
                SIs[i].ShowData(data.getLP(),PageCode*NumOfOnePage+i);
            else SIs[i].SetSpace();
        }
    }
    public void HighLight(int index){
        if(index/NumOfOnePage!=PageCode)return;
        SIs[index-PageCode*NumOfOnePage].HighLight();
    }
    public void ClearExtra(int index){
        if(index/NumOfOnePage!=PageCode)return;
        SIs[index-PageCode*NumOfOnePage].CancelHighLight();
    }
}
