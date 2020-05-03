package com.hebeishida.pay.control;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hebeishida.common.AlipayConfig;
import com.hebeishida.common.OrderInfo;
import com.hebeishida.entity.Login;
import com.hebeishida.entity.Order_menu;
import com.hebeishida.entity.Order_record;
import com.hebeishida.pay.service.PayService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class PayControl {

	private OrderInfo orderInfo = new OrderInfo();

	@Resource
	private PayService payService;

	@RequestMapping(value = "payOder")
	public String payOder(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
		request.setCharacterEncoding("UTF-8");

		// 获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
				AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
				AlipayConfig.sign_type);

		// 设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String order_no = getOrderNo();
		// 付款金额，必填
		String total_amount = request.getParameter("WIDtotal_amount");
		// 订单名称，必填
		String order_type = request.getParameter("order_type");
		// 商品描述，可空
		String body = request.getParameter("WIDbody");
		// 获取支付方式
		String payWay = request.getParameter("payway");
		// 获取登录信息
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		// 操作数据库
		int mon = order_type.split(" ")[1].charAt(0) - '0';
		// 创建时间
		Date now = new Date();
		// 过期时间
		Calendar cl = Calendar.getInstance();
		cl.setTime(now);
		cl.add(Calendar.MONTH, mon);
		Date end = cl.getTime();
		Order_record or = new Order_record();
		or.setOrder_menu(payService.getMenuByType(order_type));
		or.setLogin(login);
		or.setCreate_time(now);
		or.setEnd_time(end);
		or.setStatus(1);
		or.setPay_way(payWay);
		or.setOrder_no(order_no);
		payService.insertOrder(or);

		// 若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		alipayRequest.setBizContent("{\"out_trade_no\":\"" + order_no + "\"," + "\"total_amount\":\"" + total_amount
				+ "\"," + "\"subject\":\"" + order_type + "\"," + "\"body\":\"" + body + "\","
				+ "\"timeout_express\":\"10m\"," + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		// 请求
		String result = alipayClient.pageExecute(alipayRequest).getBody();
		session.setAttribute("result", result);

		return "demo.jsp";
	}

	// 返回套餐详情
	@RequestMapping(value = "getMenu")
	public String getMenu(HttpServletRequest request) {
		List<Order_menu> menulist = payService.getOderMenu();
		// 测试
		Order_menu om = menulist.get(0);
		Login login = (Login) request.getAttribute("login");
		HttpSession session = request.getSession();
		session.setAttribute("menu", om);
		session.setAttribute("login", login);

		// 封装返回值
		JSONArray menuJson = new JSONArray();
		JSONObject res = new JSONObject();
		res.put("code", 1);
		for (Order_menu menu : menulist) {
			orderInfo.setOrderMenu(menu);
			menuJson.add(orderInfo.menuToJson());
		}
		res.put("menuInfo", menuJson);
		System.out.println("=========" + res.toString());
		return "pay.jsp";
	}

	// 返回订单列表
	@RequestMapping(value = "getOrderList")
	public String getOrderList(HttpServletRequest request) {
		// 封装返回值
		JSONArray orderJson = new JSONArray();
		JSONObject res = new JSONObject();
		res.put("code", 1);

		Login login = (Login) request.getAttribute("login");
		Set<Order_record> orderList = login.getOrder_record();
		for (Order_record order_record : orderList) {
			orderInfo.setOrderRecord(order_record);
			orderJson.add(orderInfo.orderToJson());
		}
		
		res.put("orderInfo", orderJson);
		System.out.println("=========" + res.toString());
		
		request.setAttribute("id", 1);
		return "forward:/getOrderDetail";
	}
	
	//获取订单详情
	@RequestMapping(value = "getOrderDetail")
	public String getOrderDetail(HttpServletRequest request) {
		int id=(int) request.getAttribute("id");
		Order_record or=payService.getOrderById(id);
		orderInfo.setOrderRecord(or);
		JSONObject res = new JSONObject();
		res.put("code", 1);
		res.put("orderDetail", orderInfo.orderDetailToJson());
		System.out.println("=========" + res.toString());
		return "";
	}

	// 生成订单号
	public String getOrderNo() {
		Calendar now = Calendar.getInstance();
		String sNow = "";
		sNow += now.get(Calendar.YEAR);
		sNow += (now.get(Calendar.MONTH) + 1);
		sNow += now.get(Calendar.DAY_OF_MONTH);
		sNow += now.get(Calendar.HOUR_OF_DAY);
		sNow += now.get(Calendar.MINUTE);
		sNow += now.get(Calendar.SECOND);
		sNow += now.get(Calendar.MILLISECOND);
		return sNow;
	}

}
