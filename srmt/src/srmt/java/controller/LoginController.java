package srmt.java.controller;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import srmt.java.common.RandomValidateCode;
import srmt.java.service.LoginSevrvice;

@Controller
public class LoginController {

	@Autowired
	private RandomValidateCode randomValidateCode;
	@Autowired
	private LoginSevrvice loginSevrvice;

	@RequestMapping("/enterLoginPage.do")
	public ModelAndView enterLoginPage() {

		return new ModelAndView("login");
	}

	@ResponseBody
	@RequestMapping(value = "/loginIn.do", method = RequestMethod.POST)
	public Map loginIn(HttpServletRequest request) {

		String msg = loginSevrvice.loginIn(request);
		Map map = new HashMap();
		map.put("msg", msg);
		return map;
	}

	@RequestMapping("/randValidateCode.do")
	public void createValidateCode(HttpServletRequest request, HttpServletResponse response) {
		randomValidateCode.getRandcode(request, response);
	}

	@RequestMapping("/enterMainPage.do")
	public String enterMainPage() {

		return "mainPage4Tec";
	}
}
