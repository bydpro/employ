package srmt.java.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import srmt.java.common.Constants;
import srmt.java.dao.ResearchDao;

@Service
public class ResearchService {
	@Autowired
	private ResearchDao researchDao;

	public List<Map> queryResearchList(HttpServletRequest request) {
		List<Map> list = researchDao.queryResearchList(request);
		return list;
	}

	public List<Map> queryResearchType() {
		List<Map> list = researchDao.queryResearchType();
		return list;
	}

	public void delResearch(String rid) {
		researchDao.delResearch(rid);
	}

	public void saveThesis(HttpServletRequest request) {
		researchDao.saveThesis(request);
	}

	public Map getThesisInfo(String thesisId) {
		return researchDao.getThesisInfo(thesisId);
	}

	public void saveReward(HttpServletRequest request) {
		researchDao.saveReward(request);
	}

	public Map getRewardInfo(String rewardId) {
		return researchDao.getRewardInfo(rewardId);
	}

	public void savePatent(HttpServletRequest request) {
		researchDao.savePatent(request);
	}

	public Map getPatentInfo(String patentId) {
		return researchDao.getPatentInfo(patentId);
	}

	public void saveProject(HttpServletRequest request) {
		researchDao.saveProject(request);
	}

	public Map getProjectInfo(String projectId) {
		return researchDao.getProjectInfo(projectId);
	}

	public List<Map> queryProjectType() {
		List<Map> list = researchDao.queryProjectType();
		;
		return list;
	}

	public List<Map> queryPatentType() {
		List<Map> list = researchDao.queryPatentType();
		return list;
	}

	public List<Map> queryPlaceType() {
		List<Map> list = researchDao.queryPlaceType();
		return list;
	}

	public List<Map> queryRewardType() {
		List<Map> list = researchDao.queryRewardType();
		return list;
	}

	public List<Map> queryThesisIncluded() {
		List<Map> list = researchDao.queryThesisIncluded();
		return list;
	}

	public List<Map> queryThesisType() {
		List<Map> list = researchDao.queryThesisType();
		return list;
	}

	public List<Map> queryPatentPeople() {
		List<Map> list = researchDao.queryPatentPeople();
		return list;
	}

	public String saveFile(MultipartFile multipartFile) {

		String filePath = Constants.FILE_UPLOAD_PATH;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(date);
		File file = new File(filePath, dateString + multipartFile.getOriginalFilename());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getPath();
	}

	public List<Map> getCurrentThesisWorkload4Tec(BigInteger userNum) {
		return researchDao.getCurrentThesisWorkload4Tec(userNum);
	}

	public List<Map> getCurrentProjectWorkload4Tec(BigInteger userNum) {
		return researchDao.getCurrentProjectWorkload4Tec(userNum);
	}

	public List<Map> getCurrentPatentWorkload4Tec(BigInteger userNum) {
		return researchDao.getCurrentPatentWorkload4Tec(userNum);
	}

	public List<Map> getCurrentRewardWorkload4Tec(BigInteger userNum) {
		return researchDao.getCurrentRewardWorkload4Tec(userNum);
	}

	public List<Map> queryThesisList(HttpServletRequest request) {
		List<Map> list = researchDao.queryThesisList(request);
		return list;
	}

	public List<Map> queryProjectList(HttpServletRequest request) {
		List<Map> list = researchDao.queryProjectList(request);
		return list;
	}

	public List<Map> queryRewardList(HttpServletRequest request) {
		List<Map> list = researchDao.queryRewardList(request);
		return list;
	}

	public List<Map> queryPatentList(HttpServletRequest request) {
		List<Map> list = researchDao.queryPatentList(request);
		return list;
	}

	public Map getThesisScore() {
		return researchDao.getThesisScore();
	}

	public void saveThesisScore(HttpServletRequest request) {
		researchDao.saveThesisScore(request);
	}

	public Map getProjectScore() {
		return researchDao.getProjectScore();
	}

	public void saveProjectScore(HttpServletRequest request) {
		researchDao.saveProjectScore(request);
	}

	public Map getPatentScore() {
		return researchDao.getPatentScore();
	}

	public void savetPatentScore(HttpServletRequest request) {
		researchDao.savetPatentScore(request);
	}

	public Map getRewardScore() {
		return researchDao.getRewardScore();
	}

	public void savetRewardScore(HttpServletRequest request) {
		researchDao.savetRewardScore(request);
	}

