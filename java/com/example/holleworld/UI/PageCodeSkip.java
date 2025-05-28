package com.example.holleworld.UI;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.example.holleworld.R;
import com.example.holleworld.UI.MainActivity;
import com.example.holleworld.UI.Page;
import com.example.holleworld.Utils.IndirectClass;

public class PageCodeSkip {

    public PageCodeSkip(Page page, IndirectClass IC){
        View view = IC.getActivity().findViewById(R.id.ShowArea);
        View popview = IC.getActivity().getLayoutInflater().inflate(R.layout.skipwindow,null);
        PopupWindow skipwindow=new PopupWindow(popview,view.getWidth()/2,view.getHeight()/4,true);
        skipwindow.showAtLocation(view, Gravity.CENTER|Gravity.CENTER,0,0);
        EditText PageCodeText=popview.findViewById(R.id.skip_pc);
        PageCodeText.setText(page.PageCode+1+"");
        PageCodeText.setInputType(InputType.TYPE_CLASS_NUMBER);
        Button but_skip=popview.findViewById(R.id.skip_jump);

        Log.e("PageCodeText",PageCodeText.getText().toString());
        but_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)(IC.getActivity())).setPageCodeAndShow(Integer.parseInt(PageCodeText.getText().toString())-1);
                skipwindow.dismiss();
            }
        });
        Button but_cancel=popview.findViewById(R.id.skip_cancel);
        but_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipwindow.dismiss();
            }
        });
    }
}
