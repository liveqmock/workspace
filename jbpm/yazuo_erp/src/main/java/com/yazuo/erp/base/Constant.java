/**
 * <p>Project: yazuo_erp</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-5-7
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.erp.base;


/**
 * @InterfaceName: Constant
 */
public interface Constant {
	
	/**
	 * 行分隔符
	 */
	public static String LINE_SEPARATOR = System.getProperty("line.separator");
	/**
	 * @Description email相关 常量信息
	 * @author song
	 * @date 2014-9-18 下午4:08:02
	 */
	public static interface Email{
		/*
		 * 邮件服务器地址
		 * note： 默认spring-context有相应的配置
		 */
		@Deprecated
		public static String host = "mail.yazuo.com";
		/*
		 * 收到邮件时显示的发件人地址
		 * com.sun.mail.smtp.SMTPSenderFailedException: 553 Mail from must equal authorized user
		 */
		public static String from = "yazuoservice@yazuo.com";/*
		 * 发件人地址
		 */
		public static String username = "yazuoservice@yazuo.com";
		/*
		 * 发件人密码
		 */
		public static String password = "123.yazuo";
	}
	/*
	 * 下拉列表框对应属性
	 */
	public static interface DropDownList{
		public static String value = "value";
		public static String text = "text";
		public static String isSelected = "isSelected";
		public static String children = "children";
		public static String type = "type";
	}
	/**
	 * 页号： 参数名称
	 */
	public static final String PAGE_NUMBER = "pageNumber";
	
	/**
	 * 每页显示记录数： 参数名称
	 */
	public static final String PAGE_SIZE = "pageSize";

	/**
	 * 默认每页显示记录数
	 */
	public static final String TIME_OUT = "timeout";
	/**
	 * 默认每页显示记录数
	 */
	public static final int pageSize = 10;
	/**
	 * 案例库模块标签分隔符
	 */
	public static final String label_sperater = "#";
	/**
	 * session中的用户对象对应的key
	 */
	public static final String SESSION_USER = "session_user";
	/**
	 * 登录达到{}次锁定当前登录用户
	 */
	public static final Integer TRY_LOGIN_TIMES = 3;
	/**
	 * 数据库表中字段is_enable对应的值 ：is enable 
	 */
	public static final String IS_ENABLE = "1";
	/**
	 * 数据库表中字段is_enable对应的值 ： is not enable 
	 */
	public static final String IS_NOT_ENABLE = "0";
	/**
	 * 资源表中对应的目录级别 
	 */
	public static final String LEVEL1 = "1";
	/**
	 * 资源表中对应的目录级别 
	 */
	public static final String LEVEL2 = "2";
	/**
	 * 资源表中对应的目录级别 
	 */
	public static final String LEVEL3 = "3";
	/**
	 * 重置的用户登录密码
	 */
	public static final String RESET_PWD = "123456";
	/**
	 * 分页： 总页数字符串
	 */
	public static final String TOTAL_SIZE = "totalSize";
	/**
	 * 分页： 记录数字符串
	 */
	public static final String ROWS = "rows";
	
	/**使用*/
	public static final String Enable = "1";
	
	/**删除状态*/
	public static final String NOT_Enable = "0";
	
	/**只管理组*/
	public static final String MANAGER_GROUP = "1";
	
	/**管理组下的某人即加入*/
	public static final String MANAGER_ADD_USER = "2";
	
	/**不管理组下的某人即减人*/
	public static final String MANAGER_MINUS__USER = "3";
	/**默认添加人的ID值*/
	public static final Integer DEFAULT_ADD_USER = 999999;
	

