package com.example.admin1.myapplication1;

/**
 * Created by admin1 on 2017/6/28.
 */

public class Questions {
    /**
     * id : 6
     * question : 这个标志是何含义？
     * answer : 4
     * item1 : 右转车道
     * item2 : 掉头车道
     * item3 : 左转车道
     * item4 : 直行车道
     * explains : 表示只准一切车辆直行。此标志设在直行的路口以前适当位置。
     * url : http://images.juheapi.com/jztk/c1c2subject1/6.jpg
     */

    private int id;
    private String question;
    private String answer;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String explains;
    private String url;

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getItem1() {
        return item1;
    }

    public String getItem2() {
        return item2;
    }

    public String getItem3() {
        return item3;
    }

    public String getItem4() {
        return item4;
    }

    public String getExplains() {
        return explains;
    }

    public String getUrl() {
        return url;
    }
}