	public List<Map>  queryScore4Tong(HttpServletRequest request) {
		List<Map> usernumList = researchDao.queryUserNum4Tong(request);
		List<Map> list = new ArrayList<>();
		if (usernumList != null && usernumList.size() > 0) {
			int szie = usernumList.size() ;
			for(int i=0; i <szie; i++){
				Map userMap = usernumList.get(i);
				String userNum = (String)userMap.get("USERNUM");
				String userName = (String)userMap.get("USERNAME");
				Map map = getScore4Usernum(userNum);
				map.put("USERNUM", userNum);
				map.put("USERNAME", userName);
				list.add(map);
			}
		}
		return list;
	}

	public Map getScore4Usernum(String userNum) {
		double sum = 0.0;
		Map scoreMap = new HashMap<>();
		//论文
		List<Map> thesisList = researchDao.getCurrentThesis4UserNum(userNum);
		Map theisScore = researchDao.getThesisScore();
		double workloadSum = 0;
		int size = 0;
		if (thesisList != null && thesisList.size() > 0) {
			size = thesisList.size();
			for (Map map : thesisList) {
				double workload = 0;
				String thesisPeriodical = (String) map.get("THESISPERIODICAL");
				if (StringUtils.isNotEmpty(thesisPeriodical)) {
					if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_SHOUSCI)) {
						if ((double) theisScore.get("thesisShousci") > workload);
						{
							workload = (double) theisScore.get("thesisShousci");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_EIYUAN)) {
						if ((double) theisScore.get("thesisEiyuan") > workload);
						{
							workload = (double) theisScore.get("thesisEiyuan");
						}
					} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_EISHOU)) {
						if ((double) theisScore.get("thesisEishou") > workload);
						{
							workload = (double) theisScore.get("thesisEishou");
						}
					}
				} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_JIAOWU)) {
					if ((double) theisScore.get("thesisJiaowu") > workload);
					{
						workload = (double) theisScore.get("thesisJiaowu");
					}
				} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_EISHOULU)) {
					if ((double) theisScore.get("thesisEishoulu") > workload);
					{
						workload = (double) theisScore.get("thesisEishoulu");
					}
				} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_SHOULU)) {
					if ((double) theisScore.get("thesisShoulu") > workload);
					{
						workload = (double) theisScore.get("thesisShoulu");
					}
				} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_CHINESE)) {
					if ((double) theisScore.get("thesisChinese") > workload);
					{
						workload = (double) theisScore.get("thesisChinese");
					}
				} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_GUOJI)) {
					if ((double) theisScore.get("thesisGuoji") > workload);
					{
						workload = (double) theisScore.get("thesisGuoji");
					}
				} else if (thesisPeriodical.contains(Constants.THESIS_INCLUDED_OTHER)) {
					if ((double) theisScore.get("thesisOther") > workload);
					{
						workload = (double) theisScore.get("thesisOther");
					}
				}
				workloadSum = workloadSum + workload;
			}
			scoreMap.put("thesis", size + "/" + workloadSum);
		}else{
			scoreMap.put("thesis", "0/0");
		}
        
		//专利
		List<Map> patentList = researchDao.getCurrentPatent4UserNum(userNum);
		Map patentScore = researchDao.getPatentScore();
		double workloadSum4Patent = 0;
		int patentSzie = 0;
		if (patentList != null && patentList.size() > 0) {
			patentSzie = patentList.size();
			for (Map map : patentList) {
				String patenttype = (String) map.get("PATENTTYPE");
				String patentFirst = (String) map.get("PATENTFIRST");
				String patentIsTransfer = (String) map.get("PATENTISTRANSFER");
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
				workloadSum4Patent = workloadSum4Patent + workload4Patent;
			}
			scoreMap.put("patent", patentSzie + "/" + workloadSum4Patent);
		}else{
			scoreMap.put("patent", "0/0");
		}
		
		
		//项目
		List<Map> projectList = researchDao.getCurrentProject4UserNum(userNum);
		Map projectScore = researchDao.getProjectScore();
		double workloadSum4project = 0;
		double   workloadSum4pro = 0;
		int projectSzie = 0;
		if (projectList != null && projectList.size() > 0) {
			projectSzie = projectList.size();
			for (Map map : projectList) {
				double workload4project = 0;
				String projectType = (String) map.get("PROJECTTYPE");
				double projectFund = (double) map.get("PROJECTFUND");
				int i = (int) projectFund;
				if (Constants.PROJECT_TYPE__GUO.equals(projectType)) {
					workload4project = (i * ((double)projectScore.get("guoFund") / (double)projectScore.get("guoFundLast"))) 
							* (double)projectScore.get("guoK");
				} else if (Constants.PROJECT_TYPE_SHEN.equals(projectType)) {
					workload4project = (i * ((double)projectScore.get("shenFund") / (double)projectScore.get("shenFundLast"))) 
							* (double)projectScore.get("shenK");
				} else if (Constants.PROJECT_TYPE_OTHER.equals(projectType)) {
					workload4project = (i * ((double)projectScore.get("otherFund") / (double)projectScore.get("otherFundLast"))) 
							* (double)projectScore.get("otherK");
				}
				workloadSum4project = workloadSum4project + workload4project;
				BigDecimal   b   =   new   BigDecimal(workload4project);  
			    workloadSum4pro   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
			    if(workloadSum4pro == 0){
			    	workloadSum4pro = 0;
			    }
			}
			scoreMap.put("project", projectSzie + "/" + workloadSum4pro);
		}else{
			scoreMap.put("project", "0/0");
		}
		
		
		//奖励
		List<Map> rewardtList = researchDao.getCurrentReward4UserNum(userNum);
		Map rewardScore = researchDao.getRewardScore();
		double workloadSum4reward = 0;
		int rewardSzie = 0;
		if (rewardtList != null && rewardtList.size() > 0) {
			rewardSzie = rewardtList.size();
			for (Map map : rewardtList) {
				String rewardType = (String) map.get("REWARDTYPE");
				String rewardPlace = (String) map.get("REWARDPLACE");
				double workload4reward = 0;
				if (Constants.REWARD_TYPE_SHEN1.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shenFirst");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shenSecond");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shenThird");
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shenFourth");
					} else {
						workload4reward = (double)rewardScore.get("shenFifth");
					}
				} else if (Constants.REWARD_TYPE_SHEN2.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shen2First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shen2Second");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shen2Third");
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shen2Fourth");
					} else {
						workload4reward = (double)rewardScore.get("shen2Fifth");
					}
				} else if (Constants.REWARD_TYPE_SHEN3.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shen3First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shen3Second");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shen3Third");
					} else if (Constants.PLACE_TYPE_FOURTH.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("shen3Fourth");
					}else{
						workload4reward = (double)rewardScore.get("shen3Fifth");
					}
				} else if (Constants.REWARD_TYPE_DISHI1.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("dishiFirst");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("dishiSecond");
					} else if (Constants.PLACE_TYPE_THIRD.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("dishiFhird");
					}
				} else if (Constants.REWARD_TYPE_DISHI2.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("dishi2First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("dishi2Second");
					}else{
						workload4reward = (double)rewardScore.get("dishi2Third");
					}
				} else if (Constants.REWARD_TYPE_DISHI3.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("dishi3First");
					} else if (Constants.PLACE_TYPE_SECOND.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("dishi3Second");
					}else{
						workload4reward = (double)rewardScore.get("dishi3Third");
					}
				} else if (Constants.REWARD_TYPE_XIAO1.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("schoolFirst");
					}
				} else if (Constants.REWARD_TYPE_XIAO2.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("schoolSecond");
					}
				} else if (Constants.REWARD_TYPE_XIAO3.equals(rewardType)) {
					if (Constants.PLACE_TYPE_FIRST.equals(rewardPlace)) {
						workload4reward = (double)rewardScore.get("schoolThird");
					}
				} else if (Constants.REWARD_TYPE_JIAO.equals(rewardType)){
					workload4reward = (double)rewardScore.get("youxiuJiaoxue");
				}else if ( Constants.REWARD_TYPE_QING.equals(rewardType)) {
					workload4reward = (double)rewardScore.get("yongTeach");
				}
				workloadSum4reward = workloadSum4reward + workload4reward;
			}
			scoreMap.put("reward", rewardSzie + "/" + workloadSum4reward);
			
			sum = workloadSum +workloadSum4Patent +workloadSum4pro + workloadSum4reward;
			scoreMap.put("sum", sum);
		}else{
			scoreMap.put("reward", "0/0");
			scoreMap.put("sum", 0);
		}
		
		return scoreMap;
	}
	
	public Map getUserInfo(String userNum) {
		return researchDao.getUserInfo(userNum);
	}
	
	public List<Map> queryThesisTongList(HttpServletRequest request) {
		return researchDao.queryThesisTongList(request);
	}
	
	public List<Map> queryProjecTongtList(HttpServletRequest request) {
		return researchDao.queryProjecTongtList(request);
	}
	
	public List<Map> queryRewardTongList(HttpServletRequest request) {
		return researchDao.queryRewardTongList(request);
	}
	
	public List<Map> queryPatentTongList(HttpServletRequest request) {
		return researchDao.queryPatentTongList(request);
	}
}
