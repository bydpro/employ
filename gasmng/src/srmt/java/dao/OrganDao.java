package srmt.java.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import srmt.java.common.Constants;
import srmt.java.entity.SysOrgan;

@Repository
@Transactional
public class OrganDao {
	@Resource
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	public List<Map> queryOragnList(HttpServletRequest request) {
		String organName = request.getParameter("organName");
		String organCode = request.getParameter("organCode");
		String isValid = request.getParameter("isValid");
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT                                    ");
		sb.append(" 	SO.ORGAN_ID ORGANID,                      ");
		sb.append("   SO.ORGAN_NAME ORGANNAME,              ");
		sb.append("   SO.ORGAN_CODE ORGANCODE,              ");
		sb.append("   SO.ORGAN_ADDRESS ORGANADDRESS,        ");
		sb.append("   SO.IS_VALID ISVALID                               ");
		sb.append(" FROM                                             ");
		sb.append(" 	SYS_ORGAN SO    WHERE 1=1                                   ");
		if (StringUtils.isNotEmpty(organName)) {
			sb.append(" and  SO.ORGAN_NAME LIKE :organName       ");
		}
		if (StringUtils.isNotEmpty(organCode)) {
			sb.append("  and SO.ORGAN_CODE LIKE :organCode       ");
		}
		if (StringUtils.isNotEmpty(isValid)) {
			if(isValid.equals(Constants.YES)){
				sb.append("  and SO.IS_VALID = :isValid       ");
			}else {
				isValid = Constants.YES;
				sb.append("  and SO.IS_VALID != :isValid or SO.IS_VALID is null     ");
			}
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		if (StringUtils.isNotEmpty(organName)) {
			organName = "%" + organName + "%";
			query.setParameter("organName", organName);
		}
		if (StringUtils.isNotEmpty(organCode)) {
			organCode = "%" + organCode + "%";
			query.setParameter("organCode", organCode);
		}
		if (StringUtils.isNotEmpty(isValid)) {
			query.setParameter("isValid", isValid);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> queryList = query.list();
		return queryList;
	}

	public void delOrgan(HttpServletRequest request) {
		String organId = request.getParameter("organId");
		Transaction transaction = getSession().beginTransaction();
		SysOrgan sysOrgan = (SysOrgan) getSession().get(SysOrgan.class, organId);
		getSession().delete(sysOrgan);
		transaction.commit();
	}

	public void unLayoutOrgan(String organId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(organId)) {
			SysOrgan sysOrgan = (SysOrgan) getSession().get(SysOrgan.class, organId);
			sysOrgan.setIsValid(Constants.YES);
			getSession().update(sysOrgan);
		}
		transaction.commit();
		getSession().close();
	}

	public void layoutOrgan(String organId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(organId)) {
			SysOrgan sysOrgan = (SysOrgan) getSession().get(SysOrgan.class, organId);
			sysOrgan.setIsValid(Constants.NO);
			getSession().update(sysOrgan);
		}
		transaction.commit();
		getSession().close();
	}

	public Map getOrganInfo(String organId) {
		Map map = new HashMap();
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(organId)) {
			SysOrgan sysOrgan = (SysOrgan) getSession().get(SysOrgan.class, organId);
			map.put("organId", sysOrgan.getOrganId());
			map.put("organCode", sysOrgan.getOrganCode());
			map.put("organName", sysOrgan.getOrganName());
			map.put("isValid", sysOrgan.getIsValid());
		    map.put("address", sysOrgan.getOrganAddress());
			}
		transaction.commit();
		getSession().close();
		return map;
	}
	
	public Map saveOrgan(HttpServletRequest request) {
		String organId = request.getParameter("organId");
		String organName = request.getParameter("organName");
		String organCode = request.getParameter("organCode");
		String address = request.getParameter("address");
		String isValid = request.getParameter("isValid");
		Map result = new HashMap();
		if(isExitOrganCode(organCode, organId)){
			result.put("errorMsg", true);
			result.put("msg", "单位代码不能重复");
			return result;
		}
		Transaction transaction = getSession().beginTransaction();
		if(StringUtils.isNotEmpty(organId)){
			SysOrgan sysOrgan = (SysOrgan) getSession().get(SysOrgan.class, organId);
			sysOrgan.setIsValid(isValid);
			sysOrgan.setOrganAddress(address);
			sysOrgan.setOrganCode(organCode);
			sysOrgan.setOrganName(organName);
			getSession().update(sysOrgan);
		}else {
			SysOrgan sysOrgan = new SysOrgan();
			sysOrgan.setIsValid(isValid);
			sysOrgan.setOrganAddress(address);
			sysOrgan.setOrganCode(organCode);
			sysOrgan.setOrganName(organName);
			getSession().save(sysOrgan);
		}
		transaction.commit();
		getSession().close();
		result.put("success", true);
		return result;
	}
	
	public List<Map> queryOrgan(HttpServletRequest request){
		String sql = "SELECT SO.ORGAN_ID  ORGANID ,SO.ORGAN_NAME ORGANNAME FROM SYS_ORGAN SO WHERE SO.IS_VALID=:isValid";
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		List<Map> queryList = query.list();
		transaction.commit();
		getSession().close();
		return queryList;
	}
	
	public Boolean isExitOrganCode(String organCode,String organId){
		Boolean flag = false;
		String sql ="select * from sys_organ where organ_code=:organCode ";
		if(StringUtils.isNotEmpty(organId)){
			sql = sql +"  and  organ_id != :organId";
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("organCode", organCode);
		if(StringUtils.isNotEmpty(organId)){
			query.setParameter("organId", organId);
		}
		List queryList = query.list();
		if(queryList!=null&&queryList.size()>0){
			flag = true;
		}
		return flag;
	}
}
