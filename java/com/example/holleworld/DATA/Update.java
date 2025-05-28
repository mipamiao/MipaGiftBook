package com.example.holleworld.DATA;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.holleworld.DATA.Data;
import com.example.holleworld.DATA.Person;
import com.example.holleworld.R;
import com.example.holleworld.UI.MainActivity;
import com.example.holleworld.Utils.IndirectClass;

public class Update {
    Data DATA;
    int Index;
    IndirectClass IC;
    View popview;
    TextView pop_name,pop_arc,pop_money,pop_memo;
    public Update(Data data,int index,IndirectClass ic){
        DATA=data;Index=index;IC=ic;
        View view = IC.getActivity().findViewById(R.id.ShowArea);
        popview = IC.getActivity().getLayoutInflater().inflate(R.layout.popwindow,null);
        PopupWindow popwindow=new PopupWindow(popview,view.getWidth()/2,view.getHeight()/2,true);
        pop_name = (TextView)popview.findViewById(R.id.pop_name);
        pop_money = (TextView)popview.findViewById(R.id.pop_money);
        pop_memo = (TextView)popview.findViewById(R.id.pop_memo);
        pop_arc = (TextView)popview.findViewById(R.id.pop_arc);
        if(Index!=-1){
            pop_name.setText(DATA.getLP().get(index).name);
            pop_money.setText(Person.ReMoneyTrans(DATA.getLP().get(index).money));
            pop_memo.setText(DATA.getLP().get(index).memo);
            pop_arc.setText(DATA.getLP().get(index).arc);
        }
        popwindow.showAtLocation(view, Gravity.CENTER|Gravity.TOP,0,0);
        Log.e("sdsd", "choose"+"x:"+"w:"+index );
        Button OK=popview.findViewById(R.id.update);
        Button Cancel=popview.findViewById(R.id.cancel);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index==-1||index==DATA.getLP().size()-1)Add();
                else Change();
                ((MainActivity)(IC.getActivity())).setPageCodeAndShow(
                        ((MainActivity)(IC.getActivity())).getMid().PageCode
                );
                popwindow.dismiss();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popwindow.dismiss();
            }
        });
    }
    public void Add(){
        String name,arc,money,memo;
        name=pop_name.getText().toString();
        arc=pop_arc.getText().toString();
        money=pop_money.getText().toString();
        memo=pop_memo.getText().toString();
        if(name.length()==0||money.length()==0){
            Toast.makeText(IC.getContxt(),"没有姓名或金额无法添加！", Toast.LENGTH_SHORT).show();
        }
        DATA.AddToLP(new Person(name,arc,money  ,memo  ,2));
        DATA.Save();
    }
    public void Change(){
        DATA.getLP().get(Index).Change(pop_name.getText().toString(),
                pop_arc.getText().toString(),  pop_money.getText().toString(),  pop_memo.getText().toString());
        DATA.Save();
    }
}
