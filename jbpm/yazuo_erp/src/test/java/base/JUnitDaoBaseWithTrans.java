package base;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator.Feature;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * <b>Summary: </b>  Junit 基础类,加载环境 <b>Remarks: </b> TODO DAO测试基础类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager")
@ContextConfiguration(locations = { "classpath:applicationContext.xml" , "classpath:springMVC.xml"})
public class JUnitDaoBaseWithTrans extends AbstractTransactionalJUnit4SpringContextTests {

	public static final Log LOG = LogFactory.getLog(JUnitDaoBaseWithTrans.class);
	protected static final ObjectMapper mapper = new ObjectMapper();
	@Autowired
    public RequestMappingHandlerAdapter handlerAdapter;

    protected MockHttpServletRequest request;

    protected MockHttpServletResponse response;
    
	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response = new MockHttpServletResponse();
	}
	/**
	 * 返回json格式的对象
	 */
	public String getJsonString(Object object) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
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
	 * @Description 生成测试报告
	 * @param parameter
	 * @param method
	 * @param desc
	 * @throws
	 */
	public void genTestReport(Object parameter, String method, String desc){
		String host = "http://localhost:8080/yazuo_erp/";
		String convertParameter = null;
		String url = host+ method;
		desc = "接口描述： "+desc;
		try {
			convertParameter = "测试参数: "+ (parameter==null? "": mapper.writeValueAsString(parameter));
		} catch (JsonGenerationException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(desc);
		System.out.println("接口URL："+host+ method);
		this.printJson(convertParameter);
		File file = new File("D:\\workspaces\\YaZuo\\yazuo_erp_trunk\\src\\test\\java\\base\\interface.txt");
		Date date = new Date();
		CharSequence data = "\r\n\r\n"+date+"\r\n\r\n"+desc+"\r\n\r\n"+url+"\r\n\r\n"+convertParameter+"\r\n";
		try {
			FileUtils.write(file, data, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 输出json格式的对象, 用父类记录日志
	 */
	public void printJson(Object object) {
		try {
			LOG.info(object.getClass()+": \n"+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
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
	 * 字符串转VO对象
	 * @param <T>
	 * @return 
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public <T> Object readValueToVO(String input, @SuppressWarnings("rawtypes") Class<T> clazz) {
		Object object =null;
		try {
			this.mapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			mapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_COMMENTS, true);
			object = this.mapper.readValue(input.getBytes(), clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * <b>Summary: </b> 复写方法 setDataSource
	 * 
	 * @param dataSource
	 * @see org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests#setDataSource(javax.sql.DataSource)
	 */
	@Override
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
}