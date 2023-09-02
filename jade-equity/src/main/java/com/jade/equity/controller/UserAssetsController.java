package com.jade.equity.controller;

import com.jade.equity.entity.UserAssets;
import com.jade.equity.service.UserAssetsService;
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
 * (UserAssets)表控制层
 *
 * @author makejava
 * @since 2021-03-30 14:33:54
 */
@RestController
@RequestMapping("userAssets")
public class UserAssetsController {
    /**
     * 服务对象
     */
    @Resource
    private UserAssetsService userAssetsService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserAssets selectOne(Integer id) {
        return this.userAssetsService.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @param  userAssets
     * @return 对象列表
     */
    @GetMapping("selectAll")
    public ResponseEntity<Map<String,Object>> selectAll(
            @RequestParam(value = "offset" ,required = false)
                    Integer offset,Integer limit , UserAssets userAssets){
        Map<String,Object> map = new HashMap<>();
        if (offset == null || offset == 0){
            userAssets.setOffset(1);
        }else{
            userAssets.setOffset(offset);
        }
        userAssets.setLimit(limit);
        PageInfo<UserAssets> allData = userAssetsService.queryAllByEntity(userAssets);
        map.put("count",allData.getTotal());
        map.put("data",allData.getList());
        return ResponseEntity.ok(map);

    }



    /**
     * 新增数据
     *
     * @param  userAssets
     * @return Void
     */
    @PostMapping("insert")
    public ResponseEntity<Void> insert(@RequestBody UserAssets userAssets){
        userAssetsService.insert(userAssets);
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
        boolean b = userAssetsService.deleteById(id);
        return ResponseEntity.ok(b);
    }


    /**
     * 修改数据
     *
     * @param userAssets
     * @return Void
     */
    @PutMapping("update")
    public ResponseEntity<Void> update(@RequestBody UserAssets userAssets){
        userAssetsService.update(userAssets);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("getAssetsCollection")
    public ResponseEntity<Map<String,Object>> getAssetsCollection() {
        UserAssets userAssets = new UserAssets();
        Map<String, Object> assets = this.userAssetsService.getAssetsCollection(userAssets);
        return ResponseEntity.ok(assets);
    }

    /**
     * 下载数据
     *
     * @param
     * @return Void
     */
    @GetMapping("downloadAssets")
    public ResponseEntity<Void> downloadAssets(UserAssets userAssets) throws IOException, WriteException {
//        //todo
//        if (StringUtils.isNotBlank(userIncome.getIncomeTime()) && !",".equals(userIncome.getIncomeTime()) ){
//            String[] split = StringUtils.split(userIncome.getIncomeTime(), ',');
//            userIncome.setDate1(split[0].replaceAll("-",""));
//            userIncome.setDate2(split[1].replaceAll("-",""));
//        }else {
//            userIncome.setIncomeTime(null);
//        }
        userAssetsService.downloadFile();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}