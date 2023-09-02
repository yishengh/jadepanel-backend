package com.jade.finance.entity;

import java.io.Serializable;

/**
 * (UserStock)实体类
 *
 * @author makejava
 * @since 2021-02-25 16:34:00
 */
public class UserStock implements Serializable {
    private static final long serialVersionUID = -41559956484674479L;
    /**
    * 行id，主键
    */
    private Integer stockId;
    /**
    * 股市代码
    */
    private String stockCode;
    /**
    * 股票名称
    */
    private String stockName;
    /**
    * 股票类型
    */
    private String stockType;
    /**
    * 每股价格
    */
    private String stockPrice;
    /**
    * 购买数量
    */
    private String stockNum;
    /**
    * 购买时间
    */
    private String stockTime;
    /**
    * 购买人
    */
    private String stockUser;
    /**
    * 购买人id
    */
    private Integer stockUserId;


    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getStockTime() {
        return stockTime;
    }

    public void setStockTime(String stockTime) {
        this.stockTime = stockTime;
    }

    public String getStockUser() {
        return stockUser;
    }

    public void setStockUser(String stockUser) {
        this.stockUser = stockUser;
    }

    public Integer getStockUserId() {
        return stockUserId;
    }

    public void setStockUserId(Integer stockUserId) {
        this.stockUserId = stockUserId;
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
}