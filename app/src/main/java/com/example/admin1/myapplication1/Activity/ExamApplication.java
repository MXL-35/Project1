package com.example.admin1.myapplication1.Activity;

import android.app.Application;
import android.util.Log;

import com.example.admin1.myapplication1.Bean.ExamInformations;
import com.example.admin1.myapplication1.Bean.Questions;
import com.example.admin1.myapplication1.Bean.Result;
import com.example.admin1.myapplication1.Utils.OkHttpUtils;
import com.example.admin1.myapplication1.Utils.ResultUtils;

import java.util.List;

/**
 * Created by admin1 on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamInformations examInformations;
    List<Questions> examList;
    private  static  ExamApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initData();
    }
    public  static  ExamApplication getInstance(){
        return  instance;
    }

    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils<ExamInformations> utils=new OkHttpUtils<>(instance);
                String url="http://101.251.196.90:8080/JztkServer/examInfo";
                utils.url(url)
                        .targetClass(ExamInformations.class)
                        .execute(new OkHttpUtils.OnCompleteListener<ExamInformations>() {
                            @Override
                            public void onSuccess(ExamInformations result) {
                                Log.e("main","result="+result);
                                examInformations=result;
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);
                            }
                        });

                OkHttpUtils<String> utils1=new OkHttpUtils<>(instance);
                String url1="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url1)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {

                            @Override
                            public void onSuccess(String jsonStr) {
                                Result result= ResultUtils.getListResultFromJson(jsonStr);
                                if (result!=null && result.getError_code()==0){
                                    List<Questions> list=result.getResult();
                                    if(list!=null && list.size()>0)
                                    {
                                        examList=list;
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);
                            }
                        });
            }
        }).start();

    }
    public ExamInformations getExamInformations() {
        return examInformations;
    }

    public void setExamInformations(ExamInformations examInformations) {
        this.examInformations = examInformations;
    }

    public List<Questions> getExamList() {
        return examList;
    }

    public void setExamList(List<Questions> examList) {
        this.examList = examList;
    }
}
