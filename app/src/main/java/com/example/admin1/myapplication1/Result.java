package com.example.admin1.myapplication1;

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
    private List<ResultEntity> result;

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public String getReason() {
        return reason;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity {
        /**
         * id : 10
         * question : 这个标志是何含义？
         * answer : 2
         * item1 : 分向行驶车道
         * item2 : 掉头和左转合用车道
         * item3 : 禁止左转和掉头车道
         * item4 : 直行和左转合用车道
         * explains : 左转和掉头合并在一个标志里，你应该能看到的。
         * url : http://images.juheapi.com/jztk/c1c2subject1/10.jpg
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
}
