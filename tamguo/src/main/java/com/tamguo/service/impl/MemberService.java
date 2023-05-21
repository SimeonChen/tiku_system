package com.tamguo.service.impl;

import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.code.kaptcha.Constants;
import com.tamguo.config.redis.RedisXMLConfigure;
import com.tamguo.dao.MemberMapper;
import com.tamguo.dao.redis.CacheService;
import com.tamguo.model.MemberEntity;
import com.tamguo.service.IMemberService;
import com.tamguo.util.DateUtil;
import com.tamguo.util.Result;
import com.tamguo.util.ShiroUtils;
import com.tamguo.util.TamguoConstant;

@Service
public class MemberService extends ServiceImpl<MemberMapper, MemberEntity> implements IMemberService{
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private CacheService cacheService;

	@Override
	public Result login(String username, String password) {
		MemberEntity member = memberMapper.findByUsername(username);
		if(member == null){
			return Result.result(201, member, "用户名或密码有误，请重新输入或找回密码");
		}
		Integer loginFailureCount = this.getLoginFailureCount(member);		
		if(!new Sha256Hash(password).toHex().equals(member.getPassword())){
			loginFailureCount++;
			this.updateLoginFailureCount(member , loginFailureCount);
			return Result.result(202, member, "用户名或密码有误，请重新输入或找回密码");
		}
		this.updateLoginFailureCount(member , 0);
		return Result.result(200, member, "登录成功");
	}
	
	public void updateLoginFailureCount(MemberEntity member , Integer loginFailureCount){
		cacheService.setObject(TamguoConstant.LOGIN_FAILURE_COUNT + member.getUid(),  loginFailureCount , 2 * 60 * 60);
	}
	
	public Integer getLoginFailureCount(MemberEntity member){
		if(member == null){
			return 0;
		}
		if(!cacheService.isExist(TamguoConstant.LOGIN_FAILURE_COUNT + member.getUid())){
			return 0;
		}
		return (Integer)cacheService.getObject(TamguoConstant.LOGIN_FAILURE_COUNT + member.getUid());
	}

	@Override
	public Result checkUsername(String username) {
		MemberEntity member = memberMapper.findByUsername(username);
		if(member != null){
			return Result.result(201, null, "该用户名已经存在");
		}
		return Result.result(200, null, "该用户名可用");
	}

	@Override
	public Result checkMobile(String mobile) {
		MemberEntity member = memberMapper.findByMobile(mobile);
		if(member != null){
			return Result.result(201, null, "该手机号已经存在");
		}
		return Result.result(200, null, "该手机号可用");
	}

	@Transactional(readOnly=false)
	@Override
	public Result register(MemberEntity member) {
		MemberEntity m = memberMapper.findByUsername(member.getUsername());
		if(m != null){
			return Result.result(201, null, "该用户已经存在");
		}
		m = memberMapper.findByMobile(member.getMobile());
		if(m != null){
			return Result.result(202, null, "该手机号已经存在");
		}
		if(!cacheService.isExist(TamguoConstant.ALIYUN_MOBILE_SMS_PREFIX + member.getMobile())){
			return Result.result(203, null, "验证码错误");
		}
		String code = (String) cacheService.getObject(TamguoConstant.ALIYUN_MOBILE_SMS_PREFIX + member.getMobile());
		if(!code.equals(member.getVerifyCode())){
			return Result.result(204, null, "验证码错误");
		}
		MemberEntity entity = new MemberEntity();
		entity.setAvatar(TamguoConstant.DEFAULT_MEMBER_AVATAR);
		entity.setMobile(member.getMobile());
		entity.setPassword(new Sha256Hash(member.getPassword()).toHex());
		entity.setUsername(member.getUsername());
		entity.setNickName(member.getUsername());
		entity.setSubjectId(member.getSubjectId());
		entity.setCourseId(member.getCourseId());
		entity.setEmail(member.getEmail());
		memberMapper.insert(entity);
		return Result.result(200, entity, "注册成功");
	}

	@Override
	public Result checkAccount(String account) {
		if(StringUtils.isEmpty(account)){
			return Result.result(201, null, "帐号不存在！");
		}
		MemberEntity member = memberMapper.findByUsernameOrEmailOrMobile(account);
		if(member == null){
			return Result.result(201, null, "帐号不存在！");
		}
		return Result.result(200, null, "该帐号存在");
	}

