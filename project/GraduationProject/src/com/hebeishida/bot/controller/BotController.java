/**
 * 
 */
package com.hebeishida.bot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hebeishida.bot.entity.Bot;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *@desc:聊天机器人
 *@author 邓旸
 *@date:2020年5月4日下午3:14:44
 */
@Controller
public class BotController {

	@RequestMapping(value="bot")
	public String bot(HttpServletRequest request) throws Exception {
		Bot bot = Bot.getInstance();
		request.setCharacterEncoding("UTF-8");
		
		String utterance = request.getParameter("message");
		List<String> replys = null;
		if(!utterance.equals(""))  replys = bot.start(utterance);
		
		JSONObject resp = new JSONObject();
		if(replys==null || replys.size()==0) {
			resp.put("code", 0);
			resp.put("message", JSONArray.fromObject(new ArrayList<String>()));
		}
		else {
			resp.put("code", 1);
			resp.put("message", JSONArray.fromObject(replys));
		}
		
		
		return resp.toString();
	}

}
