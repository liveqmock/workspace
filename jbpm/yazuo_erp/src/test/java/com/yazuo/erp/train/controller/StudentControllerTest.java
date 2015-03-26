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
public class StudentControllerTest extends ControllerTestBase {
    @Resource
    private StudentController studentController;

    @Before
    public void init() {
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
    }

    @Test
    public void testGetAllCoursewares() {
        request.setRequestURI("/train/student/getAllCoursewares");
        request.setMethod(HttpMethod.GET.name());
        ModelAndView mv = null;

        try {
            handlerAdapter.handle(request, response, new HandlerMethod(studentController, "getAllCoursewares"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNull(mv);
        Assert.assertEquals(response.getStatus(), 200);
        //TODO 添加相应的逻辑测试
    }

    @Test
    public void testStartStudy() {
        request.setRequestURI("/train/student/startStudy");
        request.setParameter("courseId", "1");
        request.setParameter("coursewareId", "19");
        request.setMethod(HttpMethod.GET.name());
        ModelAndView mv = null;
        try {
            handlerAdapter.handle(request, response, new HandlerMethod(studentController, "startStudy", Integer.class, Integer.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNull(mv);
        Assert.assertEquals(response.getStatus(), 200);
        //TODO 添加相应的逻辑测试
    }

    @Test
    public void testCompleteStudy() {
        request.setRequestURI("/train/student/completeStudy");
        request.setParameter("courseId", "1");
        request.setParameter("coursewareId", "19");
        request.setMethod(HttpMethod.GET.name());
        ModelAndView mv = null;
        try {
            handlerAdapter.handle(request, response, new HandlerMethod(studentController, "completeStudy", Integer.class, Integer.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNull(mv);
        Assert.assertEquals(response.getStatus(), 200);
        //TODO 添加相应的逻辑测试
    }

    @Test
    public void testStudy(){
        request.setRequestURI("/train/student/study");
        request.setParameter("courseId", "1");
        request.setParameter("coursewareId", "19");
        request.setMethod(HttpMethod.GET.name());
        ModelAndView mv = null;
        try {
            handlerAdapter.handle(request, response, new HandlerMethod(studentController, "study", Integer.class, Integer.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String a = new String(response.getContentAsByteArray());
        Assert.assertNull(mv);
        Assert.assertEquals(response.getStatus(), 200);
        //TODO 添加相应的逻辑测试
    }

    @Test
    public void testStudyNext(){
        request.setRequestURI("/train/student/studyNext");
        request.setParameter("courseId", "1");
        request.setParameter("coursewareId", "19");
        request.setMethod(HttpMethod.GET.name());
        ModelAndView mv = null;
        try {
            handlerAdapter.handle(request, response, new HandlerMethod(studentController, "studyNext", Integer.class, Integer.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String a = new String(response.getContentAsByteArray());
        Assert.assertNull(mv);
        Assert.assertEquals(response.getStatus(), 200);
    }
    @After
    public void destroy() {

    }
}
