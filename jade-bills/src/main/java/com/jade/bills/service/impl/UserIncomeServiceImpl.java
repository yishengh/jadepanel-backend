package com.jade.bills.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jade.bills.entity.UserIncome;
import com.jade.bills.mapper.UserIncomeMapper;
import com.jade.bills.service.UserIncomeService;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import jxl.write.WriteException;
import lombok.extern.log4j.Log4j;
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
 * (UserIncome)表服务实现类
 *
 * @author makejava
 * @since 2021-02-23 15:10:05
 */
@Service("userIncomeService")
@Log4j
public class UserIncomeServiceImpl implements UserIncomeService {
    @Resource
    private UserIncomeMapper userIncomeMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param incomeId 主键
     * @return 实例对象
     */
    @Override
    public UserIncome queryById(Integer incomeId) {
        return this.userIncomeMapper.queryById(incomeId);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param userIncome 实例对象
     * @return 对象列表
     */
    @Override
    public PageInfo<UserIncome> queryAllByEntity(UserIncome userIncome) {
        PageHelper.startPage(userIncome.getOffset(),userIncome.getLimit());
        List<UserIncome> queryAll = userIncomeMapper.queryAll(userIncome);
        PageInfo<UserIncome> pageInfo = new PageInfo<>(queryAll);
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param userIncome 实例对象
     * @return 实例对象
     */
    @Override
    public UserIncome insert(UserIncome userIncome) {
        this.userIncomeMapper.insert(userIncome);
        return userIncome;
    }

    /**
     * 修改数据
     *
     * @param userIncome 实例对象
     * @return 实例对象
     */
    @Override
    public UserIncome update(UserIncome userIncome) {
        this.userIncomeMapper.update(userIncome);
        return this.queryById(userIncome.getIncomeId());
    }

    /**
     * 通过主键删除数据
     *
     * @param incomeId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer incomeId) {
        return this.userIncomeMapper.deleteById(incomeId) > 0;
    }

    @Override
    public List<String> findType() {
        return userIncomeMapper.findType();
    }

    @Override
    public void downloadFile() throws IOException, WriteException {
        UserIncome userIncomeFilter = new UserIncome(); // 创建一个空的实体作为筛选条件
        List<UserIncome> userIncomeList = userIncomeMapper.queryAll(userIncomeFilter);

        // 创建本地文件路径
        File csvFile = new File(System.getProperty("user.dir") + File.separator + "public" + File.separator + "UserIncomeTable.csv");

        // 使用CSVWriter创建一个CSV文件
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // 写入CSV表头
            csvWriter.writeNext(new String[]{
                    "收入ID", "收入时间", "收入金额（元）", "收入类型", "收入备注", "收入者ID", "收入者名字"
            });

            // 遍历数据库中的收入数据，将每行数据写入CSV文件
            for (UserIncome income : userIncomeList) {
                csvWriter.writeNext(new String[]{
                        income.getIncomeId().toString(),
                        income.getIncomeTime(),
                        income.getIncomeNum(),
                        income.getIncomeSort(),
                        income.getIncomeRemark(),
                        income.getIncomeUserId().toString(),
                        income.getIncomeUser()
                });
            }
        }

        // 设置响应头，告知浏览器为下载文件
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader("content-type", "text/csv");
        response.setContentType("text/csv");
        String fileName = new String("用户收入表.csv".getBytes("utf-8"), "ISO-8859-1");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

        // 将生成的CSV文件拷贝到响应输出流，实现文件下载
        Files.copy(csvFile.toPath(), response.getOutputStream());
    }

    /**
    * @Description: 支出统计视图，根据参数不同查询本年、月、日
    * @Param:  userIncome
    * @return:  map
    * @Author: XinWei Wu
    * @Date: 2021/3/10
    */
    @Override
    public Map<String, Object> getMonthIncome(UserIncome userIncome) {
        Map<String,Object> map = new HashMap<>();
//        支出总额
        map.put("incomeTotal",userIncomeMapper.incomeTotal(userIncome.getDate1()));

//        支出曲线图
        List<Map> timeList = userIncomeMapper.incomeIncomeList(userIncome.getDate1());
        map.put("incomeIncomeList",timeList);

//        支出分类
        List<Map> group = userIncomeMapper.incomeGroup(userIncome.getDate1());
        map.put("incomeGroup",group);

//        今日支出
        map.put("todayIncome",userIncomeMapper.todayIncome(userIncome.getIncomeTime()));
        System.out.println(map);
        return map;
    }
}