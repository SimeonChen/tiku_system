package com.tamguo.modules.book.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.modules.book.model.enums.DocumentStatusEnum;
import lombok.Data;

@Data
@TableName(value="b_document")
public class DocumentEntity extends Model<DocumentEntity>{

	private static final long serialVersionUID = 1L;
	
	@TableId
	private String id;
	private String batchNo;
	private String parentId;
	private String bookId;
	private String owner;
	private String name;
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private DocumentStatusEnum status;
	@TableField(value="is_open")
	private String isOpen;
	private Date createDate;
	private Date updateDate;
	
	private String content;
	private String markdown;
	
	@TableField(exist=false)
	private Integer level;
	@TableField(exist=false)
	private String rootId;
	@TableField(exist=false)
	private boolean leaf;
	@TableField(exist=false)
	private List<DocumentEntity> children;
	@TableField(exist=false)
	private String cover; 
	
	@TableField(exist=false)
	private List<FileUploadEntity> fileUploads;

	@Override
	protected Serializable pkVal() {
		return getId();
	}
	
}
