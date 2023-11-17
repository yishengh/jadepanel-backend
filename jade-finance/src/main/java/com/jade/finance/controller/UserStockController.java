package com.jade.finance.controller;

import com.jade.finance.entity.UserStock;
import com.jade.finance.service.UserStockService;
import com.github.pagehelper.PageInfo;
import jxl.write.WriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * (UserStock)表控制层
 *
 * @author makejava
 * @since 2021-02-25 16:34:00
 */
@RestController
@RequestMapping("userStock")
public class UserStockController {
    /**
     * 服务对象
     */
    @Resource
    private UserStockService userStockService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserStock selectOne(Integer id) {
        return this.userStockService.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @param  userStock
     * @return 对象列表
     */
    @GetMapping("selectAll")
    public ResponseEntity<Map<String,Object>> selectAll(Integer offset ,Integer limit , UserStock userStock){
        UserStock userStockFillter = new UserStock();
        if (userStock != null){
            userStockFillter = userStock;
        }
        Map<String,Object> map = new HashMap<>();
        if (offset == null || offset == 0){
            userStockFillter.setOffset(1);
        }else{
            userStockFillter.setOffset(offset);
        }
        if (limit == null || limit ==0){
            userStockFillter.setLimit(1000);
        }
        PageInfo<UserStock> allData = userStockService.queryAllByEntity(userStockFillter);
        map.put("count",allData.getTotal());
        map.put("data",allData.getList());
        return ResponseEntity.ok(map);
    }



    /**
     * 新增数据
     *
     * @param  userStock
     * @return Void
     */
    @GetMapping("insert")
    public ResponseEntity<Void> insert(UserStock userStock){
        userStockService.insert(userStock);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 通过主键删除数据
     *
     * @param stockId 主键
     * @return 是否成功
     */
    @GetMapping("delete")
    public ResponseEntity<Boolean> delete(Integer stockId){
        boolean b = userStockService.deleteById(stockId);
        return ResponseEntity.ok(b);
    }


    /**
     * 修改数据
     *
     * @param userStock
     * @return Void
     */
    @PostMapping("update")
    public ResponseEntity<Void> update(@RequestBody UserStock userStock){
        userStockService.update(userStock);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    /**
     * 下载数据
     *
     * @param
     * @return Void
     */
    @GetMapping("downloadStock")
    public ResponseEntity<Void> downloadStock(UserStock userStock) throws IOException, WriteException {
//        //todo
//        if (StringUtils.isNotBlank(userIncome.getIncomeTime()) && !",".equals(userIncome.getIncomeTime()) ){
//            String[] split = StringUtils.split(userIncome.getIncomeTime(), ',');
//            userIncome.setDate1(split[0].replaceAll("-",""));
//            userIncome.setDate2(split[1].replaceAll("-",""));
//        }else {
//            userIncome.setIncomeTime(null);
//        }
        userStockService.downloadFile();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}