package com.tamguo.modules.member.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;
import lombok.Data;

@Data
@TableName(value="t_member")
public class MemberEntity extends SuperEntity<MemberEntity> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String nickName;
	private String password;
	private String avatar;
	private String mobile;
	private String email;
	private Integer point;
	private BigDecimal amount;
	private Date lastLoginTime;
	private Integer paperNum;
	private Integer questionNum;
	private Integer downNum;
	private Integer hitsNum;
	@TableField(exist=false)
	private String verifyCode;
	@TableField(exist=false)
	private String nowPassword;

}