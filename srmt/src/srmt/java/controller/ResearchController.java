package srmt.java.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import srmt.java.common.Constants;
import srmt.java.service.ResearchService;

@Controller
@RequestMapping("/research")
public class ResearchController {
	@Autowired
	private ResearchService researchService;

	@RequestMapping("/enterResearchMng.do")
	public ModelAndView enterResearchMng(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userType = (String) session.getAttribute("userType");
		String msgView = "researchMng/research4Tec";
		if (Constants.USER_TYPE_ADMIN.equals(userType)) {
			msgView = "researchMng/researchMng";
		}
		return new ModelAndView(msgView);
	}

	@ResponseBody
	@RequestMapping("/queryResearchList.do")
	public List<Map> queryResearchList(HttpServletRequest request) {
		List<Map> list = researchService.queryResearchList(request);
		return list;
	}

	@ResponseBody
	@RequestMapping("/queryResearchType.do")
	public List<Map> queryResearchType() {
		List<Map> list = researchService.queryResearchType();
		return list;
	}

	@ResponseBody
	@RequestMapping("/delResearch.do")
	public Map delResearch(HttpServletRequest request) {
		String rid = request.getParameter("rid");
		researchService.delResearch(rid);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/saveThesis.do")
	public Map saveThesis(HttpServletRequest request) {
		researchService.saveThesis(request);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/getThesisInfo.do")
	public Map getThesisInfo(HttpServletRequest request) {
		String researchId = request.getParameter("researchId");
		return researchService.getThesisInfo(researchId);
	}

	@ResponseBody
	@RequestMapping("/saveReward.do")
	public Map saveReward(HttpServletRequest request) {
		researchService.saveReward(request);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/getRewardInfo.do")
	public Map getRewardInfo(HttpServletRequest request) {
		String researchId = request.getParameter("researchId");
		return researchService.getRewardInfo(researchId);
	}

	@ResponseBody
	@RequestMapping("/savePatent.do")
	public Map savePatent(HttpServletRequest request) {
		researchService.savePatent(request);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/getPatentInfo.do")
	public Map getPatentInfo(HttpServletRequest request) {
		String researchId = request.getParameter("researchId");
		return researchService.getPatentInfo(researchId);
	}

	@ResponseBody
	@RequestMapping("/saveProject.do")
	public Map saveProject(HttpServletRequest request) {
		researchService.saveProject(request);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/getProjectInfo.do")
	public Map getProjectInfo(HttpServletRequest request) {
		String researchId = request.getParameter("researchId");
		return researchService.getProjectInfo(researchId);
	}

	@ResponseBody
	@RequestMapping("/queryProjectType.do")
	public List<Map> queryProjectType() {
		List<Map> list = researchService.queryProjectType();
		return list;
	}

	@ResponseBody
	@RequestMapping("/queryPatentType.do")
	public List<Map> queryPatentType() {
		List<Map> list = researchService.queryPatentType();
		return list;
	}

	@ResponseBody
	@RequestMapping("/queryPlaceType.do")
	public List<Map> queryPlaceType() {
		List<Map> list = researchService.queryPlaceType();
		return list;
	}

	@ResponseBody
	@RequestMapping("/queryRewardType.do")
	public List<Map> queryRewardType() {
		List<Map> list = researchService.queryRewardType();
		return list;
	}

	@ResponseBody
	@RequestMapping("/queryThesisIncluded.do")
	public List<Map> queryThesisIncluded() {
		List<Map> list = researchService.queryThesisIncluded();
		return list;
	}

	@ResponseBody
	@RequestMapping("/queryThesisType.do")
	public List<Map> queryThesisType() {
		List<Map> list = researchService.queryThesisType();
		return list;
	}

	@ResponseBody
	@RequestMapping("/queryPatentPeople.do")
	public List<Map> queryPatentPeople() {
		List<Map> list = researchService.queryPatentPeople();
		return list;
	}

	@ResponseBody
	@RequestMapping("/saveFile.do")
	public Map saveFile(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("Filedata");
		String url = researchService.saveFile(multipartFile);
		Map map = new HashMap<>();
		map.put("url", url);
		return map;
	}

	@ResponseBody
	@RequestMapping("/getCurrentThesisWorkload4Tec.do")
	public List<Map> getCurrentThesisWorkload4Tec(HttpServletRequest request,Model Model) {
		HttpSession session = request.getSession();
		BigInteger userNum = (BigInteger) session.getAttribute("userNum");
		List<Map> sum = researchService.getCurrentThesisWorkload4Tec(userNum);
		Model.addAttribute("thesisList", sum);
		return sum;
	}

	@RequestMapping("/enterWorkload4Tec.do")
	public ModelAndView enterWorkload4Tec(HttpServletRequest request,Model Model) {
		String msgView = "researchMng/workload4Tec";
		HttpSession session = request.getSession();
		BigInteger userNum = (BigInteger) session.getAttribute("userNum");
		List<Map> thesisList = researchService.getCurrentThesisWorkload4Tec(userNum);
		List<Map> projectList = researchService.getCurrentProjectWorkload4Tec(userNum);
		List<Map> rewardList = researchService.getCurrentRewardWorkload4Tec(userNum);
		List<Map> patentList = researchService.getCurrentPatentWorkload4Tec(userNum);
        double sumWorkload = 0;
        int thesisSize = 0;
        int projectSize = 0;
        int rewardSize = 0;
        int patentSize = 0;
        if(thesisList!=null&&thesisList.size()>0){
        	thesisSize = thesisList.size()-1;
        	Map map = thesisList.get(thesisSize-1);
        	double workload =(double) map.get("workload");
        	sumWorkload = sumWorkload + workload;
        }
        if(projectList!=null&&projectList.size()>0){
        	projectSize = projectList.size()-1;
        	Map map = projectList.get(projectList.size()-1);
        	double workload =(double) map.get("workload");
        	sumWorkload = sumWorkload + workload;
        }
        if(rewardList!=null&&rewardList.size()>0){
        	rewardSize = rewardList.size()-1;
        	Map map = rewardList.get(rewardList.size()-1);
        	double workload =(double) map.get("workload");
        	sumWorkload = sumWorkload + workload;
        }
        if(patentList!=null&&patentList.size()>0){
        	patentSize = patentList.size()-1;
        	Map map = patentList.get(patentList.size()-1);
        	double workload =(double) map.get("workload");
        	sumWorkload = sumWorkload + workload;
        }
		Map sumMap = new HashMap<>();
		sumMap.put("sumWorkload", sumWorkload);
		sumMap.put("thesisSize", thesisSize);
		sumMap.put("projectSize", projectSize);
		sumMap.put("rewardSize", rewardSize);
		sumMap.put("patentSize", patentSize);
		Model.addAttribute("thesisList", thesisList);
		Model.addAttribute("projectList", projectList);
		Model.addAttribute("rewardList", rewardList);
		Model.addAttribute("patentList", patentList);
		Model.addAttribute("sumMap", sumMap);
		return new ModelAndView(msgView);
	}
}
