package com.example.admin1.myapplication1.Dao;

import android.content.Intent;
import android.util.Log;
import com.example.admin1.myapplication1.Activity.ExamApplication;
import com.example.admin1.myapplication1.Bean.ExamInformations;
import com.example.admin1.myapplication1.Bean.Questions;
import com.example.admin1.myapplication1.Bean.Result;
import com.example.admin1.myapplication1.Utils.OkHttpUtils;
import com.example.admin1.myapplication1.Utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamDao implements IExamDao {

    @Override
    public void loadExamInfo() {
        OkHttpUtils<ExamInformations> utils=new OkHttpUtils<>(ExamApplication.getInstance());
        String url="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(url)
                .targetClass(ExamInformations.class)
                .execute(new OkHttpUtils.OnCompleteListener<ExamInformations>() {
                    @Override
                    public void onSuccess(ExamInformations result) {
                        Log.e("main","result="+result);
                        ExamApplication.getInstance().setExamInformations(result);
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,true));
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
                    }
                });
    }

    @Override
    public void loadExamLists() {

        OkHttpUtils<String> utils1=new OkHttpUtils<>(ExamApplication.getInstance());
        String url1="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils1.url(url1)
                .targetClass(String.class)
                .execute(new OkHttpUtils.OnCompleteListener<String>() {

                    @Override
                    public void onSuccess(String jsonStr) {
                        boolean success=false;
                        Result result= ResultUtils.getListResultFromJson(jsonStr);
                        if (result!=null && result.getError_code()==0){
                            List<Questions> list=result.getResult();
                            if(list!=null && list.size()>0)
                            {
                                ExamApplication.getInstance().setExamList(list);
                                success=true;
                            }
                        }
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                        .putExtra(ExamApplication.LOAD_DATA_SUCCESS,success));
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
                    }
                });

    }
}
