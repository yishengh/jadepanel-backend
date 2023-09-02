package com.jade.finance.mapper;

import com.jade.finance.entity.UserFund;

import java.util.List;

/**
 * (UserFund)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 16:32:31
 */
public interface UserFundMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param fundId 主键
     * @return 实例对象
     */
    UserFund queryById(Integer fundId);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param userFund 实例对象
     * @return 对象列表
     */
    List<UserFund> queryAll(UserFund userFund);

    /**
     * 新增数据
     *
     * @param userFund 实例对象
     * @return 影响行数
     */
    int insert(UserFund userFund);

    /**
     * 修改数据
     *
     * @param userFund 实例对象
     * @return 影响行数
     */
    int update(UserFund userFund);

    /**
     * 通过主键删除数据
     *
     * @param fundId 主键
     * @return 影响行数
     */
    int deleteById(Integer fundId);

    List<String> findType();
}