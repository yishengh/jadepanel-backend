package com.jade.bills.mapper;

import com.jade.bills.entity.UserExpenses;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * (UserExpenses)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-25 15:25:47
 */
@Repository
public interface UserExpensesMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param expensesId 主键
     * @return 实例对象
     */
    UserExpenses queryById(Integer expensesId);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param userExpenses 实例对象
     * @return 对象列表
     */
    List<UserExpenses> queryAll(UserExpenses userExpenses);

    /**
     * 新增数据
     *
     * @param userExpenses 实例对象
     * @return 影响行数
     */
    int insert(UserExpenses userExpenses);

    /**
     * 修改数据
     *
     * @param userExpenses 实例对象
     * @return 影响行数
     */
    int update(UserExpenses userExpenses);

    /**
     * 通过主键删除数据
     *
     * @param expensesId 主键
     * @return 影响行数
     */
    int deleteById(Integer expensesId);

    List<String> findType();

    String expensesTotal(String date1);

    List<Map> expensesList(String date1);

    List<Map> expensesGroup(String date1);

    String todayExpenses(String expensesTime);
}