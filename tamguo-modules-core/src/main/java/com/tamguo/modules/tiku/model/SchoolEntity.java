package com.tamguo.modules.tiku.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;
import lombok.Data;


@Data
@TableName(value="t_school")
public class SchoolEntity extends SuperEntity<SchoolEntity> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String areaId;
	private String name;
	private String image;
	// 试卷
	@TableField(exist=false)
	private List<PaperEntity> paperList;

}