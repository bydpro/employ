package srmt.java.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import srmt.java.common.Constants;
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
	public String enterMainPage(HttpServletRequest request,Model model ) {
       HttpSession session = request.getSession();
       String userType =(String)session.getAttribute("userType");
       String msgView="mainPage4Tec";
       if(Constants.USER_TYPE_XT_ADMIN.equals(userType)){
    	   msgView="mainPage4Tec";
       }else if(Constants.USER_TYPE_ADMIN.equals(userType)){
    	   msgView="mainPage";
       }else {
    	   msgView="consumerPage";
       }
       model.addAttribute("username",(String)session.getAttribute("userName"));
		return msgView;
	}
	
	@ResponseBody
	@RequestMapping("/loginOut.do")
	public Map loginOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(Constants.USER_ID, null);
		session = null;
		Map map =new HashMap<>();
		map.put("msg", true);
		return map;
	}
}
