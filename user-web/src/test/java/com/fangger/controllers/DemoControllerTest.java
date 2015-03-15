package com.fangger.controllers;

import com.fangger.controller.user.DemoController;
import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DemoControllerTest extends TestCase {
	@Autowired  
    private DemoController controller;
  
    //这种方法适用于Springframework3.0，3.1换了handler的处理类。  
    @Autowired  
    private AnnotationMethodHandlerAdapter handlerAdapter;  
  
    private final MockHttpServletRequest request = new MockHttpServletRequest();  
    private final MockHttpServletResponse response = new MockHttpServletResponse();  
	protected static void setUpBeforeClass() throws Exception {
	}

	protected static void tearDownAfterClass() throws Exception {
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testHelloWorld() {
		request.setRequestURI("/");
		request.setMethod(HttpMethod.POST.name());
		ModelAndView mav;
		try {
			mav = handlerAdapter.handle(request, response, controller);
			assertEquals("", mav.getViewName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
         
	}

}
