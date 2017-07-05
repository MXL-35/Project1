package com.example.admin1.myapplication1.Biz;

import com.example.admin1.myapplication1.Activity.ExamApplication;
import com.example.admin1.myapplication1.Bean.Questions;
import com.example.admin1.myapplication1.Dao.ExamDao;
import com.example.admin1.myapplication1.Dao.IExamDao;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamBiz implements  IExamBiz {
    IExamDao dao;
    int examIndex=0;
    List<Questions> examList=null;
    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void beginExam() {
        examIndex=0;
        dao.loadExamInfo();
        dao.loadExamLists();
    }
    public Questions getExam() {
        examList=ExamApplication.getInstance().getExamList();
        if(examList!=null)
        {
            return  examList.get(examIndex);
        }else {
            return null;
        }
    }
    public Questions getExam(int index) {
        examList=ExamApplication.getInstance().getExamList();
        examIndex=index;
        if(examList!=null)
        {
            return  examList.get(examIndex);
        }else {
            return null;
        }
    }
    @Override
    public Questions nextQuestion() {
        if(examList!=null && examIndex<examList.size()-1)
        {
            examIndex++;
            return  examList.get(examIndex);
        }else {
            return null;
        }
    }

    @Override
    public Questions preQuestion() {
        if(examList!=null && examIndex>0)
        {
            examIndex--;
            return  examList.get(examIndex);
        }else {
            return null;
        }

    }

    @Override
    public int commitExam() {
        int s=0;
        for(Questions questions:examList){
            String userAnswer=questions.getUserAnswer();
            if(userAnswer!=null && !userAnswer.equals("")){
                if(questions.getAnswer().equals(userAnswer)){
                    s++;
                }
            }
        }
        return  s;
    }
    public  String getExamIndex()
    {
        return (examIndex+1)+".";
    }
}
