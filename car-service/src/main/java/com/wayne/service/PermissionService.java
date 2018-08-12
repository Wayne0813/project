package com.wayne.service;

import com.wayne.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/8/2
 */
public interface PermissionService {


    /**
     * 查找所有权限
     * @return 所有权限的集合
     */
    List<Permission> findAllPermission();


    /**
     * 查询所有菜单权限
     * @param permissionType 关键字
     * @return 集合
     */
    List<Permission> findAllMenuPermission(String permissionType);

    /**
     * 新增权限
     * @param permission 新增权限
     */
    void savePermission(Permission permission);

    /**
     * 通过id查找权限对象
     * @param id id
     * @return 对象
     */
    Permission findPermissionById(Integer id);

    /** 移除当前对象的子权限、子子权限 and so on
     * @param id id
     * @param permissionList 所有菜单权限
     * @return 集合
     */
    Permission findPermissionById(Integer id, List<Permission> permissionList);
}
