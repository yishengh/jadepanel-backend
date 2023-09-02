package com.jade.equity.service.impl;

import com.jade.equity.entity.UserAssets;
import com.jade.equity.mapper.UserAssetsMapper;
import com.jade.equity.service.UserAssetsService;
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
 * (UserAssets)表服务实现类
 *
 * @author makejava
 * @since 2021-03-30 14:33:53
 */
@Service("userAssetsService")
public class UserAssetsServiceImpl implements UserAssetsService {
    @Resource
    private UserAssetsMapper userAssetsMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param assetsId 主键
     * @return 实例对象
     */
    @Override
    public UserAssets queryById(Integer assetsId) {
        return this.userAssetsMapper.queryById(assetsId);
    }
    
     /**
     * 通过实体类查询分页数据
     *
     * @param userAssets
     * @return 实例对象列表
     */
     @Override
    public PageInfo<UserAssets> queryAllByEntity(UserAssets userAssets) {
        PageHelper.startPage(userAssets.getOffset(),userAssets.getLimit());
        List<UserAssets> queryAll = userAssetsMapper.queryAll(userAssets);
        PageInfo<UserAssets> pageInfo = new PageInfo<>(queryAll);
        return pageInfo;
    }


    /**
     * 新增数据
     *
     * @param userAssets 实例对象
     * @return 实例对象
     */
    @Override
    public UserAssets insert(UserAssets userAssets) {
        this.userAssetsMapper.insert(userAssets);
        return userAssets;
    }

    /**
     * 修改数据
     *
     * @param userAssets 实例对象
     * @return 实例对象
     */
    @Override
    public UserAssets update(UserAssets userAssets) {
        this.userAssetsMapper.update(userAssets);
        return this.queryById(userAssets.getAssetsId());
    }

    /**
     * 通过主键删除数据
     *
     * @param assetsId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer assetsId) {
        return this.userAssetsMapper.deleteById(assetsId) > 0;
    }

    @Override
    public Map<String, Object> getAssetsCollection(UserAssets userAssets) {
        Map<String,Object> map = new HashMap<>();
//        资产占比情况
        List<Map> group = userAssetsMapper.assetRatio();
        map.put("assetRatio",group);

//        资产贷款情况
        List<Map> loanSituation = userAssetsMapper.loanSituation();
        map.put("loanSituation",loanSituation);

//        资产总成本
        map.put("totalCost",userAssetsMapper.totalCost());

//        资产变现总值
        map.put("totalRealizableValue",userAssetsMapper.totalRealizableValue());

//        剩余按揭总额
        map.put("totalMortgage",userAssetsMapper.totalMortgage());

//        每月应付按揭费用
        map.put("monthCost",userAssetsMapper.monthCost());
        return map;
    }



    @Override
    public void downloadFile() throws IOException, WriteException {
        UserAssets userAssetsFilter = new UserAssets(); // 创建一个空的实体作为筛选条件
        List<UserAssets> userAssetsList = userAssetsMapper.queryAll(userAssetsFilter);
        // 创建本地文件路径
        File csvFile = new File(System.getProperty("user.dir") + File.separator + "public" + File.separator + "UserIncomeTable.csv");

        // 使用CSVWriter创建一个CSV文件
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // 写入CSV表头
            csvWriter.writeNext(new String[]{
                    "资产ID", "资产名", "资产所在地", "资产获得时间", "全款或首付花费", "历史价值",
                    "资产所有者", "资产分期", "每期价格", "剩余期限", "变现价值", "备注"
            });

            // 遍历数据库中的记录，将每行数据写入CSV文件
            for (UserAssets assets : userAssetsList) {
                csvWriter.writeNext(new String[]{
                        assets.getAssetsId().toString(),
                        assets.getAssetsName(),
                        assets.getAssetsLocation(),
                        assets.getAssetsCreateTime(),
                        assets.getTotalPrice(),
                        assets.getHistoricalValue(),
                        assets.getAssetsOwner(),
                        assets.getAssetsInstalment(),
                        assets.getInstalmentPrice(),
                        assets.getInstalmentSurplus(),
                        assets.getRealizationValue(),
                        assets.getAssetsRemark()
                });
            }
        }

        // 设置响应头，告知浏览器为下载文件
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader("content-type", "text/csv");
        response.setContentType("text/csv");
        String fileName = new String("用户资产表.csv".getBytes("utf-8"), "ISO-8859-1");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

        // 将生成的CSV文件拷贝到响应输出流，实现文件下载
        Files.copy(csvFile.toPath(), response.getOutputStream());
    }


}