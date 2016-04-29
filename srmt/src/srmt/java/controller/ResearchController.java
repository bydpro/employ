package srmt.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@RequestMapping("/getWorkload4Tec.do")
	public double getWorkload4Tec(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		double sum = researchService.getWorkload(userId);
		return sum;
	}

	@RequestMapping("/enterWorkload4Tec.do")
	public ModelAndView enterWorkload4Tec(HttpServletRequest request) {
		String msgView = "researchMng/workload4Tec";
		return new ModelAndView(msgView);
	}
}
