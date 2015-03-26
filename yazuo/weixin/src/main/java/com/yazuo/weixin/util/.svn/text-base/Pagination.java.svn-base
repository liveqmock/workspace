package com.yazuo.weixin.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * 分页函数
 */
public class Pagination<T> {
	private static Log logger = LogFactory.getLog(Pagination.class);
	public static final int NUMBERS_PER_PAGE = 10;

	/** 一页显示的记录数 */
	private int numPerPage = 0;
	/** 记录总数 */
	private int totalRows = 0;
	/** 总页数 */
	private int totalPages = 0;
	/** 当前页码 */
	private int currentPage = 1;
	/** 起始行数 */
	private int startIndex = 0;
	/** 结束行数 */
	private int lastIndex = 0;
	/** 指定类型结果列表 */
	private List<T> resultList = null;
	/** 未指定类型结果列表 */
	private List<Map<String, Object>> resultMapList = null;
	private Map<String, Object> resultMap = null;
	/** JdbcTemplate jTemplate */
	private JdbcTemplate jdbcTemplate = null;
	/** RowMapper */
	private RowMapper<T> rowMapper = null;
	/** SQL 绑定参数 */
	private Object[] objs = null;
	/**
	 * 缺省构造函数
	 */
	public Pagination() {
	}

	
	
	public Pagination(String from, int currentPage, int numPerPage, JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper,  Object ...objs){
		// 设置每页显示记录数
		setNumPerPage(numPerPage);
		// 设置要显示的页数
		setCurrentPage(currentPage);
		//	给JdbcTemplate赋值
		this.jdbcTemplate = jdbcTemplate;
		this.objs = objs;
		this.setRowMapper(rowMapper);
		this.query(from);
	}
	
	/**
	 * 分页构造函数 
	 * @param sql 根据传入的sql语句得到一些基本分页信息
	 * @param currentPage 当前页
	 * @param numPerPage 每页记录数
	 * @param jdbcTemplate JdbcTemplate实例
	 * @param objs sql参数绑定
	 */
	public Pagination(String sql, int currentPage, int numPerPage, JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper) {
		// 设置每页显示记录数
		setNumPerPage(numPerPage);
		// 设置要显示的页数
		setCurrentPage(currentPage);
		//	给JdbcTemplate赋值
		this.jdbcTemplate = jdbcTemplate;
		this.setRowMapper(rowMapper);
		this.query(sql);
	}

	/**
	 * 初始化 
	 * @param sql 根据传入的sql语句得到一些基本分页信息
	 * @param currentPage 当前页
	 * @param numPerPage 每页记录数
	 * @param jdbcTemplate JdbcTemplate实例
	 */
	private void query(String fromSql) {
		String totalSQL =  "select count(1) from ( "+fromSql+") t" ;
		// 总记录数
		try{
			if(objs == null){
				setTotalRows(jdbcTemplate.queryForInt(totalSQL));
			}else{
				setTotalRows(jdbcTemplate.queryForInt(totalSQL,objs));
			}
			
		}catch(Exception e){
			logger.error(e.getMessage()+"总数记录异常:"+ totalSQL.toString());
		}
		// 计算总页数
		setTotalPages();

		// 装入结果集List
		
		String paginationQuerySQL = fromSql + " limit "+numPerPage+" offset "+(currentPage-1)*numPerPage; // paginationSQL.toString();
		try{
			if(this.rowMapper != null){
				if(this.objs == null){
					setResultMap(this.jdbcTemplate.query(paginationQuerySQL, this.rowMapper));
				}else{
					setResultMap(this.jdbcTemplate.query(paginationQuerySQL, this.rowMapper, objs));
				}
				setResultList((List<T>)resultMap.get("Rows"));
			}else{
				if(this.objs == null){
					setResultMapList(this.jdbcTemplate.queryForList(paginationQuerySQL));
				}else{
					setResultMapList(this.jdbcTemplate.queryForList(paginationQuerySQL, objs));
				}
				setResultMapList((List<Map<String, Object>>)resultMap.get("Rows"));
			}
		}catch(Exception e){
			logger.error(e.getMessage()+"获取列表数据异常:"+ paginationQuerySQL.toString());
		}
	}

	/**
	 * 获取分页信息
	 * @return
	 */
	public PageInfo getPageInfo(){
		if(this.rowMapper != null){
			return new PageInfo(this.getTotalPages(),
					currentPage, this.getTotalRows(),
					this.getResultList() != null ? this.getResultList().size() : 0,
					this.getNumPerPage());
		}else{
			return new PageInfo(this.getTotalPages(),
					currentPage, this.getTotalRows(),
					this.getResultMapList() != null ? this.getResultMapList().size() : 0,
					this.getNumPerPage());
		}
	}

	private void setRowMapper(RowMapper<T> rowMapper) {
		this.rowMapper = rowMapper;
	}

	public RowMapper<T> getRowMapper() {
		return rowMapper;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public List<T> getResultList() {
		return this.resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public void setResultMap(List<T> list) {
		if (resultMap==null)
			resultMap = new HashMap<String, Object>();
		resultMap.put("Rows", list);
		resultMap.put("page", this.currentPage);
		resultMap.put("records", this.totalRows);
		resultMap.put("Total", this.totalRows);
	}
	public Map<String, Object> getResultMap() {
		return this.resultMap ;
	}
	public List<Map<String, Object>> getResultMapList() {
		return this.resultMapList;
	}
	public void setResultMapList(List<Map<String, Object>> resultMapList) {
		this.resultMapList = resultMapList;
	}

	public int getTotalPages() {
		return this.totalPages;
	}

	// 计算总页数
	public void setTotalPages() {
		if (totalRows % numPerPage == 0) {
			this.totalPages = totalRows / numPerPage;
		} else {
			this.totalPages = (totalRows / numPerPage) + 1;
		}
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex() {
		this.startIndex = (currentPage - 1) * numPerPage;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// 计算结束时候的索引
	public void setLastIndex() {
		if (totalRows < numPerPage) {
			this.lastIndex = totalRows;
		} else if ((totalRows % numPerPage == 0)
				|| (totalRows % numPerPage != 0 && currentPage < totalPages)) {
			this.lastIndex = currentPage * numPerPage;
		} else if (totalRows % numPerPage != 0 && currentPage == totalPages) {// 最后一页
			this.lastIndex = totalRows;
		}
	}

	public Object[] getObjs() {
		return objs;
	}

	public void setObjs(Object[] objs) {
		this.objs = objs;
	}

}
