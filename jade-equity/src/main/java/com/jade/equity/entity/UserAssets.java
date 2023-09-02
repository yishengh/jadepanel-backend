package com.jade.equity.entity;

import java.io.Serializable;

/**
 * (UserAssets)实体类
 *
 * @author makejava
 * @since 2021-03-30 14:33:48
 */
public class UserAssets implements Serializable {
    private static final long serialVersionUID = -61769372495321621L;
    /**
    * 资产id主键
    */
    private Integer assetsId;
    /**
    * 资产名
    */
    private String assetsName;
    /**
    * 资产所在地
    */
    private String assetsLocation;
    /**
    * 资产获得时间
    */
    private String assetsCreateTime;
    /**
    * 全款或首付花费
    */
    private String totalPrice;
    /**
    * 历史价值
    */
    private String historicalValue;
    /**
    * 资产所有者
    */
    private String assetsOwner;
    /**
    * 资产分期（可没有）
    */
    private String assetsInstalment;
    /**
    * 每期价格
    */
    private String instalmentPrice;
    /**
    * 剩余期限
    */
    private String instalmentSurplus;
    /**
    * 变现价值
    */
    private String realizationValue;
    /**
    * 备注
    */
    private String assetsRemark;




    public Integer getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Integer assetsId) {
        this.assetsId = assetsId;
    }

    public String getAssetsName() {
        return assetsName;
    }

    public void setAssetsName(String assetsName) {
        this.assetsName = assetsName;
    }

    public String getAssetsLocation() {
        return assetsLocation;
    }

    public void setAssetsLocation(String assetsLocation) {
        this.assetsLocation = assetsLocation;
    }

    public String getAssetsCreateTime() {
        return assetsCreateTime;
    }

    public void setAssetsCreateTime(String assetsCreateTime) {
        this.assetsCreateTime = assetsCreateTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getHistoricalValue() {
        return historicalValue;
    }

    public void setHistoricalValue(String historicalValue) {
        this.historicalValue = historicalValue;
    }

    public String getAssetsOwner() {
        return assetsOwner;
    }

    public void setAssetsOwner(String assetsOwner) {
        this.assetsOwner = assetsOwner;
    }

    public String getAssetsInstalment() {
        return assetsInstalment;
    }

    public void setAssetsInstalment(String assetsInstalment) {
        this.assetsInstalment = assetsInstalment;
    }

    public String getInstalmentPrice() {
        return instalmentPrice;
    }

    public void setInstalmentPrice(String instalmentPrice) {
        this.instalmentPrice = instalmentPrice;
    }

    public String getInstalmentSurplus() {
        return instalmentSurplus;
    }

    public void setInstalmentSurplus(String instalmentSurplus) {
        this.instalmentSurplus = instalmentSurplus;
    }

    public String getRealizationValue() {
        return realizationValue;
    }

    public void setRealizationValue(String realizationValue) {
        this.realizationValue = realizationValue;
    }

    public String getAssetsRemark() {
        return assetsRemark;
    }

    public void setAssetsRemark(String assetsRemark) {
        this.assetsRemark = assetsRemark;
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