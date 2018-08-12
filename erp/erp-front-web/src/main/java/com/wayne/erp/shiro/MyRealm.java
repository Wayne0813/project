package com.wayne.erp.shiro;

import com.wayne.erp.entity.Employee;
import com.wayne.erp.entity.Permission;
import com.wayne.erp.entity.Role;
import com.wayne.erp.service.EmployeeService;
import com.wayne.erp.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author LV
 * @date 2018/7/31
 */
public class MyRealm extends AuthorizingRealm {

    // Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 判断权限角色，授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();
        List<Role> roleList = permissionService.findRoleByEmployeeId(employee.getId());

        List<Permission> permissions = new ArrayList<>();

        Set<String> roleCodeSet = new HashSet<>();
        Set<String> permissionCodeSet = new HashSet<>();

        for (Role role : roleList) {
            roleCodeSet.add(role.getRoleCode());
            List<Permission> permissionList = permissionService.findPermissionByRoleId(role.getId());
            permissions.addAll(permissionList);
        }

        for (Permission permission : permissions) {
            permissionCodeSet.add(permission.getPermissionCode());
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.setRoles(roleCodeSet);
        simpleAuthorizationInfo.setStringPermissions(permissionCodeSet);

        return simpleAuthorizationInfo;
    }

    /**
     * 判断登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

        String userTel = usernamePasswordToken.getUsername();
        Employee employee = null;
        if(StringUtils.isNoneEmpty(userTel)){
            employee = employeeService.findByEmployeeTel(userTel);
        }

        if(employee == null) {
            throw new UnknownAccountException();
        }

        if(employee.getState().equals(Employee.EMPLOYEE_STATE_FROZEN)) {
            throw new LockedAccountException();
        }

        return new SimpleAuthenticationInfo(employee, employee.getPassword(), getName());

    }
}


