package com.jcwx.utils;

import java.util.List;

/**
* @author MaBo
* 2016年11月3日
* 分页
*/
public class Pagenate<T> {
	private int pageNum;	// 当前页码
	private int pageSize;	// 页面大小（每页显示多少条）
	private int rsCnts;		// 数据总条数
	private int pgCnts;		// 总页数
	private int nextPage;	// 下一页
	private int prePage;	// 上一页
	private int firstPage;	// 首页
	private int lastPage;	// 尾页
	private List<T> list;	// 数据结果集
	private List<Integer> pageNos;	// 页码
	
	public int getPageNum() {
		return pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getRsCnts() {
		return rsCnts;
	}
	public int getPgCnts() {
		return pgCnts;
	}
	public int getNextPage() {
		return nextPage;
	}
	public int getPrePage() {
		return prePage;
	}
	public int getFirstPage() {
		return firstPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setRsCnts(int rsCnts) {
		this.rsCnts = rsCnts;
	}
	public void setPgCnts(int pgCnts) {
		this.pgCnts = pgCnts;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public List<Integer> getPageNos() {
		return pageNos;
	}
	public void setPageNos(List<Integer> pageNos) {
		this.pageNos = pageNos;
	}
	
}
