package com.wayne.tms.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.wayne.tms.entity.*;
import com.wayne.tms.exception.ServiceException;
import com.wayne.tms.mapper.AccountRolesMapper;
import com.wayne.tms.mapper.PermissionMapper;
import com.wayne.tms.mapper.RolesMapper;
import com.wayne.tms.mapper.RolesPermissionMapper;
import com.wayne.tms.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色和权限的业务类
 * @author LV
 * @date 2018/8/30
 */
@Service(version = "1.0", timeout = 5000)
public class RolePermissionServiceImpl implements RolePermissionService {

    private Logger logger = LoggerFactory.getLogger(RolePermissionServiceImpl.class);

    @Autowired
    private RolesMapper rolesMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private AccountRolesMapper accountRolesMapper;

    @Autowired
    private RolesPermissionMapper rolesPermissionMapper;

    /**
     * 查找所有权限
     * @return 所有权限
     */
    @Override
    public List<Permission> findAllPermission() {

        List<Permission> permissionList = permissionMapper.selectByExample(new PermissionExample());
        List<Permission> resultList = new ArrayList<>();
        treeList(permissionList, resultList, 0);

        return resultList;
    }

    /**
     * 根据权限类型查找权限集
     * @param menuType 需要查找的权限类型
     * @return 符合条件的权限集
     */
    @Override
    public List<Permission> findPermissionByType(String menuType) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPermissionTypeEqualTo(menuType);
        return permissionMapper.selectByExample(permissionExample);
    }

    /**
     * 新增权限
     * @param permission 新增权限对象
     * @param accountId 操作员工id
     */
    @Override
    public void saveNewPermission(Permission permission, Integer accountId) {
        permissionMapper.insertSelective(permission);
        logger.info("用户: {} == 新增权限: {}", accountId, permission.getId());
    }

    /**
     * 删除权限
     * @param id        需要删除的权限id
     * @param accountId 操作员工id
     * @throws ServiceException id被篡改，找不到对应权限
     */
    @Override
    public void removePermissionById(Integer id, Integer accountId) throws ServiceException {
        getPermissionById(id);

        RolesPermissionExample rolesPermissionExample = new RolesPermissionExample();
        rolesPermissionExample.createCriteria().andPermissionIdEqualTo(id);
        List<RolesPermissionKey> rolesPermissionKeys = rolesPermissionMapper.selectByExample(rolesPermissionExample);
        if(rolesPermissionKeys != null && !rolesPermissionKeys.isEmpty()) {
            throw new ServiceException("该权限已被角色引用, 无法进行删除操作!");
        }

        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andParentIdEqualTo(id);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        if(permissionList != null && !permissionList.isEmpty()) {
            throw new ServiceException("该权限下有子权限, 无法进行删除操作!");
        }

        permissionMapper.deleteByPrimaryKey(id);
        logger.info("用户: {} == 删除权限: {}", accountId, id);
    }

    /**
     * 根据id查找权限
     * @param id 需要查找的权限的id
     * @return 目标对象
     * @throws ServiceException id被篡改，找不到对应权限
     */
    @Override
    public Permission findPermissionById(Integer id) throws ServiceException {
        return getPermissionById(id);
    }

    /**
     * 查找所有权限并且排除当前权限及其子权限、子子权限
     * @param id 需要排除的权限id
     * @return 符合条件的权限集
     */
    @Override
    public List<Permission> findPermissionAndExCurr(Integer id) {
        List<Permission> allMenuPermission = findPermissionByType(Permission.MENU_TYPE);
        Permission permission = getPermissionById(id);
        remove(allMenuPermission, permission);
        return allMenuPermission;
    }

    /**
     * 更新权限
     * @param permission 需要更新的权限对象
     * @param accountId  操作员工id
     * @throws ServiceException id被篡改，找不到对应权限
     */
    @Override
    public void editPermission(Permission permission, Integer accountId) throws ServiceException {
        getPermissionById(permission.getId());
        permissionMapper.updateByPrimaryKeySelective(permission);
        logger.info("用户: {} == 更新权限: {}", accountId, permission.getId());
    }

    /**
     * 查找所有角色，及角色下权限
     * @return 角色集
     */
    @Override
    public List<Roles> findAllRole() {
        return rolesMapper.selectRoleAndPermission();
    }

    /**
     * 新增角色
     * @param roles        需要新增的角色对象
     * @param permissionId 该角色所具备的权限id
     * @param accountId    操作员工id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewRoles(Roles roles, Integer[] permissionId, Integer accountId) {
        rolesMapper.insertSelective(roles);
        forUpdateRolesPermission(roles.getId(), permissionId);
        logger.info("用户: {} == 新增角色: {}", accountId, roles.getId());
    }

    /**
     * 删除角色
     * @param id        需要删除的角色id
     * @param accountId 操作员工id
     * @throws ServiceException id被篡改，找不到对应权限
     */
    @Override
    public void removeRolesById(Integer id, Integer accountId) throws ServiceException {
        getRolesById(id);

        // 查看是否有用户引用
        AccountRolesExample accountRolesExample = new AccountRolesExample();
        accountRolesExample.createCriteria().andRolesIdEqualTo(id);
        List<AccountRolesKey> accountRolesKeys = accountRolesMapper.selectByExample(accountRolesExample);
        if(accountRolesKeys != null && !accountRolesKeys.isEmpty()) {
            throw new ServiceException("该角色已被账号引用, 无法进行删除操作!");
        }

        // 删除角色
        rolesMapper.deleteByPrimaryKey(id);

        // 删除角色权限关联关系表
        RolesPermissionExample rolesPermissionExample = new RolesPermissionExample();
        rolesPermissionExample.createCriteria().andRolesIdEqualTo(id);
        rolesPermissionMapper.deleteByExample(rolesPermissionExample);

        // 记录日志
        logger.info("用户: {} == 删除角色: {}", accountId, id);
    }

    /**
     * 通过id查找对应的角色对象
     * @param id 需要查找的角色id
     * @return 目标对象
     * @throws ServiceException id被篡改，找不到对应权限
     */
    @Override
    public Roles findRolesById(Integer id) throws ServiceException {
        return  getRolesById(id);
    }

    /**
     * 修改角色
     * @param roles        修改后的角色对象
     * @param permissionId 修改后角色具备的权限id
     * @param accountId    操作员工id
     * @throws ServiceException id被篡改，找不到对应权限
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editRoles(Roles roles, Integer[] permissionId, Integer accountId) throws ServiceException {
        getRolesById(roles.getId());
        RolesPermissionExample rolesPermissionExample = new RolesPermissionExample();
        rolesPermissionExample.createCriteria().andRolesIdEqualTo(roles.getId());
        rolesPermissionMapper.deleteByExample(rolesPermissionExample);
        rolesMapper.updateByPrimaryKeySelective(roles);
        forUpdateRolesPermission(roles.getId(), permissionId);
        logger.info("用户: {} == 更新角色: {}", accountId, roles.getId());
    }

    /**
     * 查找角色下的所有权限id
     * @param id 角色id
     * @return 该角色下的所有权限集合
     */
    @Override
    public List<RolesPermissionKey> findRolesPermissionKeyByRolesId(Integer id) {
        RolesPermissionExample rolesPermissionExample = new RolesPermissionExample();
        rolesPermissionExample.createCriteria().andRolesIdEqualTo(id);
        return rolesPermissionMapper.selectByExample(rolesPermissionExample);
    }

    /**
     * 循环更新角色权限关系表
     * @param rolesId 角色id
     * @param permissionIds 权限id集合
     */
    private void forUpdateRolesPermission(Integer rolesId, Integer[] permissionIds) {
        for(Integer permissionId : permissionIds) {
            RolesPermissionKey rolesPermissionKey = new RolesPermissionKey();
            rolesPermissionKey.setRolesId(rolesId);
            rolesPermissionKey.setPermissionId(permissionId);
            rolesPermissionMapper.insertSelective(rolesPermissionKey);
        }
    }

    /**
     * 将目标权限的子权限及子子权限从allMenuPermission中移除
     * @param allMenuPermission 所有菜单权限
     * @param permission 需要移除的目标权限
     */
    private void remove(List<Permission> allMenuPermission, Permission permission) {
        List<Permission> temp = new ArrayList<>(allMenuPermission);
        for(Permission permis : temp) {
            if(permis.getParentId().equals(permission.getId())) {
                remove(allMenuPermission, permis);
                allMenuPermission.remove(permis);
            }
        }
        allMenuPermission.remove(permission);
    }

    /**
     * 通过id获得对应权限
     * @throws ServiceException 无该权限时返回的信息
     */
    private Permission getPermissionById(Integer id) throws ServiceException {
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        if(permission == null) {
            throw new ServiceException("该权限不存在,或已被删除!");
        }
        return permission;
    }

    /**
     * 通过id查找对应的角色
     * @throws ServiceException 无该角色时返回的信息
     */
    private Roles getRolesById(Integer id) throws ServiceException {
        Roles roles = rolesMapper.selectByPrimaryKey(id);
        if(roles == null) {
            throw new ServiceException("该角色不存在,或已被删除!");
        }
        return roles;
    }

    private void treeList(List<Permission> permissionList, List<Permission> resultList, Integer parentId) {

        List<Permission> tempList = Lists.newArrayList(Collections2.filter(permissionList, permission -> permission.getParentId().equals(parentId)));

        for(Permission permission : tempList) {
            resultList.add(permission);
            treeList(permissionList, resultList, permission.getId());
        }

    }

}
