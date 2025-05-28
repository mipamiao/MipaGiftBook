package com.example.holleworld.Net;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.example.holleworld.Utils.IndirectClass;
import com.example.holleworld.R;

public class Net {
    public Net(int op, IndirectClass IC){
        View view = IC.getActivity().findViewById(R.id.ShowArea);
        View popview = IC.getActivity().getLayoutInflater().inflate(R.layout.skipwindow,null);
        PopupWindow skipwindow=new PopupWindow(popview,view.getWidth(),view.getHeight()/4,true);
        skipwindow.showAtLocation(view, Gravity.CENTER|Gravity.CENTER,0,0);
        EditText TextContent=popview.findViewById(R.id.skip_pc);
        TextContent.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        Button but_ok=popview.findViewById(R.id.skip_jump);
        but_ok.setText("确定");
        if(op==1){
            TextContent.setText(IC.getActivity().VS.Server_GetLiBu);
            Log.e("Net load",TextContent.getText().toString());
            but_ok.setOnClickListener(View1->IC.getActivity().data.LoadByNet(TextContent.getText().toString()));
        }else if(op==2){
            TextContent.setText(IC.getActivity().VS.Server_PostLibu);
            Log.e("Net save",TextContent.getText().toString());
            but_ok.setOnClickListener(view1->IC.getActivity().data.SaveToNet(TextContent.getText().toString()));
        }
        Button but_cancel=popview.findViewById(R.id.skip_cancel);
        but_cancel.setOnClickListener(view1->skipwindow.dismiss());


    }
}
