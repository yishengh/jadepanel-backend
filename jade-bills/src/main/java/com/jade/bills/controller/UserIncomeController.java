package com.jade.bills.controller;

//import com.jade.bills.client.UserClient;

import com.github.pagehelper.PageInfo;
import com.jade.bills.entity.UserIncome;
import com.jade.bills.service.UserIncomeService;
import com.jade.common.utils.TimeUtils;
import jxl.write.WriteException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (UserIncome)表控制层
 *
 * @author makejava
 * @since 2021-02-23 15:10:05
 */
@RestController
@RequestMapping("userIncome")
public class UserIncomeController {
    /**
     * 服务对象
     */
    @Resource
    private UserIncomeService userIncomeService;

//    @Autowired
//    private UserClient userClient;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserIncome selectOne(Integer id) {
        return this.userIncomeService.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @GetMapping("selectAll")
    public ResponseEntity<Map<String, Object>> selectAll(
            @RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            Integer limit, UserIncome userIncome) {
        System.out.println("UserIncome userIncome: " + userIncome);
        Map<String, Object> map = new HashMap<>();
        if (offset == null || offset == 0) {
            userIncome.setOffset(1);
        } else {
            userIncome.setOffset(offset);
        }
        if (StringUtils.isNotBlank(userIncome.getIncomeTime()) && !",".equals(userIncome.getIncomeTime())) {
            String[] split = StringUtils.split(userIncome.getIncomeTime(), ',');
            userIncome.setDate1(split[0].replaceAll("-", ""));
            userIncome.setDate2(split[1].replaceAll("-", ""));
        } else {
            userIncome.setIncomeTime(null);
        }
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            userIncome.setDate1(startTime.replaceAll("-", ""));
            userIncome.setDate2(endTime.replaceAll("-", ""));
            userIncome.setIncomeTime(startTime.replaceAll("-", "") + ',' + endTime.replaceAll("-", ""));
        }
        if (limit == null) {
            userIncome.setLimit(1000);
        } else {
            userIncome.setLimit(limit);
        }
        if (userIncome.getIncomeSort() != null && userIncome.getIncomeSort().equals("all")) {
            userIncome.setIncomeSort(null);
        }
        PageInfo<UserIncome> allData = userIncomeService.queryAllByEntity(userIncome);
        map.put("count", allData.getTotal());
        map.put("data", allData.getList());
        return ResponseEntity.ok(map);
    }


    @GetMapping("findType")
    public ResponseEntity<List<String>> findType() {
        List<String> list = userIncomeService.findType();
        return ResponseEntity.ok(list);
    }


    /**
     * 新增数据
     *
     * @param userIncome 实例对象
     * @return Void
     */
    @GetMapping("insertIncome")
    public ResponseEntity<Void> insertIncome(UserIncome userIncome) {
        userIncome.setIncomeTime(TimeUtils.getCurrentDateString("YYYYMMdd"));
//        Integer userId = userClient.findId(userIncome.getIncomeUser()).getUserId();
        userIncome.setIncomeUserId(1);
        userIncomeService.insert(userIncome);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 通过主键删除数据
     *
     * @param incomeId 主键
     * @return 是否成功
     */
    @GetMapping("deleteIncome")
    public ResponseEntity<Boolean> deleteIncome(Integer incomeId) {
        boolean b = userIncomeService.deleteById(incomeId);
        return ResponseEntity.ok(b);
    }


    /**
     * 修改数据
     *
     * @param userIncome 实例对象
     * @return Void
     */
    @PostMapping("updateIncome")
    public ResponseEntity<Void> updateIncome(@RequestBody UserIncome userIncome) {
        if (userIncome.getIncomeId() == null || userIncome.getIncomeId() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userIncomeService.update(userIncome);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 下载数据
     *
     * @param
     * @return Void
     */
    @GetMapping("downloadIncome")
    public ResponseEntity<Void> downloadIncome(UserIncome userIncome) throws IOException, WriteException {
//        //todo
//        if (StringUtils.isNotBlank(userIncome.getIncomeTime()) && !",".equals(userIncome.getIncomeTime()) ){
//            String[] split = StringUtils.split(userIncome.getIncomeTime(), ',');
//            userIncome.setDate1(split[0].replaceAll("-",""));
//            userIncome.setDate2(split[1].replaceAll("-",""));
//        }else {
//            userIncome.setIncomeTime(null);
//        }
        userIncomeService.downloadFile();
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * @Description: 本月收入集合
     * @Param: flag 年、月区分
     * @return: Map<String, Object>
     * incomeTime设置为当前日期，date1设置为本月、年，根据传入参数不同进行区分
     */
    @GetMapping("getIncomeCollection")
    public ResponseEntity<Map<String, Object>> getIncomeCollection(String date) {
        if (StringUtils.isNotBlank(date)) {
            UserIncome userIncome = new UserIncome();
            userIncome.setDate1(TimeUtils.getCurrentDateString(date));
            userIncome.setIncomeTime(TimeUtils.getCurrentDateString("YYYYMMdd"));
            Map<String, Object> income = userIncomeService.getMonthIncome(userIncome);
            return ResponseEntity.ok(income);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}