package com.tamguo.modules.sys.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName(value="sys_user_post")
public class SysUserPostEntity {

	private String userCode;
	private String postCode;
}
