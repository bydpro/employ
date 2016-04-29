package srmt.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import srmt.java.service.UserSevrvice;

@Controller
@RequestMapping("/userMng")
public class UserController {

	@Autowired
	private UserSevrvice userSevrvice;

	@RequestMapping("/enterUserMng.do")
	public ModelAndView enterUserMng() {

		return new ModelAndView("userMng/userMng");
	}

	@ResponseBody
	@RequestMapping("/queryUserList.do")
	public List<Map> queryUserList(HttpServletRequest request) {
		List<Map> list = userSevrvice.queryUserList(request);
		return list;
	}

	@RequestMapping("/enterAddUser.do")
	public ModelAndView enterAddUser() {

		return new ModelAndView("userMng/editMng");
	}

	@ResponseBody
	@RequestMapping("/delUser.do")
	public Map delUser(HttpServletRequest request) {
		userSevrvice.delUser(request);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/saveUser.do")
	public Map saveUser(HttpServletRequest request) {
		Map map = userSevrvice.saveUser(request);
		return map;
	}

	@ResponseBody
	@RequestMapping("/getUserInfo.do")
	public Map getUserInfo(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		return userSevrvice.getUserInfo(userId);
	}

	@ResponseBody
	@RequestMapping("/getMyUserInfo.do")
	public Map getMyUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		return userSevrvice.getUserInfo(userId);
	}

	@ResponseBody
	@RequestMapping("/unLayoutUser.do")
	public Map unLayoutUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		userSevrvice.unLayoutUser(userId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/layoutUser.do")
	public Map layoutUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		userSevrvice.layoutUser(userId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@RequestMapping("/enterMyUser.do")
	public ModelAndView enterMyUser() {

		return new ModelAndView("userMng/myUserInfo");
	}

	@ResponseBody
	@RequestMapping("/savePassword.do")
	public Map savePassword(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String newPassword = request.getParameter("newPassword");
		String password = request.getParameter("oldPassword");
		Map map = userSevrvice.savePassword(password, userId, newPassword);
		return map;
	}

	@ResponseBody
	@RequestMapping("/isXTAdmin.do")
	public Map isXTAdmin(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		userSevrvice.isXTAdmin(userId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/isAdmin.do")
	public Map isAdmin(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		userSevrvice.isAdmin(userId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/isNormalUser.do")
	public Map isNotXTAdmin(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		userSevrvice.isNormalUser(userId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/getMyMoney.do")
	public Map getMyMoney(HttpServletRequest request) {
		Map map = userSevrvice.getMyMoney(request);
		return map;
	}

	@ResponseBody
	@RequestMapping("/reMoney.do")
	public Map reMoney(HttpServletRequest request) {
		double money = userSevrvice.reMoney(request);
		Map map = new HashMap();
		map.put("money", money);
		return map;
	}
}
