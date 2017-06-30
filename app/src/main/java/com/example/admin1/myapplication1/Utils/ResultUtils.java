package com.example.admin1.myapplication1.Utils;

import android.util.Log;

import com.example.admin1.myapplication1.Bean.Questions;
import com.example.admin1.myapplication1.Bean.Result;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class ResultUtils {
    static String UTF_8 = "utf-8";
    public static Result getListResultFromJson(String jsonStr){
        Result result = new Result();
        Log.e("Utils","jsonStr="+jsonStr);
        try {
            if(jsonStr==null || jsonStr.isEmpty() || jsonStr.length()<3)return null;
            JSONObject jsonObject = new JSONObject(jsonStr);
            if(!jsonObject.isNull("error_code")) {
                result.setError_code(jsonObject.getInt("error_code"));
            }else if(!jsonObject.isNull("msg")){
                result.setError_code(jsonObject.getInt("msg"));
            }
            if(!jsonObject.isNull("reason")) {
                result.setReason(jsonObject.getString("reason"));
            }else if(!jsonObject.isNull("result")){
                result.setReason(jsonObject.getString("result"));
            }
            if(!jsonObject.isNull("result")) {
                JSONArray array = jsonObject.getJSONArray("result");
                if (array != null) {
                    List<Questions> list = new ArrayList<Questions>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonGroupAvatar = array.getJSONObject(i);
                        Questions ga = new Gson().fromJson(jsonGroupAvatar.toString(), Questions.class);
                        list.add(ga);
                    }
                    result.setResult(list);
                    return result;
                }
            }else{
                JSONArray array = new JSONArray(jsonStr);
                if (array != null) {
                    List<Questions> list = new ArrayList<Questions>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonGroupAvatar = array.getJSONObject(i);
                        Questions ga = new Gson().fromJson(jsonGroupAvatar.toString(), Questions.class);
                        list.add(ga);
                    }
                    result.setResult(list);
                    return result;
                }
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

}
