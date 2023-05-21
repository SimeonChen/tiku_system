package com.tamguo.modules.tiku.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;
import lombok.Data;

import java.util.List;


/**
 * The persistent class for the t_subject database table.
 * 
 */
@Data
@TableName(value="t_menu")
public class MenuEntity extends SuperEntity<MenuEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String pinyin;
	private String parentId;
	private String isShow;
	private Integer orders;
	private String url;
	@TableField(value="reserve_1")
	private String reserve1;
	
	// 子类型
	@TableField(exist=false)
	private List<MenuEntity> childSubjects;

}