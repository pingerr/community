package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper // Mybatis常用，等价于 Repository,
public interface UserMapper {

    User selectByID(int id); //根据ID查user

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user); //增加用户，返回行数

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);
}
