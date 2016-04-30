package srmt.java.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import srmt.java.common.Constants;
import srmt.java.entity.PatentInfo;
import srmt.java.entity.ProjectInfo;
import srmt.java.entity.RewardInfo;
import srmt.java.entity.SysUserResearch;
import srmt.java.entity.ThesisInfo;

@Repository
@Transactional
public class ResearchDao {
	@Resource
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/** 
	 * @param request
	 * @return good
	 * @method hello
	 * @author Instant
	 * @time 2016年4月30日 下午3:26:54
	 */
	public List<Map> queryResearchList(HttpServletRequest request) {
		String dictValue = request.getParameter("dictValue");
		String researchName = request.getParameter("researchName");
		String userNum = request.getParameter("userNum");
		StringBuffer sb = new StringBuffer();
		sb.append("   SELECT                                                             ");
		sb.append("   	SU.USER_ID USERID,                                               ");
		sb.append("   	SU.USER_NUM USERNUM,                                               ");
		sb.append("   	SU.USERNAME,                                                     ");
		sb.append("   	SU.EMAIL,                                                        ");
		sb.append("   	SUR.RID,                                                        ");
		sb.append("   	SU.MOBILE,                                                        ");
		sb.append("     RE.RESEARCHID,                                                   ");
		sb.append("     RE.RESEARCHNAME,                                                 ");
		sb.append("   	SUR.RESEARCH_TYPE RESEARCHTYPE,                                  ");
		sb.append("   	(SELECT SD.DICT_NAME FROM SYS_DICT SD                            ");
		sb.append("   		WHERE SD.DICT_VALUE=SUR.RESEARCH_TYPE) DICTNAME               ");
		sb.append("   FROM                                                               ");
		sb.append("   			SYS_USER_RESEARCH SUR                                    ");
		sb.append("   		LEFT JOIN SYS_USER SU ON SUR.RESEARCH_USER_ID = SU.USER_NUM   ");
		sb.append("   		LEFT JOIN (                                                  ");
		sb.append("   			SELECT                                                   ");
		sb.append("   				P.PATENT_ID RESEARCHID,                              ");
		sb.append("   				P.PATENT_NAME RESEARCHNAME                           ");
		sb.append("   			FROM                                                     ");
		sb.append("   				PATENT_INFO P                                        ");
		sb.append("   			UNION                                                    ");
		sb.append("   				SELECT                                               ");
		sb.append("   					TI.THESIS_ID RESEARCHID,                         ");
		sb.append("   					TI.THESIS_NAME RESEARCHNAME                      ");
		sb.append("   				FROM                                                 ");
		sb.append("   					THESIS_INFO TI                                   ");
		sb.append("   				UNION                                                ");
		sb.append("   					SELECT                                           ");
		sb.append("   						PI.PROJECT_ID RESEARCHID,                    ");
		sb.append("   						PI.PROJECT_NAME RESEARCHNAME                 ");
		sb.append("   					FROM                                             ");
		sb.append("   						PROJECT_INFO PI                              ");
		sb.append("   					UNION                                            ");
		sb.append("   						SELECT                                       ");
		sb.append("   							RI.REWARD_ID RESEARCHID,                 ");
		sb.append("   							RI.REWARD_NAME RESEARCHNAME              ");
		sb.append("   						FROM                                         ");
		sb.append("   							REWARD_INFO RI                           ");
		sb.append("               		) RE ON RE.RESEARCHID = SUR.RESEARCH_ID       WHERE 1=1               ");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userType = (String) session.getAttribute("userType");
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			sb.append("    AND SU.USER_NUM = :userNum4Curr        ");
		}
		if (StringUtils.isNotEmpty(dictValue)) {
			sb.append("   AND SUR.RESEARCH_TYPE=:dictValue  ");
		}
		if (StringUtils.isNotEmpty(researchName)) {
			sb.append("  AND RE.RESEARCHNAME LIKE :researchName  ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append("  AND SU.USER_NUM = :userNum   ");
		}
		sb.append("   ORDER BY SU.USER_NUM ASC  ");
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			query.setParameter("userNum4Curr", userNum4Curr);
		}
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		if (StringUtils.isNotEmpty(dictValue)) {
			query.setParameter("dictValue", dictValue);
		}
		if (StringUtils.isNotEmpty(researchName)) {
			query.setParameter("researchName", "%" + researchName + "%");
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		return queryList;
	}

