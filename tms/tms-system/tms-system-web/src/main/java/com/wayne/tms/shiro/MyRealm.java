package com.wayne.tms.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wayne.tms.entity.Account;
import com.wayne.tms.entity.AccountLoginLog;
import com.wayne.tms.service.AccountService;
import com.wayne.tms.service.RolePermissionService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author LV
 * @date 2018/8/30
 */
public class MyRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Reference(version = "1.0")
    private AccountService accountService;

    @Reference(version = "1.0")
    private RolePermissionService rolePermissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String userMobile = token.getUsername();

        if(userMobile == null) {
            throw new UnknownAccountException("账户或密码错误!");
        }

        Account account = accountService.findByMobile(userMobile);

        if(account == null) {
            throw new UnknownAccountException("账户或密码错误!");
        }

        if(!Account.STATE_NORMAL.equals(account.getAccountState())) {
            throw new LockedAccountException("账户状态异常!");
        }

        System.out.println(account.getAccountPassword());
        System.out.println(new String(token.getPassword()));

        if(!account.getAccountPassword().equals(new String(token.getPassword()))) {
            throw new IncorrectCredentialsException("账户或密码错误!");
        }

        logger.info("{} 登录成功: {}", account, token.getHost());

        //保存登录日志
        AccountLoginLog accountLoginLog = new AccountLoginLog();
        accountLoginLog.setLoginTime(new Date());
        accountLoginLog.setLoginIp(token.getHost());
        accountLoginLog.setAccountId(account.getId());
        accountService.saveAccountLoginLog(accountLoginLog);

        return new SimpleAuthenticationInfo(account, account.getAccountPassword(), getName());
    }
}
