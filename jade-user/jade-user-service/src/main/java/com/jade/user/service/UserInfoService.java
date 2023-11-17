package com.jade.user.service;

import com.jade.user.entity.UserInfo;

import java.util.List;

/**
 * mm财务管理-用户(UserInfo)表服务接口
 *
 * @author makejava
 * @since 2021-02-23 11:19:11
 */
public interface UserInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserInfo queryById(Integer userId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserInfo> queryAllByLimit(int offset, int limit);


    UserInfo findOne(UserInfo userInfo);
    /**
     * 新增数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    UserInfo insert(UserInfo userInfo);

    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    UserInfo update(UserInfo userInfo);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer userId);

    UserInfo queryUser(UserInfo userInfo);
}