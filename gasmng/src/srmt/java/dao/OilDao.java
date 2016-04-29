package srmt.java.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import srmt.java.common.MyUtil;
import srmt.java.entity.GasRecord;
import srmt.java.entity.OilStorage;
import srmt.java.entity.SysUser;

@Repository
@Transactional
public class OilDao {
	@Resource
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<Map> queryOilStorage(HttpServletRequest request) {
		String oilType = request.getParameter("oilType");
		String oilTankId = request.getParameter("oilTankId");
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT o.oil_tank_id,o.oil_storage_id, "
				+ "(select s.dict_name from sys_dict s where s.dict_value= o.oil_type) oil_type,o.olil_num,"
				+ "DATE_FORMAT(o.oil_receive_time,'%Y-%m-%d %H:%i')  "
				+ "oil_receive_time from oil_storage    o       where 1=1        						");
		if (StringUtils.isNotEmpty(oilType)) {
			sb.append(" and o.oil_type = :oilType   ");
		}
		if (StringUtils.isNotEmpty(oilTankId)) {
			sb.append("  and o.oil_tank_id = :oilTankId   ");
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		if (StringUtils.isNotEmpty(oilType)) {
			query.setParameter("oilType", oilType);
		}
		if (StringUtils.isNotEmpty(oilTankId)) {
			query.setParameter("oilTankId", oilTankId);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		return queryList;
	}

	public void saveOliStirage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String oilStorageId = request.getParameter("oilStorageId");
		String oilType = request.getParameter("oilType");
		String olilNum = request.getParameter("olilNum");
		String oilTankId = request.getParameter("oilTankId");
		String oilReceiveTime = request.getParameter("oilReceiveTime");
		String userId = (String) session.getAttribute("userId");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date oilReceiveDate = null;
		try {
			if (StringUtils.isNotEmpty(oilReceiveTime)) {
				oilReceiveDate = sdf.parse(oilReceiveTime);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(oilStorageId)) {
			OilStorage oilStorage = getSession().get(OilStorage.class, oilStorageId);
			oilStorage.setOilReceiveTime(oilReceiveDate);
			oilStorage.setOlilNum(Integer.parseInt(olilNum));
			oilStorage.setOilType(oilType);
			oilStorage.setOilTankId(oilTankId);
			oilStorage.setCeraterDate(new Date());
			oilStorage.setCreater(userId);
			getSession().update(oilStorage);
		} else {
			OilStorage oilStorage = new OilStorage();
			oilStorage.setOilReceiveTime(oilReceiveDate);
			oilStorage.setOlilNum(Integer.parseInt(olilNum));
			oilStorage.setOilType(oilType);
			oilStorage.setOilTankId(oilTankId);
			getSession().save(oilStorage);
		}
		transaction.commit();
		getSession().close();
	}

	public Map getOliStirage(String oilStorageId) {
		Transaction transaction = getSession().beginTransaction();
		Map oilMap = new HashMap<>();
		if (StringUtils.isNoneEmpty(oilStorageId)) {
			OilStorage oilStorage = getSession().get(OilStorage.class, oilStorageId);
			oilMap.put("oilStorageId", oilStorageId);
			oilMap.put("oilType", oilStorage.getOilType());
			oilMap.put("olilNum", oilStorage.getOlilNum());
			oilMap.put("oilTankId", oilStorage.getOilTankId());
			oilMap.put("oilReceiveTime", oilStorage.getOilReceiveTime().toString());
		}
		transaction.commit();
		getSession().close();
		return oilMap;
	}

	public void delOliStirage(String oilStorageId) {
		Transaction transaction = getSession().beginTransaction();
		OilStorage oilStorage = getSession().get(OilStorage.class, oilStorageId);
		getSession().delete(oilStorage);
		transaction.commit();
	}

	public List<Map> queryOilType() {
		Transaction transaction = getSession().beginTransaction();
		String sql = "select s.dict_name dictname,s.dict_value dictvalue from sys_dict s where s.dict_is_valid=:isValid"
				+ " and s.dict_type=:dictType ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_TYPE_GAS);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		return queryList;

	}

	public List<Map> queryGasRecord(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String userNum = request.getParameter("userNum");
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT                                                 ");
		sb.append("  	g.gas_id gasid,                                     ");
		sb.append("    g.gas_price gasprice,                                ");
		sb.append("    g.gas_volume gasvolume,                              ");
		sb.append("    DATE_FORMAT(g.gas_time,'%Y-%m-%d %H:%i')  gastime,                                  ");
		sb.append("    g.gas_user_num gasusernum,                           ");
		sb.append("      s.username username,                         ");
		sb.append("      s.mobile,                         ");
		sb.append("      s.email ,                        ");
		sb.append(" (select sd.dict_name from sys_dict sd where sd.dict_value= g.gas_type) gastype  ");
		sb.append("  FROM                                                   ");
		sb.append("  	gas_record g                                        ");
		sb.append("  LEFT JOIN sys_user s ON g.gas_user_num = s.user_num    where 1=1 ");
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" and s.USERNAME like :userName   ");
		}
		if (StringUtils.isNotEmpty(email)) {
			sb.append(" and s.EMAIL like :email   ");
		}
		if (StringUtils.isNotEmpty(mobile)) {
			sb.append(" and s.mobile = :mobile   ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" and g.gas_user_num = :userNum   ");
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (StringUtils.isNotEmpty(userName)) {
			userName = "%" + userName + "%";
			query.setParameter("userName", userName);
		}
		if (StringUtils.isNotEmpty(email)) {
			email = "%" + email + "%";
			query.setParameter("email", email);
		}
		if (StringUtils.isNotEmpty(mobile)) {
			query.setParameter("mobile", mobile);
		}
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		List<Map> queryList = query.list();
		return queryList;
	}

	public void delGasRecord(String gasId) {
		Transaction transaction = getSession().beginTransaction();
		GasRecord gasRecord = getSession().get(GasRecord.class, gasId);
		getSession().delete(gasRecord);
		transaction.commit();
	}

	public Map saveGasRecord(HttpServletRequest request) {
		Map map =new HashMap<>();
		String gasId = request.getParameter("gasId");
		String gasUserNum = request.getParameter("gasUserNum");
		String gasType = request.getParameter("gasType");
		String gasPrice = request.getParameter("gasPrice");
		String gasVolume = request.getParameter("gasVolume");
		String sql = "select * from sys_user su where su.user_num=:userNum";
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("userNum", gasUserNum);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		String userId = "";
		double money=0;
		List<Map> list = query.list();
		if (list != null && list.size() > 0) {
			Map loginInfo = list.get(0);
			userId = (String) loginInfo.get("user_id");
			money = (double) loginInfo.get("user_balance");
		}else{
			map.put("error", "当前输入卡号不存在!");
			return map;
		}
		SysUser sysUser =getSession().get(SysUser.class, userId);
		if (StringUtils.isNotEmpty(gasId)) {
			GasRecord gasRecord = getSession().get(GasRecord.class, gasId);
			double userMoney = Double.valueOf(gasPrice)*Double.valueOf(gasVolume);
			double myMoney =sysUser.getUserBalance()+gasRecord.getGasPrice()*gasRecord.getGasVolume()-userMoney;
			if(myMoney<0){
				map.put("error", "用户余额不足,请充值!");
				return map;
			}
			gasRecord.setGasPrice(Double.valueOf(gasPrice));
			gasRecord.setGasUserNum(Long.valueOf(gasUserNum));
			gasRecord.setGasVolume(Double.valueOf(gasVolume));
			gasRecord.setGasType(gasType);
			getSession().update(gasRecord);
			sysUser.setUserBalance(myMoney);
			getSession().update(sysUser);
			map.put("sucesss", true);
		} else {
			double userMoney = Double.valueOf(gasPrice)*Double.valueOf(gasVolume);
			if((sysUser.getUserBalance()-userMoney)<0){
				map.put("error", "用户余额不足,请充值!");
				return map;
			}
			GasRecord gasRecord = new GasRecord();
			gasRecord.setGasPrice(Double.valueOf(gasPrice));
			gasRecord.setGasUserNum(Long.valueOf(gasUserNum));
			gasRecord.setGasVolume(Double.valueOf(gasVolume));
			gasRecord.setGasType(gasType);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			gasRecord.setGasTime(ts);
			gasRecord.setCreaterTime(ts);
			getSession().save(gasRecord);
			sysUser.setUserBalance(sysUser.getUserBalance()-userMoney);
			getSession().update(sysUser);
			map.put("sucesss", true);
		}
		transaction.commit();
		return map;
	}
	
	public Map getGasRecord(String gasId) {
		Transaction transaction = getSession().beginTransaction();
		Map gasMap = new HashMap<>();
		if (StringUtils.isNoneEmpty(gasId)) {
			GasRecord gasRecord = getSession().get(GasRecord.class, gasId);
			gasMap.put("gasId", gasId);
			gasMap.put("gasType", gasRecord.getGasType());
			gasMap.put("gasUserNum", gasRecord.getGasUserNum());
			gasMap.put("gasPrice", gasRecord.getGasPrice());
			gasMap.put("gasVolume", gasRecord.getGasVolume());
		}
		transaction.commit();
		getSession().close();
		return gasMap;
	}
	
	public List<Map> queryMyGasRecord(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT                                                 ");
		sb.append("  	g.gas_id gasid,                                     ");
		sb.append("    g.gas_price gasprice,                                ");
		sb.append("    g.gas_volume gasvolume,                              ");
		sb.append("    DATE_FORMAT(g.gas_time,'%Y-%m-%d %H:%i')  gastime,                                  ");
		sb.append("   round(g.gas_volume* g.gas_price, 2) userMoney,                                  ");
		sb.append("    g.gas_user_num gasusernum,                           ");
		sb.append("      s.username username,                         ");
		sb.append(" (select sd.dict_name from sys_dict sd where sd.dict_value= g.gas_type) gastype  ");
		sb.append("  FROM                                                   ");
		sb.append("  	gas_record g                                        ");
		sb.append("  LEFT JOIN sys_user s ON g.gas_user_num = s.user_num    where 1=1 ");
		sb.append("  and g.gas_user_num=:userNum ");
		HttpSession session =request.getSession();
		BigInteger userNum =(BigInteger)session.getAttribute("userNum");
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("userNum", userNum);
		List<Map> queryList = query.list();
		return queryList;
	}
	
	public Double queryOilSalVolume(int year,int month,String gasType){
		String monthStr =Integer.toString(month);
		int length=monthStr.length();
		if(length==1){
			monthStr="0"+monthStr;
		}
		String dateStr=Integer.toString(year)+monthStr;
		Transaction transaction = getSession().beginTransaction();
		StringBuffer sb = new StringBuffer();
		sb.append("  select SUM(gr.gas_volume) gasvolume from gas_record gr  ");
		sb.append("  where DATE_FORMAT(gr.gas_time,'%Y%m') = :dateStr       ");
		sb.append("  and gr.gas_type= :gasType                              ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
		query.setParameter("dateStr", dateStr);
		query.setParameter("gasType", gasType);
		List<Map> queryList = query.list(); 
		Map map = new HashMap<>();
		Double gasVolume=0.0;
	   if(queryList!=null&&queryList.size()>0){
		   map=queryList.get(0);
		   gasVolume=(Double)map.get("gasvolume");
	   }	
	  return gasVolume;
	}
	
	
	public double queryGasTotal(String gasType){
		Transaction transaction = getSession().beginTransaction();
		String sql = "select SUM(os.olil_num)  olilnum from oil_storage os where os.oil_type=:gasType";
		SQLQuery totalQuery = getSession().createSQLQuery(sql);
		totalQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		totalQuery.setParameter("gasType", gasType);
		List<Map> totalList = totalQuery.list();
		double oilTotal = 0.00;
		Map totalMap = new HashMap<>();
		if (totalList != null && totalList.size() > 0) {
			totalMap = totalList.get(0);
			BigDecimal oilnum =(BigDecimal) totalMap.get("olilnum");
			oilTotal = oilnum.doubleValue();
		}
		return oilTotal;
	}
	
	public double queryOilSalVolume(String gasType){
		Transaction transaction = getSession().beginTransaction();
		String sql = "select sum(gr.gas_volume)  gasvolume  from gas_record gr where gr.gas_type=:gasType";
		SQLQuery totalQuery = getSession().createSQLQuery(sql);
		totalQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		totalQuery.setParameter("gasType", gasType);
		List<Map> totalList = totalQuery.list();
		double oilTotal =0;
		Map totalMap = new HashMap<>();
		if (totalList != null && totalList.size() > 0) {
			totalMap = totalList.get(0);
			oilTotal =(Double) totalMap.get("gasvolume");
		}
		return oilTotal;
	}
}