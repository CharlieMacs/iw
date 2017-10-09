package com.xnx3.j2ee.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pingplusplus.model.Charge;
import com.xnx3.j2ee.module.pingxx.PingxxUtil;
import com.xnx3.j2ee.module.pingxx.bean.Event;
import com.xnx3.j2ee.module.pingxx.bean.SmallCharge;
import com.xnx3.j2ee.util.IpUtil;

/**
 * ping++支付 Demo
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/pingxx")
public class PingxxController_ extends BaseController {
	
	/**
	 * 
	 * @return View
	 * @throws IOException 
	 */
	@RequestMapping("/Webhooks")
	@ResponseBody
	public String Webhooks(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Event event = PingxxUtil.webhooks(request, response);
		if(event!=null){
			if(event.getType().equals(Event.TYPE_CHARGE_SUCCEEDED)){
				System.out.println("支付成功的回调，支付成功的订单号："+event.getSmallCharge().getOrderNo());
				
				/**
				 * 支付订单成功逻辑处理
				 */
				
			}else if (event.getType().equals(Event.TYPE_REFUND_SUCCEEDED)) {
				System.out.println("退款成功的回调，退款成功的订单号："+event.getSmallCharge().getOrderNo());
				
				/**
				 * 退单成功逻辑处理
				 */
				
			}else{
				response.setStatus(500);
			}
		}else{
			response.setStatus(500);
		}
		
		return "ping++ webhooks";
	}
	
	/**
	 * 支付（这里是针对APP做的，其他的应该用也没问题），返回Charge数据
	 */
	@RequestMapping("/charge")
	@ResponseBody
	public Charge charge(HttpServletRequest request){
		Charge charge = PingxxUtil.createCharge(100, "这是标题", "这是内容", "dingdanhao", SmallCharge.CHANNEL_ALIPAY, IpUtil.getIpAddress(request));
		return charge;
	}
}
