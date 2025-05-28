package com.example.holleworld.UI;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.holleworld.Utils.IndirectClass;
import com.example.holleworld.Net.Net;
import com.example.holleworld.R;

public class Setting {
    IndirectClass ic;
    public Setting(IndirectClass IC){
        ic=IC;
        View view = IC.getActivity().findViewById(R.id.ShowArea);
        View popview = IC.getActivity().getLayoutInflater().inflate(R.layout.settinglayout,null);
        PopupWindow skipwindow=new PopupWindow(popview,view.getWidth(),view.getHeight()/2,true);
        skipwindow.showAtLocation(view, Gravity.CENTER|Gravity.BOTTOM,0,0);
        Button but_Save=popview.findViewById(R.id.SaveToSD);
        Button but_Load=popview.findViewById(R.id.LoadFromSD);
        Button but_LoadFromNet = popview.findViewById(R.id.LoadFromNet);
        Button but_SaveToNet = popview.findViewById(R.id.SaveToNet);
        but_Save.setOnClickListener(view1 -> IC.getActivity().data.SaveToSDCard());
        but_Load.setOnClickListener(view12 -> IC.getActivity().data.LoadBySDCard());
        but_LoadFromNet.setOnClickListener(view13->new Net(1,IC));
        but_SaveToNet.setOnClickListener(view13->new Net(2,IC));
    }
}
