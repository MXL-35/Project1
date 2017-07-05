package com.example.admin1.myapplication1.View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin1.myapplication1.Activity.ExamApplication;
import com.example.admin1.myapplication1.Bean.Questions;
import com.example.admin1.myapplication1.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class QuestionAdapter extends BaseAdapter {
Context context;
    List<Questions> examList;

    public QuestionAdapter(Context context) {
        this.context = context;
        examList= ExamApplication.getInstance().getExamList();
    }

    @Override
    public int getCount() {
        return examList==null?0:examList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context, R.layout.item_question,null);
        TextView tvNo=(TextView) view.findViewById(R.id.tv_no);
        ImageView ivQuestion=(ImageView) view.findViewById(R.id.iv_question);
        String ua=examList.get(position).getUserAnswer();
        String ra=examList.get(position).getAnswer();
        if(ua!=null && !ua.equals("")){
            ivQuestion.setImageResource(ua.equals(ra)?R.mipmap.answer24x24:R.mipmap.error24x24);
        }else {
            ivQuestion.setImageResource(R.mipmap.unknown24x24);
        }
        tvNo.setText("第"+(position+1)+"题");
        return view;
    }
}
