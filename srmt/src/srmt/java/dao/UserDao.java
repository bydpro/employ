package srmt.java.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import srmt.java.entity.SysDict;
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
		String deptId = request.getParameter("deptId");
		String usertype = request.getParameter("usertype");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT                                               									");
		sb.append(" 		SU.USERNAME,                                    							    ");
		sb.append(" 		SU.USER_ID     USERID,                          						       ");
		sb.append(" 		SU.LOGIN_ID LOGINID,                            							");
		sb.append(" 		SU.USER_NUM USERNUM,                         						");
		sb.append(" 		SU.USER_TYPE USERTYPE,                         						");
		sb.append(" 		SU.SEX,                                                 							");
		sb.append(" 		SU.EMAIL,                                                                         ");
		sb.append(" 		SU.MOBILE,                                                                      ");
		sb.append(" 		SO.ORGAN_NAME ORGANNAME,                                     ");
		sb.append(" 		(SELECT S.ORGAN_NAME FROM SYS_ORGAN S  WHERE S.ORGAN_ID =SU.DEPT)  DEPTNAME,   ");
		sb.append(" 		SU.IS_VALID       ISVALID,                                                ");
		sb.append("     SU.ADRESS,                                                                       ");
		sb.append("     DATE_FORMAT(SU.BIRTHDAY,'%Y-%m-%d')   BIRTHDAY           ");
		sb.append("	 FROM                                                                                 ");
		sb.append(" 		SYS_USER SU                                                                    ");
		sb.append(" 	LEFT JOIN SYS_ORGAN SO ON SU.ORGAN_ID = SO.ORGAN_ID  where 1=1 ");
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" and su.USERNAME like :userName   ");
		}
		if (StringUtils.isNotEmpty(deptId)) {
			sb.append(" and su.dept = :deptId   ");
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
				sb.append("  and su.IS_VALID != :isValid      ");
			}
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" and su.user_num = :userNum   ");
		}
		if (StringUtils.isNotEmpty(usertype)) {
			sb.append(" and su.user_type = :usertype   ");
		}
		sb.append(" ORDER BY SU.USER_NUM ASC ");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
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
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		if (StringUtils.isNotEmpty(deptId)) {
			query.setParameter("deptId", deptId);
		}
		if (StringUtils.isNotEmpty(usertype)) {
			query.setParameter("usertype", usertype);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		transaction.commit();
		return queryList;
	}

	public void delUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SysUser sysUser = (SysUser) currSession.get(SysUser.class, userId);
		currSession.delete(sysUser);
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
		String userType = request.getParameter("userType");
		String deptId = request.getParameter("deptId");

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
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		if (StringUtils.isNotEmpty(userId)) {
			SysUser sysUser = (SysUser) currSession.get(SysUser.class, userId);
			sysUser.setUsername(userName);
			sysUser.setSex(sex);
			sysUser.setEmail(email);
			sysUser.setMobile(mobile);
			sysUser.setOrganId(organId);
			sysUser.setAdress(address);
			sysUser.setBirthday(birthday);
			sysUser.setDept(deptId);
			sysUser.setUserType(userType);
			currSession.update(sysUser);
		} else {
			SysUser sysUser = new SysUser();
			sysUser.setUserNum(getUserNum());
			sysUser.setUsername(userName);
			sysUser.setSex(sex);
			sysUser.setEmail(email);
			sysUser.setMobile(mobile);
			sysUser.setOrganId(organId);
			sysUser.setAdress(address);
			sysUser.setUserType(userType);
			sysUser.setIsValid(Constants.YES);
			sysUser.setBirthday(birthday);
			sysUser.setPassword(MyUtil.getMd5(Constants.DEFULT_PASSWORD));
			sysUser.setDept(deptId);
			currSession.save(sysUser);
		}
		transaction.commit();
		result.put("success", true);
		return result;
	}
	
	public Map saveMyUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String organId = request.getParameter("organId");
		String birhtdayStr = request.getParameter("birhtday");
		String address = request.getParameter("address");
		String deptId = request.getParameter("deptId");

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
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		if (StringUtils.isNotEmpty(userId)) {
			SysUser sysUser = (SysUser) currSession.get(SysUser.class, userId);
			sysUser.setUsername(userName);
			sysUser.setSex(sex);
			sysUser.setEmail(email);
			sysUser.setMobile(mobile);
			sysUser.setOrganId(organId);
			sysUser.setAdress(address);
			sysUser.setBirthday(birthday);
			sysUser.setDept(deptId);
			currSession.update(sysUser);
		} else {
			SysUser sysUser = new SysUser();
			sysUser.setUserNum(getUserNum());
			sysUser.setUsername(userName);
			sysUser.setSex(sex);
			sysUser.setEmail(email);
			sysUser.setMobile(mobile);
			sysUser.setOrganId(organId);
			sysUser.setAdress(address);
			sysUser.setIsValid(Constants.YES);
			sysUser.setBirthday(birthday);
			sysUser.setPassword(MyUtil.getMd5(Constants.DEFULT_PASSWORD));
			sysUser.setDept(deptId);
			currSession.save(sysUser);
		}
		transaction.commit();
		result.put("success", true);
		return result;
	}

	public Map getUserInfo(String userId) {
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		Map userMap = new HashMap<>();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) currSession.get(SysUser.class, userId);
			userMap.put("userId", userId);
			userMap.put("userName", sysUser.getUsername());
			userMap.put("address", sysUser.getAdress());
			userMap.put("email", sysUser.getEmail());
			userMap.put("mobile", sysUser.getMobile());
			userMap.put("organId", sysUser.getOrganId());
			userMap.put("sex", sysUser.getSex());
			userMap.put("userType", sysUser.getUserType());
			userMap.put("birhtday", sysUser.getBirthday());
			userMap.put("deptId", sysUser.getDept());
			userMap.put("picPath", sysUser.getPicPath());
		}
		transaction.commit();
		return userMap;
	}

	public void unLayoutUser(String userId) {
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) currSession.get(SysUser.class, userId);
			sysUser.setIsValid(Constants.YES);
			currSession.update(sysUser);
		}
		transaction.commit();
	}

	public void layoutUser(String userId) {
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) currSession.get(SysUser.class, userId);
			sysUser.setIsValid(Constants.NO);
			currSession.update(sysUser);
		}
		transaction.commit();
	}

	public Boolean isExitEmail(String email, String userId) {
		Boolean flag = false;
		String sql = "select * from sys_user where email =:email  ";
		if (StringUtils.isNotEmpty(userId)) {
			sql = sql + "  and  user_id != :userId";
		}
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setParameter("email", email);
		if (StringUtils.isNotEmpty(userId)) {
			query.setParameter("userId", userId);
		}
		List queryList = query.list();
		if (queryList != null && queryList.size() > 0) {
			flag = true;
		}
		transaction.commit();
		return flag;
	}

	public Boolean isExitMobile(String mobile, String userId) {
		Boolean flag = false;
		String sql = "select * from sys_user where mobile =:mobile  ";
		if (StringUtils.isNotEmpty(userId)) {
			sql = sql + "  and  user_id != :userId";
		}
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setParameter("mobile", mobile);
		if (StringUtils.isNotEmpty(userId)) {
			query.setParameter("userId", userId);
		}
		List queryList = query.list();
		if (queryList != null && queryList.size() > 0) {
			flag = true;
		}
		transaction.commit();
		return flag;
	}

	public Map savePassword(String password, String userId, String newPassword) {
		Map result = new HashMap();
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) currSession.get(SysUser.class, userId);
			if (MyUtil.getMd5(password).equals(sysUser.getPassword())) {
				sysUser.setPassword(MyUtil.getMd5(newPassword));
				currSession.update(sysUser);
			} else {

				result.put("errorMsg", true);
				result.put("msg", "原密码输入有误");
				return result;
			}
		}
		transaction.commit();
		result.put("success", true);
		return result;
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
	
	public Map queryUserNum(String sql) {
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		Map map = new HashMap<>();
		if (list != null && list.size() > 0) {
			map = list.get(0);
		}
		return map;
	}
	
	public List<Map> queryUser4sel(HttpServletRequest request) {
		String organId = request.getParameter("organId");
		String deptId = request.getParameter("deptId");
		String sql = "select su.username,su.username str from sys_user su where 1=1 ";
		if(StringUtils.isNotEmpty(organId)){
			sql = sql + "  AND Su.ORGAN_ID = :organId ";
		}
		if(StringUtils.isNotEmpty(deptId)){
			sql = sql + "  AND Su.DEPT = :deptId ";
		}
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(StringUtils.isNotEmpty(organId)){
			query.setParameter("organId",organId);
		}
		if(StringUtils.isNotEmpty(deptId)){
			query.setParameter("deptId",deptId);
		}
		List<Map> queryList = query.list();
		transaction.commit();
		return queryList;
	}
	
	public List<Map> querySysDict(HttpServletRequest request) {
		String dictType = request.getParameter("dictType");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from sys_dict s where s.dict_type in "
				+ "('reward_type','patent_type','project_type','thesis_type','patent_people') ");
		if (StringUtils.isNotEmpty(dictType)) {
			sb.append(" and s.dict_type = :dictType   ");
		}
		sb.append("  order by s.dict_type desc ");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		if (StringUtils.isNotEmpty(dictType)) {
			query.setParameter("dictType", dictType);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		transaction.commit();
		return queryList;
	}
	
	public Map saveDict(HttpServletRequest request) {
		String dictId = request.getParameter("dictId");
		String dictName = request.getParameter("dictName");
		String dictType = request.getParameter("dictType");

		Map result = new HashMap();
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		if (StringUtils.isNotEmpty(dictId)) {
			SysDict sysDict = (SysDict) currSession.get(SysDict.class, dictId);
			sysDict.setDictName(dictName);
			sysDict.setDictType(dictType);
			currSession.update(sysDict);
		} else {
			SysDict sysDict = new SysDict();
			String uuid = UUID.randomUUID().toString();
			sysDict.setDictName(dictName);
			sysDict.setDictType(dictType);
			sysDict.setDictValue(uuid);
			sysDict.setIsValid(Constants.YES);
			currSession.save(sysDict);
		}
		transaction.commit();
		result.put("success", true);
		return result;
	}
	
	public void delDict(HttpServletRequest request) {
		String dictId = request.getParameter("dictId");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SysDict sysDict = (SysDict) currSession.get(SysDict.class, dictId);
		currSession.delete(sysDict);
		transaction.commit();
	}
	
	public Map getDictInfo(String dictId) {
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		Map userMap = new HashMap<>();
		if (StringUtils.isNoneEmpty(dictId)) {
			SysDict sysDict = (SysDict) currSession.get(SysDict.class, dictId);
			userMap.put("dictId", dictId);
			userMap.put("dictName", sysDict.getDictName());
			userMap.put("dictType", sysDict.getDictType());
		}
		transaction.commit();
		return userMap;
	}
}