	@Override
	public Result confirmAccount(String account, String veritycode) {
		if(StringUtils.isEmpty(account)){
			return Result.result(201, null, "帐号不存在！");
		}
		MemberEntity member = memberMapper.findByUsernameOrEmailOrMobile(account);
		if(member == null){
			return Result.result(201, null, "帐号不存在！");
		}
		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if (!veritycode.equalsIgnoreCase(kaptcha)) {
			return Result.result(202, null, "验证码错误");
		}
		return Result.result(200, member, "该帐号存在");
	}

	@Override
	public Result securityCheck(String username , String isEmail , String vcode) {
		MemberEntity member = memberMapper.findByUsername(username);
		if("1".equals(isEmail)){
			if(!cacheService.isExist(TamguoConstant.ALIYUN_MAIL_FIND_PASSWORD_PREFIX + member.getEmail())){
				return Result.result(201, member, "验证码错误");
			}
			String code = (String) cacheService.getObject(TamguoConstant.ALIYUN_MAIL_FIND_PASSWORD_PREFIX + member.getEmail());
			if(!code.equals(vcode)){
				return Result.result(202, member, "验证码错误");
			}
		}else{
			if(!cacheService.isExist(TamguoConstant.ALIYUN_MOBILE_SMS_PREFIX + member.getMobile())){
				return Result.result(203, member, "验证码错误");
			}
			String code = (String) cacheService.getObject(TamguoConstant.ALIYUN_MOBILE_SMS_PREFIX + member.getMobile());
			if(!code.equals(vcode)){
				return Result.result(204, member, "验证码错误");
			}
		}
		String key = UUID.randomUUID().toString();
		cacheService.setObject(TamguoConstant.SECURITY_CHECK_PREFIX + key,  username , 2 * 60 * 60);
		return Result.result(200, key, "安全验证通过");
	}

	@Override
	public Result resetPassword(String resetPasswordKey , String username , String password, String verifypwd) {
		if(cacheService.isExist(TamguoConstant.SECURITY_CHECK_PREFIX + resetPasswordKey)){
			MemberEntity member = memberMapper.findByUsername(username);
			if(password.equals(verifypwd)){
				member.setPassword(new Sha256Hash(password).toHex());
				memberMapper.updateById(member);
			}
		}
		return Result.result(200, null, "更新成功");
	}

	@Transactional(readOnly=false)
	@Override
	public void updateMember(MemberEntity member) {
		MemberEntity entity = memberMapper.selectById(ShiroUtils.getUserId());
		entity.setAvatar(member.getAvatar());
		entity.setEmail(member.getEmail());
		entity.setMobile(member.getMobile());
		entity.setCourseId(member.getCourseId());
		entity.setSubjectId(member.getSubjectId());
		entity.setNickName(member.getNickName());
		
		memberMapper.updateById(entity);
	}

	@Transactional(readOnly=true)
	@Override
	public MemberEntity findByUid(String uid) {
		return memberMapper.selectById(uid);
	}

	@Transactional(readOnly=true)
	@Override
	public MemberEntity findByUsername(String username) {
		return memberMapper.findByUsername(username);
	}

	@Transactional(readOnly=false)
	@Override
	public void updateLastLoginTime(String uid) {
		MemberEntity member = memberMapper.selectById(uid);
		member.setLastLoginTime(DateUtil.getTime());
		memberMapper.updateById(member);
	}

	@Override
	public MemberEntity findCurrMember() {
		MemberEntity member = memberMapper.selectById(ShiroUtils.getUserId());
		member.setPassword(null);
		return member;
	}
	
	@Transactional(readOnly=false)
	@Override
	public Result updatePwd(MemberEntity member) {
		MemberEntity entity = memberMapper.selectById(ShiroUtils.getUserId());
		if(!entity.getPassword().equals(new Sha256Hash(member.getPassword()).toHex())) {
			return Result.result(501, null, "旧密码错误！");
		}
		if(!cacheService.isExist(TamguoConstant.ALIYUN_MOBILE_SMS_PREFIX + member.getMobile())){
			return Result.result(502, null, "验证码错误");
		}
		entity.setPassword(new Sha256Hash(member.getNowPassword()).toHex());
		return Result.result(0, null, "修改成功");
	}
	
}
