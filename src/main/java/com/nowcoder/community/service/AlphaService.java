package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
//@Scope("prototype") // 表示多例，默认单例
public class AlphaService {

    @Autowired // 注入Dao
    @Qualifier("alphaHibernate")
    private AlphaDao alphaDao;

    public AlphaService(){ // 构造器
        System.out.println("实例化AlphaService");
    }

    @PostConstruct // 表示构造器之后调用
    public void init () {
        System.out.println("初始化AlphaService");
    }

    @PreDestroy // 表示销毁方法
    public void destroy() {
        System.out.println("销毁AlphaService");
    }

    // 访问Dao
    public String find(){
        return alphaDao.select();
    }
}
