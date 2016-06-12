package srmt.java.dao;

import java.math.BigDecimal;
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
import srmt.java.entity.PatentScore;
import srmt.java.entity.ProjectInfo;
import srmt.java.entity.ProjectScore;
import srmt.java.entity.RewardInfo;
import srmt.java.entity.RewardScore;
import srmt.java.entity.SysUser;
import srmt.java.entity.SysUserResearch;
import srmt.java.entity.ThesisInfo;
import srmt.java.entity.ThesisScore;

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
		String userName = request.getParameter("userName");
		String organId = request.getParameter("organId");
		String deptId = request.getParameter("deptId");
		String check = request.getParameter("check");
		StringBuilder sb = new StringBuilder();
		sb.append("   SELECT                                                             ");
		sb.append("   	SU.USER_ID USERID,                                               ");
		sb.append("   	SU.USER_NUM USERNUM,                                             ");
		sb.append("   	SU.USERNAME,                                                     ");
		sb.append("   	SU.EMAIL,                                                        ");
		sb.append("   	SUR.RID,                                                         ");
		sb.append("   	SU.MOBILE,                                                       ");
		sb.append(" 	(SELECT S.ORGAN_NAME FROM SYS_ORGAN S  WHERE S.ORGAN_ID =SU.ORGAN_ID)  ORGANNAME,   ");
		sb.append(" 	(SELECT S.ORGAN_NAME FROM SYS_ORGAN S  WHERE S.ORGAN_ID =SU.DEPT)  DEPTNAME,   ");
		sb.append("     RE.RESEARCHID,                                                   ");
		sb.append("     RE.RESEARCHNAME,                                                 ");
		sb.append("     RE.STATUS,                                                 ");
		sb.append("   	SUR.RESEARCH_TYPE RESEARCHTYPE,                                  ");
		sb.append("   	(SELECT SD.DICT_NAME FROM SYS_DICT SD                            ");
		sb.append("   		WHERE SD.DICT_VALUE=SUR.RESEARCH_TYPE) DICTNAME              ");
		sb.append("   FROM                                                               ");
		sb.append("   			SYS_USER_RESEARCH SUR                                    ");
		sb.append("   		LEFT JOIN SYS_USER SU ON SUR.RESEARCH_USER_ID = SU.USER_NUM  ");
		sb.append("   		LEFT JOIN (                                                  ");
		sb.append("   			SELECT                                                   ");
		sb.append("   				P.PATENT_ID RESEARCHID,                              ");
		sb.append("   				P.PATENT_NAME RESEARCHNAME ,                         ");
		sb.append("   				P.PATENT_PASS STATUS                        		 ");
		sb.append("   			FROM                                                     ");
		sb.append("   				PATENT_INFO P                                        ");
		sb.append("   			UNION                                                    ");
		sb.append("   				SELECT                                               ");
		sb.append("   					TI.THESIS_ID RESEARCHID,                         ");
		sb.append("   					TI.THESIS_NAME RESEARCHNAME,                     ");
		sb.append("   					TI.THESIS_PASS STATUS                      		 ");
		sb.append("   				FROM                                                 ");
		sb.append("   					THESIS_INFO TI                                   ");
		sb.append("   				UNION                                                ");
		sb.append("   					SELECT                                           ");
		sb.append("   						PI.PROJECT_ID RESEARCHID,                    ");
		sb.append("   						PI.PROJECT_NAME RESEARCHNAME,                ");
		sb.append("   						PI.PROJECT_PASS STATUS                       ");
		sb.append("   					FROM                                             ");
		sb.append("   						PROJECT_INFO PI                              ");
		sb.append("   					UNION                                            ");
		sb.append("   						SELECT                                       ");
		sb.append("   							RI.REWARD_ID RESEARCHID,                 ");
		sb.append("   							RI.REWARD_NAME RESEARCHNAME,             ");
		sb.append("   							RI.REWARD_PASS STATUS                    ");
		sb.append("   						FROM                                         ");
		sb.append("   							REWARD_INFO RI                           ");
		sb.append("               		) RE ON RE.RESEARCHID = SUR.RESEARCH_ID       WHERE 1=1               ");
		HttpSession session = request.getSession();
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
		if (StringUtils.isNotEmpty(check)) {
			sb.append("  AND RE.STATUS = :check  ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append("  AND SU.USER_NUM = :userNum   ");
		}
		if (StringUtils.isNotEmpty(userName)) {
			sb.append("  AND SU.USERNAME LIKE :userName   ");
		}
		if (StringUtils.isNotEmpty(organId)) {
			sb.append("  AND SU.organ_id = :organId   ");
		}
		if (StringUtils.isNotEmpty(deptId)) {
			sb.append("  AND SU.dept =:deptId   ");
		}
		sb.append("   ORDER BY SU.USER_NUM ASC  ");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
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
		if (StringUtils.isNotEmpty(userName)) {
			query.setParameter("userName", "%" + userName + "%");
		}
		if (StringUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}
		if (StringUtils.isNotEmpty(deptId)) {
			query.setParameter("deptId", deptId);
		}
		if (StringUtils.isNotEmpty(check)) {
			query.setParameter("check", check);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		transaction.commit();
		return queryList;
	}

	public List<Map> queryResearchType() {
		String sql = "select sd.dict_value dictvalue,sd.dict_name dictname from sys_dict sd where sd.dict_type=:dictType and sd.is_valid=:isValid";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_TYPE_RESEARCH);
		List<Map> queryList = query.list();
		transaction.commit();
		
		return queryList;
	}

	public void delResearch(String rid) {
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
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
		currSession.delete(sysUserResearch);
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
		String thesisPage = request.getParameter("thesisPage");
		String thesisDateStr = request.getParameter("thesisDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date thesisDate = null;
		try {
			if (StringUtils.isNotEmpty(thesisDateStr)) {
				thesisDate = sdf.parse(thesisDateStr);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
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
			thesisInfo.setThesisPage(thesisPage);
			thesisInfo.setThesisDate(thesisDate);
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
			thesisInfo.setThesisPage(thesisPage);
			thesisInfo.setThesisDate(thesisDate);
			thesisInfo.setThesisPass(Constants.SHEN_UN_CHECK);
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
			map.put("thesisDate", thesisInfo.getThesisDate());
			map.put("thesisPage", thesisInfo.getThesisPage());
			map.put("userNum", "");

		}
		transaction.commit();
		
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
			rewardInfo.setRewardPass(Constants.SHEN_UN_CHECK);
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
			patentInfo.setPatentPass(Constants.SHEN_UN_CHECK);
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
			projectInfo.setProjectPass(Constants.SHEN_UN_CHECK);
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
			map.put("projectThird", projectInfo.getProjectThird());
			map.put("endTime", projectInfo.getEndTime());
			map.put("startTime", projectInfo.getStartTime());
			map.put("projectContent", projectInfo.getProjectContent());
			map.put("projectFile", projectInfo.getProectFile());
			map.put("userNum", "201610000001");
		}
		transaction.commit();
		
		return map;
	}

	public List<Map> queryProjectType() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_PROJECT_TYPE);
		List<Map> queryList = query.list();
		transaction.commit();
		
		return queryList;
	}

	public List<Map> queryPatentType() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType ";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_PATENT_TYPE);
		List<Map> queryList = query.list();
		transaction.commit();
		
		return queryList;
	}

	public List<Map> queryPlaceType() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_PLACE_TYPE);
		List<Map> queryList = query.list();
		transaction.commit();
		
		return queryList;
	}

	public List<Map> queryRewardType() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_REWARD_TYPE);
		List<Map> queryList = query.list();
		transaction.commit();
		
		return queryList;
	}

	public List<Map> queryThesisIncluded() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_THESIS_INCLUDED);
		List<Map> queryList = query.list();
		transaction.commit();
		
		return queryList;
	}

	public List<Map> queryThesisType() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_THESIS_TYPE);
		List<Map> queryList = query.list();
		transaction.commit();
		
		return queryList;
	}

	public List<Map> queryPatentPeople() {
		String sql = "SELECT SD.DICT_VALUE DICTVALUE,SD.DICT_NAME  DICTNAME FROM SYS_DICT SD "
				+ "WHERE SD.IS_VALID= :isValid AND SD.DICT_TYPE= :dictType  ORDER BY SD.SEQ ASC";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_PATENT_PEOPLE);
		List<Map> queryList = query.list();
		transaction.commit();
		
		return queryList;
	}

	public List<Map> getCurrentThesisWorkload4Tec(BigInteger userNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("   SELECT                                                   ");
		sb.append("   	T.THESIS_NAME THESISNAME,                              ");
		sb.append("   	T.THESIS_PERIODICAL THESISPERIODICAL,                  ");
		sb.append("     T.THESIS_ID THESISID,                                  ");
		sb.append("     T.THESIS_AUTHOR THESISAUTHOR,                          ");
		sb.append(
				"    (SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=T.THESIS_TYPE) THESISTYPE,                                 ");
		sb.append("     T.THESIS_RECORD   THESISRECORD                         ");
		sb.append("   FROM                                                     ");
		sb.append("   	SYS_USER_RESEARCH SUR                                  ");
		sb.append("   LEFT JOIN THESIS_INFO T ON SUR.RESEARCH_ID = T.THESIS_ID ");
		sb.append("   WHERE                                                    ");
		sb.append("   	SUR.RESEARCH_TYPE = :researchThesis                    ");
		sb.append("   AND SUR.RESEARCH_USER_ID = :userId   AND T.THESIS_PASS=:pass      ");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchThesis", Constants.RESEARCH_TYPE_THESIS);
		query.setParameter("userId", userNum.toString());
		query.setParameter("pass", Constants.SHEN_PASS);
		List<Map> queryList = query.list();
		double workloadSum = 0;
		List<Map> list = new ArrayList<>();
		Map theisScore = getThesisScore();
		if (!queryList.isEmpty()) {
			for (Map map : queryList) {
				double workload = 0;
				String thesisPeriodical = (String) map.get("THESISPERIODICAL");
				if (StringUtils.isNotEmpty(thesisPeriodical)) {
					if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_SHOUSCI)) {
						if ((double) theisScore.get("thesisShousci") > workload)
							;
						{
							workload = (double) theisScore.get("thesisShousci");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_EIYUAN)) {
						if ((double) theisScore.get("thesisEiyuan") > workload)
							;
						{
							workload = (double) theisScore.get("thesisEiyuan");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_EISHOU)) {
						if ((double) theisScore.get("thesisEishou") > workload)
							;
						{
							workload = (double) theisScore.get("thesisEishou");
						}
					}else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_JIAOWU)) {
						if ((double) theisScore.get("thesisJiaowu") > workload)
							;
						{
							workload = (double) theisScore.get("thesisJiaowu");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_EISHOULU)) {
						if ((double) theisScore.get("thesisEishoulu") > workload)
							;
						{
							workload = (double) theisScore.get("thesisEishoulu");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_SHOULU)) {
						if ((double) theisScore.get("thesisShoulu") > workload)
							;
						{
							workload = (double) theisScore.get("thesisShoulu");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_CHINESE)) {
						if ((double) theisScore.get("thesisChinese") > workload)
							;
						{
							workload = (double) theisScore.get("thesisChinese");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_GUOJI)) {
						if ((double) theisScore.get("thesisGuoji") > workload)
							;
						{
							workload = (double) theisScore.get("thesisGuoji");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_OTHER)) {
						if ((double) theisScore.get("thesisOther") > workload)
							;
						{
							workload = (double) theisScore.get("thesisOther");
						}
					}
				} 
				workloadSum = workloadSum + workload;

				String[] str = thesisPeriodical.split(",");
				StringBuilder sql = new StringBuilder();
				sql.append("select GROUP_CONCAT(S.DICT_NAME) DICTNAME FROM SYS_DICT S WHERE S.DICT_VALUE IN(");
				int length = str.length;
				int i = 0;
				for (String strSql : str) {
					i++;
					strSql = "'" + strSql + "'";
					if (i == length) {
						sql.append(strSql);
					} else {
						sql.append(strSql + ",");
					}
				}
				sql.append("  ) ");
				getSession().beginTransaction();
				SQLQuery querySql = getSession().createSQLQuery(sql.toString());
				querySql.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> list4query = querySql.list();
				if (!list4query.isEmpty()) {
					Map mapStr = list4query.get(0);
					String dictName = (String) mapStr.get("DICTNAME");
					map.put("THESISPERIODICAL", dictName);
				}

				Map thesisMap = new HashMap<>();
				thesisMap.putAll(map);
				thesisMap.put("workload", workload);
				list.add(thesisMap);
			}
		}
		Map workMap = new HashMap<>();
		workMap.put("THESISNAME", "论文科研分");
		workMap.put("workload", workloadSum);
		list.add(workMap);
		return list;
	}

	public List<Map> getCurrentProjectWorkload4Tec(BigInteger userNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("  SELECT                                                        ");
		sb.append(" 	T.PROJECT_ID PROJECTID,                                    ");
		sb.append(" 	T.PROJECT_NAME PROJECTNAME,                                ");
		sb.append(" 	T.START_TIME STARTTIME,                                    ");
		sb.append(" 	T.END_TIME ENDTIME,                                        ");
		sb.append(" 	T.PROJECT_FUND PROJECTFUND,                                ");
		sb.append(" 	T.PROJECT_TYPE PROJECT,                                ");
		sb.append(" 	(SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=T.PROJECT_TYPE) PROJECTTYPE, ");
		sb.append(" 	T.PROJECT_SOURCE PROJECTSOURCE                             ");
		sb.append("  FROM                                                           ");
		sb.append("  	SYS_USER_RESEARCH SUR                                       ");
		sb.append("  LEFT JOIN PROJECT_INFO T ON SUR.RESEARCH_ID = T.PROJECT_ID     ");
		sb.append("  WHERE                                                          ");
		sb.append("  	SUR.RESEARCH_TYPE = :researchProject                        ");
		sb.append("  AND SUR.RESEARCH_USER_ID = :userId      AND T.PROJECT_PASS=:pass                       ");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchProject", Constants.RESEARCH_TYPE_PROJECT);
		query.setParameter("userId", userNum.toString());
		query.setParameter("pass", Constants.SHEN_PASS);
		List<Map> queryList = query.list();
		double workloadSum = 0;
		List<Map> list = new ArrayList<>();
		Map projectScore = getProjectScore();
		if (!queryList.isEmpty()) {
			for (Map map : queryList) {
				double workload4project = 0;
				String projectType = (String) map.get("PROJECT");
				double projectFund = (double) map.get("PROJECTFUND");
				int i = (int) projectFund;
				if (Constants.PROJECT_TYPE__GUO.equals(projectType)) {
					workload4project = (i
							* ((double) projectScore.get("guoFund") / (double) projectScore.get("guoFundLast")))
							* (double) projectScore.get("guoK");
				} else if (Constants.PROJECT_TYPE_SHEN.equals(projectType)) {
					workload4project = (i
							* ((double) projectScore.get("shenFund") / (double) projectScore.get("shenFundLast")))
							* (double) projectScore.get("shenK");
				} else if (Constants.PROJECT_TYPE_OTHER.equals(projectType)) {
					workload4project = (i
							* ((double) projectScore.get("otherFund") / (double) projectScore.get("otherFundLast")))
							* (double) projectScore.get("otherK");
				}

				workloadSum = workloadSum + workload4project;
				BigDecimal b = new BigDecimal(workload4project);
				double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

				Map thesisMap = new HashMap<>();
				thesisMap.putAll(map);
				thesisMap.put("workload", f1);
				list.add(thesisMap);
			}
		}
		Map workMap = new HashMap<>();
		BigDecimal b2 = new BigDecimal(workloadSum);
		double f2 = b2.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		workMap.put("PROJECTNAME", "项目科研总分");
		workMap.put("workload", f2);
		list.add(workMap);
		return list;
	}

	public List<Map> getCurrentRewardWorkload4Tec(BigInteger userNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("    SELECT                                                      ");
		sb.append("     T.REWARD_PLACE     REWARDPLACE,                              ");
		sb.append(" 	T.REWARD_ID REWARDID,                                       ");
		sb.append(" 	T.REWARD_TYPE TYPE,                                       ");
		sb.append(" 	T.REWARD_NAME REWARDNAME,                                   ");
		sb.append(" 	T.REWARD_TIME REWARDTIME,                                   ");
		sb.append(
				"     (SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=T.REWARD_TYPE) REWARDTYPE,                                   ");
		sb.append(" 	T.REWARD_ORGAN REWARDORGAN,                                 ");
		sb.append(" 	T.REWARD_USER REWARDUSER                                    ");
		sb.append("    FROM                                                        ");
		sb.append("    	SYS_USER_RESEARCH SUR                                      ");
		sb.append("    LEFT JOIN REWARD_INFO T ON SUR.RESEARCH_ID = T.REWARD_ID    ");
		sb.append("    WHERE                                                       ");
		sb.append("    	SUR.RESEARCH_TYPE = :researchReward                        ");
		sb.append("    AND SUR.RESEARCH_USER_ID = :userId      and t.REWARD_PASS=:pass                   ");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchReward", Constants.RESEARCH_TYPE_REWARD);
		query.setParameter("userId", userNum.toString());
		query.setParameter("pass", Constants.SHEN_PASS);
		List<Map> queryList = query.list();
		double workloadSum = 0;
		Map rewardScore = getRewardScore();
		List<Map> list = new ArrayList<>();
		if (!queryList.isEmpty()) {
			for (Map map : queryList) {
				String rewardType = (String) map.get("TYPE");
				String rewardPlace = (String) map.get("REWARDPLACE");
				double workload4reward = 0;
				if (Constants.REWARD_TYPE_SHEN1.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shenFirst");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shenSecond");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shenThird");
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shenFourth");
					} else {
						workload4reward = (double) rewardScore.get("shenFifth");
					}
				} else if (Constants.REWARD_TYPE_SHEN2.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen2First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen2Second");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen2Third");
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen2Fourth");
					} else {
						workload4reward = (double) rewardScore.get("shen2Fifth");
					}
				} else if (Constants.REWARD_TYPE_SHEN3.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen3First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen3Second");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen3Third");
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen3Fourth");
					} else {
						workload4reward = (double) rewardScore.get("shen3Fifth");
					}
				} else if (Constants.REWARD_TYPE_DISHI1.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishiFirst");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishiSecond");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishiFhird");
					}
				} else if (Constants.REWARD_TYPE_DISHI2.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishi2First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishi2Second");
					} else {
						workload4reward = (double) rewardScore.get("dishi2Third");
					}
				} else if (Constants.REWARD_TYPE_DISHI3.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishi3First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishi3Second");
					} else {
						workload4reward = (double) rewardScore.get("dishi3Third");
					}
				} else if (Constants.REWARD_TYPE_XIAO1.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("schoolFirst");
					}
				} else if (Constants.REWARD_TYPE_XIAO2.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("schoolSecond");
					}
				} else if (Constants.REWARD_TYPE_XIAO3.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("schoolThird");
					}
				} else if (Constants.REWARD_TYPE_JIAO.equals(rewardType)) {
					workload4reward = (double) rewardScore.get("youxiuJiaoxue");
				} else if (Constants.REWARD_TYPE_QING.equals(rewardType)) {
					workload4reward = (double) rewardScore.get("yongTeach");
				}
				workloadSum = workloadSum + workload4reward;
				Map thesisMap = new HashMap<>();
				thesisMap.putAll(map);
				thesisMap.put("workload", workload4reward);
				list.add(thesisMap);
			}
		}
		Map workMap = new HashMap<>();
		workMap.put("REWARDNAME", "奖励科研总分");
		workMap.put("workload", workloadSum);
		list.add(workMap);
		return list;
	}

	public List<Map> getCurrentPatentWorkload4Tec(BigInteger userNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("  SELECT                                                       ");
		sb.append("  	T.PATENT_TYPE TYPE,                                       ");
		sb.append("  	T.PATENT_FIRST FIRST,                                     ");
		sb.append("  	T.PATENT_IS_TRANSFER ISTRANSFER,                          ");
		sb.append(" 	T.PATENT_ID PATENTID,                                     ");
		sb.append(" 	T.PATENT_CREATER PATENTCREATER,                           ");
		sb.append(
				" 	(SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=T.PATENT_TYPE) PATENTTYPE,                                 ");
		sb.append(" 	T.PATENT_NAME PATENTNAME,                                 ");
		sb.append(
				" 	(SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=T.PATENT_PEOPLE) PATENTPEOPLE,                             ");
		sb.append(" 	T.PATENT_DATE PATENTDATE,                                 ");
		sb.append(" 	T.PATENT_FIRST PATENTFIRST,                               ");
		sb.append(" 	T.PATENT_IS_TRANSFER PATENTISTRANSFER                     ");
		sb.append("  FROM                                                         ");
		sb.append("  	SYS_USER_RESEARCH SUR                                     ");
		sb.append("  LEFT JOIN PATENT_INFO T ON SUR.RESEARCH_ID = T.PATENT_ID     ");
		sb.append("  WHERE                                                        ");
		sb.append("  	SUR.RESEARCH_TYPE = :researchPatent                       ");
		sb.append("  AND SUR.RESEARCH_USER_ID = :userId     and  T.PATENT_PASS =:pass                     ");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchPatent", Constants.RESEARCH_TYPE_PATENT);
		query.setParameter("userId", userNum.toString());
		query.setParameter("pass", Constants.SHEN_PASS);
		List<Map> queryList = query.list();
		double workloadSum = 0;
		Map patentScore = getPatentScore();
		List<Map> list = new ArrayList<>();
		if (queryList != null && queryList.size() > 0) {
			for (Map map : queryList) {
				String patenttype = (String) map.get("TYPE");
				String patentFirst = (String) map.get("FIRST");
				String patentIsTransfer = (String) map.get("ISTRANSFER");
				if (StringUtils.isNotEmpty(patentFirst)) {
					if (Constants.YES.equals(patentFirst)) {
						map.put("PATENTFIRSTSTR", "是");
					} else {
						map.put("PATENTFIRSTSTR", "否");
					}
				} else {
					map.put("PATENTFIRSTSTR", "否");
				}
				if (StringUtils.isNotEmpty(patentIsTransfer)) {
					if (Constants.YES.equals(patentIsTransfer)) {
						map.put("PATENTISTRANSFERSTR", "是");
					} else {
						map.put("PATENTISTRANSFERSTR", "否");
					}
				} else {
					map.put("PATENTISTRANSFERSTR", "否");
				}
				double workload4Patent = 0;
				if (Constants.PATENT_TYPE_FAMING.equals(patenttype)) {
					if (Constants.YES.equals(patentFirst)) {
						workload4Patent = (double) patentScore.get("inventFist");
						if (Constants.YES.equals(patentIsTransfer)) {
							workload4Patent = workload4Patent + (double) patentScore.get("inventIsMove");
						}
					}
				} else if (Constants.PATENT_TYPE_SHIYONG.equals(patenttype)) {
					if (Constants.YES.equals(patentFirst)) {
						workload4Patent = (double) patentScore.get("practicalFirst");
						if (Constants.YES.equals(patentIsTransfer)) {
							workload4Patent = workload4Patent + (double) patentScore.get("practicalIsMove");
						}
					}
				} else if (Constants.PATENT_TYPE_WAIGAUAN.equals(patenttype)) {
					if (Constants.YES.equals(patentFirst)) {
						workload4Patent = (double) patentScore.get("viewFirst");
						if (Constants.YES.equals(patentIsTransfer)) {
							workload4Patent = workload4Patent + (double) patentScore.get("viewIsMove");
						}
					}
				} else if (Constants.PATENT_TYPE_SOFTWAR.equals(patenttype)) {
					if (Constants.YES.equals(patentFirst)) {
						workload4Patent = (double) patentScore.get("softFirst");
						if (Constants.YES.equals(patentIsTransfer)) {
							workload4Patent = workload4Patent + (double) patentScore.get("softIsMove");
						}
					}
				}
				workloadSum = workloadSum + workload4Patent;
				Map thesisMap = new HashMap<>();
				thesisMap.putAll(map);
				thesisMap.put("workload", workload4Patent);
				list.add(thesisMap);
			}
		}
		Map workMap = new HashMap<>();
		workMap.put("PATENTNAME", "专利科研总分");
		workMap.put("workload", workloadSum);
		list.add(workMap);
		return list;

	}

	public List<Map> queryThesisList(HttpServletRequest request) {
		String userNum = request.getParameter("userNum");
		String thesisName = request.getParameter("thesisName");
		String thesisType = request.getParameter("thesisType");
		String userName = request.getParameter("userName");
		String organId = request.getParameter("organId");
		String deptId = request.getParameter("deptId");
		String thesisStartDate = request.getParameter("thesisStartDate");
		String thesisEndDate = request.getParameter("thesisEndDate");

		StringBuilder sb = new StringBuilder();
		sb.append("  SELECT                                                       ");
		sb.append("  	SUR.RID,                                                  ");
		sb.append("    SU.USER_NUM USERNUM,                                       ");
		sb.append("    SU.USERNAME ,                                              ");
		sb.append("    SU.USER_ID USERID,                                         ");
		sb.append("    SU.MOBILE,                                                 ");
		sb.append("    SU.EMAIL,                                                  ");
		sb.append("    TI.THESIS_ID THESISID,                                     ");
		sb.append("    TI.THESIS_AUTHOR THESISAUTHOR,                             ");
		sb.append("    TI.THESIS_PAGE THESISPAGE,                                 ");
		sb.append("    TI.THESIS_PASS THESISPASS,                                 ");
		sb.append("    TI.THESIS_DATE THESISDATE,                                 ");
		sb.append("    TI.THESIS_NAME THESISNAME,                                 ");
		sb.append(
				"    (SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=TI.THESIS_TYPE) THESISTYPE,                                 ");
		sb.append("    TI.THESIS_PERIODICAL THESISPERIODICAL,                     ");
		sb.append("    TI.THESIS_RECORD   THESISRECORD                            ");
		sb.append("  FROM                                                         ");
		sb.append("  	SYS_USER_RESEARCH SUR                                     ");
		sb.append("  LEFT JOIN SYS_USER SU ON SU.USER_NUM = SUR.RESEARCH_USER_ID  ");
		sb.append("  LEFT JOIN THESIS_INFO TI ON TI.THESIS_ID = SUR.RESEARCH_ID   ");
		sb.append("  WHERE                                                        ");
		sb.append("  	SUR.RESEARCH_TYPE = :researchThesis                       ");

		HttpSession session = request.getSession();
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			sb.append(" AND SU.USER_NUM = :userNumTec  ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" AND SU.USER_NUM = :userNum ");
		}
		if (StringUtils.isNotEmpty(thesisName)) {
			sb.append(" AND TI.THESIS_NAME LIKE :thesisName ");
		}
		if (StringUtils.isNotEmpty(thesisType)) {
			sb.append(" AND TI.THESIS_TYPE = :thesisType ");
		}
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" AND SU.USERNAME LIKE :userName ");
		}
		if (StringUtils.isNotEmpty(organId)) {
			sb.append(" AND SU.ORGAN_ID = :organId ");
		}
		if (StringUtils.isNotEmpty(deptId)) {
			sb.append(" AND SU.DEPT = :deptId ");
		}
		if (StringUtils.isNotEmpty(thesisStartDate)) {
			sb.append(" AND TI.THESIS_DATE >= :thesisStartDate ");
		}
		if (StringUtils.isNotEmpty(thesisEndDate)) {
			sb.append(" AND TI.THESIS_DATE <= :thesisEndDate ");
		}

		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setParameter("researchThesis", Constants.RESEARCH_TYPE_THESIS);
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		if (StringUtils.isNotEmpty(thesisName)) {
			query.setParameter("thesisName", "%" + thesisName + "%");
		}
		if (StringUtils.isNotEmpty(thesisType)) {
			query.setParameter("thesisType", thesisType);
		}
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			query.setParameter("userNumTec", userNum4Curr);
		}
		if (StringUtils.isNotEmpty(userName)) {
			query.setParameter("userName", "%" + userName + "%");
		}
		if (StringUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}
		if (StringUtils.isNotEmpty(deptId)) {
			query.setParameter("deptId", deptId);
		}
		if (StringUtils.isNotEmpty(thesisStartDate)) {
			query.setParameter("thesisStartDate", thesisStartDate);
		}
		if (StringUtils.isNotEmpty(thesisEndDate)) {
			query.setParameter("thesisEndDate", thesisEndDate);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		if (queryList != null && queryList.size() > 0) {
			for (Map map : queryList) {
				String thesisPeriodical = (String) map.get("THESISPERIODICAL");
				if (StringUtils.isNotEmpty(thesisPeriodical)) {
					String[] str = thesisPeriodical.split(",");
					StringBuilder sql = new StringBuilder();
					sql.append("select GROUP_CONCAT(S.DICT_NAME) DICTNAME FROM SYS_DICT S WHERE S.DICT_VALUE IN(");
					int length = str.length;
					int i = 0;
					for (String strSql : str) {
						i++;
						strSql = "'" + strSql + "'";
						if (i == length) {
							sql.append(strSql);
						} else {
							sql.append(strSql + ",");
						}
					}
					sql.append("  ) ");
					SQLQuery querySql = getSession().createSQLQuery(sql.toString());
					querySql.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List<Map> list = querySql.list();
					if (list != null && list.size() > 0) {
						Map mapStr = list.get(0);
						String dictName = (String) mapStr.get("DICTNAME");
						map.put("THESISPERIODICAL", dictName);
					}
				}
			}
		}
		transaction.commit();
		return queryList;
	}

	public List<Map> queryProjectList(HttpServletRequest request) {
		String userNum = request.getParameter("userNum");
		String projectName = request.getParameter("projectName");
		String projectType = request.getParameter("projectType");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String organId = request.getParameter("organId");
		String deptId = request.getParameter("deptId");
		String userName = request.getParameter("userName");

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT                                                          ");
		sb.append(" 	SUR.RID,                                                    ");
		sb.append(" 	SU.USER_NUM USERNUM ,                                       ");
		sb.append(" 	SU.USERNAME,                                                ");
		sb.append(" 	SU.USER_ID USERID,                                          ");
		sb.append(" 	SU.MOBILE,                                                  ");
		sb.append(" 	SU.EMAIL,                                                   ");
		sb.append(" 	TI.PROJECT_ID PROJECTID,                                    ");
		sb.append(" 	TI.PROJECT_NAME PROJECTNAME,                                ");
		sb.append(" 	TI.PROJECT_PASS PROJECTPASS,                                ");
		sb.append(" 	TI.START_TIME STARTTIME,                                    ");
		sb.append(" 	TI.END_TIME ENDTIME,                                        ");
		sb.append(" 	TI.PROJECT_FUND PROJECTFUND,                                ");
		sb.append(
				" 	(SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=TI.PROJECT_TYPE) PROJECTTYPE,                                ");
		sb.append(" 	TI.PROJECT_SOURCE PROJECTSOURCE                             ");
		sb.append(" FROM                                                            ");
		sb.append(" 	SYS_USER_RESEARCH SUR                                       ");
		sb.append(" LEFT JOIN SYS_USER SU ON SU.USER_NUM = SUR.RESEARCH_USER_ID     ");
		sb.append(" LEFT JOIN PROJECT_INFO TI ON TI.PROJECT_ID = SUR.RESEARCH_ID    ");
		sb.append(" WHERE                                                           ");
		sb.append(" 	SUR.RESEARCH_TYPE = :researchProject                        ");

		HttpSession session = request.getSession();
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			sb.append(" AND SU.USER_NUM = :userNumTec  ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" AND SU.USER_NUM = :userNum ");
		}
		if (StringUtils.isNotEmpty(projectName)) {
			sb.append(" AND TI.PROJECT_NAME LIKE :projectName ");
		}
		if (StringUtils.isNotEmpty(projectType)) {
			sb.append(" AND TI.PROJECT_TYPE = :projectType ");
		}
		if (StringUtils.isNotEmpty(startTime)) {
			sb.append(" AND TI.START_TIME = :startTime ");
		}
		if (StringUtils.isNotEmpty(endTime)) {
			sb.append(" AND TI.END_TIME = :endTime ");
		}
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" AND SU.USERNAME LIKE :userName ");
		}
		if (StringUtils.isNotEmpty(organId)) {
			sb.append(" AND SU.ORGAN_ID = :organId ");
		}
		if (StringUtils.isNotEmpty(deptId)) {
			sb.append(" AND SU.DEPT = :deptId ");
		}
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setParameter("researchProject", Constants.RESEARCH_TYPE_PROJECT);
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		if (StringUtils.isNotEmpty(projectName)) {
			query.setParameter("projectName", "%" + projectName + "%");
		}
		if (StringUtils.isNotEmpty(projectType)) {
			query.setParameter("projectType", projectType);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			query.setParameter("startTime", startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			query.setParameter("endTime", endTime);
		}
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			query.setParameter("userNumTec", userNum4Curr);
		}
		if (StringUtils.isNotEmpty(userName)) {
			query.setParameter("userName", "%" + userName + "%");
		}
		if (StringUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}
		if (StringUtils.isNotEmpty(deptId)) {
			query.setParameter("deptId", deptId);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		transaction.commit();
		return queryList;
	}

	public List<Map> queryRewardList(HttpServletRequest request) {
		String userNum = request.getParameter("userNum");
		String rewardName = request.getParameter("rewardName");
		String rewardType = request.getParameter("rewardType");
		String rewardOrgan = request.getParameter("rewardOrgan");
		String rewardTime = request.getParameter("rewardTime");
		String organId = request.getParameter("organId");
		String deptId = request.getParameter("deptId");
		String userName = request.getParameter("userName");

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT                                                           ");
		sb.append(" 	SUR.RID,                                                     ");
		sb.append(" 	SU.USER_NUM USERNUM,                                         ");
		sb.append(" 	SU.USERNAME,                                                 ");
		sb.append(" 	SU.USER_ID USERID,                                           ");
		sb.append(" 	SU.MOBILE,                                                   ");
		sb.append(" 	SU.EMAIL,                                                    ");
		sb.append(" 	TI.REWARD_ID REWARDID,                                       ");
		sb.append(" 	TI.REWARD_NAME REWARDNAME,                                   ");
		sb.append(" 	TI.REWARD_PASS REWARDPASS,                                   ");
		sb.append(" 	TI.REWARD_TIME REWARDTIME,                                   ");
		sb.append(
				"     (SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=TI.REWARD_TYPE)REWARDTYPE,                                   ");
		sb.append(" 	TI.REWARD_ORGAN REWARDORGAN,                                 ");
		sb.append(" 	TI.REWARD_USER REWARDUSER                                    ");
		sb.append(" FROM                                                             ");
		sb.append(" 	SYS_USER_RESEARCH SUR                                        ");
		sb.append(" LEFT JOIN SYS_USER SU ON SU.USER_NUM = SUR.RESEARCH_USER_ID      ");
		sb.append(" LEFT JOIN REWARD_INFO TI ON TI.REWARD_ID = SUR.RESEARCH_ID       ");
		sb.append(" WHERE                                                            ");
		sb.append(" 	SUR.RESEARCH_TYPE = :researchReward                          ");

		HttpSession session = request.getSession();
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			sb.append(" AND SU.USER_NUM = :userNumTec  ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" AND SU.USER_NUM = :userNum ");
		}
		if (StringUtils.isNotEmpty(rewardName)) {
			sb.append(" AND TI.REWARD_NAME LIKE :rewardName ");
		}
		if (StringUtils.isNotEmpty(rewardType)) {
			sb.append(" AND TI.REWARD_TYPE = :rewardType ");
		}
		if (StringUtils.isNotEmpty(rewardOrgan)) {
			sb.append(" AND TI.REWARD_ORGAN LIKE :rewardOrgan ");
		}
		if (StringUtils.isNotEmpty(rewardTime)) {
			sb.append(" AND TI.REWARD_TIME = :rewardTime ");
		}
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" AND SU.USERNAME LIKE :userName ");
		}
		if (StringUtils.isNotEmpty(organId)) {
			sb.append(" AND SU.ORGAN_ID = :organId ");
		}
		if (StringUtils.isNotEmpty(deptId)) {
			sb.append(" AND SU.DEPT = :deptId ");
		}
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setParameter("researchReward", Constants.RESEARCH_TYPE_REWARD);
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		if (StringUtils.isNotEmpty(rewardName)) {
			query.setParameter("rewardName", "%" + rewardName + "%");
		}
		if (StringUtils.isNotEmpty(rewardType)) {
			query.setParameter("rewardType", rewardType);
		}
		if (StringUtils.isNotEmpty(rewardOrgan)) {
			query.setParameter("rewardOrgan", "%" + rewardOrgan + "%");
		}
		if (StringUtils.isNotEmpty(rewardTime)) {
			query.setParameter("rewardTime", rewardTime);
		}
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			query.setParameter("userNumTec", userNum4Curr);
		}
		if (StringUtils.isNotEmpty(userName)) {
			query.setParameter("userName", "%" + userName + "%");
		}
		if (StringUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}
		if (StringUtils.isNotEmpty(deptId)) {
			query.setParameter("deptId", deptId);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		transaction.commit();
		return queryList;
	}

	public List<Map> queryPatentList(HttpServletRequest request) {
		String userNum = request.getParameter("userNum");
		String patentName = request.getParameter("patentName");
		String patentType = request.getParameter("patentType");
		String patentPeople = request.getParameter("patentPeople");
		String patentDate = request.getParameter("patentDate");
		String patentFirst = request.getParameter("patentFirst");
		String patentIsTransfer = request.getParameter("patentIsTransfer");
		String organId = request.getParameter("organId");
		String deptId = request.getParameter("deptId");
		String userName = request.getParameter("userName");

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT                                                         ");
		sb.append(" 	SUR.RID,                                                   ");
		sb.append(" 	SU.USER_NUM USERNUM,                                       ");
		sb.append(" 	SU.USERNAME,                                               ");
		sb.append(" 	SU.USER_ID USERID,                                         ");
		sb.append(" 	SU.MOBILE,                                                 ");
		sb.append(" 	SU.EMAIL,                                                  ");
		sb.append(" 	TI.PATENT_ID PATENTID,                                     ");
		sb.append(" 	TI.PATENT_PASS PATENTPASS,                                 ");
		sb.append(" 	TI.PATENT_CREATER PATENTCREATER,                           ");
		sb.append(
				" 	(SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=TI.PATENT_TYPE) PATENTTYPE,                                 ");
		sb.append(" 	TI.PATENT_NAME PATENTNAME,                                 ");
		sb.append(
				" 	(SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=TI.PATENT_PEOPLE) PATENTPEOPLE,                             ");
		sb.append(" 	TI.PATENT_DATE PATENTDATE,                                 ");
		sb.append(" 	TI.PATENT_FIRST PATENTFIRST,                               ");
		sb.append(" 	TI.PATENT_IS_TRANSFER PATENTISTRANSFER                     ");
		sb.append(" FROM                                                           ");
		sb.append(" 	SYS_USER_RESEARCH SUR                                      ");
		sb.append(" LEFT JOIN SYS_USER SU ON SU.USER_NUM = SUR.RESEARCH_USER_ID    ");
		sb.append(" LEFT JOIN PATENT_INFO TI ON TI.PATENT_ID = SUR.RESEARCH_ID     ");
		sb.append(" WHERE                                                          ");
		sb.append(" 	SUR.RESEARCH_TYPE = :researchPatent                        ");

		HttpSession session = request.getSession();
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			sb.append(" AND SU.USER_NUM = :userNumTec  ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" AND SU.USER_NUM = :userNum ");
		}
		if (StringUtils.isNotEmpty(patentName)) {
			sb.append(" AND TI.PATENT_NAME LIKE :patentName ");
		}
		if (StringUtils.isNotEmpty(patentType)) {
			sb.append(" AND TI.PATENT_TYPE = :patentType ");
		}
		if (StringUtils.isNotEmpty(patentPeople)) {
			sb.append(" AND TI.PATENT_PEOPLE = :patentPeople ");
		}
		if (StringUtils.isNotEmpty(patentDate)) {
			sb.append(" AND TI.PATENT_DATE = :patentDate ");
		}

		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" AND SU.USERNAME LIKE :userName ");
		}
		if (StringUtils.isNotEmpty(organId)) {
			sb.append(" AND SU.ORGAN_ID = :organId ");
		}
		if (StringUtils.isNotEmpty(deptId)) {
			sb.append(" AND SU.DEPT = :deptId ");
		}
		if (StringUtils.isNotEmpty(patentFirst)) {
			if (patentFirst.equals(Constants.YES)) {
				sb.append("  AND TI.PATENT_FIRST = :patentFirst       ");
			} else {
				patentFirst = Constants.YES;
				sb.append("  AND TI.PATENT_FIRST != :patentFirst     ");
			}
		}
		if (StringUtils.isNotEmpty(patentIsTransfer)) {
			if (patentIsTransfer.equals(Constants.YES)) {
				sb.append("  AND TI.PATENT_IS_TRANSFER = :patentIsTransfer       ");
			} else {
				patentIsTransfer = Constants.YES;
				sb.append("  AND TI.PATENT_IS_TRANSFER != :patentIsTransfer      ");
			}
		}
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setParameter("researchPatent", Constants.RESEARCH_TYPE_PATENT);
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		if (StringUtils.isNotEmpty(patentName)) {
			query.setParameter("patentName", "%" + patentName + "%");
		}
		if (StringUtils.isNotEmpty(patentType)) {
			query.setParameter("patentType", patentType);
		}
		if (StringUtils.isNotEmpty(patentPeople)) {
			query.setParameter("patentPeople", patentPeople);
		}
		if (StringUtils.isNotEmpty(patentDate)) {
			query.setParameter("patentDate", patentDate);
		}
		if (StringUtils.isNotEmpty(patentFirst)) {
			query.setParameter("patentFirst", patentFirst);
		}
		if (StringUtils.isNotEmpty(patentIsTransfer)) {
			query.setParameter("patentIsTransfer", patentIsTransfer);
		}
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			query.setParameter("userNumTec", userNum4Curr);
		}
		if (StringUtils.isNotEmpty(userName)) {
			query.setParameter("userName", "%" + userName + "%");
		}
		if (StringUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}
		if (StringUtils.isNotEmpty(deptId)) {
			query.setParameter("deptId", deptId);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		transaction.commit();
		return queryList;
	}

	public Map getThesisScore() {
		Map map = new HashMap();
		Transaction transaction = getSession().beginTransaction();

		ThesisScore thesisScore = (ThesisScore) getSession().get(ThesisScore.class, Constants.THESIS_SCORE_ID);
		map.put("thesisChinese", thesisScore.getThesisChinese());
		map.put("thesisEishou", thesisScore.getThesisEishou());
		map.put("thesisEishoulu", thesisScore.getThesisEishoulu());
		map.put("thesisEiyuan", thesisScore.getThesisEiyuan());
		map.put("thesisGuoji", thesisScore.getThesisGuoji());
		map.put("thesisJiaowu", thesisScore.getThesisJiaowu());
		map.put("thesisOther", thesisScore.getThesisOther());
		map.put("thesisShousci", thesisScore.getThesisShousci());
		map.put("thesisShoulu", thesisScore.getThesisShoulu());

		transaction.commit();
		
		return map;
	}

	public void saveThesisScore(HttpServletRequest request) {
		String thesisChinese = request.getParameter("thesisChinese");
		String thesisEishou = request.getParameter("thesisEishou");
		String thesisEishoulu = request.getParameter("thesisEishoulu");
		String thesisEiyuan = request.getParameter("thesisEiyuan");
		String thesisGuoji = request.getParameter("thesisGuoji");
		String thesisJiaowu = request.getParameter("thesisJiaowu");
		String thesisOther = request.getParameter("thesisOther");
		String thesisShousci = request.getParameter("thesisShousci");
		String thesisShoulu = request.getParameter("thesisShoulu");

		Transaction transaction = getSession().beginTransaction();

		ThesisScore thesisScore = (ThesisScore) getSession().get(ThesisScore.class, Constants.THESIS_SCORE_ID);
		thesisScore.setThesisChinese(Double.parseDouble(thesisChinese));
		thesisScore.setThesisEishou(Double.parseDouble(thesisEishou));
		thesisScore.setThesisEishoulu(Double.parseDouble(thesisEishoulu));
		thesisScore.setThesisEiyuan(Double.parseDouble(thesisEiyuan));
		thesisScore.setThesisGuoji(Double.parseDouble(thesisGuoji));
		thesisScore.setThesisJiaowu(Double.parseDouble(thesisJiaowu));
		thesisScore.setThesisOther(Double.parseDouble(thesisOther));
		thesisScore.setThesisShoulu(Double.parseDouble(thesisShoulu));
		thesisScore.setThesisShousci(Double.parseDouble(thesisShousci));
		transaction.commit();

	}

	public Map getProjectScore() {
		Map map = new HashMap();
		Transaction transaction = getSession().beginTransaction();

		ProjectScore projectScore = (ProjectScore) getSession().get(ProjectScore.class, Constants.PROJECT_SCORE_ID);
		map.put("guoK", projectScore.getGuoK());
		map.put("guoFund", projectScore.getGuoFund());
		map.put("guoFundLast", projectScore.getGuoFundLast());
		map.put("otherK", projectScore.getOtherK());
		map.put("otherFund", projectScore.getOtherFund());
		map.put("otherFundLast", projectScore.getOtherFundLast());
		map.put("shenFund", projectScore.getShenFund());
		map.put("shenFundLast", projectScore.getShenFundLast());
		map.put("shenK", projectScore.getShenK());
		transaction.commit();
		
		return map;
	}

	public void saveProjectScore(HttpServletRequest request) {
		String guoK = request.getParameter("guoK");
		String guoFund = request.getParameter("guoFund");
		String guoFundLast = request.getParameter("guoFundLast");
		String otherK = request.getParameter("otherK");
		String otherFund = request.getParameter("otherFund");
		String otherFundLast = request.getParameter("otherFundLast");
		String shenFund = request.getParameter("shenFund");
		String shenFundLast = request.getParameter("shenFundLast");
		String shenK = request.getParameter("shenK");

		Transaction transaction = getSession().beginTransaction();

		ProjectScore projectScore = (ProjectScore) getSession().get(ProjectScore.class, Constants.PROJECT_SCORE_ID);
		projectScore.setGuoFund(Double.parseDouble(guoFund));
		projectScore.setGuoFundLast(Double.parseDouble(guoFundLast));
		projectScore.setGuoK(Double.parseDouble(guoK));
		projectScore.setOtherFund(Double.parseDouble(otherFund));
		projectScore.setOtherFundLast(Double.parseDouble(otherFundLast));
		projectScore.setOtherK(Double.parseDouble(otherK));
		projectScore.setShenFund(Double.parseDouble(shenFund));
		projectScore.setShenFundLast(Double.parseDouble(shenFundLast));
		projectScore.setShenK(Double.parseDouble(shenK));
		transaction.commit();

	}

	public Map getPatentScore() {
		Map map = new HashMap();
		Transaction transaction = getSession().beginTransaction();

		PatentScore patentScore = (PatentScore) getSession().get(PatentScore.class, Constants.PATENT_SCORE_ID);
		map.put("inventFist", patentScore.getInventFist());
		map.put("inventIsMove", patentScore.getInventIsMove());
		map.put("practicalFirst", patentScore.getPracticalFirst());
		map.put("practicalIsMove", patentScore.getPracticalIsMove());
		map.put("viewFirst", patentScore.getViewFirst());
		map.put("viewIsMove", patentScore.getViewIsMove());
		map.put("softFirst", patentScore.getSoftFirst());
		map.put("softIsMove", patentScore.getViewIsMove());
		transaction.commit();
		
		return map;
	}

	public void savetPatentScore(HttpServletRequest request) {
		String inventFist = request.getParameter("inventFist");
		String inventIsMove = request.getParameter("inventIsMove");
		String practicalFirst = request.getParameter("practicalFirst");
		String practicalIsMove = request.getParameter("practicalIsMove");
		String viewFirst = request.getParameter("viewFirst");
		String viewIsMove = request.getParameter("viewIsMove");
		String softFirst = request.getParameter("softFirst");
		String softIsMove = request.getParameter("softIsMove");

		Transaction transaction = getSession().beginTransaction();

		PatentScore patentScore = (PatentScore) getSession().get(PatentScore.class, Constants.PATENT_SCORE_ID);
		patentScore.setInventFist(Double.parseDouble(inventFist));
		patentScore.setInventIsMove(Double.parseDouble(inventIsMove));
		patentScore.setViewFirst(Double.parseDouble(viewFirst));
		patentScore.setViewIsMove(Double.parseDouble(viewIsMove));
		patentScore.setPracticalFirst(Double.parseDouble(practicalFirst));
		patentScore.setPracticalIsMove(Double.parseDouble(practicalIsMove));
		patentScore.setSoftFirst(Double.parseDouble(softFirst));
		patentScore.setSoftIsMove(Double.parseDouble(softIsMove));

		transaction.commit();

	}

	public Map getRewardScore() {
		Map map = new HashMap();
		Transaction transaction = getSession().beginTransaction();

		RewardScore rewardScore = (RewardScore) getSession().get(RewardScore.class, Constants.REWARD_SCORE_ID);
		map.put("dishiFirst", rewardScore.getDishiFirst());
		map.put("dishiSecond", rewardScore.getDishiSecond());
		map.put("dishiThird", rewardScore.getDishiThird());
		map.put("dishi2First", rewardScore.getDishi2First());
		map.put("dishi2Third", rewardScore.getDishi2Third());
		map.put("dishi2Second", rewardScore.getDishi2Second());
		map.put("dishi3First", rewardScore.getDishi3First());
		map.put("dishi3Second", rewardScore.getDishi3Second());
		map.put("dishi3Third", rewardScore.getDishi3Third());
		map.put("shenFifth", rewardScore.getShenFifth());
		map.put("shenFirst", rewardScore.getShenFirst());
		map.put("shenFourth", rewardScore.getShenFourth());
		map.put("shenThird", rewardScore.getShenThird());
		map.put("shenSecond", rewardScore.getShenSecond());
		map.put("shen2Fifth", rewardScore.getShen2Fifth());
		map.put("shen2First", rewardScore.getShen2First());
		map.put("shen2Fourth", rewardScore.getShen2Fourth());
		map.put("shen2Third", rewardScore.getShen2Third());
		map.put("shen2Second", rewardScore.getShen2Second());
		map.put("shen3Fifth", rewardScore.getShen3Fifth());
		map.put("shen3First", rewardScore.getShen3First());
		map.put("shen3Fourth", rewardScore.getShen3Fourth());
		map.put("shen3Third", rewardScore.getShen3Third());
		map.put("shen3Second", rewardScore.getShen3Second());
		map.put("schoolSecond", rewardScore.getSchoolSecond());
		map.put("schoolThird", rewardScore.getSchoolThird());
		map.put("schoolFirst", rewardScore.getSchoolFirst());
		map.put("youxiuJiaoxue", rewardScore.getYouxiuJiaoxue());
		map.put("yongTeach", rewardScore.getYongTeach());
		transaction.commit();
		
		return map;
	}

	public void savetRewardScore(HttpServletRequest request) {
		String dishiFirst = request.getParameter("dishiFirst");
		String dishiSecond = request.getParameter("dishiSecond");
		String dishiThird = request.getParameter("dishiThird");
		String dishi2First = request.getParameter("dishi2First");
		String dishi2Third = request.getParameter("dishi2Third");
		String dishi2Second = request.getParameter("dishi2Second");
		String dishi3First = request.getParameter("dishi3First");
		String dishi3Second = request.getParameter("dishi3Second");
		String dishi3Third = request.getParameter("dishi3Third");
		String shenFifth = request.getParameter("shenFifth");
		String shenFirst = request.getParameter("shenFirst");
		String shenFourth = request.getParameter("shenFourth");
		String shenThird = request.getParameter("shenThird");
		String shenSecond = request.getParameter("shenSecond");
		String shen2Fifth = request.getParameter("shen2Fifth");
		String shen2First = request.getParameter("shen2First");
		String shen2Fourth = request.getParameter("shen2Fourth");
		String shen2Third = request.getParameter("shen2Third");
		String shen2Second = request.getParameter("shen2Second");
		String shen3Fifth = request.getParameter("shen3Fifth");
		String shen3First = request.getParameter("shen3First");
		String shen3Fourth = request.getParameter("shen3Fourth");
		String shen3Third = request.getParameter("shen3Third");
		String shen3Second = request.getParameter("shen3Second");
		String youxiuJiaoxue = request.getParameter("youxiuJiaoxue");
		String yongTeach = request.getParameter("yongTeach");
		String schoolSecond = request.getParameter("schoolSecond");
		String schoolThird = request.getParameter("schoolThird");
		String schoolFirst = request.getParameter("schoolFirst");

		Transaction transaction = getSession().beginTransaction();

		RewardScore rewardScore = (RewardScore) getSession().get(RewardScore.class, Constants.REWARD_SCORE_ID);
		rewardScore.setSchoolFirst(Double.parseDouble(schoolFirst));
		rewardScore.setSchoolSecond(Double.parseDouble(schoolSecond));
		rewardScore.setSchoolThird(Double.parseDouble(schoolThird));
		rewardScore.setDishiFirst(Double.parseDouble(dishiFirst));
		rewardScore.setDishiSecond(Double.parseDouble(dishiSecond));
		rewardScore.setDishiThird(Double.parseDouble(dishiThird));
		rewardScore.setDishi2First(Double.parseDouble(dishi2First));
		rewardScore.setDishi2Second(Double.parseDouble(dishi2Second));
		rewardScore.setDishi2Third(Double.parseDouble(dishi2Third));
		rewardScore.setDishi3First(Double.parseDouble(dishi3First));
		rewardScore.setDishi3Second(Double.parseDouble(dishi3Second));
		rewardScore.setDishi3Third(Double.parseDouble(dishi3Third));
		rewardScore.setShenFirst(Double.parseDouble(shenFirst));
		rewardScore.setShenSecond(Double.parseDouble(shenSecond));
		rewardScore.setShenThird(Double.parseDouble(shenThird));
		rewardScore.setShenFourth(Double.parseDouble(shenFourth));
		rewardScore.setShenFifth(Double.parseDouble(shenFifth));
		rewardScore.setShen2First(Double.parseDouble(shen2First));
		rewardScore.setShen2Second(Double.parseDouble(shen2Second));
		rewardScore.setShen2Third(Double.parseDouble(shen2Third));
		rewardScore.setShen2Fourth(Double.parseDouble(shen2Fourth));
		rewardScore.setShen2Fifth(Double.parseDouble(shen2Fifth));
		rewardScore.setShen3First(Double.parseDouble(shen3First));
		rewardScore.setShen3Second(Double.parseDouble(shen3Second));
		rewardScore.setShen3Third(Double.parseDouble(shen3Third));
		rewardScore.setShen3Fourth(Double.parseDouble(shen3Fourth));
		rewardScore.setShen3Fifth(Double.parseDouble(shen3Fifth));
		rewardScore.setYouxiuJiaoxue(Double.parseDouble(youxiuJiaoxue));
		rewardScore.setYongTeach(Double.parseDouble(yongTeach));
		transaction.commit();

	}

	public List<Map> queryUserNum4Tong(HttpServletRequest request) {
		String userNum = request.getParameter("userNum");
		String sql = "SELECT DISTINCT SUR.RESEARCH_USER_ID USERNUM,SU.USERNAME FROM SYS_USER_RESEARCH SUR"
				+ " LEFT JOIN SYS_USER SU ON SU.USER_NUM=SUR.RESEARCH_USER_ID ";
		if (StringUtils.isNotEmpty(userNum)) {
			sql = sql + "  	where SU.USER_NUM=:userNum  ";
		}
		sql = sql + " ORDER BY SUR.RESEARCH_USER_ID ASC  ";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		transaction.commit();
		return list;
	}

	public Map queryThesisScore4Tong() {
		String sql = "SELECT * FROM THESIS_SCORE";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		Map map = new HashMap<>();
		if (list != null && list.size() > 0) {
			map = list.get(0);
		}
		transaction.commit();
		return map;
	}

	public Map queryProjectScore4Tong() {
		String sql = "SELECT * FROM PROJECT_SCORE";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		Map map = new HashMap<>();
		if (list != null && list.size() > 0) {
			map = list.get(0);
		}
		transaction.commit();
		return map;
	}

	public Map queryPatentScore4Tong() {
		String sql = "SELECT * FROM PATENT_SCORE";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		Map map = new HashMap<>();
		if (list != null && list.size() > 0) {
			map = list.get(0);
		}
		transaction.commit();
		return map;
	}

	public Map queryRewardScore4Tong() {
		String sql = "SELECT * FROM REWARD_SCORE";
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		Map map = new HashMap<>();
		if (list != null && list.size() > 0) {
			map = list.get(0);
		}
		transaction.commit();
		return map;
	}

	public List<Map> getCurrentThesis4UserNum(String userNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("   SELECT                                                   ");
		sb.append("   	T.THESIS_NAME THESISNAME,                              ");
		sb.append("   	T.THESIS_PERIODICAL THESISPERIODICAL                   ");
		sb.append("   FROM                                                     ");
		sb.append("   	SYS_USER_RESEARCH SUR                                  ");
		sb.append("   LEFT JOIN THESIS_INFO T ON SUR.RESEARCH_ID = T.THESIS_ID ");
		sb.append("   WHERE                                                    ");
		sb.append("   	SUR.RESEARCH_TYPE = :researchThesis                    ");
		sb.append("   AND SUR.RESEARCH_USER_ID = :userId   AND T.THESIS_PASS=:pass                    ");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchThesis", Constants.RESEARCH_TYPE_THESIS);
		query.setParameter("userId", userNum);
		query.setParameter("pass", Constants.SHEN_PASS);
		List<Map> queryList = query.list();
		transaction.commit();
		return queryList;
	}

	public List<Map> getCurrentPatent4UserNum(String userNum) {
		StringBuilder sb = new StringBuilder();
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
		sb.append("  AND SUR.RESEARCH_USER_ID = :userId   AND T.PATENT_PASS=:pass                       ");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchPatent", Constants.RESEARCH_TYPE_PATENT);
		query.setParameter("userId", userNum);
		query.setParameter("pass", Constants.SHEN_PASS);
		List<Map> queryList = query.list();
		transaction.commit();
		return queryList;

	}

	public List<Map> getCurrentProject4UserNum(String userNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("  SELECT                                                         ");
		sb.append("  	T.project_name projectname,                                 ");
		sb.append("  	T.PROJECT_TYPE PROJECTTYPE,                                 ");
		sb.append("      T.PROJECT_FUND PROJECTFUND                                 ");
		sb.append("  FROM                                                           ");
		sb.append("  	SYS_USER_RESEARCH SUR                                       ");
		sb.append("  LEFT JOIN PROJECT_INFO T ON SUR.RESEARCH_ID = T.PROJECT_ID     ");
		sb.append("  WHERE                                                          ");
		sb.append("  	SUR.RESEARCH_TYPE = :researchProject                        ");
		sb.append("  AND SUR.RESEARCH_USER_ID = :userId   AND T.PROJECT_PASS=:pass                           ");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchProject", Constants.RESEARCH_TYPE_PROJECT);
		query.setParameter("userId", userNum);
		query.setParameter("pass", Constants.SHEN_PASS);
		List<Map> queryList = query.list();
		transaction.commit();
		return queryList;
	}

	public List<Map> getCurrentReward4UserNum(String userNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("    SELECT                                                      ");
		sb.append("    T.reward_name   rewardname,                                 ");
		sb.append("    T.REWARD_TYPE   REWARDTYPE,                                 ");
		sb.append("   T.REWARD_PLACE     REWARDPLACE                               ");
		sb.append("    FROM                                                        ");
		sb.append("    	SYS_USER_RESEARCH SUR                                      ");
		sb.append("    LEFT JOIN REWARD_INFO T ON SUR.RESEARCH_ID = T.REWARD_ID    ");
		sb.append("    WHERE                                                       ");
		sb.append("    	SUR.RESEARCH_TYPE = :researchReward                        ");
		sb.append("    AND SUR.RESEARCH_USER_ID = :userId   AND T.REWARD_PASS=:pass                       ");
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("researchReward", Constants.RESEARCH_TYPE_REWARD);
		query.setParameter("userId", userNum);
		query.setParameter("pass", Constants.SHEN_PASS);
		List<Map> queryList = query.list();
		transaction.commit();
		return queryList;
	}

	public Map getUserInfo(String userNum) {
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();

		Map userMap = new HashMap<>();
		if (StringUtils.isNoneEmpty(userNum)) {
			String sql = "select user_id from sys_user where user_num =:userNum";
		
			SQLQuery query = currSession.createSQLQuery(sql);
			query.setParameter("userNum", userNum);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map> queryList = query.list();
			String userId = "";
			if (!queryList.isEmpty()) {
				Map map = queryList.get(0);
				userId = (String) map.get("user_id");
			}
			if (StringUtils.isNotEmpty(userId)) {
				SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
				userMap.put("userId", userId);
				userMap.put("userName", sysUser.getUsername());
				userMap.put("userNum", sysUser.getUserNum());
				userMap.put("address", sysUser.getAdress());
				userMap.put("email", sysUser.getEmail());
				userMap.put("mobile", sysUser.getMobile());
			}

		}
		transaction.commit();
		return userMap;
	}

	public List<Map> queryThesisTongList(HttpServletRequest request) {
		String userNum = request.getParameter("userNum");
		String thesisName = request.getParameter("thesisName");
		String thesisType = request.getParameter("thesisType");
		String userName = request.getParameter("userName");
		String organId = request.getParameter("organId");
		String deptId = request.getParameter("deptId");
		String thesisStartDate = request.getParameter("thesisStartDate");
		String thesisEndDate = request.getParameter("thesisEndDate");

		StringBuilder sb = new StringBuilder();
		sb.append("  SELECT                                                       ");
		sb.append("  	SUR.RID,                                                  ");
		sb.append("    SU.USER_NUM USERNUM,                                       ");
		sb.append("    SU.USERNAME ,                                              ");
		sb.append("    SU.USER_ID USERID,                                         ");
		sb.append("    SU.MOBILE,                                                 ");
		sb.append("    SU.EMAIL,                                                  ");
		sb.append("    TI.THESIS_ID THESISID,                                     ");
		sb.append("    TI.THESIS_AUTHOR THESISAUTHOR,                             ");
		sb.append("    TI.THESIS_PAGE THESISPAGE,                                 ");
		sb.append("    TI.THESIS_PASS THESISPASS,                                 ");
		sb.append("    TI.THESIS_DATE THESISDATE,                                 ");
		sb.append("    TI.THESIS_NAME THESISNAME,                                 ");
		sb.append(
				"    (SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=TI.THESIS_TYPE) THESISTYPE,                                 ");
		sb.append("    TI.THESIS_PERIODICAL THESISPERIODICAL,                     ");
		sb.append("    TI.THESIS_RECORD   THESISRECORD                            ");
		sb.append("  FROM                                                         ");
		sb.append("  	SYS_USER_RESEARCH SUR                                     ");
		sb.append("  LEFT JOIN SYS_USER SU ON SU.USER_NUM = SUR.RESEARCH_USER_ID  ");
		sb.append("  LEFT JOIN THESIS_INFO TI ON TI.THESIS_ID = SUR.RESEARCH_ID   ");
		sb.append("  WHERE                                                        ");
		sb.append("  	SUR.RESEARCH_TYPE = :researchThesis     AND TI.THESIS_PASS=:pass     ");

		HttpSession session = request.getSession();
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			sb.append(" AND SU.USER_NUM = :userNumTec  ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" AND SU.USER_NUM = :userNum ");
		}
		if (StringUtils.isNotEmpty(thesisName)) {
			sb.append(" AND TI.THESIS_NAME LIKE :thesisName ");
		}
		if (StringUtils.isNotEmpty(thesisType)) {
			sb.append(" AND TI.THESIS_TYPE = :thesisType ");
		}
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" AND SU.USERNAME LIKE :userName ");
		}
		if (StringUtils.isNotEmpty(organId)) {
			sb.append(" AND SU.organ_Id = :organId ");
		}
		if (StringUtils.isNotEmpty(deptId)) {
			sb.append(" AND SU.dept = :deptId ");
		}
		if (StringUtils.isNotEmpty(thesisStartDate)) {
			sb.append(" AND TI.THESIS_DATE >= :thesisStartDate ");
		}
		if (StringUtils.isNotEmpty(thesisEndDate)) {
			sb.append(" AND TI.THESIS_DATE <= :thesisEndDate ");
		}
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setParameter("researchThesis", Constants.RESEARCH_TYPE_THESIS);
		query.setParameter("pass", Constants.SHEN_PASS);
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		if (StringUtils.isNotEmpty(thesisName)) {
			query.setParameter("thesisName", "%" + thesisName + "%");
		}
		if (StringUtils.isNotEmpty(thesisType)) {
			query.setParameter("thesisType", thesisType);
		}
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			query.setParameter("userNumTec", userNum4Curr);
		}
		if (StringUtils.isNotEmpty(userName)) {
			query.setParameter("userName", "%" + userName + "%");
		}
		if (StringUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}
		if (StringUtils.isNotEmpty(deptId)) {
			query.setParameter("deptId", deptId);
		}
		if (StringUtils.isNotEmpty(thesisStartDate)) {
			query.setParameter("thesisStartDate", thesisStartDate);
		}
		if (StringUtils.isNotEmpty(thesisEndDate)) {
			query.setParameter("thesisEndDate", thesisEndDate);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		Map theisScore = getThesisScore();
		if (queryList != null && queryList.size() > 0) {
			for (Map map : queryList) {
				double workload = 0;
				String thesisPeriodical = (String) map.get("THESISPERIODICAL");
				if (StringUtils.isNotEmpty(thesisPeriodical)) {
					if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_SHOUSCI)) {
						if ((double) theisScore.get("thesisShousci") > workload)
							;
						{
							workload = (double) theisScore.get("thesisShousci");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_EIYUAN)) {
						if ((double) theisScore.get("thesisEiyuan") > workload)
							;
						{
							workload = (double) theisScore.get("thesisEiyuan");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_EISHOU)) {
						if ((double) theisScore.get("thesisEishou") > workload)
							;
						{
							workload = (double) theisScore.get("thesisEishou");
						}
					}else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_JIAOWU)) {
						if ((double) theisScore.get("thesisJiaowu") > workload)
							;
						{
							workload = (double) theisScore.get("thesisJiaowu");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_EISHOULU)) {
						if ((double) theisScore.get("thesisEishoulu") > workload)
							;
						{
							workload = (double) theisScore.get("thesisEishoulu");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_SHOULU)) {
						if ((double) theisScore.get("thesisShoulu") > workload)
							;
						{
							workload = (double) theisScore.get("thesisShoulu");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_CHINESE)) {
						if ((double) theisScore.get("thesisChinese") > workload)
							;
						{
							workload = (double) theisScore.get("thesisChinese");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_GUOJI)) {
						if ((double) theisScore.get("thesisGuoji") > workload);
						{
							workload = (double) theisScore.get("thesisGuoji");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_OTHER)) {
						if ((double) theisScore.get("thesisOther") > workload)
							;
						{
							workload = (double) theisScore.get("thesisOther");
						}
					}
				} 
				map.put("workload", workload);
				if (StringUtils.isNotEmpty(thesisPeriodical)) {
					String[] str = thesisPeriodical.split(",");
					StringBuilder sql = new StringBuilder();
					sql.append("select GROUP_CONCAT(S.DICT_NAME) DICTNAME FROM SYS_DICT S WHERE S.DICT_VALUE IN(");
					int length = str.length;
					int i = 0;
					for (String strSql : str) {
						i++;
						strSql = "'" + strSql + "'";
						if (i == length) {
							sql.append(strSql);
						} else {
							sql.append(strSql + ",");
						}
					}
					sql.append("  ) ");
					Session currSession2 = getSession();
					Transaction transaction2 = currSession2.beginTransaction();
					SQLQuery querySql = currSession2.createSQLQuery(sql.toString());
					querySql.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List<Map> list = querySql.list();
					transaction2.commit();
					if (list != null && list.size() > 0) {
						Map mapStr = list.get(0);
						String dictName = (String) mapStr.get("DICTNAME");
						map.put("THESISPERIODICAL", dictName);
					}
				}
			}
		}
		return queryList;
	}

	public List<Map> queryProjecTongtList(HttpServletRequest request) {
		String userNum = request.getParameter("userNum");
		String projectName = request.getParameter("projectName");
		String projectType = request.getParameter("projectType");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String userName = request.getParameter("userName");

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT                                                          ");
		sb.append(" 	SUR.RID,                                                    ");
		sb.append(" 	SU.USER_NUM USERNUM ,                                       ");
		sb.append(" 	SU.USERNAME,                                                ");
		sb.append(" 	SU.USER_ID USERID,                                          ");
		sb.append(" 	SU.MOBILE,                                                  ");
		sb.append(" 	SU.EMAIL,                                                   ");
		sb.append(" 	TI.PROJECT_ID PROJECTID,                                    ");
		sb.append(" 	TI.PROJECT_NAME PROJECTNAME,                                ");
		sb.append(" 	TI.START_TIME STARTTIME,                                    ");
		sb.append(" 	TI.END_TIME ENDTIME,                                        ");
		sb.append(" 	TI.PROJECT_FUND PROJECTFUND,                                ");
		sb.append(" 	TI.PROJECT_TYPE PROJECT,                                    ");
		sb.append(
				" 	(SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=TI.PROJECT_TYPE) PROJECTTYPE,                                ");
		sb.append(" 	TI.PROJECT_SOURCE PROJECTSOURCE                             ");
		sb.append(" FROM                                                            ");
		sb.append(" 	SYS_USER_RESEARCH SUR                                       ");
		sb.append(" LEFT JOIN SYS_USER SU ON SU.USER_NUM = SUR.RESEARCH_USER_ID     ");
		sb.append(" LEFT JOIN PROJECT_INFO TI ON TI.PROJECT_ID = SUR.RESEARCH_ID    ");
		sb.append(" WHERE                                                           ");
		sb.append(" 	SUR.RESEARCH_TYPE = :researchProject     AND   TI.PROJECT_PASS=:pass                 ");

		HttpSession session = request.getSession();
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			sb.append(" AND SU.USER_NUM = :userNumTec  ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" AND SU.USER_NUM = :userNum ");
		}
		if (StringUtils.isNotEmpty(projectName)) {
			sb.append(" AND TI.PROJECT_NAME LIKE :projectName ");
		}
		if (StringUtils.isNotEmpty(projectType)) {
			sb.append(" AND TI.PROJECT_TYPE = :projectType ");
		}
		if (StringUtils.isNotEmpty(startTime)) {
			sb.append(" AND TI.START_TIME = :startTime ");
		}
		if (StringUtils.isNotEmpty(endTime)) {
			sb.append(" AND TI.END_TIME = :endTime ");
		}
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" AND SU.USERNAME LIKE :userName ");
		}

		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setParameter("researchProject", Constants.RESEARCH_TYPE_PROJECT);
		query.setParameter("pass", Constants.SHEN_PASS);
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		if (StringUtils.isNotEmpty(projectName)) {
			query.setParameter("projectName", "%" + projectName + "%");
		}
		if (StringUtils.isNotEmpty(projectType)) {
			query.setParameter("projectType", projectType);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			query.setParameter("startTime", startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			query.setParameter("endTime", endTime);
		}
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			query.setParameter("userNumTec", userNum4Curr);
		}
		if (StringUtils.isNotEmpty(userName)) {
			query.setParameter("userName", "%" + userName + "%");
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		Map projectScore = getProjectScore();
		if (!queryList.isEmpty()) {
			for (Map map : queryList) {
				double workload4project = 0;
				String project = (String) map.get("PROJECT");
				double projectFund = (double) map.get("PROJECTFUND");
				int i = (int) projectFund;
				if (Constants.PROJECT_TYPE__GUO.equals(project)) {
					workload4project = (i
							* ((double) projectScore.get("guoFund") / (double) projectScore.get("guoFundLast")))
							* (double) projectScore.get("guoK");
				} else if (Constants.PROJECT_TYPE_SHEN.equals(project)) {
					workload4project = (i
							* ((double) projectScore.get("shenFund") / (double) projectScore.get("shenFundLast")))
							* (double) projectScore.get("shenK");
				} else if (Constants.PROJECT_TYPE_OTHER.equals(project)) {
					workload4project = (i
							* ((double) projectScore.get("otherFund") / (double) projectScore.get("otherFundLast")))
							* (double) projectScore.get("otherK");
				}
				BigDecimal b = new BigDecimal(workload4project);
				double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				map.put("workload", f1);

			}
		}
		return queryList;
	}

	public List<Map> queryRewardTongList(HttpServletRequest request) {
		String userNum = request.getParameter("userNum");
		String rewardName = request.getParameter("rewardName");
		String rewardType = request.getParameter("rewardType");
		String rewardOrgan = request.getParameter("rewardOrgan");
		String rewardTime = request.getParameter("rewardTime");
		String userName = request.getParameter("userName");

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT                                                           ");
		sb.append(" 	SUR.RID,                                                     ");
		sb.append(" 	SU.USER_NUM USERNUM,                                         ");
		sb.append(" 	SU.USERNAME,                                                 ");
		sb.append(" 	SU.USER_ID USERID,                                           ");
		sb.append(" 	SU.MOBILE,                                                   ");
		sb.append(" 	SU.EMAIL,                                                    ");
		sb.append(" 	TI.REWARD_ID REWARDID,                                       ");
		sb.append(" 	TI.REWARD_NAME REWARDNAME,                                   ");
		sb.append(" 	TI.REWARD_TIME REWARDTIME,                                   ");
		sb.append(" 	TI.REWARD_TYPE TYPE,                                         ");
		sb.append(
				"     (SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=TI.REWARD_TYPE)REWARDTYPE,                                   ");
		sb.append(" 	TI.REWARD_ORGAN REWARDORGAN,                                 ");
		sb.append(" 	TI.REWARD_PLACE REWARDPLACE,                                 ");
		sb.append(" 	TI.REWARD_USER REWARDUSER                                    ");
		sb.append(" FROM                                                             ");
		sb.append(" 	SYS_USER_RESEARCH SUR                                        ");
		sb.append(" LEFT JOIN SYS_USER SU ON SU.USER_NUM = SUR.RESEARCH_USER_ID      ");
		sb.append(" LEFT JOIN REWARD_INFO TI ON TI.REWARD_ID = SUR.RESEARCH_ID       ");
		sb.append(" WHERE                                                            ");
		sb.append(" 	SUR.RESEARCH_TYPE = :researchReward   and   TI.REWARD_pass = :pass                     ");

		HttpSession session = request.getSession();
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			sb.append(" AND SU.USER_NUM = :userNumTec  ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" AND SU.USER_NUM = :userNum ");
		}
		if (StringUtils.isNotEmpty(rewardName)) {
			sb.append(" AND TI.REWARD_NAME LIKE :rewardName ");
		}
		if (StringUtils.isNotEmpty(rewardType)) {
			sb.append(" AND TI.REWARD_TYPE = :rewardType ");
		}
		if (StringUtils.isNotEmpty(rewardOrgan)) {
			sb.append(" AND TI.REWARD_ORGAN LIKE :rewardOrgan ");
		}
		if (StringUtils.isNotEmpty(rewardTime)) {
			sb.append(" AND TI.REWARD_TIME = :rewardTime ");
		}
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" AND SU.USERNAME LIKE :userName ");
		}
		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = currSession.createSQLQuery(sb.toString());
		query.setParameter("researchReward", Constants.RESEARCH_TYPE_REWARD);
		query.setParameter("pass", Constants.SHEN_PASS);
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		if (StringUtils.isNotEmpty(rewardName)) {
			query.setParameter("rewardName", "%" + rewardName + "%");
		}
		if (StringUtils.isNotEmpty(rewardType)) {
			query.setParameter("rewardType", rewardType);
		}
		if (StringUtils.isNotEmpty(rewardOrgan)) {
			query.setParameter("rewardOrgan", "%" + rewardOrgan + "%");
		}
		if (StringUtils.isNotEmpty(rewardTime)) {
			query.setParameter("rewardTime", rewardTime);
		}
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			query.setParameter("userNumTec", userNum4Curr);
		}
		if (StringUtils.isNotEmpty(userName)) {
			query.setParameter("userName", "%" + userName + "%");
		}

		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();

		Map rewardScore = getRewardScore();
		if (!queryList.isEmpty()) {
			for (Map map : queryList) {
				String type = (String) map.get("TYPE");
				String rewardPlace = (String) map.get("REWARDPLACE");
				double workload4reward = 0;
				if (Constants.REWARD_TYPE_SHEN1.equals(type)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shenFirst");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shenSecond");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shenThird");
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shenFourth");
					} else {
						workload4reward = (double) rewardScore.get("shenFifth");
					}
				} else if (Constants.REWARD_TYPE_SHEN2.equals(type)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen2First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen2Second");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen2Third");
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen2Fourth");
					} else {
						workload4reward = (double) rewardScore.get("shen2Fifth");
					}
				} else if (Constants.REWARD_TYPE_SHEN3.equals(type)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen3First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen3Second");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen3Third");
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("shen3Fourth");
					} else {
						workload4reward = (double) rewardScore.get("shen3Fifth");
					}
				} else if (Constants.REWARD_TYPE_DISHI1.equals(type)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishiFirst");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishiSecond");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishiFhird");
					}
				} else if (Constants.REWARD_TYPE_DISHI2.equals(type)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishi2First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishi2Second");
					} else {
						workload4reward = (double) rewardScore.get("dishi2Third");
					}
				} else if (Constants.REWARD_TYPE_DISHI3.equals(type)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishi3First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("dishi3Second");
					} else {
						workload4reward = (double) rewardScore.get("dishi3Third");
					}
				} else if (Constants.REWARD_TYPE_XIAO1.equals(type)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("schoolFirst");
					}
				} else if (Constants.REWARD_TYPE_XIAO2.equals(type)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("schoolSecond");
					}
				} else if (Constants.REWARD_TYPE_XIAO3.equals(type)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double) rewardScore.get("schoolThird");
					}
				} else if (Constants.REWARD_TYPE_JIAO.equals(type)) {
					workload4reward = (double) rewardScore.get("youxiuJiaoxue");
				} else if (Constants.REWARD_TYPE_QING.equals(type)) {
					workload4reward = (double) rewardScore.get("yongTeach");
				}
				map.put("workload", workload4reward);
			}
		}
		return queryList;
	}

	public List<Map> queryPatentTongList(HttpServletRequest request) {
		String userNum = request.getParameter("userNum");
		String patentName = request.getParameter("patentName");
		String patentType = request.getParameter("patentType");
		String patentPeople = request.getParameter("patentPeople");
		String patentDate = request.getParameter("patentDate");
		String patentFirst = request.getParameter("patentFirst");
		String patentIsTransfer = request.getParameter("patentIsTransfer");
		String userName = request.getParameter("userName");

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT                                                         ");
		sb.append(" 	SUR.RID,                                                   ");
		sb.append(" 	SU.USER_NUM USERNUM,                                       ");
		sb.append(" 	SU.USERNAME,                                               ");
		sb.append(" 	SU.USER_ID USERID,                                         ");
		sb.append(" 	SU.MOBILE,                                                 ");
		sb.append(" 	SU.EMAIL,                                                  ");
		sb.append(" 	TI.PATENT_ID PATENTID,                                     ");
		sb.append(" 	TI.PATENT_CREATER PATENTCREATER,                           ");
		sb.append(" 	TI.PATENT_TYPE TYPE,                                       ");
		sb.append(
				" 	(SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=TI.PATENT_TYPE) PATENTTYPE,                                 ");
		sb.append(" 	TI.PATENT_NAME PATENTNAME,                                 ");
		sb.append(
				" 	(SELECT S.DICT_NAME FROM SYS_DICT S WHERE S.DICT_VALUE=TI.PATENT_PEOPLE) PATENTPEOPLE,                             ");
		sb.append(" 	TI.PATENT_DATE PATENTDATE,                                 ");
		sb.append(" 	TI.PATENT_FIRST PATENTFIRST,                               ");
		sb.append(" 	TI.PATENT_IS_TRANSFER PATENTISTRANSFER                     ");
		sb.append(" FROM                                                           ");
		sb.append(" 	SYS_USER_RESEARCH SUR                                      ");
		sb.append(" LEFT JOIN SYS_USER SU ON SU.USER_NUM = SUR.RESEARCH_USER_ID    ");
		sb.append(" LEFT JOIN PATENT_INFO TI ON TI.PATENT_ID = SUR.RESEARCH_ID     ");
		sb.append(" WHERE                                                          ");
		sb.append(" 	SUR.RESEARCH_TYPE = :researchPatent      and ti.PATENT_PASS=:pass                  ");

		HttpSession session = request.getSession();
		BigInteger userNum4Curr = (BigInteger) session.getAttribute("userNum");
		String userType = (String) session.getAttribute("userType");
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			sb.append(" AND SU.USER_NUM = :userNumTec  ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" AND SU.USER_NUM = :userNum ");
		}
		if (StringUtils.isNotEmpty(patentName)) {
			sb.append(" AND TI.PATENT_NAME LIKE :patentName ");
		}
		if (StringUtils.isNotEmpty(patentType)) {
			sb.append(" AND TI.PATENT_TYPE = :patentType ");
		}
		if (StringUtils.isNotEmpty(patentPeople)) {
			sb.append(" AND TI.PATENT_PEOPLE = :patentPeople ");
		}
		if (StringUtils.isNotEmpty(patentDate)) {
			sb.append(" AND TI.PATENT_DATE = :patentDate ");
		}
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" AND SU.USERNAME LIKE :userName ");
		}
		if (StringUtils.isNotEmpty(patentFirst)) {
			if (patentFirst.equals(Constants.YES)) {
				sb.append("  AND TI.PATENT_FIRST = :patentFirst       ");
			} else {
				patentFirst = Constants.YES;
				sb.append("  AND TI.PATENT_FIRST != :patentFirst     ");
			}
		}
		if (StringUtils.isNotEmpty(patentIsTransfer)) {
			if (patentIsTransfer.equals(Constants.YES)) {
				sb.append("  AND TI.PATENT_IS_TRANSFER = :patentIsTransfer       ");
			} else {
				patentIsTransfer = Constants.YES;
				sb.append("  AND TI.PATENT_IS_TRANSFER != :patentIsTransfer      ");
			}
		}

		Session currSession = getSession();
		Transaction transaction = currSession.beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setParameter("researchPatent", Constants.RESEARCH_TYPE_PATENT);
		query.setParameter("pass", Constants.SHEN_PASS);
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		if (StringUtils.isNotEmpty(patentName)) {
			query.setParameter("patentName", "%" + patentName + "%");
		}
		if (StringUtils.isNotEmpty(patentType)) {
			query.setParameter("patentType", patentType);
		}
		if (StringUtils.isNotEmpty(patentPeople)) {
			query.setParameter("patentPeople", patentPeople);
		}
		if (StringUtils.isNotEmpty(patentDate)) {
			query.setParameter("patentDate", patentDate);
		}
		if (StringUtils.isNotEmpty(patentFirst)) {
			query.setParameter("patentFirst", patentFirst);
		}
		if (StringUtils.isNotEmpty(patentIsTransfer)) {
			query.setParameter("patentIsTransfer", patentIsTransfer);
		}
		if (StringUtils.isNotEmpty(userName)) {
			query.setParameter("userName", "%" + userName + "%");
		}
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			query.setParameter("userNumTec", userNum4Curr);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		Map patentScore = getPatentScore();
		if (!queryList.isEmpty()) {
			for (Map map : queryList) {
				String type = (String) map.get("TYPE");
				String first = (String) map.get("PATENTFIRST");
				String isTransfer = (String) map.get("PATENTISTRANSFER");
				double workload4Patent = 0;
				if (Constants.PATENT_TYPE_FAMING.equals(type)) {
					if (Constants.YES.equals(first)) {
						workload4Patent = (double) patentScore.get("inventFist");
						if (Constants.YES.equals(isTransfer)) {
							workload4Patent = workload4Patent + (double) patentScore.get("inventIsMove");
						}
					}
				} else if (Constants.PATENT_TYPE_SHIYONG.equals(type)) {
					if (Constants.YES.equals(first)) {
						workload4Patent = (double) patentScore.get("practicalFirst");
						if (Constants.YES.equals(isTransfer)) {
							workload4Patent = workload4Patent + (double) patentScore.get("practicalIsMove");
						}
					}
				} else if (Constants.PATENT_TYPE_WAIGAUAN.equals(type)) {
					if (Constants.YES.equals(first)) {
						workload4Patent = (double) patentScore.get("viewFirst");
						if (Constants.YES.equals(isTransfer)) {
							workload4Patent = workload4Patent + (double) patentScore.get("viewIsMove");
						}
					}
				} else if (Constants.PATENT_TYPE_SOFTWAR.equals(type)) {
					if (Constants.YES.equals(first)) {
						workload4Patent = (double) patentScore.get("softFirst");
						if (Constants.YES.equals(isTransfer)) {
							workload4Patent = workload4Patent + (double) patentScore.get("softIsMove");
						}
					}
				}
				map.put("workload", workload4Patent);
			}
		}
		return queryList;
	}

	public void pass(HttpServletRequest request) {
		String researchId = request.getParameter("researchId");
		String researchType = request.getParameter("researchType");
		Transaction tan = getSession().beginTransaction();
		if (Constants.RESEARCH_TYPE_THESIS.equals(researchType)) {
			ThesisInfo thesisInfo = getSession().get(ThesisInfo.class, researchId);
			thesisInfo.setThesisPass(Constants.SHEN_PASS);
		} else if (Constants.RESEARCH_TYPE_REWARD.equals(researchType)) {
			RewardInfo rewardInfo = getSession().get(RewardInfo.class, researchId);
			rewardInfo.setRewardPass(Constants.SHEN_PASS);
		} else if (Constants.RESEARCH_TYPE_PROJECT.equals(researchType)) {
			ProjectInfo projectInfo = getSession().get(ProjectInfo.class, researchId);
			projectInfo.setProjectPass(Constants.SHEN_PASS);
		} else if (Constants.RESEARCH_TYPE_PATENT.equals(researchType)) {
			PatentInfo patentInfo = getSession().get(PatentInfo.class, researchId);
			patentInfo.setPatentPass(Constants.SHEN_PASS);
		}
		tan.commit();
	}

	public void unpass(HttpServletRequest request) {
		String researchId = request.getParameter("researchId");
		String researchType = request.getParameter("researchType");
		Transaction tan = getSession().beginTransaction();
		if (Constants.RESEARCH_TYPE_THESIS.equals(researchType)) {
			ThesisInfo thesisInfo = getSession().get(ThesisInfo.class, researchId);
			thesisInfo.setThesisPass(Constants.SHEN_UNPASS);
		} else if (Constants.RESEARCH_TYPE_REWARD.equals(researchType)) {
			RewardInfo rewardInfo = getSession().get(RewardInfo.class, researchId);
			rewardInfo.setRewardPass(Constants.SHEN_UNPASS);
		} else if (Constants.RESEARCH_TYPE_PROJECT.equals(researchType)) {
			ProjectInfo projectInfo = getSession().get(ProjectInfo.class, researchId);
			projectInfo.setProjectPass(Constants.SHEN_UNPASS);
		} else if (Constants.RESEARCH_TYPE_PATENT.equals(researchType)) {
			PatentInfo patentInfo = getSession().get(PatentInfo.class, researchId);
			patentInfo.setPatentPass(Constants.SHEN_UNPASS);
		}
		tan.commit();
	}

}