package com.jade.equity.service.impl;

import com.jade.equity.entity.ClaimsAndDebt;
import com.jade.equity.mapper.ClaimsAndDebtMapper;
import com.jade.equity.service.ClaimsAndDebtService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (ClaimsAndDebt)表服务实现类
 *
 * @author makejava
 * @since 2021-03-30 14:35:58
 */
@Service("claimsAndDebtService")
public class ClaimsAndDebtServiceImpl implements ClaimsAndDebtService {
    @Resource
    private ClaimsAndDebtMapper claimsAndDebtMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param cadId 主键
     * @return 实例对象
     */
    @Override
    public ClaimsAndDebt queryById(Integer cadId) {
        return this.claimsAndDebtMapper.queryById(cadId);
    }
    
     /**
     * 通过实体类查询分页数据
     *
     * @param claimsAndDebt
     * @return 实例对象列表
     */
     @Override
    public PageInfo<ClaimsAndDebt> queryAllByEntity(ClaimsAndDebt claimsAndDebt) {
        PageHelper.startPage(claimsAndDebt.getOffset(),claimsAndDebt.getLimit());
        List<ClaimsAndDebt> queryAll = claimsAndDebtMapper.queryAll(claimsAndDebt);
        PageInfo<ClaimsAndDebt> pageInfo = new PageInfo<>(queryAll);
        return pageInfo;
    }


    /**
     * 新增数据
     *
     * @param claimsAndDebt 实例对象
     * @return 实例对象
     */
    @Override
    public ClaimsAndDebt insert(ClaimsAndDebt claimsAndDebt) {
        this.claimsAndDebtMapper.insert(claimsAndDebt);
        return claimsAndDebt;
    }

    /**
     * 修改数据
     *
     * @param claimsAndDebt 实例对象
     * @return 实例对象
     */
    @Override
    public ClaimsAndDebt update(ClaimsAndDebt claimsAndDebt) {
        this.claimsAndDebtMapper.update(claimsAndDebt);
        return this.queryById(claimsAndDebt.getCadId());
    }

    /**
     * 通过主键删除数据
     *
     * @param cadId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer cadId) {
        return this.claimsAndDebtMapper.deleteById(cadId) > 0;
    }

    @Override
    public Map<String, Object> getCADCollection(ClaimsAndDebt claimsAndDebt) {
        Map<String,Object> map = new HashMap<>();
//        债务占比图
        List<Map> claimsGroup = claimsAndDebtMapper.claimsGroup();
        map.put("claimsGroup",claimsGroup);

//        债权占比图
        List<Map> debtGroup = claimsAndDebtMapper.debtGroup();
        map.put("debtGroup",debtGroup);
//        债务总额
        map.put("sumClaim",claimsAndDebtMapper.sumClaim());

//        债权总额
        map.put("sumDebt",claimsAndDebtMapper.sumDebt());

//        最高债务额
        map.put("maxClaim",claimsAndDebtMapper.maxClaim());

//        最高债权额
        map.put("maxDebt",claimsAndDebtMapper.maxDebt());

        return map;
    }

    @Override
    public void downloadFile() throws IOException, WriteException {
        // 查询数据库中的用户支出数据
        ClaimsAndDebt claimsAndDebtFilter = new ClaimsAndDebt(); // 创建一个空的实体作为筛选条件
        List<ClaimsAndDebt> claimsAndDebtList = claimsAndDebtMapper.queryAll(claimsAndDebtFilter);
        // 创建本地文件路径
        File csvFile = new File(System.getProperty("user.dir") + File.separator + "public" + File.separator + "UserIncomeTable.csv");

        // 使用CSVWriter创建一个CSV文件
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // 写入CSV表头
            csvWriter.writeNext(new String[]{
                    "资产负债ID", "交易类型", "债权人", "债务人", "交易数量", "交易时间", "已偿还金额", "预计偿还时间", "备注", "状态"
            });

            // 遍历数据库中的记录，将每行数据写入CSV文件
            for (ClaimsAndDebt cad : claimsAndDebtList) {
                csvWriter.writeNext(new String[]{
                        cad.getCadId().toString(),
                        cad.getCadType(),
                        cad.getCreditor(),
                        cad.getObligor(),
                        cad.getCadNum(),
                        cad.getCadTime(),
                        cad.getCadRepay(),
                        cad.getCadPlan(),
                        cad.getCadRemark(),
                        cad.getCadStatus()
                });
            }
        }

        // 设置响应头，告知浏览器为下载文件
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader("content-type", "text/csv");
        response.setContentType("text/csv");
        String fileName = new String("资产负债表.csv".getBytes("utf-8"), "ISO-8859-1");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

        // 将生成的CSV文件拷贝到响应输出流，实现文件下载
        Files.copy(csvFile.toPath(), response.getOutputStream());
    }


}