package com.jade.finance.entity;

import java.io.Serializable;

/**
 * (UserFund)实体类
 *
 * @author makejava
 * @since 2021-02-25 16:32:31
 */
public class UserFund implements Serializable {
    private static final long serialVersionUID = 414505981135630738L;
    /**
    * 基金自增交易ID
    */
    private Integer fundId;
    /**
    * 基金代码
    */
    private String fundCode;
    /**
    * 基金名
    */
    private String fundName;
    /**
    * 基金类型
    */
    private String fundType;
    /**
    * 当前基金单位净值
    */
    private String netWorth;
    /**
    * 原始买入费率,单位为百分比
    */
    private String buySourceRate;
    /**
    * 当前买入费率,单位为百分比
    */
    private String buyRate;
    /**
    * 基金经理
    */
    private String manager;
    /**
    * 每万分收益(货币基金)
    */
    private String millionCopiesIncome;


    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(String netWorth) {
        this.netWorth = netWorth;
    }

    public String getBuySourceRate() {
        return buySourceRate;
    }

    public void setBuySourceRate(String buySourceRate) {
        this.buySourceRate = buySourceRate;
    }

    public String getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(String buyRate) {
        this.buyRate = buyRate;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMillionCopiesIncome() {
        return millionCopiesIncome;
    }

    public void setMillionCopiesIncome(String millionCopiesIncome) {
        this.millionCopiesIncome = millionCopiesIncome;
    }

    private Integer offset;

    private Integer limit;

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

    @Override
    public String toString() {
        return "UserFund{" +
                "fundId=" + fundId +
                ", fundCode='" + fundCode + '\'' +
                ", fundName='" + fundName + '\'' +
                ", fundType='" + fundType + '\'' +
                ", netWorth='" + netWorth + '\'' +
                ", buySourceRate='" + buySourceRate + '\'' +
                ", buyRate='" + buyRate + '\'' +
                ", manager='" + manager + '\'' +
                ", millionCopiesIncome='" + millionCopiesIncome + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}