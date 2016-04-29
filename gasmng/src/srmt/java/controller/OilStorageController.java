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

import srmt.java.service.OilStorageService;

@Controller
@RequestMapping("/oilStorage")
public class OilStorageController {
	@Autowired
	private OilStorageService oilStorageService;

	@ResponseBody
	@RequestMapping("/queryOilStorage.do")
	public List<Map> queryOilStorage(HttpServletRequest request) {
		List<Map> list = oilStorageService.queryOilStorage(request);
		return list;
	}
	
	@RequestMapping("/enterUserMng.do")
	public ModelAndView enterUserMng() {

		return new ModelAndView("oilStorageMng/oilStorageMng");
	}
	
	@ResponseBody
	@RequestMapping("/delOliStirage.do")
	public Map delOliStirage(HttpServletRequest request){
		String oilStorageId = request.getParameter("oilStorageId");
		oilStorageService.delOliStirage(oilStorageId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/saveOliStirage.do")
	public Map saveOliStirage(HttpServletRequest request){
		oilStorageService.saveOliStirage(request);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/getOliStirage.do")
	public Map getOliStirage(HttpServletRequest request){
		String oilStorageId = request.getParameter("oilStorageId");
		return oilStorageService.getOliStirage(oilStorageId);
	}
	
	@ResponseBody
	@RequestMapping("/queryOilType.do")
	public List<Map> queryOilType() {
		List<Map> list = oilStorageService.queryOilType();
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/queryGasRecord.do")
	public List<Map> queryGasRecord(HttpServletRequest request) {
		List<Map> list = oilStorageService.queryGasRecord(request);
		return list;
	}
	
	@RequestMapping("/enterGasRecord.do")
	public ModelAndView enterGasRecord() {

		return new ModelAndView("gas/gasRecordMng");
	}
	
	@ResponseBody
	@RequestMapping("/delGasRecord.do")
	public Map delGasRecord(HttpServletRequest request){
		String gasId = request.getParameter("gasId");
		oilStorageService.delGasRecord(gasId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/saveGasRecord.do")
	public Map saveGasRecord(HttpServletRequest request){
		Map map=oilStorageService.saveGasRecord(request);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/getGasRecord.do")
	public Map getGasRecord(HttpServletRequest request){
		String gasId = request.getParameter("gasId");
		return oilStorageService.getGasRecord(gasId);
	}
	
	@RequestMapping("/enterMyOilRecord.do")
	public ModelAndView enterMyOilRecord() {

		return new ModelAndView("gas/myGasRecord");
	}
	
	@ResponseBody
	@RequestMapping("/queryMyGasRecord.do")
	public List<Map> queryMyGasRecord(HttpServletRequest request) {
		List<Map> list = oilStorageService.queryMyGasRecord(request);
		return list;
	}
	
	@RequestMapping("/enterRecharge.do")
	public ModelAndView enterRecharge() {

		return new ModelAndView("gas/recharge");
	}
	
	@RequestMapping("/enterGasSaleMng.do")
	public ModelAndView enterGasSaleMng() {

		return new ModelAndView("gas/gasSaleMng");
	}
	
	@ResponseBody
	@RequestMapping("/getOilSalInfo.do")
	public Map getOilSalInfo(HttpServletRequest request) {
		Map  map= oilStorageService.getOilSalInfo();
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/queryOilStorage4All.do")
	public Map queryOilStorage4All(HttpServletRequest request) {
		Map  map= oilStorageService.queryOilStorage4All();
		return map;
	}
}
