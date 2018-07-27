package com.wayne.erp.service.impl;

import com.wayne.erp.entity.*;
import com.wayne.erp.exception.PermissionsException;
import com.wayne.erp.mapper.PermissionMapper;
import com.wayne.erp.mapper.RoleMapper;
import com.wayne.erp.mapper.RolePermissionMapper;
import com.wayne.erp.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LV
 * @date 2018/7/27
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    /**
     * 查找所有权限
     *
     * @return 权限集合
     */
    @Override
    public List<Permission> findAllPermission() {
        PermissionExample permissionExample = new PermissionExample();
        return permissionMapper.selectByExample(permissionExample);
    }

    /**
     * 根据权限的类型查找
     *
     * @param permissionType 权限的类型
     * @return 符合类型的权限的集合
     */
    @Override
    public List<Permission> findPermissionByType(String permissionType) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPermissionTypeEqualTo(permissionType);
        return permissionMapper.selectByExample(permissionExample);
    }

    /**
     * 新增权限
     *
     * @param permission 权限对象
     */
    @Override
    public void save(Permission permission) {
        permissionMapper.insertSelective(permission);
    }

    /**
     * 根据id查找权限
     *
     * @param id 权限的id
     * @return 权限对象
     */
    @Override
    public Permission findPermissionById(Integer id){
        return permissionMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改权限
     *
     * @param permission 需要修改的内容
     */
    @Override
    public void edit(Permission permission) {
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    /**
     * 根据id删除权限
     *
     * @param id id
     */
    @Override
    public void delete(Integer id) throws PermissionsException {
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andPermissionIdEqualTo(id);
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);

        if(rolePermissionList != null && rolePermissionList.size() > 0){
            throw new PermissionsException("请移除该权限下的所有角色,再删除!");
        }

        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPidEqualTo(id);
        List<Permission> permissionListp = permissionMapper.selectByExample(permissionExample);
        if(permissionListp != null && permissionListp.size() > 0){
            throw new PermissionsException("请移除该权限下的所有子权限,再删除!");
        }
        permissionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查找所有角色
     *
     * @return 角色集合
     */
    @Override
    public List<Role> findAllRole() {
        return roleMapper.selectWithPermission();
    }

    /**
     * 根据id查找角色
     *
     * @param id id
     * @return 角色对象
     */
    @Override
    public Role findRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增角色
     *
     * @param role          角色对象
     * @param permissionIds 权限id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveRole(Role role, Integer[] permissionIds) {
        roleMapper.insertSelective(role);

        for (Integer permissionId : permissionIds){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(permissionId);
            rolePermission.setRoleId(role.getId());
            rolePermissionMapper.insertSelective(rolePermission);
        }

    }


}
