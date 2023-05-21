package com.tamguo.modules.book.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName(value="b_file_entity")
public class FileEntity {

    @TableId("file_id")
	private String fileId;
    
	private String fileMd5;
	private String filePath;
	private String fileContentType;
	private String fileExtension;
	private Long fileSize;
}
