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
    public  static  String LOAD_EXAM_INFO="load_exam_info";
    public static  String LOAD_EXAM_QUESTION="load_exam_question";
    public static String LOAD_DATA_SUCCESS="load_data_success";
    ExamInformations examInformations;
    List<Questions> examList;
    private  static  ExamApplication instance;
    IExamBiz biz;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
    public  static  ExamApplication getInstance(){
        return  instance;
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
