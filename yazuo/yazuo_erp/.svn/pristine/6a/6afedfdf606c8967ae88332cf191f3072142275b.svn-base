package com.yazuo.erp.system.controller;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.vo.SysUserVO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 */
public class AbstractBasicController {
    protected Integer getUserId(HttpSession session) {
        SysUserVO user = this.getUser(session);
        return user == null ? null : user.getId();
    }

    protected Integer getUserId(HttpServletRequest request) {
        SysUserVO user = this.getUser(request);
        return user == null ? null : user.getId();
    }

    protected SysUserVO getUser(HttpServletRequest request) {
        SysUserVO userVO = this.getUser(request.getSession());
        if (userVO == null) {
            userVO = (SysUserVO) request.getAttribute(Constant.SESSION_USER);
        }
        return userVO;
    }


    protected SysUserVO getUser(HttpSession session) {
        SysUserVO user = (SysUserVO) session.getAttribute(Constant.SESSION_USER);
        return user;
    }

    protected String getApplicationRoot(HttpSession session) {
        return session.getServletContext().getRealPath("/");
    }
}
