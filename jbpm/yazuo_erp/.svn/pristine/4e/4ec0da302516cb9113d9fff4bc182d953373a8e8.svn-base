package com.yazuo.erp.bes.controller;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.vo.SysUserVO;

import javax.servlet.http.HttpSession;

/**
 * 后端服务Controller基类
 * 添加共用方法
 */
public abstract class BesBasicController {
    protected static final String BES_MONTHLY_CHECK_DATE = "bes_monthly_check_date";

    protected Integer getUserId(HttpSession session) {
        SysUserVO user = this.getUser(session);
        return user.getId();
    }

    protected SysUserVO getUser(HttpSession session) {
        SysUserVO user = (SysUserVO) session.getAttribute(Constant.SESSION_USER);
        return user;
    }
}
