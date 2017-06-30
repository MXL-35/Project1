package com.example.admin1.myapplication1.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin1.myapplication1.Bean.ExamInformations;
import com.example.admin1.myapplication1.Bean.Questions;
import com.example.admin1.myapplication1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    TextView tv_examinfo,tv_exam_title,tv_op1,tv_op2,tv_op3,tv_op4;
    ImageView img_examimg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_layout);
        initView();
        initData();
    }

    private void initData() {
        ExamInformations examInformation=ExamApplication.getInstance().getExamInformations();
        if(examInformation!=null)
        {
            showData(examInformation);
        }
        List<Questions> examList=ExamApplication.getInstance().getExamList();
        if(examList!=null){
            showExam(examList);
        }
    }

    private void showExam(List<Questions> examList) {
        Questions questions=examList.get(0);
        if(questions!=null){
          tv_exam_title.setText(questions.getQuestion());
            tv_op1.setText(questions.getItem1());
            tv_op2.setText(questions.getItem2());
            tv_op3.setText(questions.getItem3());
            tv_op4.setText(questions.getItem4());
            Picasso.with(ExamActivity.this)
                    .load(questions.getUrl())
                    .into(img_examimg);

        }
    }


    private void showData(ExamInformations examInformation) {
        tv_examinfo.setText(examInformation.toString());
    }

    private void initView() {
        tv_examinfo=(TextView) findViewById(R.id.tv_examinfo);
        tv_exam_title=(TextView) findViewById(R.id.tv_exam_title);
        tv_op1=(TextView) findViewById(R.id.tv_op1);
        tv_op2=(TextView) findViewById(R.id.tv_op2);
        tv_op3=(TextView) findViewById(R.id.tv_op3);
        tv_op4=(TextView) findViewById(R.id.tv_op4);
        img_examimg=(ImageView) findViewById(R.id.img_exam_img);
    }


}
