package com.tamguo.modules.sys.model;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;
import com.tamguo.modules.sys.model.enums.TeacherStatus;
import lombok.Data;

@Data
@TableName(value="t_teacher")
public class TeacherEntity extends SuperEntity<TeacherEntity> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String mobile;
	private String email;
	private String name;
	private String motto;
	private String provinceId;
	private String cityId;
	private String subjectId;
	private String courseId;
	private String cardId;
	private String cardPhoto;
	private String occupationPapers;
	private Long createTime;
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private TeacherStatus status;
	private String qq;

}