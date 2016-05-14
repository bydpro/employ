package srmt.java.service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
		;
		return list;
	}

	public List<Map> queryResearchType() {
		List<Map> list = researchDao.queryResearchType();
		;
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
		File file = new File(filePath,dateString+multipartFile.getOriginalFilename());
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
}
