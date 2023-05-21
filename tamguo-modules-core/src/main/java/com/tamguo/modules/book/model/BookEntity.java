package com.tamguo.modules.book.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName(value="b_book")
public class BookEntity extends Model<BookEntity>{

	private static final long serialVersionUID = 1L;
	
	@TableId
	private String id;
	private String categoryId;
	private String bookImage;
	private String owner;
	private String name;
	private String seoTitle;
	private String seoKeywords;
	private String seoDescription;
	private Date createDate;
	private Date updateDate;
	
	@TableField(exist=false)
	private List<String> categoryIds;
	@TableField(exist=false)
	private String memberName;
	@TableField(exist=false)
	private String categoryName;

	@Override
	protected Serializable pkVal() {
		return getId();
	}
}
