package com.jade.bills.entity;

import java.io.Serializable;

/**
 * (UserIncome)实体类
 *
 * @author makejava
 * @since 2021-02-23 15:10:01
 */
public class UserIncome  implements Serializable {
    private static final long serialVersionUID = -80246787635708730L;
    /**
    * 主键，收入id
    */
    private Integer incomeId;
    /**
    * 收入时间
    */
    private String incomeTime;
    /**
    * 收入金额
    */
    private String incomeNum;
    /**
    * 收入类型
    */
    private String incomeSort;
    /**
    * 收入备注
    */
    private String incomeRemark;
    /**
    * 收入者ID
    */
    private Integer incomeUserId;
    /**
    * 收入者名字
    */
    private String incomeUser;


    private Integer offset;

    private Integer limit;

    private String date1;

    private String date2;

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }


    public Integer getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Integer incomeId) {
        this.incomeId = incomeId;
    }

    public String getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(String incomeTime) {
        this.incomeTime = incomeTime;
    }

    public String getIncomeNum() {
        return incomeNum;
    }

    public void setIncomeNum(String incomeNum) {
        this.incomeNum = incomeNum;
    }

    public String getIncomeSort() {
        return incomeSort;
    }

    public void setIncomeSort(String incomeSort) {
        this.incomeSort = incomeSort;
    }

    public String getIncomeRemark() {
        return incomeRemark;
    }

    public void setIncomeRemark(String incomeRemark) {
        this.incomeRemark = incomeRemark;
    }

    public Integer getIncomeUserId() {
        return incomeUserId;
    }

    public void setIncomeUserId(Integer incomeUserId) {
        this.incomeUserId = incomeUserId;
    }

    public String getIncomeUser() {
        return incomeUser;
    }

    public void setIncomeUser(String incomeUser) {
        this.incomeUser = incomeUser;
    }

    @Override
    public String toString() {
        return "UserIncome{" +
                "incomeId=" + incomeId +
                ", incomeTime='" + incomeTime + '\'' +
                ", incomeNum='" + incomeNum + '\'' +
                ", incomeSort='" + incomeSort + '\'' +
                ", incomeRemark='" + incomeRemark + '\'' +
                ", incomeUserId=" + incomeUserId +
                ", incomeUser='" + incomeUser + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                ", date1='" + date1 + '\'' +
                ", date2='" + date2 + '\'' +
                '}';
    }
}