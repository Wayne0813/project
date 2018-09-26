package com.wayne.tms.config;

import com.wayne.tms.shiro.CustomerFilterChainDefinition;
import com.wayne.tms.shiro.MyRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * @author LV
 * @date 2018/8/30
 */
@Configuration
public class ShiroConfig {

    @Bean
    public Realm getRealm() {
        return new MyRealm();
    }

    @Bean
    public CustomerFilterChainDefinition customerFilterChainDefinition(ShiroFilterFactoryBean shiroFilterFactoryBean) throws Exception {

        CustomerFilterChainDefinition customerFilterChainDefinition = new CustomerFilterChainDefinition();
        customerFilterChainDefinition.setShiroFilter((AbstractShiroFilter) shiroFilterFactoryBean.getObject());

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("/favicon.ico","anon");
        map.put("/bootstrap/**","anon");
        map.put("/dist/**","anon");
        map.put("/js/**","anon");
        map.put("/plugins/**","anon");
        map.put("/logout","logout");
        map.put("/**","user");

        customerFilterChainDefinition.setFilterChainDefinitions(map);

        return customerFilterChainDefinition;
    }

}
