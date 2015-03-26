package com.yazuo.erp.system.controller;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.vo.SysUserVO;

import javax.servlet.http.HttpSession;

/**
 */
public abstract class SysBasicController {
    protected Integer getUserId(HttpSession session) {
        SysUserVO user = this.getUser(session);
        return user.getId();
    }

    protected SysUserVO getUser(HttpSession session) {
        SysUserVO user = (SysUserVO) session.getAttribute(Constant.SESSION_USER);
        return user;
    }
}
