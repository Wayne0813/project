package com.wayne.tms.service;

import com.wayne.tms.entity.Permission;
import com.wayne.tms.entity.Roles;
import com.wayne.tms.entity.RolesPermissionKey;
import com.wayne.tms.exception.ServiceException;

import java.util.List;

/**
 * @author LV
 * @date 2018/8/30
 */
public interface RolePermissionService {

    /**
     * 查找所有权限
     * @return 所有权限
     */
    List<Permission> findAllPermission();

    /**
     * 根据权限类型查找权限集
     * @param menuType 需要查找的权限类型
     * @return 符合条件的权限集
     */
    List<Permission> findPermissionByType(String menuType);

    /**
     * 新增权限
     * @param permission 新增权限对象
     * @param accountId 操作员工id
     */
    void saveNewPermission(Permission permission, Integer accountId);

    /**
     * 删除权限
     * @param id 需要删除的权限id
     * @param accountId 操作员工id
     * @throws ServiceException id被篡改，找不到对应权限
     */
    void removePermissionById(Integer id, Integer accountId) throws ServiceException;

    /**
     * 根据id查找权限
     * @param id 需要查找的权限的id
     * @return 目标对象
     * @throws ServiceException id被篡改，找不到对应权限
     */
    Permission findPermissionById(Integer id) throws ServiceException;

    /**
     * 查找所有权限并且排除当前权限及其子权限、子子权限
     * @param id 需要排除的权限id
     * @return 符合条件的权限集
     */
    List<Permission> findPermissionAndExCurr(Integer id);

    /**
     * 更新权限
     * @param permission 需要更新的权限对象
     * @param accountId 操作员工id
     * @throws ServiceException id被篡改，找不到对应权限
     */
    void editPermission(Permission permission, Integer accountId) throws ServiceException;

    /**
     * 查找所有角色，及角色下权限
     * @return 角色集
     */
    List<Roles> findAllRole();


    /**
     * 新增角色
     * @param roles 需要新增的角色对象
     * @param permissionId 该角色所具备的权限id
     * @param accountId 操作员工id
     */
    void saveNewRoles(Roles roles, Integer[] permissionId, Integer accountId);

    /**
     * 删除角色
     * @param id 需要删除的角色id
     * @param accountId 操作员工id
     * @throws ServiceException id被篡改，找不到对应权限
     */
    void removeRolesById(Integer id, Integer accountId) throws ServiceException;

    /**
     * 通过id查找对应的角色对象
     * @param id 需要查找的角色id
     * @return 目标对象
     * @throws ServiceException id被篡改，找不到对应权限
     */
    Roles findRolesById(Integer id) throws ServiceException;

    /**
     * 修改角色
     * @param roles 修改后的角色对象
     * @param permissionId 修改后角色具备的权限id
     * @param accountId 操作员工id
     * @throws ServiceException id被篡改，找不到对应权限
     */
    void editRoles(Roles roles, Integer[] permissionId, Integer accountId) throws ServiceException;

    /**
     * 查找角色下的所有权限id
     * @param id 角色id
     * @return 该角色下的所有权限集合
     */
    List<RolesPermissionKey> findRolesPermissionKeyByRolesId(Integer id);
}
