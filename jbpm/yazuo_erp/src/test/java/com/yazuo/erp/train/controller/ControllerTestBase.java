package com.yazuo.erp.train.controller;

import com.yazuo.erp.train.JUnitBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author congshuanglong
 */
@ContextConfiguration(locations = {"classpath:springMVC.xml"})
public class ControllerTestBase extends JUnitBase {
    @Autowired
    public RequestMappingHandlerAdapter handlerAdapter;

    protected MockHttpServletRequest request;

    protected MockHttpServletResponse response;
}
