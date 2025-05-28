package com.example.holleworld.Utils;

import android.app.Activity;
import android.content.Context;

import com.example.holleworld.UI.MainActivity;

public class IndirectClass {
    private Context contxt;
    private MainActivity activity;

    public Context getContxt() {
        return contxt;
    }

    public void setContxt(Context contxt) {
        this.contxt = (MainActivity) contxt;
    }

    public MainActivity getActivity() {
        return (MainActivity) activity;
    }

    public void setActivity(Activity activity) {
        this.activity = (MainActivity) activity;
    }

    public IndirectClass(Context context, MainActivity activity) {
        this.setContxt(context);
        this.setActivity(activity);
    }
}

