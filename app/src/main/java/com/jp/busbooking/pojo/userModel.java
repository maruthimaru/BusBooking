package com.jp.busbooking.pojo;

import java.io.Serializable;

public class userModel implements Serializable {
    String name,mobile,age;

    public userModel(String name, String mobile, String age) {
        this.name = name;
        this.mobile = mobile;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
