package com.student.mvcproject.bean;

import java.util.List;

public class StudentBean {
    private String stuId;
    private String stuName;
    private String stuDob;
    private String stuGender;
    private String stuPhone;
    private String stuEducation;
    private List<String> stuAttend;
    private String searchCourse;
    private String searchId;
    private String searchName;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuDob() {
        return stuDob;
    }

    public void setStuDob(String stuDob) {
        this.stuDob = stuDob;
    }

    public String getStuGender() {
        return stuGender;
    }

    public void setStuGender(String stuGender) {
        this.stuGender = stuGender;
    }

    public String getStuPhone() {
        return stuPhone;
    }

    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }

    public String getStuEducation() {
        return stuEducation;
    }

    public void setStuEducation(String stuEducation) {
        this.stuEducation = stuEducation;
    }

    public List<String> getStuAttend() {
        return stuAttend;
    }

    public void setStuAttend(List<String> stuAttend) {
        this.stuAttend = stuAttend;
    }

    public String getSearchCourse() {
        return searchCourse;
    }

    public void setSearchCourse(String searchCourse) {
        this.searchCourse = searchCourse;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public StudentBean() {
        super();
        // TODO Auto-generated constructor stub
    }

}
