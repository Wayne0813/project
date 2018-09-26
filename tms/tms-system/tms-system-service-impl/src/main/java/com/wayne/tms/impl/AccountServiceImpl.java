package com.wayne.tms.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wayne.tms.entity.*;
import com.wayne.tms.exception.ServiceException;
import com.wayne.tms.mapper.AccountLoginLogMapper;
import com.wayne.tms.mapper.AccountMapper;
import com.wayne.tms.mapper.AccountRolesMapper;
import com.wayne.tms.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LV
 * @date 2018/8/30
 */
@Service(version = "1.0", timeout = 5000)
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountRolesMapper accountRolesMapper;

    @Autowired
    private AccountLoginLogMapper accountLoginLogMapper;

    /**
     * 根据输入电话号码查找用户
     * @param userMobile 用户电话
     * @return 当前用户
     */
    @Override
    public Account findByMobile(String userMobile) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andAccountMobileEqualTo(userMobile);
        List<Account> accountList =  accountMapper.selectByExample(accountExample);
        Account account = null;
        if(accountList != null && !accountList.isEmpty()) {
            account =  accountList.get(0);
        }
        return account;
    }

    /**
     * 记录登录日志
     * @param accountLoginLog 登录信息
     */
    @Override
    public void saveAccountLoginLog(AccountLoginLog accountLoginLog) {
        accountLoginLogMapper.insertSelective(accountLoginLog);
    }

    /**
     * 查找所有用户
     * @return 用户集
     */
    @Override
    public List<Account> findAccountAll() {
        return accountMapper.selectAccountAndROles();
    }

    /**
     * 新增用户
     * @param account   需要新增的用户对象
     * @param rolesId   新增用户具备的所有角色集
     * @param accountId 操作员工id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveAccount(Account account, Integer[] rolesId, Integer accountId) {
        accountMapper.insertSelective(account);
        forInsertAccountRoles(account.getId(), rolesId);
        logger.info("用户: {} == 新增用户: {}", accountId, account.getId());
    }

    /**
     * 根据id删除对应用户
     * @param id        需要删除的用户id
     * @param accountId 操作员工id
     * @exception ServiceException id被篡改，找不到对应用户
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void removeAccountById(Integer id, Integer accountId) throws ServiceException {
        getAccountById(id);
        accountMapper.deleteByPrimaryKey(id);
        AccountRolesExample accountRolesExample = new AccountRolesExample();
        accountRolesExample.createCriteria().andAccountIdEqualTo(id);
        accountRolesMapper.deleteByExample(accountRolesExample);
        logger.info("用户: {} == 删除用户: {}", accountId, id);
    }

    /**
     * 根据id查找对应用户
     * @param id 需要查找的用户id
     * @return 目标对象
     * @exception ServiceException id被篡改，找不到对应用户
     */
    @Override
    public Account findAccountById(Integer id) throws ServiceException {
        return getAccountById(id);
    }

    /**
     * 修改用户信息
     * @param account   修改后的用户对象
     * @param rolesIds   修改后用户所具有的角色集
     * @param accountId 操作员工id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editAccount(Account account, Integer[] rolesIds, Integer accountId) {
        getAccountById(account.getId());
        accountMapper.updateByPrimaryKeySelective(account);
        AccountRolesExample accountRolesExample = new AccountRolesExample();
        accountRolesExample.createCriteria().andAccountIdEqualTo(account.getId());
        accountRolesMapper.deleteByExample(accountRolesExample);
        forInsertAccountRoles(account.getId(), rolesIds);
        logger.info("用户: {} == 修改用户: {}", accountId, account.getId());
    }

    private Account getAccountById(Integer id) throws ServiceException {
        Account account = accountMapper.selectAccountAndRolesById(id);
        if(account == null) {
            throw new ServiceException("该用户不存在, 或已被删除!");
        }
        return account;
    }

    private void forInsertAccountRoles(Integer accountId, Integer[] rolesIds) {
        for(Integer rolesId : rolesIds) {
            AccountRolesKey accountRolesKey = new AccountRolesKey();
            accountRolesKey.setAccountId(accountId);
            accountRolesKey.setRolesId(rolesId);
            accountRolesMapper.insertSelective(accountRolesKey);
        }
    }
}
