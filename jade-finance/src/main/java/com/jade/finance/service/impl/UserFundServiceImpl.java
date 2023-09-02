package com.jade.finance.service.impl;

import com.jade.finance.entity.UserFund;
import com.jade.finance.mapper.UserFundMapper;
import com.jade.finance.service.UserFundService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opencsv.CSVWriter;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * (UserFund)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 16:32:31
 */
@Service("userFundService")
public class UserFundServiceImpl implements UserFundService {
    @Resource
    private UserFundMapper userFundMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param fundId 主键
     * @return 实例对象
     */
    @Override
    public UserFund queryById(Integer fundId) {
        return this.userFundMapper.queryById(fundId);
    }
    
     /**
     * 通过实体类查询分页数据
     *
     * @param userFund
     * @return 实例对象列表
     */
     @Override
    public PageInfo<UserFund> queryAllByEntity(UserFund userFund) {
        PageHelper.startPage(userFund.getOffset(),userFund.getLimit());
        List<UserFund> queryAll = userFundMapper.queryAll(userFund);
        PageInfo<UserFund> pageInfo = new PageInfo<>(queryAll);
        return pageInfo;
    }


    /**
     * 新增数据
     *
     * @param userFund 实例对象
     * @return 实例对象
     */
    @Override
    public UserFund insert(UserFund userFund) {
        this.userFundMapper.insert(userFund);
        return userFund;
    }

    /**
     * 修改数据
     *
     * @param userFund 实例对象
     * @return 实例对象
     */
    @Override
    public UserFund update(UserFund userFund) {
        this.userFundMapper.update(userFund);
        return this.queryById(userFund.getFundId());
    }

    /**
     * 通过主键删除数据
     *
     * @param fundId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer fundId) {
        return this.userFundMapper.deleteById(fundId) > 0;
    }

    @Override
    public List<String> findType() {
        return userFundMapper.findType();
    }


    @Override
    public void downloadFile() throws IOException, WriteException {
        UserFund userFundFilter = new UserFund(); // 创建一个空的实体作为筛选条件
        List<UserFund> userFundList = userFundMapper.queryAll(userFundFilter);
        // 创建本地文件路径
        File csvFile = new File(System.getProperty("user.dir") + File.separator + "public" + File.separator + "UserIncomeTable.csv");

        // 使用CSVWriter创建一个CSV文件
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // 写入CSV表头
            csvWriter.writeNext(new String[]{
                    "基金ID", "基金代码", "基金名", "基金类型", "当前基金单位净值", "原始买入费率(百分比)",
                    "当前买入费率(百分比)", "基金经理", "每万份收益(货币基金)"
            });

            // 遍历数据库中的记录，将每行数据写入CSV文件
            for (UserFund fund : userFundList) {
                csvWriter.writeNext(new String[]{
                        fund.getFundId().toString(),
                        fund.getFundCode(),
                        fund.getFundName(),
                        fund.getFundType(),
                        fund.getNetWorth(),
                        fund.getBuySourceRate(),
                        fund.getBuyRate(),
                        fund.getManager(),
                        fund.getMillionCopiesIncome()
                });
            }
        }

        // 设置响应头，告知浏览器为下载文件
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader("content-type", "text/csv");
        response.setContentType("text/csv");
        String fileName = new String("用户基金表.csv".getBytes("utf-8"), "ISO-8859-1");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

        // 将生成的CSV文件拷贝到响应输出流，实现文件下载
        Files.copy(csvFile.toPath(), response.getOutputStream());
    }
}