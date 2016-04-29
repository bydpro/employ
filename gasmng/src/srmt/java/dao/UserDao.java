package srmt.java.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import srmt.java.common.Constants;
import srmt.java.common.MyUtil;
import srmt.java.entity.SysUser;

@Repository
@Transactional
public class UserDao {
	@Resource
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<Map> queryUserList(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String organId = request.getParameter("organId");
		String userNum = request.getParameter("userNum");
		String isValid = request.getParameter("isValid");
		String isAdmin = request.getParameter("isAdmin");

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT                                               									");
		sb.append(" 		SU.USERNAME,                                    							    ");
		sb.append(" 		SU.USER_ID     USERID,                          						    ");
		sb.append(" 		SU.SEX,                                                 							");
		sb.append(" 		SU.EMAIL,                                                                         ");
		sb.append(" 		SU.MOBILE,                                                                      ");
		sb.append(" 		SU.USER_NUM USERNUM,                                               ");
		sb.append(" 		SO.ORGAN_NAME ORGANNAME,                                     ");
		sb.append(" 		SU.IS_VALID       ISVALID,                                                ");
		sb.append(" 		SU.is_admin       ISADMIN,                                                ");
		sb.append(" 		SU.user_type       USERTYPE,                                              ");
		sb.append("     SU.ADRESS,                                                                       ");
		sb.append("     DATE_FORMAT(SU.BIRTHDAY,'%Y-%m-%d')   BIRTHDAY           ");
		sb.append("	 FROM                                                                                 ");
		sb.append(" 		SYS_USER SU                                                                    ");
		sb.append(" 	LEFT JOIN SYS_ORGAN SO ON SU.ORGAN_ID = SO.ORGAN_ID  where 1=1 ");
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" and su.USERNAME like :userName   ");
		}
		if (StringUtils.isNotEmpty(email)) {
			sb.append(" and su.EMAIL like :email   ");
		}
		if (StringUtils.isNotEmpty(mobile)) {
			sb.append(" and su.mobile = :mobile   ");
		}
		if (StringUtils.isNotEmpty(organId)) {
			sb.append(" and su.ORGAN_ID = :organId   ");
		}
		if (StringUtils.isNotEmpty(isValid)) {
			if (isValid.equals(Constants.YES)) {
				sb.append("  and su.IS_VALID = :isValid       ");
			} else {
				isValid = Constants.YES;
				sb.append("  and su.IS_VALID != :isValid or su.IS_VALID is null     ");
			}
		}
		if (StringUtils.isNotEmpty(isAdmin)) {
			if (isAdmin.equals(Constants.YES)) {
				sb.append("  and su.is_admin = :isAdmin       ");
			} else {
				isAdmin = Constants.YES;
				sb.append("  and su.is_admin != :isAdmin or su.is_admin is null     ");
			}
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" and su.USER_NUM = :userNum   ");
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		if (StringUtils.isNotEmpty(userName)) {
			userName = "%" + userName + "%";
			query.setParameter("userName", userName);
		}
		if (StringUtils.isNotEmpty(email)) {
			email = "%" + email + "%";
			query.setParameter("email", email);
		}
		if (StringUtils.isNotEmpty(mobile)) {
			query.setParameter("mobile", mobile);
		}
		if (StringUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}
		if (StringUtils.isNotEmpty(isAdmin)) {
			query.setParameter("isAdmin", isAdmin);
		}
		if (StringUtils.isNotEmpty(isValid)) {
			query.setParameter("isValid", isValid);
		}
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		return queryList;
	}

	public void delUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		Transaction transaction = getSession().beginTransaction();
		SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
		getSession().delete(sysUser);
		transaction.commit();
	}

	public Map saveUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String organId = request.getParameter("organId");
		String birhtdayStr = request.getParameter("birhtday");
		String address = request.getParameter("address");
		String isAdmin = request.getParameter("isAdmin");

		Map result = new HashMap();
		if (isExitEmail(email, userId)) {
			result.put("errorMsg", true);
			result.put("msg", "电子邮箱不能重复");
			return result;
		}
		if (isExitMobile(mobile, userId)) {
			result.put("errorMsg", true);
			result.put("msg", "移动电话不能重复");
			return result;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = null;
		try {
			if (StringUtils.isNotEmpty(birhtdayStr)) {
				birthday = sdf.parse(birhtdayStr);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setUsername(userName);
			sysUser.setSex(sex);
			sysUser.setEmail(email);
			sysUser.setMobile(mobile);
			sysUser.setOrganId(organId);
			sysUser.setAdress(address);
			sysUser.setIsAdmin(isAdmin);
			if (Constants.YES.equals(isAdmin)) {
				sysUser.setUserType(Constants.USER_TYPE_ADMIN);
			} else {
				sysUser.setUserType(Constants.USER_TYPE_NORMAL);
			}
			sysUser.setBirthday(birthday);
			getSession().update(sysUser);
		} else {
			SysUser sysUser = new SysUser();
			sysUser.setUsername(userName);
			sysUser.setSex(sex);
			sysUser.setEmail(email);
			sysUser.setMobile(mobile);
			sysUser.setOrganId(organId);
			sysUser.setAdress(address);
			sysUser.setIsAdmin(isAdmin);
			sysUser.setUserNum(getUserNum());
			if (Constants.YES.equals(isAdmin)) {
				sysUser.setUserType(Constants.USER_TYPE_ADMIN);
			} else {
				sysUser.setUserType(Constants.USER_TYPE_NORMAL);
			}
			sysUser.setIsValid(Constants.YES);
			sysUser.setBirthday(birthday);
			sysUser.setPassword(MyUtil.getMd5(Constants.DEFULT_PASSWORD));
			getSession().save(sysUser);
		}
		transaction.commit();
		getSession().close();
		result.put("success", true);
		return result;
	}

	public Map getUserInfo(String userId) {
		Transaction transaction = getSession().beginTransaction();
		Map userMap = new HashMap<>();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			userMap.put("userId", userId);
			userMap.put("userName", sysUser.getUsername());
			userMap.put("address", sysUser.getAdress());
			userMap.put("email", sysUser.getEmail());
			userMap.put("mobile", sysUser.getMobile());
			userMap.put("organId", sysUser.getOrganId());
			userMap.put("sex", sysUser.getSex());
			userMap.put("isAdmin", sysUser.getIsAdmin());
			userMap.put("birhtday", sysUser.getBirthday());
		}
		transaction.commit();
		getSession().close();
		return userMap;
	}

	public void unLayoutUser(String userId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setIsValid(Constants.YES);
			getSession().update(sysUser);
		}
		transaction.commit();
		getSession().close();
	}

	public void layoutUser(String userId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setIsValid(Constants.NO);
			getSession().update(sysUser);
		}
		transaction.commit();
		getSession().close();
	}

	public Boolean isExitEmail(String email, String userId) {
		Boolean flag = false;
		String sql = "select * from sys_user where email =:email  ";
		if (StringUtils.isNotEmpty(userId)) {
			sql = sql + "  and  user_id != :userId";
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("email", email);
		if (StringUtils.isNotEmpty(userId)) {
			query.setParameter("userId", userId);
		}
		List queryList = query.list();
		if (queryList != null && queryList.size() > 0) {
			flag = true;
		}
		return flag;
	}

	public Boolean isExitMobile(String mobile, String userId) {
		Boolean flag = false;
		String sql = "select * from sys_user where mobile =:mobile  ";
		if (StringUtils.isNotEmpty(userId)) {
			sql = sql + "  and  user_id != :userId";
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("mobile", mobile);
		if (StringUtils.isNotEmpty(userId)) {
			query.setParameter("userId", userId);
		}
		List queryList = query.list();
		if (queryList != null && queryList.size() > 0) {
			flag = true;
		}
		return flag;
	}

	public Map savePassword(String password, String userId, String newPassword) {
		Map result = new HashMap();
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			if (MyUtil.getMd5(password).equals(sysUser.getPassword())) {
				sysUser.setPassword(MyUtil.getMd5(newPassword));
				getSession().update(sysUser);
			} else {

				result.put("errorMsg", true);
				result.put("msg", "原密码输入有误");
				return result;
			}
		}
		transaction.commit();
		getSession().close();
		result.put("success", true);
		return result;
	}

	public void isXTAdmin(String userId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setUserType(Constants.USER_TYPE_XT_ADMIN);
			sysUser.setIsAdmin(Constants.YES);
			getSession().update(sysUser);
		}
		transaction.commit();
		getSession().close();
	}

	public void isAdmin(String userId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setUserType(Constants.USER_TYPE_ADMIN);
			sysUser.setIsAdmin(Constants.YES);
			getSession().update(sysUser);
		}
		transaction.commit();
		getSession().close();
	}

	public void isNormalUser(String userId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setUserType(Constants.USER_TYPE_NORMAL);
			sysUser.setIsAdmin(Constants.NO);
			getSession().update(sysUser);
		}
		transaction.commit();
		getSession().close();
	}

	public Map queryUserNum(String sql) {
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		Map map = new HashMap<>();
		if (list != null && list.size() > 0) {
			map = list.get(0);
		}
		return map;
	}

	public Long getUserNum() {
		String sql = "select  CONVERT(max(s.user_num),CHAR) maxNum from sys_user s ";
		Map map = queryUserNum(sql);
		String maxNum = (String) map.get("maxNum");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long userNum = 0;
		if (StringUtils.isNotEmpty(maxNum)) {
			String year = maxNum.substring(0, 4);
			if (String.valueOf(cal.get(Calendar.YEAR)).equals(year)) {
				userNum = Long.parseLong(maxNum.substring(4)) + 1;
			} else {
				userNum = Constants.USER_NUM_MIN;
			}
		} else {
			userNum = Constants.USER_NUM_MIN;
		}

		String userNumStr = String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf(userNum);
		userNum = Long.parseLong(userNumStr.trim());
		return userNum;

	}
	public Map getMyMoney(HttpServletRequest request){
		Transaction transaction = getSession().beginTransaction();
		HttpSession session =request.getSession();
		String userId =(String)session.getAttribute("userId");
		SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
		double userBalance = sysUser.getUserBalance();
		String money =new java.text.DecimalFormat("0.00").format(userBalance);  
		Map map =new HashMap<>();
		map.put("money", money);
		return map;
	}
	
	public double reMoney(HttpServletRequest request){
		Transaction transaction = getSession().beginTransaction();
		HttpSession session =request.getSession();
		String userId =(String)session.getAttribute("userId");
		String reMoney=request.getParameter("money");
		SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
		double userBalance = sysUser.getUserBalance();
		userBalance= userBalance+Double.parseDouble(reMoney);
		sysUser.setUserBalance(userBalance);
		getSession().update(sysUser);
		transaction.commit();
		getSession().close();
		return userBalance;
	}
}
