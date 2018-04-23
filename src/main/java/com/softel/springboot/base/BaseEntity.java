package com.softel.springboot.base;

import java.io.Serializable;
import lombok.Data;

@Data
public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 分页页码，默认页码为1
	 */
	protected int page = 1;
	
	/**
	 * 分页每页数量，默认20
	 */
	protected int size = 20;
	
	/**
	 * 排序名称，默认为id
	 */
	protected String sidx = "id";
	
	/**
	 * 排序方式，默认asc
	 */
	protected String sord = "asc";

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

}
