package com.example.admin1.myapplication1.Bean;

import java.util.List;

/**
 * Created by admin1 on 2017/6/28.
 */

public class Result {
    /**
     * error_code : 0
     * reason : ok
     * result : [{"id":10,"question":"这个标志是何含义？","answer":"2","item1":"分向行驶车道","item2":"掉头和左转合用车道","item3":"禁止左转和掉头车道","item4":"直行和左转合用车道","explains":"左转和掉头合并在一个标志里，你应该能看到的。","url":"http://images.juheapi.com/jztk/c1c2subject1/10.jpg"}]
     */

    private int error_code;
    private String reason;
    private List<Questions> result;

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(List<Questions> result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public String getReason() {
        return reason;
    }

    public List<Questions> getResult() {
        return result;
    }
}
