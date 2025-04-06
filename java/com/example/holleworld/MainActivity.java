package com.example.holleworld;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

//#fcaac3
public class MainActivity extends AppCompatActivity {

    public IndirectClass IC;
    Page pre,mid,post,temp;
    Button pageCode;
    Search search;
    public Typeface tf_hgxk;
    public  int ScreenH,ScreenW;
    public Data data;
    public VariationSave VS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tf_hgxk=Typeface.createFromAsset(getAssets(), "font/hgxk.TTF");
        IC=new IndirectClass(this,MainActivity.this);
        GetHW();
        Gen();
    }

    public void Gen(){
        search=new Search(findViewById(R.id.search_text));
        pre=new Page(findViewById(R.id.pre),this,tf_hgxk,IC);
        pre.LL.setTranslationX((float)(-ScreenW));
        mid=new Page(findViewById(R.id.mid),this,tf_hgxk,IC);
        post=new Page(findViewById(R.id.post),this,tf_hgxk,IC);
        data=new Data(this,IC);
        VS = new VariationSave();
        pageCode=findViewById(R.id.PC);
        if(data.canLoad())data.Load();
        else data.FillLPByFile(this);
        System.out.println("Holle noprint");

        //

        setPageCodeAndShow(0);
    }

    void GetHW(){
        ScreenH= Resources.getSystem().getDisplayMetrics().heightPixels;
        ScreenW= Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public void NextPage(View view) {
        if(pre.LL.getTranslationX()!=(float)(-ScreenW)||
                mid.PageCode==(int)(data.LP.size()/Page.NumOfOnePage))return;
        Log.e("TAG", "NextPage: click"+ScreenW);
        ObjectAnimator TranslateAnim=ObjectAnimator.ofFloat(mid.LL,"TranslationX",0f,(float)(-ScreenW));
        TranslateAnim.setDuration(400);
        temp=pre;pre=mid;mid=post;post=temp;
        setShowLevel(pre.LL,mid.LL,post.LL);
        post.LL.setTranslationX(0f);
        setPageCodeAndShow(mid.PageCode);
        TranslateAnim.start();
    }
    public void setShowLevel(LinearLayout pre,LinearLayout mid,LinearLayout post){
        pre.setZ(1f);
        mid.setZ(0f);
        post.setZ(-1f);
    }
    public void setPageCodeAndShow(int midpage){
        if(midpage>data.LP.size()/Page.NumOfOnePage)return;
        pre.ShowData(data,midpage-1);
        mid.ShowData(data,midpage);
        post.ShowData(data,midpage+1);
        setShowLevel(pre.LL,mid.LL,post.LL);
        SetPageCode();
        ClearExtra();
    }

    private void ClearExtra() {
        if(search.CurrentIndex==-1)return;
        pre.ClearExtra(search.CurrentIndex);
        mid.ClearExtra(search.CurrentIndex);
        post.ClearExtra(search.CurrentIndex);
    }

    public void LastPage(View view) {
        if(mid.LL.getTranslationX()!=0||mid.PageCode==0)return;
        Log.e("TAG", "LastPage: click"+ScreenW);
        ObjectAnimator TranslateAnim=ObjectAnimator.ofFloat(pre.LL,"TranslationX",(float)(-ScreenW),0f);
        TranslateAnim.setDuration(400);
        temp=post;post=mid;mid=pre;pre=temp;
        setShowLevel(pre.LL,mid.LL,post.LL);
        pre.LL.setTranslationX((float)(-ScreenW));
        setPageCodeAndShow(mid.PageCode);
        TranslateAnim.start();
    }

    public void Search(View view) {
        ClearExtra();
        search.isInSearch=true;
        search.CurrentIndex=data.SearchPeople(search.EText.getText().toString(),0,false);
        if(search.CurrentIndex==-1){
            Log.e("sdsd", "Search: 无" );
            Toast.makeText(this,"查无此人！",Toast.LENGTH_SHORT).show();
            return ;
        }
        ClearExtra();
        setPageCodeAndShow(search.CurrentIndex/Page.NumOfOnePage);
        mid.HighLight(search.CurrentIndex);

    }
    public void LastSearch(View view){
        search.isInSearch=true;
        int RIndex=data.SearchPeople(search.EText.getText().toString(),search.CurrentIndex-1,true);
        if(RIndex==-1){
            Log.e("sdsd", "Search: 无" );
            Toast.makeText(this,"前面没有了！",Toast.LENGTH_SHORT).show();
            return ;
        }
        ClearExtra();
        search.CurrentIndex=RIndex;
        setPageCodeAndShow(search.CurrentIndex/Page.NumOfOnePage);
        mid.HighLight(search.CurrentIndex);
    }
    public void NextSearch(View view){
        search.isInSearch=true;
        int RIndex=data.SearchPeople(search.EText.getText().toString(),search.CurrentIndex+1,false);
        if(RIndex==-1){
            Log.e("sdsd", "Search: 无" );
            Toast.makeText(this,"后面没有了！",Toast.LENGTH_SHORT).show();
            return ;
        }
        ClearExtra();
        search.CurrentIndex=RIndex;
        setPageCodeAndShow(search.CurrentIndex/Page.NumOfOnePage);
        mid.HighLight(search.CurrentIndex);
    }
    public void Alter(int index) {
        new Update(data,index,IC);
    }
    public void SetPageCode(){
        pageCode.setText(mid.PageCode+1+"");
    }

    public void Skip(View view) {
        new PageCodeSkip(mid,IC);
    }

    public void ToSetting(View view) {
        new Setting(IC);
    }
    public void UpdataAfterLoad(){
        data.Save();
        setPageCodeAndShow(mid.PageCode);
    }
}
