package com.castle.dysms;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;

public class DySmsSender {

	private IAcsClient acsClient;

	public DySmsSender(IAcsClient acsClient) {
		this.acsClient = acsClient;
	}

	//"18023458612", "移动城堡", "validate_code", "{\"number\":\"123342\"}", "yourOutId"
	public SendSmsResponse send(String phoneNumbers, String signName, String templateCode, String TemplateParam, String businessId)
			throws ServerException, ClientException {
		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(phoneNumbers);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(signName);
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(templateCode);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam(TemplateParam);

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId(businessId);

		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		return sendSmsResponse;
	}
}
