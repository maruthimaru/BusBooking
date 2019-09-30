package com.jp.busbooking.pojo;

import java.io.Serializable;

public class userModel implements Serializable {
    String name,mobile,age,base64;

    public userModel(String name, String mobile, String age,String base64) {
        this.name = name;
        this.mobile = mobile;
        this.age = age;
        this.base64=base64;
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

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
