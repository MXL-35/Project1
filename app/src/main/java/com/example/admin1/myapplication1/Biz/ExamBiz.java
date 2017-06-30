package com.example.admin1.myapplication1.Biz;

import com.example.admin1.myapplication1.Dao.ExamDao;
import com.example.admin1.myapplication1.Dao.IExamDao;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamBiz implements  IExamBiz {
    IExamDao dao;

    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void beginExam() {
        dao.loadExamInfo();
        dao.loadExamLists();
    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}
