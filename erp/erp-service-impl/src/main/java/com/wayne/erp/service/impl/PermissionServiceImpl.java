package com.wayne.erp.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.wayne.erp.entity.*;
import com.wayne.erp.exception.PermissionsException;
import com.wayne.erp.mapper.EmployeeRoleMapper;
import com.wayne.erp.mapper.PermissionMapper;
import com.wayne.erp.mapper.RoleMapper;
import com.wayne.erp.mapper.RolePermissionMapper;
import com.wayne.erp.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LV
 * @date 2018/7/27
 */
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;


    /**
     * 查找所有权限
     *
     * @return 权限集合
     */
    @Override
    public List<Permission> findAllPermission() {
        PermissionExample permissionExample = new PermissionExample();

        List<Permission> permissionList =  permissionMapper.selectByExample(permissionExample);
        List<Permission> endList = new ArrayList<>();
        treeList(permissionList, endList, 0);
        return endList;
    }

    private void treeList(List<Permission> sourceList, List<Permission> endList, int parentId) {
        List<Permission> tempList = Lists.newArrayList(Collections2.filter(sourceList, new Predicate<Permission>() {
            @Override
            public boolean apply(Permission permission) {
                return permission.getPid().equals(parentId);
            }
        }));

        for(Permission permission : tempList) {
            endList.add(permission);
            treeList(sourceList,endList,permission.getId());
        }
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
        Role role = roleMapper.selectByPrimaryKey(id);
        List<Permission> newList = new ArrayList();

        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(role.getId());
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);

        for (RolePermission rolePermission : rolePermissionList){
            PermissionExample permissionExample = new PermissionExample();
            permissionExample.createCriteria().andIdEqualTo(rolePermission.getPermissionId());
            List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
            for (Permission permission : permissionList) {
                newList.add(permission);
            }
        }

        role.setPermissionList(newList);

        return role;
    }

    /**
     * 新增角色
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

    /**
     * 查找当前id下的所有子权限，及其子子权限
     *
     * @param id 当前权限的id
     * @return 子权限及其子子权限
     */
    @Override
    public List<Permission> findSonPermissionListAndSoOn(Integer id) {
        List<Permission> endList = new ArrayList<>();
        findAndSoOn(endList, id);
        return endList;
    }

    /**
     * 修改角色对象
     * @param role          角色对象
     * @param permissionIds 角色具备的权限id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editRole(Role role, Integer[] permissionIds) {
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(role.getId());
        rolePermissionMapper.deleteByExample(rolePermissionExample);

        roleMapper.updateByPrimaryKeySelective(role);

        for (Integer id : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(id);
            rolePermissionMapper.insertSelective(rolePermission);
        }
    }

    /**
     * 删除角色
     * @param id 要删除的角色id
     * @throws PermissionsException 该角色被员工占用
     */
    @Override
    public void deleteRole(Integer id) throws PermissionsException {
        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andRoleIdEqualTo(id);
        List<EmployeeRole> employeeRoleList = employeeRoleMapper.selectByExample(employeeRoleExample);
        if(employeeRoleList.size() > 0){
            throw new PermissionsException("该角色已被员工绑定,请解除绑定后重新操作!");
        }

        roleMapper.deleteByPrimaryKey(id);

        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(id);
        rolePermissionMapper.deleteByExample(rolePermissionExample);
    }

    /**
     * 根据员工id查找角色
     *
     * @param id 员工id
     * @return 角色集合
     */
    @Override
    public List<Role> findRoleByEmployeeId(Integer id) {
        return roleMapper.selectByEmployeeId(id);
    }

    /**
     * 根据角色id查找权限
     *
     * @param id 角色id
     * @return 权限集合
     */
    @Override
    public List<Permission> findPermissionByRoleId(Integer id) {
        return permissionMapper.selectByRoleId(id);
    }


    /**
     * 递归
     * @param endList 新集合
     * @param id 当前权限id
     */
    private void findAndSoOn(List<Permission> endList, Integer id){
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPidEqualTo(id);

        List<Permission> permissionLisp = permissionMapper.selectByExample(permissionExample);

        if(permissionLisp.size() > 0){
            for (Permission permission : permissionLisp){
                endList.add(permission);
                findAndSoOn(endList, permission.getId());
            }
        }
    }


}
