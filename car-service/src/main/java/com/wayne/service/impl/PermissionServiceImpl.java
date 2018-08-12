package com.wayne.service.impl;

import com.wayne.entity.Permission;
import com.wayne.entity.PermissionExample;
import com.wayne.mapper.PermissionMapper;
import com.wayne.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/8/2
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 查找所有权限
     * @return 所有权限的集合
     */
    @Override
    public List<Permission> findAllPermission() {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria();
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);

        List<Permission> newList = new ArrayList<>();

        treeList(newList, permissionList, 0);


        return newList;
    }

    /**
     * 查询所有菜单权限
     *
     * @param permissionType 关键字
     * @return 集合
     */
    @Override
    public List<Permission> findAllMenuPermission(String permissionType) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPermissionTypeEqualTo(permissionType);
        return permissionMapper.selectByExample(permissionExample);
    }

    /**
     * 新增权限
     *
     * @param permission 新增权限
     */
    @Override
    public void savePermission(Permission permission) {
        permissionMapper.insertSelective(permission);
    }

    /**
     * 通过id查找权限对象
     *
     * @param id id
     * @return 对象
     */
    @Override
    public Permission findPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    /**
     * 移除当前对象的子权限、子子权限 and so on
     *
     * @param id             id
     * @param permissionList 所有菜单权限
     * @return 集合
     */
    @Override
    public Permission findPermissionById(Integer id, List<Permission> permissionList) {
        Permission permission = permissionMapper.selectByPrimaryKey(id);

        removeSonPermission(permissionList, permission);

        return permission;
    }

    private void removeSonPermission(List<Permission> permissionList, Permission permission) {

        List<Permission> tempList = permissionList;

        for (int i = 0; i < tempList.size(); i++) {

            if (permissionList.get(i).getPid().equals(permission.getId())) {
                removeSonPermission(permissionList, permissionList.get(i));
            }
        }
        permissionList.remove(permission);

    }


    private void treeList(List<Permission> newList, List<Permission> permissionList, Integer pid) {

        for (Permission permission : permissionList) {
            if (permission.getPid().equals(pid)) {
                newList.add(permission);
                treeList(newList, permissionList, permission.getId());
            }

        }

    }

}
