package com.example.admin1.myapplication1.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin1.myapplication1.Bean.ExamInformations;
import com.example.admin1.myapplication1.Bean.Questions;
import com.example.admin1.myapplication1.Biz.ExamBiz;
import com.example.admin1.myapplication1.Biz.IExamBiz;
import com.example.admin1.myapplication1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    boolean isLoadExamInfoReceiver=false;
    boolean isLoadQuestionReceiver=false;
    boolean isLoadExamInfo=false;
    boolean isLoadQuestion=false;
    TextView tv_examinfo,tv_exam_title,tv_op1,tv_op2,tv_op3,tv_op4,tv_load;
    ProgressBar dialog;
    ImageView img_examimg;
    LinearLayout layoutLoading;
    IExamBiz biz;
    LoadExamBroadcast loadExamBroadcast;
    LoadQuestionBroadcast loadQuestionBroadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_layout);
        loadExamBroadcast=new LoadExamBroadcast();
        loadQuestionBroadcast=new LoadQuestionBroadcast();
        setListener();
        initView();
        biz=new ExamBiz();
        loadData();
    }
    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tv_load.setText("下载数据...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }
    private void setListener() {
        registerReceiver(loadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(loadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void initData() {
        if(isLoadExamInfoReceiver && isLoadQuestionReceiver)
        {
            if(isLoadExamInfo && isLoadQuestion)
            {   layoutLoading.setVisibility(View.GONE);
                ExamInformations examInformation=ExamApplication.getInstance().getExamInformations();
                if(examInformation!=null)
                {
                    showData(examInformation);
                }
                List<Questions> examList=ExamApplication.getInstance().getExamList();
                if(examList!=null){
                    showExam(examList);
                }
            } else
            {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tv_load.setText("下载失败,点击重新下载");

            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loadExamBroadcast!=null){
            unregisterReceiver(loadExamBroadcast);
        }
        if(loadQuestionBroadcast!=null)
        {
            unregisterReceiver(loadQuestionBroadcast);
        }
    }

    class  LoadExamBroadcast extends BroadcastReceiver
   {

    @Override
       public void onReceive(Context context, Intent intent) {
          boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
          if(isSuccess){
               isLoadExamInfo=true;
        }
        isLoadExamInfoReceiver=true;
        initData();
      }
   }

    class  LoadQuestionBroadcast extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            if(isSuccess){
                isLoadQuestion=true;
            }
            isLoadQuestionReceiver=true;
            initData();
        }
    }
    private void initView() {
        layoutLoading=(LinearLayout) findViewById(R.id.layout_loading);
        tv_examinfo=(TextView) findViewById(R.id.tv_examinfo);
        tv_exam_title=(TextView) findViewById(R.id.tv_exam_title);
        tv_op1=(TextView) findViewById(R.id.tv_op1);
        tv_op2=(TextView) findViewById(R.id.tv_op2);
        tv_op3=(TextView) findViewById(R.id.tv_op3);
        tv_op4=(TextView) findViewById(R.id.tv_op4);
        img_examimg=(ImageView) findViewById(R.id.img_exam_img);
        tv_load=(TextView) findViewById(R.id.tv_load);
        dialog=(ProgressBar) findViewById(R.id.load_dialog);
        layoutLoading.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }


}
