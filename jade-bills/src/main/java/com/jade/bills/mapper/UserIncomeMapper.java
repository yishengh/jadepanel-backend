package com.jade.bills.mapper;

import com.jade.bills.entity.UserIncome;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * (UserIncome)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-23 15:10:02
 */
@Repository
public interface UserIncomeMapper {

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
    List<UserIncome> queryAll(UserIncome userIncome);

    /**
     * 新增数据
     *
     * @param userIncome 实例对象
     * @return 影响行数
     */
    int insert(UserIncome userIncome);

    /**
     * 修改数据
     *
     * @param userIncome 实例对象
     * @return 影响行数
     */
    int update(UserIncome userIncome);

    /**
     * 通过主键删除数据
     *
     * @param incomeId 主键
     * @return 影响行数
     */
    int deleteById(Integer incomeId);

    List<String> findType();


    String incomeTotal(String date1);

    List<Map> incomeIncomeList(String date1);

    List<Map> incomeGroup(String date1);

    String todayIncome(String incomeTime);
}