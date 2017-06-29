package com.example.admin1.myapplication1.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.admin1.myapplication1.Bean.ExamInformations;
import com.example.admin1.myapplication1.Bean.Result;
import com.example.admin1.myapplication1.R;
import com.example.admin1.myapplication1.Utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exit(View view) {
        finish();
    }

    public void test(View view) {
        OkHttpUtils<ExamInformations> utils=new OkHttpUtils<>(getApplicationContext());
        String url="http://101.251.196.90:8080/JztkServer/examInfo";
        Log.e("main","u333333333333333rl"+url);
       utils.url(url).targetClass(ExamInformations.class)
       .execute(new OkHttpUtils.OnCompleteListener<ExamInformations>() {
           @Override
           public void onSuccess(ExamInformations result) {
               Log.e("main","result"+result);
           }

           @Override
           public void onError(String error) {
               Log.e("main","error"+error);
           }
       });
        startActivity(new Intent(MainActivity.this,ExamActivity.class));
    }
}
