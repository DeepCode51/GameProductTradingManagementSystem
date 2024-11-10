package com.shine.game.bean;
/**
* @version
*/
public class PageBean {
	private int curPage;//当前页
	private int prePage;//上一页
	private int nextPage;//下一页
	private int maxSize;//每页最大数
	private int pageCount;//总页数
	private long readCount;//查询总记录数

	public PageBean() {
		super();
	}

	public PageBean(int curPage, int maxSize,long readCount) {
		super();
		this.curPage = curPage;
		this.maxSize = maxSize;
		this.readCount=readCount;
		updatePage();
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public long getReadCount() {
		return readCount;
	}

	public void setReadCount(long readCount) {
		this.readCount = readCount;
	}

	public void updatePage(){

		//总页数
		this.pageCount = (int) (this.readCount/this.maxSize+(this.readCount%this.maxSize==0?0:1));
		//上一页
		this.prePage=this.curPage>1?(this.curPage-1):1;
		//下一页
		this.nextPage=this.curPage>=this.pageCount?this.pageCount:(this.curPage+1);


	}

	@Override
	public String toString() {
		return "PageBean [curPage=" + curPage + ", prePage=" + prePage + ", nextPage=" + nextPage + ", maxSize="
				+ maxSize + ", pageCount=" + pageCount + ", readCount=" + readCount + "]";
	}


}