	public List<Map> queryResearchType() {
		String sql = "select sd.dict_value dictvalue,sd.dict_name dictname from sys_dict sd where sd.dict_type=:dictType and sd.is_valid=:isValid";
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_TYPE_RESEARCH);
		List<Map> queryList = query.list();
		transaction.commit();
		getSession().close();
		return queryList;
	}

	public void delResearch(String rid) {
		Transaction transaction = getSession().beginTransaction();
		SysUserResearch sysUserResearch = (SysUserResearch) getSession().get(SysUserResearch.class, rid);
		String researchId = sysUserResearch.getResearchId();
		String researchType = sysUserResearch.getResearchType();
		if (Constants.RESEARCH_TYPE_PATENT.equals(researchType)) {
			PatentInfo patentInfo = (PatentInfo) getSession().get(PatentInfo.class, researchId);
			getSession().delete(patentInfo);
		} else if (Constants.RESEARCH_TYPE_PROJECT.equals(researchType)) {
			ProjectInfo projectInfo = (ProjectInfo) getSession().get(ProjectInfo.class, researchId);
			getSession().delete(projectInfo);
		} else if (Constants.RESEARCH_TYPE_REWARD.equals(researchType)) {
			RewardInfo rewardInfo = (RewardInfo) getSession().get(RewardInfo.class, researchId);
			getSession().delete(rewardInfo);
		} else if (Constants.RESEARCH_TYPE_THESIS.equals(researchType)) {
			ThesisInfo thesisInfo = (ThesisInfo) getSession().get(ThesisInfo.class, researchId);
			getSession().delete(thesisInfo);
		}
		getSession().delete(sysUserResearch);
		transaction.commit();
	}

	public void saveThesis(HttpServletRequest request) {
		String thesisId = request.getParameter("thesisId");
		String thesisName = request.getParameter("thesisName");
		String thesisAuthor = request.getParameter("thesisAuthor");
		String thesisPeriodical = request.getParameter("thesisPeriodicalStr");
		String thesisRecord = request.getParameter("thesisRecord");
		String thesisFileUrl = request.getParameter("thesisFileUrl");
		String thesisType = request.getParameter("thesisType");
		String thesisAbstract = request.getParameter("thesisAbstract");
		String userNum = request.getParameter("userNum");
		Transaction transaction = getSession().beginTransaction();
		HttpSession session = request.getSession();
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		String userId = (String) session.getAttribute("userId");
		if (StringUtils.isNotEmpty(thesisId)) {
			ThesisInfo thesisInfo = (ThesisInfo) getSession().get(ThesisInfo.class, thesisId);
			thesisInfo.setThesisRecord(thesisRecord);
			thesisInfo.setThesisPeriodical(thesisPeriodical);
			thesisInfo.setThesisName(thesisName);
			thesisInfo.setThesisAuthor(thesisAuthor);
			thesisInfo.setThesisFile(thesisFileUrl);
			thesisInfo.setThesisAbstract(thesisAbstract);
			thesisInfo.setThesisType(thesisType);
			getSession().update(thesisInfo);
		} else {
			ThesisInfo thesisInfo = new ThesisInfo();
			thesisInfo.setThesisRecord(thesisRecord);
			thesisInfo.setThesisPeriodical(thesisPeriodical);
			thesisInfo.setThesisName(thesisName);
			thesisInfo.setThesisAuthor(thesisAuthor);
			thesisInfo.setThesisFile(thesisFileUrl);
			thesisInfo.setThesisAbstract(thesisAbstract);
			thesisInfo.setThesisType(thesisType);
			getSession().save(thesisInfo);
			SysUserResearch sysUserResearch = new SysUserResearch();
			if (Constants.USER_TYPE_ADMIN.equals(userType)) {
				sysUserResearch.setResearchUserId(Long.parseLong(userNum));
			} else {
				sysUserResearch.setResearchUserId(userNum4Curr.longValue());
			}
			sysUserResearch.setResearchType(Constants.RESEARCH_TYPE_THESIS);
			sysUserResearch.setResearchId(thesisInfo.getThesisId());
			sysUserResearch.setCreater(userId);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			sysUserResearch.setCreaterDate(ts);
			getSession().save(sysUserResearch);
		}
		transaction.commit();

	}

	public Map getThesisInfo(String thesisId) {
		Map map = new HashMap();
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(thesisId)) {
			ThesisInfo thesisInfo = (ThesisInfo) getSession().get(ThesisInfo.class, thesisId);
			map.put("thesisId", thesisInfo.getThesisId());
			map.put("thesisName", thesisInfo.getThesisName());
			map.put("thesisPeriodical", thesisInfo.getThesisPeriodical());
			map.put("thesisRecord", thesisInfo.getThesisRecord());
			map.put("thesisAuthor", thesisInfo.getThesisAuthor());
			map.put("thesisFile", thesisInfo.getThesisFile());
			map.put("thesisFileUrl", thesisInfo.getThesisFile());
			map.put("thesisAbstract", thesisInfo.getThesisAbstract());
			map.put("thesisType", thesisInfo.getThesisType());
			map.put("userNum", "201610000001");
		}
		transaction.commit();
		getSession().close();
		return map;
	}

	public void saveReward(HttpServletRequest request) {
		String rewardId = request.getParameter("rewardId");
		String rewardName = request.getParameter("rewardName");
		String rewardOrgan = request.getParameter("rewardOrgan");
		String rewardLevel = request.getParameter("rewardLevel");
		String rewardTime = request.getParameter("rewardTime");
		String rewardUser = request.getParameter("rewardUser");
		String rewardType = request.getParameter("rewardType");
		String rewardPlace = request.getParameter("rewardPlace");
		String rewardContent = request.getParameter("rewardContent");
		String rewardFileUrl = request.getParameter("rewardFileUrl");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String userNum = request.getParameter("userNum");
		Date rewardDate = null;
		try {
			if (StringUtils.isNotEmpty(rewardTime)) {
				rewardDate = sdf.parse(rewardTime);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		Transaction transaction = getSession().beginTransaction();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		if (StringUtils.isNotEmpty(rewardId)) {
			RewardInfo rewardInfo = (RewardInfo) getSession().get(RewardInfo.class, rewardId);
			rewardInfo.setRewardLevel(rewardLevel);
			rewardInfo.setRewardName(rewardName);
			rewardInfo.setRewardOrgan(rewardOrgan);
			rewardInfo.setRewardPlace(rewardPlace);
			rewardInfo.setRewardTime(rewardDate);
			rewardInfo.setRewardType(rewardType);
			rewardInfo.setRewardUser(rewardUser);
			rewardInfo.setRewardContent(rewardContent);
			rewardInfo.setRewardFile(rewardFileUrl);
			getSession().update(rewardInfo);
		} else {
			RewardInfo rewardInfo = new RewardInfo();
			rewardInfo.setRewardLevel(rewardLevel);
			rewardInfo.setRewardName(rewardName);
			rewardInfo.setRewardOrgan(rewardOrgan);
			rewardInfo.setRewardPlace(rewardPlace);
			rewardInfo.setRewardTime(rewardDate);
			rewardInfo.setRewardType(rewardType);
			rewardInfo.setRewardUser(rewardUser);
			rewardInfo.setRewardContent(rewardContent);
			rewardInfo.setRewardFile(rewardFileUrl);
			getSession().save(rewardInfo);
			SysUserResearch sysUserResearch = new SysUserResearch();
			if (Constants.USER_TYPE_ADMIN.equals(userType)) {
				sysUserResearch.setResearchUserId(Long.parseLong(userNum));
			} else {
				sysUserResearch.setResearchUserId(userNum4Curr.longValue());
			}
			sysUserResearch.setResearchType(Constants.RESEARCH_TYPE_REWARD);
			sysUserResearch.setResearchId(rewardInfo.getRewardId());
			sysUserResearch.setCreater(userId);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			sysUserResearch.setCreaterDate(ts);
			getSession().save(sysUserResearch);
		}
		transaction.commit();

	}

	public Map getRewardInfo(String rewardId) {
		Map map = new HashMap();
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(rewardId)) {
			RewardInfo rewardInfo = (RewardInfo) getSession().get(RewardInfo.class, rewardId);
			map.put("rewardId", rewardInfo.getRewardId());
			map.put("rewardLevel", rewardInfo.getRewardLevel());
			map.put("rewardName", rewardInfo.getRewardName());
			map.put("rewardOrgan", rewardInfo.getRewardOrgan());
			map.put("rewardPlace", rewardInfo.getRewardPlace());
			map.put("rewardTime", rewardInfo.getRewardTime());
			map.put("rewardUser", rewardInfo.getRewardUser());
			map.put("rewardType", rewardInfo.getRewardType());
			map.put("rewardContent", rewardInfo.getRewardContent());
			map.put("rewardFile", rewardInfo.getRewardFile());
			map.put("userNum", "201610000001");
		}
		transaction.commit();
		getSession().close();
		return map;
	}

	public void savePatent(HttpServletRequest request) {
		String patentId = request.getParameter("patentId");
		String patentName = request.getParameter("patentName");
		String patentType = request.getParameter("patentType");
		String patentCreater = request.getParameter("patentCreater");
		String patentFirst = request.getParameter("patentFirst");
		String patentIsTransfer = request.getParameter("patentIsTransfer");
		String patentPeople = request.getParameter("patentPeople");
		String patentContent = request.getParameter("patentContent");
		String patentFileUrl = request.getParameter("patentFileUrl");
		String patentDateStr = request.getParameter("patentDate");
		String userNum = request.getParameter("userNum");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date patentDate = null;
		try {
			if (StringUtils.isNotEmpty(patentDateStr)) {
				patentDate = sdf.parse(patentDateStr);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		Transaction transaction = getSession().beginTransaction();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		if (StringUtils.isNotEmpty(patentId)) {
			PatentInfo patentInfo = (PatentInfo) getSession().get(PatentInfo.class, patentId);
			patentInfo.setPatentCreater(patentCreater);
			patentInfo.setPatentDate(patentDate);
			patentInfo.setPatentFirst(patentFirst);
			patentInfo.setPatentIsTransfer(patentIsTransfer);
			patentInfo.setPatentName(patentName);
			patentInfo.setPatentType(patentType);
			patentInfo.setPatentName(patentName);
			patentInfo.setPatentContent(patentContent);
			patentInfo.setPatentFile(patentFileUrl);
			patentInfo.setPatentPeople(patentPeople);
			getSession().update(patentInfo);
		} else {
			PatentInfo patentInfo = new PatentInfo();
			patentInfo.setPatentCreater(patentCreater);
			patentInfo.setPatentDate(patentDate);
			patentInfo.setPatentFirst(patentFirst);
			patentInfo.setPatentIsTransfer(patentIsTransfer);
			patentInfo.setPatentName(patentName);
			patentInfo.setPatentType(patentType);
			patentInfo.setPatentName(patentName);
			patentInfo.setPatentContent(patentContent);
			patentInfo.setPatentFile(patentFileUrl);
			patentInfo.setPatentPeople(patentPeople);
			getSession().save(patentInfo);
			SysUserResearch sysUserResearch = new SysUserResearch();
			if (Constants.USER_TYPE_ADMIN.equals(userType)) {
				sysUserResearch.setResearchUserId(Long.parseLong(userNum));
			} else {
				sysUserResearch.setResearchUserId(userNum4Curr.longValue());
			}
			sysUserResearch.setResearchType(Constants.RESEARCH_TYPE_PATENT);
			sysUserResearch.setResearchId(patentInfo.getPatentId());
			sysUserResearch.setCreater(userId);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			sysUserResearch.setCreaterDate(ts);
			getSession().save(sysUserResearch);
		}
		transaction.commit();

	}

	public Map getPatentInfo(String patentId) {
		Map map = new HashMap();
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(patentId)) {
			PatentInfo patentInfo = (PatentInfo) getSession().get(PatentInfo.class, patentId);
			map.put("patentId", patentInfo.getPatentId());
			map.put("patentCreater", patentInfo.getPatentCreater());
			map.put("patentDate", patentInfo.getPatentDate());
			map.put("patentFirst", patentInfo.getPatentFirst());
			map.put("patentIsTransfer", patentInfo.getPatentIsTransfer());
			map.put("patentName", patentInfo.getPatentName());
			map.put("patentType", patentInfo.getPatentType());
			map.put("patentContent", patentInfo.getPatentContent());
			map.put("patentPeople", patentInfo.getPatentPeople());
			map.put("patentFile", patentInfo.getPatentFile());
			map.put("userNum", "201610000001");
		}
		transaction.commit();
		getSession().close();
		return map;
	}

	public void saveProject(HttpServletRequest request) {
		String projectId = request.getParameter("projectId");
		String projectName = request.getParameter("projectName");
		String projectSource = request.getParameter("projectSource");
		String projectType = request.getParameter("projectType");
		String projectFund = request.getParameter("projectFund");
		String projectFirst = request.getParameter("projectFirst");
		String projectSecond = request.getParameter("projectSecond");
		String projectThird = request.getParameter("projectThird");
		String projectFileUrl = request.getParameter("projectFileUrl");
		String projectContent = request.getParameter("projectContent");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String userNum = request.getParameter("userNum");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		try {
			if (StringUtils.isNotEmpty(startTime)) {
				startDate = sdf.parse(startTime);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		Date endDate = null;
		try {
			if (StringUtils.isNotEmpty(endTime)) {
				endDate = sdf.parse(endTime);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		Transaction transaction = getSession().beginTransaction();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		if (StringUtils.isNotEmpty(projectId)) {
			ProjectInfo projectInfo = (ProjectInfo) getSession().get(ProjectInfo.class, projectId);
			projectInfo.setProjectName(projectName);
			projectInfo.setProjectFund(Float.parseFloat(projectFund));
			projectInfo.setProjectFirst(projectFirst);
			projectInfo.setProjectSecond(projectSecond);
			projectInfo.setProjectThird(projectThird);
			projectInfo.setProjectType(projectType);
			projectInfo.setProjectSource(projectSource);
			projectInfo.setProjectContent(projectContent);
			projectInfo.setStartTime(startDate);
			projectInfo.setEndTime(endDate);
			projectInfo.setProectFile(projectFileUrl);
			getSession().update(projectInfo);
		} else {
			ProjectInfo projectInfo = new ProjectInfo();
			projectInfo.setProjectName(projectName);
			projectInfo.setProjectFund(Float.parseFloat(projectFund));
			projectInfo.setProjectFirst(projectFirst);
			projectInfo.setProjectSecond(projectSecond);
			projectInfo.setProjectThird(projectThird);
			projectInfo.setProjectType(projectType);
			projectInfo.setProjectSource(projectSource);
			projectInfo.setProjectContent(projectContent);
			projectInfo.setStartTime(startDate);
			projectInfo.setEndTime(endDate);
			projectInfo.setProectFile(projectFileUrl);
			getSession().save(projectInfo);
			SysUserResearch sysUserResearch = new SysUserResearch();
			if (Constants.USER_TYPE_ADMIN.equals(userType)) {
				sysUserResearch.setResearchUserId(Long.parseLong(userNum));
			} else {
				sysUserResearch.setResearchUserId(userNum4Curr.longValue());
			}
			sysUserResearch.setResearchType(Constants.RESEARCH_TYPE_PROJECT);
			sysUserResearch.setResearchId(projectInfo.getProjectId());
			sysUserResearch.setCreater(userId);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			sysUserResearch.setCreaterDate(ts);
			getSession().save(sysUserResearch);
		}
		transaction.commit();

	}

	public Map getProjectInfo(String projectId) {
		Map map = new HashMap();
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(projectId)) {
			ProjectInfo projectInfo = (ProjectInfo) getSession().get(ProjectInfo.class, projectId);
			map.put("projectId", projectInfo.getProjectId());
			map.put("projectFirst", projectInfo.getProjectFirst());
			map.put("projectFund", projectInfo.getProjectFund());
			map.put("projectName", projectInfo.getProjectName());
			map.put("projectSecond", projectInfo.getProjectSecond());
			map.put("projectSource", projectInfo.getProjectSource());
			map.put("projectType", projectInfo.getProjectType());
			map.put("projectThird", projectInfo.getProjectType());
			map.put("endTime", projectInfo.getEndTime());
			map.put("startTime", projectInfo.getStartTime());
			map.put("projectContent", projectInfo.getProjectContent());
			map.put("projectFile", projectInfo.getProectFile());
			map.put("userNum", "201610000001");
		}
		transaction.commit();
		getSession().close();
		return map;
	}

	public List<Map> queryProjectType() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_PROJECT_TYPE);
		List<Map> queryList = query.list();
		transaction.commit();
		getSession().close();
		return queryList;
	}

	public List<Map> queryPatentType() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType ";
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_PATENT_TYPE);
		List<Map> queryList = query.list();
		transaction.commit();
		getSession().close();
		return queryList;
	}

	public List<Map> queryPlaceType() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_PLACE_TYPE);
		List<Map> queryList = query.list();
		transaction.commit();
		getSession().close();
		return queryList;
	}

	public List<Map> queryRewardType() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_REWARD_TYPE);
		List<Map> queryList = query.list();
		transaction.commit();
		getSession().close();
		return queryList;
	}

	public List<Map> queryThesisIncluded() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_THESIS_INCLUDED);
		List<Map> queryList = query.list();
		transaction.commit();
		getSession().close();
		return queryList;
	}

	public List<Map> queryThesisType() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_THESIS_TYPE);
		List<Map> queryList = query.list();
		transaction.commit();
		getSession().close();
		return queryList;
	}

	public List<Map> queryPatentPeople() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_PATENT_PEOPLE);
		List<Map> queryList = query.list();
		transaction.commit();
		getSession().close();
		return queryList;
	}

	public List<Map> getCurrentThesisWorkload4Tec(BigInteger userNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("   SELECT                                                   ");
		sb.append("   	T.THESIS_NAME THESISNAME,                   ");
		sb.append("   	T.THESIS_PERIODICAL THESISPERIODICAL                   ");
		sb.append("   FROM                                                     ");
		sb.append("   	SYS_USER_RESEARCH SUR                                  ");
		sb.append("   LEFT JOIN THESIS_INFO T ON SUR.RESEARCH_ID = T.THESIS_ID   ");
		sb.append("   WHERE                                                    ");
		sb.append("   	SUR.RESEARCH_TYPE = :researchThesis                ");
		sb.append("   AND SUR.RESEARCH_USER_ID = :userId                        ");
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchThesis", Constants.RESEARCH_TYPE_THESIS);
		query.setParameter("userId", userNum.toString());
		List<Map> queryList = query.list();
		double workloadSum = 0;
		List<Map> list = new ArrayList<>();
		if (queryList != null && queryList.size() > 0) {
			for (Map map : queryList) {
				double workload = 0;
				String thesisPeriodical = (String) map.get("THESISPERIODICAL");
				if (StringUtils.isNotEmpty(thesisPeriodical)) {
					if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_SHOUSCI)) {
						workload = 2.0;
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_EIYUAN)
							|| thesisPeriodical.contains(Constants.THESIS_INCLUDED_EISHOU)
							|| thesisPeriodical.contains(Constants.THESIS_INCLUDED_JIAOWU)) {
						workload = 1.2;
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_EISHOULU)) {
						workload = 1.0;
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_SHOULU)) {
						workload = 0.8;
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_CHINESE)
							|| thesisPeriodical.contains(Constants.THESIS_INCLUDED_GUOJI)) {
						workload = 0.5;
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_OTHER)) {
						workload = 0.1;
					}
					workloadSum = workloadSum + workload;
					Map thesisMap = new HashMap<>();
					thesisMap.put("thesisName", (String) map.get("THESISNAME"));
					thesisMap.put("workload", workload);
					list.add(thesisMap);
				}
			}
		}
		Map workMap = new HashMap<>();
		workMap.put("thesisName", "论文科研分");
		workMap.put("workload", workloadSum);
		list.add(workMap);
		return list;
	}

	public List<Map> getCurrentProjectWorkload4Tec(BigInteger userNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT                                                            ");
		sb.append("  	T.project_name projectname,                                 ");
		sb.append("  	T.PROJECT_TYPE PROJECTTYPE,                                    ");
		sb.append("      T.PROJECT_FUND PROJECTFUND                                      ");
		sb.append("  FROM                                                              ");
		sb.append("  	SYS_USER_RESEARCH SUR                                          ");
		sb.append("  LEFT JOIN PROJECT_INFO T ON SUR.RESEARCH_ID = T.PROJECT_ID        ");
		sb.append("  WHERE                                                             ");
		sb.append("  	SUR.RESEARCH_TYPE = :researchProject                        ");
		sb.append("  AND SUR.RESEARCH_USER_ID = :userId                               ");
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchProject", Constants.RESEARCH_TYPE_PROJECT);
		query.setParameter("userId", userNum.toString());
		List<Map> queryList = query.list();
		double workloadSum = 0;
		List<Map> list = new ArrayList<>();
		if (queryList != null && queryList.size() > 0) {
			for (Map map : queryList) {
				double workload = 0;
				String projectType = (String) map.get("PROJECTTYPE");
				double projectFund = (double) map.get("PROJECTFUND");
				int i = (int) projectFund;
				if (Constants.PROJECT_TYPE__GUO.equals(projectType)) {
					workload = (i * (12 / 200000)) * Constants.PROECT_TIAO_K;
				} else if (Constants.PROJECT_TYPE_SHEN.equals(projectType)) {
					workload = (i * (8 / 200000)) * Constants.PROECT_TIAO_K;
				} else if (Constants.PROJECT_TYPE_OTHER.equals(projectType)) {
					workload = (i * (5 / 200000)) * Constants.PROECT_TIAO_K;
				}
				workloadSum = workloadSum + workload;
				Map thesisMap = new HashMap<>();
				thesisMap.put("projectName", (String) map.get("projectname"));
				thesisMap.put("workload", workload);
				list.add(thesisMap);
			}
		}
		Map workMap = new HashMap<>();
		workMap.put("projectName", "项目科研总分");
		workMap.put("workload", workloadSum);
		list.add(workMap);
		return list;
	}

	public List<Map> getCurrentRewardWorkload4Tec(BigInteger userNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("    SELECT                                                      ");
		sb.append("    T.reward_name   rewardname,                                 ");
		sb.append("    T.REWARD_TYPE   REWARDTYPE,                                 ");
		sb.append("   T.REWARD_PLACE     REWARDPLACE                               ");
		sb.append("    FROM                                                        ");
		sb.append("    	SYS_USER_RESEARCH SUR                                      ");
		sb.append("    LEFT JOIN REWARD_INFO T ON SUR.RESEARCH_ID = T.REWARD_ID    ");
		sb.append("    WHERE                                                       ");
		sb.append("    	SUR.RESEARCH_TYPE = :researchReward                     ");
		sb.append("    AND SUR.RESEARCH_USER_ID = :userId                         ");
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchReward", Constants.RESEARCH_TYPE_REWARD);
		query.setParameter("userId", userNum.toString());
		List<Map> queryList = query.list();
		double workloadSum = 0;
		List<Map> list = new ArrayList<>();
		if (queryList != null && queryList.size() > 0) {
			for (Map map : queryList) {
				String rewardType = (String) map.get("REWARDTYPE");
				String rewardPlace = (String) map.get("REWARDPLACE");
				double workload = 0;
				if (Constants.REWARD_TYPE_SHEN1.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload = 10;
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload = 6;
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload = 4;
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload = 2;
					} else {
						workload = 1;
					}
				} else if (Constants.REWARD_TYPE_SHEN2.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload = 6;
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload = 4;
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload = 2;
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload = 1;
					} else {
						workload = 0.5;
					}
				} else if (Constants.REWARD_TYPE_SHEN3.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload = 4;
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload = 2;
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload = 1;
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload = 0.5;
					}
				} else if (Constants.REWARD_TYPE_DISHI1.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload = 2;
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload = 1;
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload = 0.5;
					}
				} else if (Constants.REWARD_TYPE_DISHI2.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload = 1;
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload = 0.5;
					}
				} else if (Constants.REWARD_TYPE_DISHI3.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload = 0.5;
					}
				} else if (Constants.REWARD_TYPE_XIAO1.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload = 0.3;
					}
				} else if (Constants.REWARD_TYPE_XIAO2.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload = 0.2;
					}
				} else if (Constants.REWARD_TYPE_XIAO3.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload = 0.1;
					}
				} else if (Constants.REWARD_TYPE_XIAO1.equals(rewardType)
						|| Constants.REWARD_TYPE_XIAO2.equals(rewardType)) {
					workload = 1;
				}

				workloadSum = workloadSum + workload;
				Map thesisMap = new HashMap<>();
				thesisMap.put("rewardName", (String) map.get("rewardname"));
				thesisMap.put("workload", workload);
				list.add(thesisMap);
			}
		}
		Map workMap = new HashMap<>();
		workMap.put("rewardName", "奖励科研总分");
		workMap.put("workload", workloadSum);
		list.add(workMap);
		return list;
	}

	public List<Map> getCurrentPatentWorkload4Tec(BigInteger userNum) {
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT                                                    ");
		sb.append("  	T.patent_name patentname,                              ");
		sb.append("  	T.PATENT_TYPE PATENTTYPE,                              ");
		sb.append("  	T.PATENT_FIRST PATENTFIRST,                            ");
		sb.append("  	T.PATENT_IS_TRANSFER PATENTISTRANSFER                  ");
		sb.append("  FROM                                                      ");
		sb.append("  	SYS_USER_RESEARCH SUR                                  ");
		sb.append("  LEFT JOIN PATENT_INFO T ON SUR.RESEARCH_ID = T.PATENT_ID  ");
		sb.append("  WHERE                                                     ");
		sb.append("  	SUR.RESEARCH_TYPE = :researchPatent                  ");
		sb.append("  AND SUR.RESEARCH_USER_ID = :userId                        ");
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchPatent", Constants.RESEARCH_TYPE_PATENT);
		query.setParameter("userId", userNum.toString());
		List<Map> queryList = query.list();
		double workloadSum = 0;
		List<Map> list = new ArrayList<>();
		if (queryList != null && queryList.size() > 0) {
			for (Map map : queryList) {
				String patenttype = (String) map.get("PATENTTYPE");
				String patentFirst = (String) map.get("PATENTFIRST");
				String patentIsTransfer = (String) map.get("PATENTISTRANSFER");
				double workload = 0;
				if (Constants.PATENT_TYPE_FAMING.equals(patenttype)) {
					if (Constants.YES.equals(patentFirst)) {
						workload = 3;
						if (Constants.YES.equals(patentIsTransfer)) {
							workload = workload + 1;
						}
					}
				} else if (Constants.PATENT_TYPE_SHIYONG.equals(patenttype)
						|| Constants.PATENT_TYPE_WAIGAUAN.equals(patenttype)) {
					if (Constants.YES.equals(patentFirst)) {
						workload = 1;
					}
				} else if (Constants.PATENT_TYPE_SOFTWAR.equals(patenttype)) {
					if (Constants.YES.equals(patentFirst)) {
						workload = 1;
					}
				}
				workloadSum = workloadSum + workload;
				Map thesisMap = new HashMap<>();
				thesisMap.put("patentName", (String) map.get("patentname"));
				thesisMap.put("workload", workload);
				list.add(thesisMap);
			}
		}
		Map workMap = new HashMap<>();
		workMap.put("patentName", "专利科研总分");
		workMap.put("workload", workloadSum);
		list.add(workMap);
		return list;

	}
}