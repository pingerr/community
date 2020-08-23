package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    //网站主页不需要userId，需要动态SQL
    //支持分页：offset起始号，limit每页数量
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    //返回总页数
    // @Param用于参数取别名
    // 如果只有一个参数，并且在<if>里使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId); // 注解表示别名

}
