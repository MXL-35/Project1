package com.example.admin1.myapplication1.Biz;

import com.example.admin1.myapplication1.Bean.Questions;

/**
 * Created by Administrator on 2017/6/30.
 */

public interface IExamBiz {
    void beginExam();
    Questions getExam();
    Questions nextQuestion();
    Questions preQuestion();
    void commitExam();
    String getExamIndex();
}
