/*
 * 文件名：MemberService.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年8月7日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.member.service;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yazuo.superwifi.member.vo.Member;
import com.yazuo.superwifi.member.vo.MemberLoginInfo;
import com.yazuo.superwifi.util.JsonResult;


public interface MemberService
{
    Member getMemberByMac(Integer brandId, String mac)
        throws Exception;

    JsonResult memberAddStatic(Map<String, Object> map)
        throws Exception;

    void saveMember(Member member);

    JsonResult exportMemberInfo(Map<String, Object> obj, HttpServletRequest request, HttpServletResponse response)
        throws Exception;

    Map<String, Object> getBossMobile(Map<String, Object> map)
        throws Exception;

    void setMemberSendMobile(Map<String, Object> map)
        throws Exception;

    Member getMemberByMobileAndBrandId(Integer brandId, String mobile)
        throws Exception;

    void updateMember(Member member, Member updateMember);

    void saveMemberLoginInfo(MemberLoginInfo memberLoginInfo);
}
