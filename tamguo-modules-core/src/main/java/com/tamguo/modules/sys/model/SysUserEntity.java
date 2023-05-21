package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.tamguo.modules.sys.model.enums.SysUserMgrTypeEnum;
import com.tamguo.modules.sys.model.enums.SysUserStatusEnum;
import com.tamguo.modules.sys.model.enums.SysUserTypeEnum;
import lombok.Data;

@Data
@TableName(value="sys_user")
public class SysUserEntity extends Model<SysUserEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private String userCode;
	private String officeCode;
	private String officeName;
	private String companyCode;
	private String companyName;
	private String avatar;
	private String corpCode;
	private String corpName;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String createBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createDate;
	private String email;
	private String freezeCause;
	private Date freezeDate;
	private Date lastLoginDate;
	private String lastLoginIp;
	private String loginCode;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SysUserMgrTypeEnum mgrType;

	private String mobile;
	private String mobileImei;
	private String password;
	private String phone;
	private Date pwdQuestUpdateDate;
	private String pwdQuestion;
	@TableField(value="pwd_question_2")
	private String pwdQuestion2;
	@TableField(value="pwd_question_3")
	private String pwdQuestion3;
	private String pwdQuestionAnswer;
	@TableField(value="pwd_question_answer_2")
	private String pwdQuestionAnswer2;
	@TableField(value="pwd_question_answer_3")
	private String pwdQuestionAnswer3;
	private BigDecimal pwdSecurityLevel;
	private Date pwdUpdateDate;
	private String pwdUpdateRecord;
	private String refCode;
	private String refName;
	private String remarks;
	private String sex;
	private String sign;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SysUserStatusEnum status;
	
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
	private String userName;
	private String empName;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SysUserTypeEnum userType;
	
	private BigDecimal userWeight;
	private String wxOpenid;
	private String userNameEn;
	
	@TableField(exist=false)
	private List<String> employeePosts;
	@TableField(exist=false)
	private String userRoleString;
	@TableField(exist=false)
	private String userDataScopeListJson;


	@Override
	protected Serializable pkVal() {
		return getUserCode();
	}


}