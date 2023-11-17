package com.jade.equity.controller;

import com.jade.common.utils.TimeUtils;
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
    public ResponseEntity<Map<String,Object>> selectAll(
            Integer offset ,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            Integer limit , ClaimsAndDebt claimsAndDebt){
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
        if (org.apache.commons.lang3.StringUtils.isNotBlank(startTime) && org.apache.commons.lang3.StringUtils.isNotBlank(endTime)) {
            claimsAndDebt.setDate1(startTime.replaceAll("-", ""));
            claimsAndDebt.setDate2(endTime.replaceAll("-", ""));
            claimsAndDebt.setCadTime(startTime.replaceAll("-", "") + ',' + endTime.replaceAll("-", ""));
        }



        if (limit == null) {
            claimsAndDebt.setLimit(1000);
        } else {
            claimsAndDebt.setLimit(limit);
        }
        if (claimsAndDebt.getCadType() != null && claimsAndDebt.getCadType().equals("all")) {
            claimsAndDebt.setCadType(null);
        }
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
    @GetMapping("insertClaimsAndDebt")
    public ResponseEntity<Void> insert(ClaimsAndDebt claimsAndDebt){
        claimsAndDebt.setCadTime(TimeUtils.getCurrentDateString("YYYYMMdd"));
        // setID
        claimsAndDebtService.insert(claimsAndDebt);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 通过主键删除数据
     *
     * @param cadId 主键
     * @return 是否成功
     */
    @GetMapping("deleteClaimsAndDebt")
    public ResponseEntity<Boolean> delete(Integer cadId){
        boolean b = claimsAndDebtService.deleteById(cadId);
        return ResponseEntity.ok(b);
    }


    /**
     * 修改数据
     *
     * @param claimsAndDebt
     * @return Void
     */
    @PostMapping("updateClaimsAndDebt")
    public ResponseEntity<Void> update(@RequestBody ClaimsAndDebt claimsAndDebt){
        if (claimsAndDebt.getCadId() == null || claimsAndDebt.getCadId() == 0){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        claimsAndDebtService.update(claimsAndDebt);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 下载数据
     *
     * @param
     * @return Void
     */
    @GetMapping("downloadClaimsAndDebts")
    public ResponseEntity<Void> downloadClaimsAndDebts(ClaimsAndDebt claimsAndDebts) throws IOException, WriteException {
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


    /**
     * @Description: 本月收入集合
     * @Param: flag 年、月区分
     * @return: Map<String, Object>
     * incomeTime设置为当前日期，date1设置为本月、年，根据传入参数不同进行区分
     */
    @GetMapping("getClaimsAndDebtsCollection")
    public ResponseEntity<Map<String, Object>> getClaimsAndDebtsCollection(String date) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(date)) {
            ClaimsAndDebt userClaimsAndDebt = new ClaimsAndDebt();
            userClaimsAndDebt.setDate1(TimeUtils.getCurrentDateString(date));
            userClaimsAndDebt.setCadTime(TimeUtils.getCurrentDateString("YYYYMMdd"));
            Map<String, Object> cad = claimsAndDebtService.getCADCollection(userClaimsAndDebt);
            return ResponseEntity.ok(cad);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}