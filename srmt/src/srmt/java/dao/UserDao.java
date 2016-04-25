package srmt.java.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
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
		String loginId = request.getParameter("loginId");
		String isValid = request.getParameter("isValid");
		String page = request.getParameter("pageNum");
		String rows = request.getParameter("pageSize");

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT                                               									");
		sb.append(" 		SU.USERNAME,                                    							    ");
		sb.append(" 		SU.USER_ID     USERID,                          						    ");
		sb.append(" 		SU.LOGIN_ID LOGINID,                            							");
		sb.append(" 		SU.SEX,                                                 							");
		sb.append(" 		SU.EMAIL,                                                                         ");
		sb.append(" 		SU.MOBILE,                                                                      ");
		sb.append(" 		SO.ORGAN_NAME ORGANNAME,                                     ");
		sb.append(" 		SU.IS_VALID       ISVALID,                                                ");
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
		if (StringUtils.isNotEmpty(loginId)) {
			sb.append(" and su.LOGIN_ID = :loginId   ");
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
		if (StringUtils.isNotEmpty(isValid)) {
			query.setParameter("isValid", isValid);
		}
		if (StringUtils.isNotEmpty(loginId)) {
			loginId = "%" + loginId + "%";
			query.setParameter("loginId", loginId);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		int pageNum = 0;
		int pageRows = 10;
		if (StringUtils.isNotEmpty(page)) {
			pageNum = Integer.parseInt(page);
		}
		if (StringUtils.isNotEmpty(rows)) {
			pageRows = Integer.parseInt(rows);
		}
		int total = getTotal(query);
		query.setFirstResult((pageNum - 1) * pageRows);
		query.setMaxResults(pageRows);
		Map totalMap = new HashMap();
		totalMap.put("total", total);
		List<Map> list = new ArrayList<Map>();
		list.add(totalMap);
		List<Map> queryList = query.list();
		list.addAll(queryList);
		return list;
	}

	public int getTotal(SQLQuery query) {
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		int size = 0;
		if (list != null && list.size() > 0) {
			size = list.size();
		}
		return size;
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
		if (StringUtils.isNotEmpty(email)) {
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
		if (StringUtils.isNotEmpty(mobile)) {
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
}
