package com.tamguo.modules.book.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName(value="b_book_category")
public class BookCategoryEntity extends Model<BookCategoryEntity>{

	private static final long serialVersionUID = 1L;
	
	@TableId
	private String id;
	private String parentId;
	private String parentIds;
	private String treeSort;
	private String treeSorts;
	private String treeLeaf;
	private String treeLevel;
	private String treeNames;
	private String name;
	private String seoTitle;
	private String seoKeywords;
	private String seoDescription;
	private Date createDate;
	private Date updateDate;

	@Override
	protected Serializable pkVal() {
		return getId();
	}
	
}
