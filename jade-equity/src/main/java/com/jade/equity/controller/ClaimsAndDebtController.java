package com.jade.equity.controller;

import com.jade.equity.entity.ClaimsAndDebt;
import com.jade.equity.service.ClaimsAndDebtService;
import com.github.pagehelper.PageInfo;
import jxl.write.WriteException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * (ClaimsAndDebt)表控制层
 *
 * @author makejava
 * @since 2021-03-30 14:35:58
 */
@RestController
@RequestMapping("claimsAndDebt")
public class ClaimsAndDebtController {
    /**
     * 服务对象
     */
    @Resource
    private ClaimsAndDebtService claimsAndDebtService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ClaimsAndDebt selectOne(Integer id) {
        return this.claimsAndDebtService.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @param  claimsAndDebt
     * @return 对象列表
     */
    @GetMapping("selectAll")
    public ResponseEntity<Map<String,Object>> selectAll(Integer offset ,Integer limit , ClaimsAndDebt claimsAndDebt){
        Map<String,Object> map = new HashMap<>();
        if (offset == null || offset == 0){
            claimsAndDebt.setOffset(1);
        }else{
            claimsAndDebt.setOffset(offset);
        }

        if (StringUtils.isNotBlank(claimsAndDebt.getCadTime()) && !",".equals(claimsAndDebt.getCadTime())){
            String[] split = StringUtils.split(claimsAndDebt.getCadTime(), ',');
            claimsAndDebt.setDate1(split[0].replaceAll("-",""));
            claimsAndDebt.setDate2(split[1].replaceAll("-",""));
        }else {
            claimsAndDebt.setCadTime(null);
        }
        claimsAndDebt.setLimit(limit);
        PageInfo<ClaimsAndDebt> allData = claimsAndDebtService.queryAllByEntity(claimsAndDebt);
        map.put("count",allData.getTotal());
        map.put("data",allData.getList());
        return ResponseEntity.ok(map);
    }



    /**
     * 新增数据
     *
     * @param  claimsAndDebt
     * @return Void
     */
    @PostMapping("insert")
    public ResponseEntity<Void> insert(@RequestBody ClaimsAndDebt claimsAndDebt){
        claimsAndDebtService.insert(claimsAndDebt);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @GetMapping("delete")
    public ResponseEntity<Boolean> delete(Integer id){
        boolean b = claimsAndDebtService.deleteById(id);
        return ResponseEntity.ok(b);
    }


    /**
     * 修改数据
     *
     * @param claimsAndDebt
     * @return Void
     */
    @PutMapping("update")
    public ResponseEntity<Void> update(@RequestBody ClaimsAndDebt claimsAndDebt){
        claimsAndDebtService.update(claimsAndDebt);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("getClaimsAndDebtsCollection")
    public ResponseEntity<Map<String,Object>> getClaimsAndDebtsCollection() {
        ClaimsAndDebt claimsAndDebt = new ClaimsAndDebt();
        Map<String, Object> assets = this.claimsAndDebtService.getCADCollection(claimsAndDebt);
        return ResponseEntity.ok(assets);
    }


    /**
     * 下载数据
     *
     * @param
     * @return Void
     */
    @GetMapping("downloadClaimsAndDebts")
    public ResponseEntity<Void> downloadIncome(ClaimsAndDebt claimsAndDebts) throws IOException, WriteException {
//        //todo
//        if (StringUtils.isNotBlank(userIncome.getIncomeTime()) && !",".equals(userIncome.getIncomeTime()) ){
//            String[] split = StringUtils.split(userIncome.getIncomeTime(), ',');
//            userIncome.setDate1(split[0].replaceAll("-",""));
//            userIncome.setDate2(split[1].replaceAll("-",""));
//        }else {
//            userIncome.setIncomeTime(null);
//        }
        claimsAndDebtService.downloadFile();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}