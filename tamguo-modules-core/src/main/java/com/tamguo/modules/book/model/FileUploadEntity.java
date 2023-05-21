package com.tamguo.modules.book.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.modules.book.model.enums.BizTypeEnum;
import com.tamguo.modules.book.model.enums.FileUploadStatusEnum;
import lombok.Data;

@Data
@TableName(value="b_file_upload")
public class FileUploadEntity extends Model<FileUploadEntity>{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String fileId;
	private String fileName;
	private String fileType;
	private String bizKey;	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private BizTypeEnum bizType;
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private FileUploadStatusEnum status;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private String remarks;
	@TableField(exist=false)
	private String filePath;
	@TableField(exist=false)
	private Long fileSize;
	@Override
	protected Serializable pkVal() {
		return getId();
	}
	
}
