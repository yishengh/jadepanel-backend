package com.jade.bills.service;

import com.jade.bills.entity.UserIncome;
import com.github.pagehelper.PageInfo;
import jxl.write.WriteException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * (UserIncome)表服务接口
 *
 * @author makejava
 * @since 2021-02-23 15:10:04
 */
public interface UserIncomeService {

    /**
     * 通过ID查询单条数据
     *
     * @param incomeId 主键
     * @return 实例对象
     */
    UserIncome queryById(Integer incomeId);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userIncome 实例对象
     * @return 对象列表
     */
    PageInfo<UserIncome> queryAllByEntity(UserIncome userIncome);

    /**
     * 新增数据
     *
     * @param userIncome 实例对象
     * @return 实例对象
     */
    UserIncome insert(UserIncome userIncome);

    /**
     * 修改数据
     *
     * @param userIncome 实例对象
     * @return 实例对象
     */
    UserIncome update(UserIncome userIncome);

    /**
     * 通过主键删除数据
     *
     * @param incomeId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer incomeId);

    List<String> findType();

    void downloadFile() throws IOException, WriteException;

    Map<String, Object> getMonthIncome(UserIncome userIncome);
}