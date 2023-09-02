package com.jade.bills.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jade.bills.entity.UserExpenses;
import com.jade.bills.mapper.UserExpensesMapper;
import com.jade.bills.service.UserExpensesService;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (UserExpenses)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 15:25:47
 */
@Service("userExpensesService")
public class UserExpensesServiceImpl implements UserExpensesService {
    @Resource
    private UserExpensesMapper userExpensesMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param expensesId 主键
     * @return 实例对象
     */
    @Override
    public UserExpenses queryById(Integer expensesId) {
        return this.userExpensesMapper.queryById(expensesId);
    }
    
     /**
     * 通过实体类查询分页数据
     *
     * @param userExpenses
     * @return 实例对象列表
     */
     @Override
    public PageInfo<UserExpenses> queryAllByEntity(UserExpenses userExpenses) {
        PageHelper.startPage(userExpenses.getOffset(),userExpenses.getLimit());
        List<UserExpenses> queryAll = userExpensesMapper.queryAll(userExpenses);
        PageInfo<UserExpenses> pageInfo = new PageInfo<>(queryAll);
        return pageInfo;
    }


    /**
     * 新增数据
     *
     * @param userExpenses 实例对象
     * @return 实例对象
     */
    @Override
    public UserExpenses insert(UserExpenses userExpenses) {
        this.userExpensesMapper.insert(userExpenses);
        return userExpenses;
    }

    /**
     * 修改数据
     *
     * @param userExpenses 实例对象
     * @return 实例对象
     */
    @Override
    public UserExpenses update(UserExpenses userExpenses) {
        this.userExpensesMapper.update(userExpenses);
        return this.queryById(userExpenses.getExpensesId());
    }

    /**
     * 通过主键删除数据
     *
     * @param expensesId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer expensesId) {
        return this.userExpensesMapper.deleteById(expensesId) > 0;
    }

    @Override
    public List<String> findType() {
        return userExpensesMapper.findType();
    }

    @Override
    public void downloadFile() throws IOException, WriteException {
        // 查询数据库中的用户支出数据
        UserExpenses userExpensesFilter = new UserExpenses(); // 创建一个空的实体作为筛选条件
        List<UserExpenses> userExpensesList = userExpensesMapper.queryAll(userExpensesFilter);

        // 创建本地文件路径
        File csvFile = new File(System.getProperty("user.dir") + File.separator + "public" + File.separator + "UserIncomeTable.csv");

        // 使用CSVWriter创建一个CSV文件
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // 写入CSV表头
            csvWriter.writeNext(new String[]{
                    "订单号", "支出时间", "支出金额（元）", "分类", "备注", "支出人id", "支出人"
            });

            // 遍历数据库中的支出数据，将每行数据写入CSV文件
            for (UserExpenses us : userExpensesList) {
                csvWriter.writeNext(new String[]{
                        us.getExpensesId().toString(),
                        us.getExpensesTime(),
                        us.getExpensesNum(),
                        us.getExpensesSort(),
                        us.getExpensesRemark(),
                        us.getExpensesUserId(),
                        us.getExpensesUser()
                });
            }
        }

        // 设置响应头，告知浏览器为下载文件
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader("content-type", "text/csv");
        response.setContentType("text/csv");
        String fileName = new String("用户支出表.csv".getBytes("utf-8"), "ISO-8859-1");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

        // 将生成的CSV文件拷贝到响应输出流，实现文件下载
        Files.copy(csvFile.toPath(), response.getOutputStream());
    }


    /**
     * @Description: 支出统计视图，根据参数不同查询本年、月、日
     * @Param:  userExpenses
     * @return:  map
     * @Author: XinWei Wu
     * @Date: 2021/3/10
     */
    @Override
    public Map<String, Object> getMonthExpenses(UserExpenses userExpenses) {
        Map<String,Object> map = new HashMap<>();
        map.put("expensesTotal",userExpensesMapper.expensesTotal(userExpenses.getDate1()));
        List<Map> timeList = userExpensesMapper.expensesList(userExpenses.getDate1());
        map.put("expensesList",timeList);
        List<Map> group = userExpensesMapper.expensesGroup(userExpenses.getDate1());
        map.put("expensesGroup" ,group);
        map.put("todayExpenses",userExpensesMapper.todayExpenses(userExpenses.getExpensesTime()));
        System.out.println(map);
        return map;
    }
}