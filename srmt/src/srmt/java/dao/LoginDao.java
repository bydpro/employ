package srmt.java.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import srmt.java.common.Constants;

@Repository
@Transactional
public class LoginDao {

	@Resource
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public String loginIn(String sql, String loginId, String password,HttpServletRequest request) {
		
		Transaction  transaction  = getSession().beginTransaction();
		SQLQuery query =getSession().createSQLQuery(sql);
		query.setParameter("loginId", loginId);
		query.setParameter("password", password);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list =query.list();
		String msg = "";
		if (list!=null&&list.size()>0) {
			Map loginInfo = list.get(0);
			String userId = (String)loginInfo.get("user_id");
			String login = (String)loginInfo.get("login_id");
			String organId = (String)loginInfo.get("organ_id");
			String userName = (String)loginInfo.get("username");
			String userType = (String)loginInfo.get("user_type");
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			session.setAttribute("loginId", login);
			session.setAttribute("organId", organId);
			session.setAttribute("userName", userName);
			session.setAttribute("userType", userType);
			msg =Constants.YES;
		} else {
			msg = Constants.NO;
		}
		return msg;
	}

}
