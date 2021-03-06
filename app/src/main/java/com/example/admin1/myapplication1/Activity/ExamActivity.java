package com.example.admin1.myapplication1.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin1.myapplication1.Bean.ExamInformations;
import com.example.admin1.myapplication1.Bean.Questions;
import com.example.admin1.myapplication1.Biz.ExamBiz;
import com.example.admin1.myapplication1.Biz.IExamBiz;
import com.example.admin1.myapplication1.R;
import com.example.admin1.myapplication1.View.QuestionAdapter;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    boolean isLoadExamInfoReceiver = false;
    boolean isLoadQuestionReceiver = false;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestion = false;
    CheckBox[] cbs = new CheckBox[4];
    TextView[] tv0ps = new TextView[4];
    TextView  tv_result;
    IExamBiz biz;
    QuestionAdapter adapter;
    LoadExamBroadcast loadExamBroadcast;
    LoadQuestionBroadcast loadQuestionBroadcast;
    @BindView(R.id.load_dialog)
    ProgressBar dialog;
    @BindView(R.id.tv_load)
    TextView tv_load;
    @BindView(R.id.layout_loading)
    LinearLayout layoutLoading;
    @BindView(R.id.tv_examinfo)
    TextView tv_examinfo;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_exam_no)
    TextView tv_exam_no;
    @BindView(R.id.tv_exam_title)
    TextView tv_exam_title;
    @BindView(R.id.img_exam_img)
    ImageView img_examimg;
    @BindView(R.id.tv_op1)
    TextView tv_op1;
    @BindView(R.id.tv_explains)
    TextView tv_explains;
    @BindView(R.id.layout_01)
    LinearLayout  layout_01;
    @BindView(R.id.tv_op2)
    TextView tv_op2;
    @BindView(R.id.layout_02)
    LinearLayout  layout_02;
    @BindView(R.id.tv_op3)
    TextView tv_op3;
    @BindView(R.id.layout_03)
    LinearLayout  layout_03;
    @BindView(R.id.tv_op4)
    TextView tv_op4;
    @BindView(R.id.layout_04)
    LinearLayout  layout_04;
    @BindView(R.id.cb_01)
    CheckBox cb_01;
    @BindView(R.id.cb_02)
    CheckBox cb_02;
    @BindView(R.id.cb_03)
    CheckBox cb_03;
    @BindView(R.id.cb_04)
    CheckBox cb_04;
    @BindView(R.id.gallery)
    Gallery gallery;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_layout);
        ButterKnife.bind(this);
        loadExamBroadcast = new LoadExamBroadcast();
        loadQuestionBroadcast = new LoadQuestionBroadcast();
        setListener();
        initView();
        biz = new ExamBiz();
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
        registerReceiver(loadExamBroadcast, new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(loadQuestionBroadcast, new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void initData() {
        if (isLoadExamInfoReceiver && isLoadQuestionReceiver) {
            if (isLoadExamInfo && isLoadQuestion) {
                layoutLoading.setVisibility(View.GONE);
                ExamInformations examInformation = ExamApplication.getInstance().getExamInformations();
                if (examInformation != null) {
                    showData(examInformation);
                    initTimer(examInformation);
                }
                initGallery();
                showExam(biz.getExam());
            } else {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tv_load.setText("下载失败,点击重新下载");

            }
        }
    }

    private void initGallery() {
        adapter = new QuestionAdapter(this);
        gallery.setAdapter(adapter);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveUserAnswer();
                showExam(biz.getExam(position));
            }
        });
    }

    private void initTimer(ExamInformations examInformation) {
        int sumTime = examInformation.getLimitTime() * 60 * 1000;
        final long overTime = sumTime + System.currentTimeMillis();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long l = overTime - System.currentTimeMillis();
                final long min = l / 1000 / 60;
                final long sec = l / 1000 % 60;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_time.setText("剩余时间:" + min + "分" + sec + "秒");
                    }
                });
            }
        }, 0, 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commitExam();
                    }
                });
            }
        }, sumTime);
    }

    private void showExam(Questions questions) {
        if (questions != null) {
            tv_exam_no.setText(biz.getExamIndex());
            tv_exam_title.setText(questions.getQuestion());
            tv_op1.setText(questions.getItem1());
            tv_op2.setText(questions.getItem2());
            tv_op3.setText(questions.getItem3());
            tv_op4.setText(questions.getItem4());
            tv_time = (TextView) findViewById(R.id.tv_time);
            layout_03.setVisibility(questions.getItem3().equals("") ? View.GONE : View.VISIBLE);
            cb_03.setVisibility(questions.getItem3().equals("") ? View.GONE : View.VISIBLE);
            layout_04.setVisibility(questions.getItem4().equals("") ? View.GONE : View.VISIBLE);
            cb_04.setVisibility(questions.getItem4().equals("") ? View.GONE : View.VISIBLE);
            if (questions.getUrl() != null && !questions.getUrl().equals("")) {
                img_examimg.setVisibility(View.VISIBLE);
                Picasso.with(ExamActivity.this)
                        .load(questions.getUrl())
                        .into(img_examimg);
            } else {
                img_examimg.setVisibility(View.GONE);
            }
            for (TextView tv : tv0ps) {
                tv.setTextColor(getResources().getColor(R.color.black));
            }
            resetOptions();
            String userAnswer = questions.getUserAnswer();
            if (userAnswer != null && !userAnswer.equals("")) {
                int userCB = Integer.parseInt(userAnswer) - 1;
                cbs[userCB].setChecked(true);
                setOptions(true);
                for (CheckBox cb:cbs){
                    cb.setEnabled(false);
                    setAnswerTextColor(userAnswer, questions.getAnswer());
                    tv_explains.setText("解析:\n"+questions.getExplains());
                }
            } else {
                setOptions(false);
                setOptionsColor();
            }
        }
    }
    private void setOptionsColor() {
        for (TextView tvOp : tv0ps) {
            tvOp.setTextColor(getResources().getColor(R.color.black));
        }
    }

    private void setAnswerTextColor(String userAnswer, String answer) {
        int ra = Integer.parseInt(answer) - 1;
        for (int i = 0; i < tv0ps.length; i++) {
          if (i == ra) {
              tv0ps[i].setTextColor(getResources().getColor(R.color.green));
            } else {
                if (!userAnswer.equals(answer)) {
                    int ua = Integer.parseInt(userAnswer) - 1;
                    if (i == ua) {
                        tv0ps[i].setTextColor(getResources().getColor(R.color.red));
                    } else {
                        tv0ps[i].setTextColor(getResources().getColor(R.color.black));
                    }
                }
            }
        }
    }

    private void setOptions(boolean hasAnswer) {
        for (CheckBox cb : cbs) {
            cb.setEnabled(!hasAnswer);
            tv_explains.setText("");
        }
    }

    private void resetOptions() {
        for (CheckBox cb : cbs) {
            cb.setChecked(false);
        }
    }

    private void saveUserAnswer() {

        for (int i = 0; i < cbs.length; i++) {
            if (cbs[i].isChecked()) {
                biz.getExam().setUserAnswer(String.valueOf(i + 1));
                setOptions(true);
                adapter.notifyDataSetChanged();
                return;
            }
        }
        biz.getExam().setUserAnswer("");
        adapter.notifyDataSetChanged();
    }

    private void showData(ExamInformations examInformation) {
        tv_examinfo.setText(examInformation.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadExamBroadcast != null) {
            unregisterReceiver(loadExamBroadcast);
        }
        if (loadQuestionBroadcast != null) {
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
    public void commitExam(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("交卷")
                .setMessage("你还有剩余时间，确认交卷吗?")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        commitExam();
                    }
                })
                .setNegativeButton("取消",null);
        builder.create().show();
    }

    public void commitExam() {
        saveUserAnswer();
        int s = biz.commitExam();
        View inflate = View.inflate(this, R.layout.layout_result, null);
        TextView tvResult = (TextView) inflate.findViewById(R.id.tv_result);
        tvResult.setText("你的分数为\n" + s + "分!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.exam_commit32x32)
                .setTitle("交卷")
                .setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setCancelable(false);
        builder.create().show();
    }

    class LoadExamBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            if (isSuccess) {
                isLoadExamInfo = true;
            }
            isLoadExamInfoReceiver = true;
            initData();
        }
    }

    class LoadQuestionBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            if (isSuccess) {
                isLoadQuestion = true;
            }
            isLoadQuestionReceiver = true;
            initData();
        }
    }

    private void initView() {
       /* gallery = (Gallery) findViewById(R.id.gallery);
        layoutLoading = (LinearLayout) findViewById(R.id.layout_loading);
        layout_01 = (LinearLayout) findViewById(R.id.layout_01);
        layout_02 = (LinearLayout) findViewById(R.id.layout_02);
        layout_03 = (LinearLayout) findViewById(R.id.layout_03);
        layout_04 = (LinearLayout) findViewById(R.id.layout_04);
        tv_examinfo = (TextView) findViewById(R.id.tv_examinfo);
        tv_exam_title = (TextView) findViewById(R.id.tv_exam_title);
        tv_op1 = (TextView) findViewById(R.id.tv_op1);
        tv_op2 = (TextView) findViewById(R.id.tv_op2);
        tv_op3 = (TextView) findViewById(R.id.tv_op3);
        tv_op4 = (TextView) findViewById(R.id.tv_op4);
        cb_01 = (CheckBox) findViewById(R.id.cb_01);
        cb_02 = (CheckBox) findViewById(R.id.cb_02);
        cb_03 = (CheckBox) findViewById(R.id.cb_03);
        cb_04 = (CheckBox) findViewById(R.id.cb_04);
        img_examimg = (ImageView) findViewById(R.id.img_exam_img);
        tv_load = (TextView) findViewById(R.id.tv_load);
        dialog = (ProgressBar) findViewById(R.id.load_dialog);
        tv_exam_no = (TextView) findViewById(R.id.tv_exam_no);*/
        cbs[0] = cb_01;
        cbs[1] = cb_02;
        cbs[2] = cb_03;
        cbs[3] = cb_04;
        tv0ps[0] = tv_op1;
        tv0ps[1] = tv_op2;
        tv0ps[2] = tv_op3;
        tv0ps[3] = tv_op4;
       /* layoutLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });*/
        cb_01.setOnCheckedChangeListener(listener);
        cb_02.setOnCheckedChangeListener(listener);
        cb_03.setOnCheckedChangeListener(listener);
        cb_04.setOnCheckedChangeListener(listener);
    }
@OnClick(R.id.layout_loading) void  onLoadClick(){
    loadData();
}
    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                int userAnswer = 0;
                switch (buttonView.getId()) {
                    case R.id.cb_01:
                        userAnswer = 1;
                        break;
                    case R.id.cb_02:
                        userAnswer = 2;
                        break;
                    case R.id.cb_03:
                        userAnswer = 3;
                        break;
                    case R.id.cb_04:
                        userAnswer = 4;
                        break;
                }
                if (userAnswer > 0) {
                    for (CheckBox cb : cbs) {
                        cb.setChecked(false);
                    }
                    cbs[userAnswer - 1].setChecked(true);
                }
            }
        }
    };
}
