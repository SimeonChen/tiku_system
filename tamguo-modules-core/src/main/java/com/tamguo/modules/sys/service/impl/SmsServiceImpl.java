package com.tamguo.modules.sys.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.tamguo.common.utils.Result;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.config.redis.CacheService;
import com.tamguo.modules.sys.service.ISmsService;

@Service
public class SmsServiceImpl implements ISmsService{
	
	@Autowired
	private CacheService cacheService;

	@Override
	public Result sendFindPasswordSms(String mobile) throws ClientException {
		//可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", SystemConstant.ALIYUN_ACCESS_KEY_ID, SystemConstant.ALIYUN_ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("探果网");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_127152965");
        Integer vcode = (int) ((Math.random()*9+1)*100000);  
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\""+vcode+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse response = acsClient.getAcsResponse(request);
        if("OK".equals(response.getCode())) {
        	cacheService.setObject(SystemConstant.ALIYUN_MOBILE_SMS_PREFIX + mobile , vcode.toString() , 10 * 60);
            return Result.result(200, null, "发送成功");
        }
        return Result.result(501, null, response.getMessage());
	}

	@Override
	public Result sendRewardSms(String mobile , String name ,String bookName , Integer point, BigDecimal money) throws ClientException {
		//可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", SystemConstant.ALIYUN_ACCESS_KEY_ID, SystemConstant.ALIYUN_ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("探果网");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_152285431");
        Integer vcode = (int) ((Math.random()*9+1)*100000);  
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"name\":\""+name+"\",\"bookName\":\""+bookName+"\",\"point\":\""+point+"\",\"money\":\""+money+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse response = acsClient.getAcsResponse(request);
        if("OK".equals(response.getCode())) {
        	cacheService.setObject(SystemConstant.ALIYUN_MOBILE_SMS_PREFIX + mobile , vcode.toString() , 10 * 60);
            return Result.result(200, null, "发送成功");
        }
        return Result.result(501, null, response.getMessage());
	}

}
