package com.jade.user.service.impl;

import com.jade.user.entity.UserInfo;
import com.jade.user.mapper.UserInfoMapper;
import com.jade.user.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * mm财务管理-用户(UserInfo)表服务实现类
 *
 * @author makejava
 * @since 2021-02-23 11:19:11
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public UserInfo queryById(Integer userId) {
        return this.userInfoMapper.queryById(userId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserInfo> queryAllByLimit(int offset, int limit) {
        return this.userInfoMapper.queryAllByLimit(offset, limit);
    }

    @Override
    public UserInfo findOne(UserInfo userInfo) {
        return userInfoMapper.queryId(userInfo);
    }

    /**
     * 新增数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    @Override
    public UserInfo insert(UserInfo userInfo) {
        this.userInfoMapper.insert(userInfo);
        return userInfo;
    }

    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    @Override
    public UserInfo update(UserInfo userInfo) {
        this.userInfoMapper.update(userInfo);
        return this.queryById(userInfo.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer userId) {
        return this.userInfoMapper.deleteById(userId) > 0;
    }

    @Override
    public UserInfo queryUser(UserInfo userInfo) {
        System.out.println("queryUser");
        return userInfoMapper.selectOne(userInfo.getUserName(), userInfo.getPassword());
    }
}