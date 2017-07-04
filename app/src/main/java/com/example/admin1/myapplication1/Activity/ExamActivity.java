package com.example.admin1.myapplication1.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    CheckBox cb_01,cb_02,cb_03,cb_04;
    CheckBox [] cbs=new CheckBox[4];
    TextView tv_examinfo,tv_exam_title,tv_op1,tv_op2,tv_op3,tv_op4,tv_load,tv_exam_no;
    ProgressBar dialog;
    ImageView img_examimg;
    LinearLayout layoutLoading,layout_01,layout_02,layout_03,layout_04;
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
                showExam( biz.getExam());
            } else
            {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tv_load.setText("下载失败,点击重新下载");

            }
        }
    }

    private void showExam(Questions questions) {
        if(questions!=null){
            tv_exam_no.setText(biz.getExamIndex());
            tv_exam_title.setText(questions.getQuestion());
            tv_op1.setText(questions.getItem1());
            tv_op2.setText(questions.getItem2());
            tv_op3.setText(questions.getItem3());
            tv_op4.setText(questions.getItem4());
            layout_03.setVisibility(questions.getItem3().equals("")?View.GONE:View.VISIBLE);
            cb_03.setVisibility(questions.getItem3().equals("")?View.GONE:View.VISIBLE);
            layout_04.setVisibility(questions.getItem4().equals("")?View.GONE:View.VISIBLE);
            cb_04.setVisibility(questions.getItem4().equals("")?View.GONE:View.VISIBLE);
            if(questions.getUrl()!=null && !questions.getUrl().equals(""))
            {
                img_examimg.setVisibility(View.VISIBLE);
                Picasso.with(ExamActivity.this)
                        .load(questions.getUrl())
                        .into(img_examimg);
            }else {
                img_examimg.setVisibility(View.GONE);
            }
            resetOptions();
            String userAnswer=questions.getUserAnswer();
            if(userAnswer!=null && !userAnswer.equals(""))
            {
                int userCB=Integer.parseInt(userAnswer)-1;
                cbs[userCB].setChecked(true);
            }
        }
    }

    private void resetOptions() {
        for(CheckBox cb:cbs)
        {
            cb.setChecked(false);
        }
    }
    private void saveUserAnswer(){

        for (int i = 0; i <cbs.length ; i++) {
            if(cbs[i].isChecked()){
                biz.getExam().setUserAnswer(String.valueOf(i+1));
                return;
            }
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

    public void preExam(View view) {
       saveUserAnswer();
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
       saveUserAnswer();
        showExam(biz.nextQuestion());
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
        layout_01=(LinearLayout) findViewById(R.id.layout_01);
        layout_02=(LinearLayout) findViewById(R.id.layout_02);
        layout_03=(LinearLayout) findViewById(R.id.layout_03);
        layout_04=(LinearLayout) findViewById(R.id.layout_04);
        tv_examinfo=(TextView) findViewById(R.id.tv_examinfo);
        tv_exam_title=(TextView) findViewById(R.id.tv_exam_title);
        tv_op1=(TextView) findViewById(R.id.tv_op1);
        tv_op2=(TextView) findViewById(R.id.tv_op2);
        tv_op3=(TextView) findViewById(R.id.tv_op3);
        tv_op4=(TextView) findViewById(R.id.tv_op4);
        cb_01=(CheckBox) findViewById(R.id.cb_01);
        cb_02=(CheckBox) findViewById(R.id.cb_02);
        cb_03=(CheckBox) findViewById(R.id.cb_03);
        cb_04=(CheckBox) findViewById(R.id.cb_04);
        cbs[0]=cb_01;
        cbs[1]=cb_02;
        cbs[2]=cb_03;
        cbs[3]=cb_04;
        img_examimg=(ImageView) findViewById(R.id.img_exam_img);
        tv_load=(TextView) findViewById(R.id.tv_load);
        dialog=(ProgressBar) findViewById(R.id.load_dialog);
        tv_exam_no=(TextView) findViewById(R.id.tv_exam_no);
        layoutLoading.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        cb_01.setOnCheckedChangeListener(listener);
        cb_02.setOnCheckedChangeListener(listener);
        cb_03.setOnCheckedChangeListener(listener);
        cb_04.setOnCheckedChangeListener(listener);
    }
    CompoundButton.OnCheckedChangeListener listener=new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {
                int userAnswer=0;
                switch(buttonView.getId())
                {
                    case R.id.cb_01:
                        userAnswer=1;
                        break;
                    case R.id.cb_02:
                        userAnswer=2;
                        break;
                    case R.id.cb_03:
                        userAnswer=3;
                        break;
                    case R.id.cb_04:
                        userAnswer=4;
                        break;
                }
                if(userAnswer>0)
                {
                    for(CheckBox cb:cbs)
                    {
                        cb.setChecked(false);
                    }
                    cbs[userAnswer-1].setChecked(true);
                }
            }
        }
    };
}
