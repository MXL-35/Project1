package com.example.admin1.myapplication1.Activity;

import android.app.Application;
import android.util.Log;

import com.example.admin1.myapplication1.Bean.ExamInformations;
import com.example.admin1.myapplication1.Bean.Questions;
import com.example.admin1.myapplication1.Bean.Result;
import com.example.admin1.myapplication1.Biz.ExamBiz;
import com.example.admin1.myapplication1.Biz.IExamBiz;
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
    IExamBiz biz;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        biz=new ExamBiz();
        initData();
    }
    public  static  ExamApplication getInstance(){
        return  instance;
    }

    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
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
