package srmt.java.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srmt.java.dao.OrganDao;

@Service
public class OrganService {
	@Autowired
	private OrganDao organDao;

	public List<Map> queryOragnList(HttpServletRequest request) {
		List<Map> list = organDao.queryOragnList(request);
		return list;
	}

	public void layoutOrgan(String organId) {
		organDao.layoutOrgan(organId);
	}

	public void unLayoutOrgan(String organId) {
		organDao.unLayoutOrgan(organId);
	}

	public void delOrgan(HttpServletRequest request) {
		organDao.delOrgan(request);
	}

	public Map getOrganInfo(String organId) {
		return organDao.getOrganInfo(organId);
	}

	public Map saveOrgan(HttpServletRequest request) {
		return organDao.saveOrgan(request);
	}

	public List<Map> queryOragn(HttpServletRequest request) {
		List<Map> list = organDao.queryOrgan(request);
		return list;
	}
}
