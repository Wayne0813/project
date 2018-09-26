package com.wayne.tms.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wayne.tms.entity.Permission;
import com.wayne.tms.service.RolePermissionService;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/8/30
 */
public class CustomerFilterChainDefinition {

    private Logger logger = LoggerFactory.getLogger(CustomerFilterChainDefinition.class);

    @Reference(version = "1.0")
    private RolePermissionService rolePermissionService;

    private AbstractShiroFilter shiroFilter;
    private Map<String, String> FilterChainDefinitions;


    public void setShiroFilter(AbstractShiroFilter shiroFilter) {
        this.shiroFilter = shiroFilter;
    }

    public void setFilterChainDefinitions(LinkedHashMap<String,String> filterChainDefinitions) {
        this.FilterChainDefinitions = filterChainDefinitions;
    }

    /**
     * Spring容器启动时调用
     */
    @PostConstruct
    public synchronized void init() {
        logger.info("------初始化URL权限-----------");
        //清除原有的URL权限
        getFilterChainManager().getFilterChains().clear();
        //加载现有的URL权限
        load();
        logger.info("------初始化URL权限结束-----------");
    }

    /**
     * 重新加载URL权限
     */
    public synchronized void updateUrlPermission() {
        logger.info("------刷新URL权限-----------");
        //清除原有的URL权限
        getFilterChainManager().getFilterChains().clear();
        //加载现有的URL权限
        load();
        logger.info("------刷新URL权限结束-----------");
    }

    public synchronized void load() {
        LinkedHashMap<String,String> urlMap = new LinkedHashMap<>();
        urlMap.putAll(FilterChainDefinitions);

        List<Permission> permissionList = rolePermissionService.findAllPermission();

        for(Permission permission : permissionList) {
            urlMap.put(permission.getUrl(),"perms["+permission.getPermissionCode()+"]");
        }

        urlMap.put("/**","user");

        //URL和权限的关系设置到shiroFilter中
        DefaultFilterChainManager defaultFilterChainManager = getFilterChainManager();
        for(Map.Entry<String,String> entry : urlMap.entrySet()) {
            defaultFilterChainManager.createChain(entry.getKey(),entry.getValue());
        }
    }

    private DefaultFilterChainManager getFilterChainManager() {
        PathMatchingFilterChainResolver pathMatchingFilterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager defaultFilterChainManager = (DefaultFilterChainManager) pathMatchingFilterChainResolver.getFilterChainManager();
        return defaultFilterChainManager;
    }

}
