package com.jade.finance.service.impl;

import com.jade.finance.entity.UserStock;
import com.jade.finance.mapper.UserStockMapper;
import com.jade.finance.service.UserStockService;
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
 * (UserStock)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 16:34:00
 */
@Service("userStockService")
public class UserStockServiceImpl implements UserStockService {
    @Resource
    private UserStockMapper userStockMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param stockId 主键
     * @return 实例对象
     */
    @Override
    public UserStock queryById(Integer stockId) {
        return this.userStockMapper.queryById(stockId);
    }
    
     /**
     * 通过实体类查询分页数据
     *
     * @param userStock
     * @return 实例对象列表
     */
     @Override
    public PageInfo<UserStock> queryAllByEntity(UserStock userStock) {
        PageHelper.startPage(userStock.getOffset(),userStock.getLimit());
        List<UserStock> queryAll = userStockMapper.queryAll(userStock);
        PageInfo<UserStock> pageInfo = new PageInfo<>(queryAll);
        return pageInfo;
    }


    /**
     * 新增数据
     *
     * @param userStock 实例对象
     * @return 实例对象
     */
    @Override
    public UserStock insert(UserStock userStock) {
        this.userStockMapper.insert(userStock);
        return userStock;
    }

    /**
     * 修改数据
     *
     * @param userStock 实例对象
     * @return 实例对象
     */
    @Override
    public UserStock update(UserStock userStock) {
        this.userStockMapper.update(userStock);
        return this.queryById(userStock.getStockId());
    }

    /**
     * 通过主键删除数据
     *
     * @param stockId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer stockId) {
        return this.userStockMapper.deleteById(stockId) > 0;
    }

    @Override
    public void downloadFile() throws IOException, WriteException {
        UserStock userStockFilter = new UserStock(); // 创建一个空的实体作为筛选条件
        List<UserStock> userStockList = userStockMapper.queryAll(userStockFilter);
        // 创建本地文件路径
        File csvFile = new File(System.getProperty("user.dir") + File.separator + "public" + File.separator + "UserIncomeTable.csv");

        // 使用CSVWriter创建一个CSV文件
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // 写入CSV表头
            csvWriter.writeNext(new String[]{
                    "股票ID", "股市代码", "股票名称", "股票类型", "每股价格", "购买数量",
                    "购买时间", "购买人", "购买人ID"
            });

            // 遍历数据库中的记录，将每行数据写入CSV文件
            for (UserStock stock : userStockList) {
                csvWriter.writeNext(new String[]{
                        stock.getStockId().toString(),
                        stock.getStockCode(),
                        stock.getStockName(),
                        stock.getStockType(),
                        stock.getStockPrice(),
                        stock.getStockNum(),
                        stock.getStockTime(),
                        stock.getStockUser(),
                        stock.getStockUserId().toString()
                });
            }
        }

        // 设置响应头，告知浏览器为下载文件
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader("content-type", "text/csv");
        response.setContentType("text/csv");
        String fileName = new String("用户股票表.csv".getBytes("utf-8"), "ISO-8859-1");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

        // 将生成的CSV文件拷贝到响应输出流，实现文件下载
        Files.copy(csvFile.toPath(), response.getOutputStream());
    }


}