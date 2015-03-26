package base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import junit.framework.Assert;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.train.JUnitBase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author congshuanglong
 */
@ContextConfiguration(locations = {"classpath:springMVC.xml"})
public class BaseControllerTest extends JUnitBase {
    @Autowired
    public RequestMappingHandlerAdapter handlerAdapter;

    protected MockHttpServletRequest request;

    protected MockHttpServletResponse response;
    
    private static final Log LOG = LogFactory.getLog(BaseControllerTest.class);
	protected static final ObjectMapper mapper = new ObjectMapper();
	/**
	 * 返回json格式的对象
	 */
	public String getJsonString(Object o) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 输出json格式的对象, 用父类记录日志
	 */
	public void printJson(Object o) {
		try {
			LOG.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
//			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 处理请求回应，输出回应信息
	 */
	public void afterHandle() throws UnsupportedEncodingException, IOException, JsonParseException, JsonMappingException {
		String result = response.getContentAsString();
		Assert.assertNotNull(result);
		JsonResult jsonResult = mapper.readValue(result.getBytes(), JsonResult.class);
		this.printJson(jsonResult);
		Assert.assertEquals(response.getStatus(), 200);
	}
}
