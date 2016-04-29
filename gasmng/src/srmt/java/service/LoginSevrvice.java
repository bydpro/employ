package srmt.java.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srmt.java.common.Constants;
import srmt.java.common.MyUtil;
import srmt.java.dao.LoginDao;

@Service
public class LoginSevrvice {
	@Autowired
	private LoginDao loginDao;

	public String loginIn(HttpServletRequest request) {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String validateCode = request.getParameter("validateCode");
		HttpSession session = request.getSession();
		String oldValidateCode = (String) session.getAttribute(Constants.VALIDATE_CODE_KEY);
		Date validateCodeTime = (Date) session.getAttribute(Constants.VALIDATE_CODE_TIME);
		Date nowDate = new Date();
		String msg = "";
		long s = (nowDate.getTime() - validateCodeTime.getTime()) / 1000;
		if (s > Constants.IS_VALID_TIME) {
			msg = "验证码已失效";
		} else {
			if (!validateCode.toUpperCase().equals(oldValidateCode.toUpperCase())) {
				msg = "验证码输入有误";
			} else {
				String sql = "select * from sys_user su where (su.login_Id=:loginId or su.email =:loginId or su.mobile =  :loginId or su.user_num= :loginId) and su.password=:password";
				msg = loginDao.loginIn(sql, loginId, MyUtil.getMd5(password),request);
			}
		}
		return msg;
	}

}
