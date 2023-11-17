package com.jade.bills.controller;

import com.github.pagehelper.PageInfo;
import com.jade.bills.entity.UserExpenses;
import com.jade.bills.service.UserExpensesService;
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
 * (UserExpenses)表控制层
 *
 * @author makejava
 * @since 2021-02-25 15:25:47
 */
@RestController
@RequestMapping("userExpenses")

public class UserExpensesController {
    /**
     * 服务对象
     */
    @Resource
    private UserExpensesService userExpensesService;

//    @Autowired
//    private UserClient userClient;

//    @Autowired
//    private RestTemplate rest;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserExpenses selectOne(int id) {
        return this.userExpensesService.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @GetMapping("selectAll")
    public ResponseEntity<Map<String,Object>> selectAll(
            @RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            Integer limit, UserExpenses userExpenses) {
        System.out.println("UserExpenses userExpenses: " + userExpenses);
        Map<String, Object> map = new HashMap<>();
        if (offset == null || offset == 0) {
            userExpenses.setOffset(1);
        } else {
            userExpenses.setOffset(offset);
        }
        if (StringUtils.isNotBlank(userExpenses.getExpensesTime()) && !",".equals(userExpenses.getExpensesTime())) {
            String[] split = StringUtils.split(userExpenses.getExpensesTime(), ',');
            userExpenses.setDate1(split[0].replaceAll("-", ""));
            System.out.println(split[0].replaceAll("-", ""));
            userExpenses.setDate2(split[1].replaceAll("-", ""));
            System.out.println(split[1].replaceAll("-", ""));
        } else {
            userExpenses.setExpensesTime(null);
        }
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            userExpenses.setDate1(startTime.replaceAll("-", ""));
            System.out.println(startTime.replaceAll("-", ""));
            userExpenses.setDate2(endTime.replaceAll("-", ""));
            System.out.println(endTime.replaceAll("-", ""));
            userExpenses.setExpensesTime(startTime.replaceAll("-", "") + ',' + endTime.replaceAll("-", ""));
        }
        if (limit == null) {
            userExpenses.setLimit(1000);
        } else {
            userExpenses.setLimit(limit);
        }
        if (userExpenses.getExpensesSort() != null && userExpenses.getExpensesSort().equals("all")) {
            userExpenses.setExpensesTime(null);
        }
        PageInfo<UserExpenses> allData = userExpensesService.queryAllByEntity(userExpenses);
        map.put("count", allData.getTotal());
        map.put("data", allData.getList());
        return ResponseEntity.ok(map);
    }



    @GetMapping("findType")
    public ResponseEntity<List<String>> findType(){
        List<String> list = userExpensesService.findType();
        return ResponseEntity.ok(list);
    }



    /**
     * 新增数据
     *
     * @param  userExpenses 实例对象
     * @return Void
     */
    @GetMapping("insertExpenses")
    public ResponseEntity<Void> insert(UserExpenses userExpenses){
        userExpenses.setExpensesTime(TimeUtils.getCurrentDateString("YYYYMMdd"));
        //Integer userId = userClient.findId(userExpenses.getExpensesUser()).getUserId();
        userExpenses.setExpensesUserId(String.valueOf(1));
        userExpensesService.insert(userExpenses);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 通过主键删除数据
     *
     * @param expensesId 主键
     * @return 是否成功
     */
    @GetMapping("deleteExpenses")
    public ResponseEntity<Boolean> delete(Integer expensesId){
        boolean b = userExpensesService.deleteById(expensesId);
        return ResponseEntity.ok(b);
    }


    /**
     * 修改数据
     *
     * @param userExpenses
     * @return Void
     */
    @PostMapping("updateExpenses")
    public ResponseEntity<Void> update(@RequestBody UserExpenses userExpenses){
        if (userExpenses.getExpensesId() == null || userExpenses.getExpensesId() == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userExpensesService.update(userExpenses);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 下载数据
     *
     * @param
     * @return Void
     */
    @GetMapping("downloadExpenses")
    public ResponseEntity<Void> downloadExpenses(UserExpenses userExpenses) throws IOException, WriteException {
//        //todo
//        if (StringUtils.isNotBlank(userExpenses.getExpensesTime()) && !",".equals(userExpenses.getExpensesTime()) ){
//            String[] split = StringUtils.split(userExpenses.getExpensesTime(), ',');
//            userExpenses.setDate1(split[0].replaceAll("-",""));
//            userExpenses.setDate2(split[1].replaceAll("-",""));
//        }else {
//            userExpenses.setExpensesTime(null);
//        }
        userExpensesService.downloadFile();
        return ResponseEntity.status(HttpStatus.OK).build();
    }




    /**
     * @Description: 本月收入集合
     * @Param: flag 年、月区分
     * @return: Map<String,Object>
     * ExpensesTime设置为当前日期，date1设置为本月、年，根据传入参数不同进行区分
     */
    @GetMapping("getExpensesCollection")
    public ResponseEntity<Map<String,Object>> getExpensesCollection(String date){
        if (StringUtils.isNotBlank(date)) {
            UserExpenses userExpenses = new UserExpenses();
            userExpenses.setDate1(TimeUtils.getCurrentDateString(date));
            userExpenses.setExpensesTime(TimeUtils.getCurrentDateString("YYYYMMdd"));
            Map<String, Object> expenses = userExpensesService.getMonthExpenses(userExpenses);
            return ResponseEntity.ok(expenses);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}