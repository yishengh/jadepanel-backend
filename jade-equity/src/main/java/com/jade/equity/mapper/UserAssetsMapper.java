package com.jade.equity.mapper;

import com.jade.equity.entity.UserAssets;

import java.util.List;
import java.util.Map;

/**
 * (UserAssets)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-30 14:33:51
 */
public interface UserAssetsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param assetsId 主键
     * @return 实例对象
     */
    UserAssets queryById(Integer assetsId);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param userAssets 实例对象
     * @return 对象列表
     */
    List<UserAssets> queryAll(UserAssets userAssets);

    /**
     * 新增数据
     *
     * @param userAssets 实例对象
     * @return 影响行数
     */
    int insert(UserAssets userAssets);

    /**
     * 修改数据
     *
     * @param userAssets 实例对象
     * @return 影响行数
     */
    int update(UserAssets userAssets);

    /**
     * 通过主键删除数据
     *
     * @param assetsId 主键
     * @return 影响行数
     */
    int deleteById(Integer assetsId);

    List<Map> assetRatio();

    List<Map> loanSituation();

    Object totalCost();

    Object totalRealizableValue();

    Object totalMortgage();

    Object monthCost();
}