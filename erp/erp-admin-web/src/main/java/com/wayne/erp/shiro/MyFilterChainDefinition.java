package com.wayne.erp.shiro;


import com.wayne.erp.entity.Permission;
import com.wayne.erp.service.PermissionService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author LV
 * @date 2018/8/1
 */
public class MyFilterChainDefinition {

    @Autowired
    private PermissionService permissionService;

    private AbstractShiroFilter shiroFilter;

    private String filterChainDefinitions;

    @PostConstruct
    public void init(){
        System.out.println("------------权限初始化中-----------");
        getFilterChainManager().getFilterChains().clear();
        load();
    }

    public void updatePermission(){
        System.out.println("------------权限更新中-----------");
        getFilterChainManager().getFilterChains().clear();
        load();
    }

    public void load() {
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);

        List<Permission> permissionList = permissionService.findAllPermission();

        for (Permission permission : permissionList) {
            //   /manage/employee/add, perms[employee:add]
            section.put(permission.getUrl(), "perms[" + permission.getPermissionCode() + "]");
        }
        section.put("/**", "user");

        DefaultFilterChainManager defaultFilterChainManager = getFilterChainManager();
        for(Ini.Section.Entry<String,String> entry : section.entrySet() ) {
            defaultFilterChainManager.createChain(entry.getKey(), entry.getValue());
        }

    }

    private DefaultFilterChainManager getFilterChainManager() {
        PathMatchingFilterChainResolver pathMatchingFilterChainResolver = (PathMatchingFilterChainResolver) this.shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager defaultFilterChainManager = (DefaultFilterChainManager) pathMatchingFilterChainResolver.getFilterChainManager();
        return defaultFilterChainManager;
    }


    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    public void setShiroFilter(AbstractShiroFilter shiroFilter) {
        this.shiroFilter = shiroFilter;
    }
}
