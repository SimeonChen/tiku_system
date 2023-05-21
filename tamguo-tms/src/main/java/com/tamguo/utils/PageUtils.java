package com.tamguo.utils;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;

public class PageUtils {
    
	// 是否下一页按钮
	private Boolean isShowNextBtn = false;
	
	// 是否上一页按钮
	private Boolean isShowPreBtn = false;
	
	// 当前页
	private Integer currPageNum;
	
	// 页码列表
	private List<String> pageNums;
	
	// 总页数
	private Integer totalPage;
	
	// 总数量
	private Integer total;
	
	// 数据
	private List<?> list;
	
	public static PageUtils getPage(Page<?> page){
		PageUtils pg = new PageUtils();
		if(page.getCurrent() > 1){
			pg.setIsShowPreBtn(true);
		}
		if(page.getCurrent() < page.getPages()){
			pg.setIsShowNextBtn(true);
		}
		List<String> pgNums = new ArrayList<>();
		if(page.getPages() > 1){
			if(page.getPages() > 10){
				pgNums.add("1");
				pgNums.add("2");
				pgNums.add("3");
				pgNums.add("...");
				if(page.getCurrent() == page.getPages()){
					pgNums.add(((Integer)(page.getCurrent() - 2)).toString());
					pgNums.add(((Integer)(page.getCurrent() - 1)).toString());
					pgNums.add(((Integer)page.getCurrent()).toString());
				} else {
					pgNums.add(((Integer)(page.getCurrent() - 1)).toString());
					pgNums.add(((Integer)(page.getCurrent())).toString());
					pgNums.add(((Integer)(page.getCurrent() + 1)).toString());
				}
			}else{
				Integer n = 1;
				if(page.getTotal() > 0){
					while(true){
						pgNums.add(n.toString());
						if(n  >= page.getPages()){
							break;
						}
						n ++;
					}
				}
			}
		} else {
			Integer n = 1;
			if(page.getTotal() > 0){
				while(true){
					pgNums.add(n.toString());
					if(n  >= page.getPages()){
						break;
					}
					n ++;
				}
			}
		}
		pg.setPageNums(pgNums);
		pg.setList(page.getRecords());
		pg.setCurrPageNum(((Integer)page.getCurrent()));
		pg.setTotal(((Integer)page.getTotal()));
		pg.setTotalPage(((Integer)page.getPages()));
		return pg;
	}


	public Boolean getIsShowNextBtn() {
		return isShowNextBtn;
	}

	public void setIsShowNextBtn(Boolean isShowNextBtn) {
		this.isShowNextBtn = isShowNextBtn;
	}

	public List<String> getPageNums() {
		return pageNums;
	}

	public void setPageNums(List<String> pageNums) {
		this.pageNums = pageNums;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}


	public Boolean getIsShowPreBtn() {
		return isShowPreBtn;
	}


	public void setIsShowPreBtn(Boolean isShowPreBtn) {
		this.isShowPreBtn = isShowPreBtn;
	}


	public Integer getCurrPageNum() {
		return currPageNum;
	}


	public void setCurrPageNum(Integer currPageNum) {
		this.currPageNum = currPageNum;
	}


	public Integer getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}


	public Integer getTotal() {
		return total;
	}


	public void setTotal(Integer total) {
		this.total = total;
	}
}