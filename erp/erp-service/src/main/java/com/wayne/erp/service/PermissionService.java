package com.wayne.erp.service;

import com.wayne.erp.entity.Permission;
import com.wayne.erp.entity.Role;

import java.util.List;

/**
 * @author LV
 * @date 2018/7/27
 */
public interface PermissionService {
    /**
     * 查找所有权限
     * @return 权限集合
     */
    List<Permission> findAllPermission();

    /**
     * 根据权限的类型查找
     * @param permissionType 权限的类型
     * @return 符合类型的权限的集合
     */
    List<Permission> findPermissionByType(String permissionType);

    /**
     * 新增权限
     * @param permission 权限对象
     */
    void save(Permission permission);

    /**
     * 根据id查找权限
     * @param id 权限的id
     * @return 权限对象
     */
    Permission findPermissionById(Integer id);

    /**
     * 修改权限
     * @param permission 需要修改的内容
     */
    void edit(Permission permission);

    /**
     * 根据id删除权限
     * @param id id
     */
    void delete(Integer id);


    /**
     * 查找所有角色
     * @return 角色集合
     */
    List<Role> findAllRole();

    /**
     * 根据id查找角色
     * @param id id
     * @return 角色对象
     */
    Role findRoleById(Integer id);

    /**
     * 新增角色
     * @param role 角色对象
     * @param permissionIds 权限id
     */
    void saveRole(Role role, Integer[] permissionIds);
}
