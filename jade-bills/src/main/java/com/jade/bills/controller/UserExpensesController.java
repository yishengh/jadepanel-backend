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
     * @param  userExpenses
     * @return 对象列表
     */
    @GetMapping("selectAll")
    public ResponseEntity<Map<String,Object>> selectAll(
            @RequestParam(value = "offset" ,required = false) Integer offset,
            Integer limit , UserExpenses userExpenses){
        Map<String,Object> map = new HashMap<>();
        if (offset == null || offset == 0){
            userExpenses.setOffset(1);
        }else{
            userExpenses.setOffset(offset);
        }

        if (StringUtils.isNotBlank(userExpenses.getExpensesTime()) && !",".equals(userExpenses.getExpensesTime())){
            String[] split = StringUtils.split(userExpenses.getExpensesTime(), ',');
            userExpenses.setDate1(split[0].replaceAll("-",""));
            userExpenses.setDate2(split[1].replaceAll("-",""));
        }else {
            userExpenses.setExpensesTime(null);
        }
        userExpenses.setLimit(limit);
        PageInfo<UserExpenses> allData = userExpensesService.queryAllByEntity(userExpenses);
        map.put("count",allData.getTotal());
        map.put("data",allData.getList());
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
     * @param  userExpenses
     * @return Void
     */
    @PostMapping("insert")
    public ResponseEntity<Void> insert(@RequestBody UserExpenses userExpenses){
        userExpenses.setExpensesTime(TimeUtils.getCurrentDateString("YYYYMMdd"));
        //Integer userId = userClient.findId(userExpenses.getExpensesUser()).getUserId();
        userExpenses.setExpensesUserId(String.valueOf(1));
        userExpensesService.insert(userExpenses);
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
        boolean b = userExpensesService.deleteById(id);
        return ResponseEntity.ok(b);
    }


    /**
     * 修改数据
     *
     * @param userExpenses
     * @return Void
     */
    @PostMapping("update")
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
    public ResponseEntity<Map<String,Object>> getExpensesCollection(String flag){
        if (StringUtils.isNotBlank(flag)){
            UserExpenses userExpenses = new UserExpenses();
            if ("month".equals(flag)){
                userExpenses.setDate1(TimeUtils.getCurrentDateString("YYYYMM"));
            }else {
                userExpenses.setDate1(TimeUtils.getCurrentDateString("YYYY"));
            }
            userExpenses.setExpensesTime(TimeUtils.getCurrentDateString("YYYYMMdd"));
            Map<String,Object> expenses = userExpensesService.getMonthExpenses(userExpenses);
            return ResponseEntity.ok(expenses);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}