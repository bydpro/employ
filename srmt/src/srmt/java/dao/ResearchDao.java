package srmt.java.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sun.org.apache.regexp.internal.recompile;

import srmt.java.common.Constants;
import srmt.java.entity.PatentInfo;
import srmt.java.entity.ProjectInfo;
import srmt.java.entity.RewardInfo;
import srmt.java.entity.SysOrgan;
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

	public List<Map> queryResearchList(HttpServletRequest request) {
		String dictValue = request.getParameter("dictValue");
		String researchName = request.getParameter("researchName");
		StringBuffer sb = new StringBuffer();
		sb.append("   SELECT                                                             ");
		sb.append("   	SU.USER_ID USERID,                                               ");
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
		sb.append("   		LEFT JOIN SYS_USER SU ON SUR.RESEARCH_USER_ID = SU.USER_ID   ");
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
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			sb.append("    AND SU.USER_ID = :userId        ");
		}
		if (StringUtils.isNotEmpty(dictValue)) {
			sb.append("   AND SUR.RESEARCH_TYPE=:dictValue  ");
		}
		if (StringUtils.isNotEmpty(researchName)) {
			sb.append("  AND RE.RESEARCHNAME LIKE :researchName  ");
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		if (Constants.USER_TYPE_TEC.equals(userType)) {
			query.setParameter("userId", userId);
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

		Transaction transaction = getSession().beginTransaction();
		HttpSession session = request.getSession();
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
			sysUserResearch.setResearchUserId(userId);
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
			sysUserResearch.setResearchUserId(userId);
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
			sysUserResearch.setResearchUserId(userId);
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
			map.put("patentCreater",patentInfo.getPatentCreater());
			map.put("patentDate", patentInfo.getPatentDate());
			map.put("patentFirst", patentInfo.getPatentFirst());
		    map.put("patentIsTransfer",patentInfo.getPatentIsTransfer());
		    map.put("patentName",patentInfo.getPatentName());
		    map.put("patentType",patentInfo.getPatentType());
		    map.put("patentContent",patentInfo.getPatentContent());
		    map.put("patentPeople",patentInfo.getPatentPeople());
		    map.put("patentFile",patentInfo.getPatentFile());
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
			sysUserResearch.setResearchUserId(userId);
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
			map.put("projectFirst",projectInfo.getProjectFirst());
			map.put("projectFund", projectInfo.getProjectFund());
			map.put("projectName", projectInfo.getProjectName());
		    map.put("projectSecond",projectInfo.getProjectSecond());
		    map.put("projectSource",projectInfo.getProjectSource());
		    map.put("projectType",projectInfo.getProjectType());
		    map.put("projectThird",projectInfo.getProjectType());
		    map.put("endTime",projectInfo.getEndTime());
		    map.put("startTime",projectInfo.getStartTime());
		    map.put("projectContent",projectInfo.getProjectContent());
		    map.put("projectFile",projectInfo.getProectFile());
			}
		transaction.commit();
		getSession().close();
		return map;
	}
	
	public List<Map> queryProjectType(){
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
	
	public List<Map> queryPatentType(){
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
	
	public List<Map> queryPlaceType(){
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
	
	public List<Map> queryRewardType(){
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
	
	public List<Map> queryThesisIncluded(){
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
	
	public List<Map> queryThesisType(){
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
	
	public List<Map> queryPatentPeople(){
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

}
