package com.lesports.qmt.web.model;

import java.util.Map;

/**
 * Created by zhonglin on 2016/9/26.
 */
public class TeamPlayerStatsVo {

    //id
    private Long pid;
    //球员名称
    private String name;
    //是否首发
    private Boolean starting;
    //位置
    private String position;
    //球员号码
    private Integer number;
    //球员logo
    private String logo;
    //球员身高
    private String height;
    //球员体重
    private String weight;
    //球员年龄
    private String age;
    //球员球龄
    private String year;
    //球员薪资
    private String salary;
    //技术统计
    private Map<String, String> stats;
    //平均技术统计
    private Map<String, String> avgStats;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStarting() {
        return starting;
    }

    public void setStarting(Boolean starting) {
        this.starting = starting;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Map<String, String> getStats() {
        return stats;
    }

    public void setStats(Map<String, String> stats) {
        this.stats = stats;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Map<String, String> getAvgStats() {
        return avgStats;
    }

    public void setAvgStats(Map<String, String> avgStats) {
        this.avgStats = avgStats;
    }

    @Override
    public String toString() {
        return "BestPlayerStatsVo{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", starting=" + starting +
                ", position='" + position + '\'' +
                ", number=" + number +
                ", logo='" + logo + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", age='" + age + '\'' +
                ", year='" + year + '\'' +
                ", salary='" + salary + '\'' +
                ", stats=" + stats +
                ", avgStats=" + avgStats +
                '}';
    }


}
