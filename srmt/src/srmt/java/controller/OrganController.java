package srmt.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import srmt.java.service.OrganService;
import srmt.java.service.UserSevrvice;

@Controller
@RequestMapping("/organMng")
public class OrganController {
	@Autowired
	private OrganService organSevrvice;

	@RequestMapping("/enterOrganMng.do")
	public ModelAndView enterOrganMng() {

		return new ModelAndView("organMng/organMng");
	}

	@ResponseBody
	@RequestMapping("/queryOragnList.do")
	public List<Map> queryOragnList(HttpServletRequest request) {
		List<Map> list = organSevrvice.queryOragnList(request);
		return list;
	}

	@ResponseBody
	@RequestMapping("/queryOragn.do")
	public List<Map> queryOragn(HttpServletRequest request) {
		List<Map> list = organSevrvice.queryOragn(request);
		return list;
	}

	@ResponseBody
	@RequestMapping("/unLayoutOrgan.do")
	public Map unLayoutOrgan(HttpServletRequest request) {
		String organId = request.getParameter("organId");
		organSevrvice.unLayoutOrgan(organId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/layoutOrgan.do")
	public Map layoutOrgan(HttpServletRequest request) {
		String organId = request.getParameter("organId");
		organSevrvice.layoutOrgan(organId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/delOrgan.do")
	public Map delOrgan(HttpServletRequest request) {
		organSevrvice.delOrgan(request);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@RequestMapping("/getOrganInfo.do")
	public Map getOrganInfo(HttpServletRequest request) {
		String organId = request.getParameter("organId");
		return organSevrvice.getOrganInfo(organId);
	}

	@ResponseBody
	@RequestMapping("/saveOrgan.do")
	public Map saveOrgan(HttpServletRequest request) {;
		Map map = organSevrvice.saveOrgan(request);
		return map;
	}
}
