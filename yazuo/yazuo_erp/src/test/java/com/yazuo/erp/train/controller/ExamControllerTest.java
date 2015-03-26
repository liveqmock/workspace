package com.yazuo.erp.train.controller;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author congshuanglong
 */
public class ExamControllerTest extends ControllerTestBase {
    @Resource
    private ExamController examController;



    @Before
    public void init() {

        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
    }

    @Test
    public void testExamine(){
        request.setRequestURI("/train/exam/examine");
        request.setMethod(HttpMethod.GET.name());
        request.setParameter("courseId", "110");
        request.setParameter("coursewareId","223");
        ModelAndView mv = null;

        try {
            handlerAdapter.handle(request, response, new HandlerMethod(examController, "examine",Integer.class,Integer.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(new String(response.getContentAsByteArray()));
        Assert.assertNull(mv);
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void testSaveExam(){
        request.setRequestURI("/train/exam/examine");
        request.setMethod(HttpMethod.GET.name());
        request.setAttribute("examDtlVOs","");
        ModelAndView mv = null;
        //TODO 未测试
        try {
            handlerAdapter.handle(request, response, new HandlerMethod(examController, "examine",Integer.class,Integer.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(new String(response.getContentAsByteArray()));
        Assert.assertNull(mv);
        Assert.assertEquals(response.getStatus(), 200);

    }

    @After
    public void destroy() {

    }
}
