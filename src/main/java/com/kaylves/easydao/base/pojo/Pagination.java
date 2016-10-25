package com.kaylves.easydao.base.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright(c) 2013 hofort. All Rights Reserved.
 * Compiler: JDK1.6.0_23
 * @author Kaylves
 * @create_date 2014-6-15 下午07:49:30
 * @version 1.0
 * @update_user Kaylves
 * @update_date 2014-6-15 下午07:49:30
 * @description 此处写类的描述
 */
public class Pagination implements Serializable {
	
	public static final int DEAULT_ROWS		=20;
	
	public static final int DEFAULT_PAGE	=1;

	public List<?> getResultList() {
		return resultList;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}
	private static final long serialVersionUID = 1L;
	/**
	 * 当前页
	 */
	private int pageNumber;	
	
	/**
	 *  每页数
	 */
	private int pageSize;
	
	/**
	 * 反馈结果集
	 */
	private List<?> resultList;
	
	/**
	 * 总页数
	 */
	private int pageCount;
	
	/**
	 * 总数
	 */
	private int totalCount;
	
	public Pagination(){
		resultList = new ArrayList<Object>();
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		if(pageSize <= 0){
			return DEAULT_ROWS;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
