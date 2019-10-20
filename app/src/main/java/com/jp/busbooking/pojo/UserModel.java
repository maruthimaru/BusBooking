package com.jp.busbooking.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModel implements Serializable {
    String name,mobile,age,base64,busid,checkenStatus,gmobile;
    ArrayList<String> arrayList;

    public UserModel(String name, String mobile, String age, String base64, String busid, String checkenStatus, ArrayList<String> arrayList,String gmobile) {
        this.name = name;
        this.mobile = mobile;
        this.age = age;
        this.base64 = base64;
        this.busid = busid;
        this.checkenStatus = checkenStatus;
        this.arrayList = arrayList;
        this.gmobile=gmobile;
    }

    public UserModel(String name, String mobile, String age, String base64, String busid, String checkenStatus,String gmobile) {
        this.name = name;
        this.mobile = mobile;
        this.age = age;
        this.base64=base64;
        this.busid = busid;
        this.checkenStatus = checkenStatus;
        this.gmobile=gmobile;
    }

    public String getGmobile() {
        return gmobile;
    }

    public void setGmobile(String gmobile) {
        this.gmobile = gmobile;
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

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getCheckenStatus() {
        return checkenStatus;
    }

    public void setCheckenStatus(String checkenStatus) {
        this.checkenStatus = checkenStatus;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }
}
