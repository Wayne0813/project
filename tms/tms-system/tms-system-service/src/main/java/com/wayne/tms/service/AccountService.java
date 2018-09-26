package com.wayne.tms.service;

import com.wayne.tms.entity.Account;
import com.wayne.tms.entity.AccountLoginLog;
import com.wayne.tms.exception.ServiceException;

import java.util.List;

/**
 * @author LV
 * @date 2018/8/30
 */
public interface AccountService {

    /**
     * 根据输入电话号码查找用户
     * @param userMobile 用户电话
     * @return 当前用户
     */
    Account findByMobile(String userMobile);

    /**
     * 记录登录日志
     * @param accountLoginLog 登录信息
     */
    void saveAccountLoginLog(AccountLoginLog accountLoginLog);

    /**
     * 查找所有用户
     * @return 用户集
     */
    List<Account> findAccountAll();

    /**
     * 新增用户
     * @param account 需要新增的用户对象
     * @param rolesId 新增用户具备的所有角色集
     * @param accountId 操作员工id
     */
    void saveAccount(Account account, Integer[] rolesId, Integer accountId);

    /**
     * 根据id删除对应用户
     * @param id        需要删除的用户id
     * @param accountId 操作员工id
     * @exception ServiceException id被篡改，找不到对应用户
     */
    void removeAccountById(Integer id, Integer accountId);

    /**
     * 根据id查找对应用户
     * @param id 需要查找的用户id
     * @return 目标对象
     * @throws ServiceException id被篡改，找不到对应用户
     */
    Account findAccountById(Integer id) throws ServiceException;

    /**
     * 修改用户信息
     * @param account 修改后的用户对象
     * @param rolesIds 修改后用户所具有的角色集
     * @param accountId 操作员工id
     */
    void editAccount(Account account, Integer[] rolesIds, Integer accountId);
}