	/**
	 * 程序中的分隔符
	 */
	public static final String logic_sperater = "#";
	/**
	 * 数据字典表--选中 描述
	 */
	public static final String DIC_CHECKED_VALS= "checkedVals";
	/**
	 * 数据字典表--字典对象
	 */
	public static final String DIC_DIC_OBJECT= "dicObject";
	/**
	 * 数据字典表--类型-业态
	 */
	public static final String DIC_TYPE_CATE= "00000001";
	/**
	 * 数据字典表--类型-分类
	 */
	public static final String DIC_TYPE_CAT= "00000002";
	/**
	 * 数据字典表--类型-城市
	 */
	public static final String DIC_TYPE_CITY= "00000003";
	/**
	 * 数据字典表--类型-人均
	 */
	public static final String DIC_TYPE_AVGPRICE= "00000004";
	/**
	 * 数据字典表--类型-优惠
	 */
	public static final String DIC_TYPE_PROMOTE= "00000005";
	/**
	 * 数据字典表--类型-计划类型
	 */
	public static final String DIC_TYPE_PLAN_TYPE= "00000032";
	/**
	 * 数据字典表--类型-项目状态
	 */
	public static final String DIC_TYPE_PORJECT_STATE= "00000035";
	/**
	 * 数据字典表--类型-上线流程状态
	 */
	public static final String DIC_TYPE_PROCESS_STATE= "00000043";
	/**
	 * 数据字典表--类型-附件类型
	 */
	public static final String DIC_ATTACHMENT_TYPE= "00000044";
	/**
	 * 数据字典表--类型-配载品类
	 */
	public static final String DIC_CATEGORY= "00000045";
	/**
	 * 数据字典表--类型-填单类型
	 */
	public static final String DIC_SYS_DOCUMENT= "00000078";
	/**
	 * 数据字典表--类型-所属产品
	 */
	public static final String DIC_PRODUCT_TYPE= "00000097";
	/**
	 * 数据字典表--类型-推广平台
	 */
	public static final String DIC_PROMOTION_PLATFORM= "00000098";
	/**
	 * 参数名session id.
	 */
	public static final String SESSION_ID= "sessionId";
	/**
	 * 标识自己更改权限.
	 */
	public static final String SELF_ROLE_CHANGED= "selfRoleChanged";
	/**
	 * 项目根路径描述.
	 */
	public static final String BASEPATH= "basePath";
	/**
	 * 项目存放历史文件的路径.
	 */
	public static final String TEMP_FILE_PATH = "tempFilePath";
	/**
	 * 视频案例库合法用户标识.
	 */
	public static final String LEGAL_USER= "legalUser";
	/**
	 * 显示文字分隔符.
	 */
	public static final String WORD_SEPARATE= ",";
	/**
	 * mark not null.
	 */
	public static final String NOT_NULL= "t";
	/**
	 * 石山视频，所有视频分类信息
	 */
	public static final String smvpVideoCatInfo = "{\"token\":\"OgLeEZWvsFOCxI2hKfhcwSJ3o2JNCtuwUDX5F1yghpZVSeG-r8aeBZ39t6ZXGeeW\",\"category\":[{\"id\":\"618388394897518013\",\"name\":\"视频库\",},{\"id\":\"618388424962289683\",\"name\":\"课件\"}]}";
	/**
	 * 系统需要对外提供接口，对于在白名单数组中的接口链接，不做session拦截处理
	 */
	public static final String[] WRITE_LIST_NO_SESSION_CTRL = 
			new String[]{"superReport","product/updateMerProductsCache","external/fesPlan/saveFesPlan.do","external/fesPlan/updateFesPlan.do", "external/fesPlan/updateAbandonFesPlanById.do", "external/fesPlan/updateDelayFesPlanById.do",
		"fesOnlineProcess/afterDays", "user/getAllUsersByResourceCode", "synMerchant/saveSynMerchant", "mktTeam/list", 
		"sysProductOperation/listSysProductOperations",
		"sysUserRequirement/saveSysUserRequirement",
	    "external/webkit/getNewVersionWebKit",
		"synMerchant/getMerchantInfo",
         "besMonthlyApi"};



	/**
	 * 模块名称
    /**
     * 模块名称
	 */
	public static String _SYS = "sys";//系统管理
	public static String _TRA = "tra";//培训系统
	public static String _FES = "fes";//前端服务
	public static String _REQ = "req";//需求管理
}
