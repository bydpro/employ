package srmt.java.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srmt.java.dao.UserDao;

@Service
public class UserSevrvice {
	
	@Autowired
	private UserDao userDao;
	
	public List<Map> queryUserList(HttpServletRequest request){
		List<Map> list = userDao.queryUserList(request);
		return list;
	}
	
	public void delUser(HttpServletRequest request){
		userDao.delUser(request);
	}
	
	public Map saveUser(HttpServletRequest request){
		return userDao.saveUser(request);
	}
	
	public Map getUserInfo(String userId){
		return userDao.getUserInfo(userId);
	}
	
	public void layoutUser(String userId) {
		userDao.layoutUser(userId);
	}

	public void unLayoutUser(String userId) {
		userDao.unLayoutUser(userId);
	}
	
	public Map savePassword(String password,String userId ,String newPassword) {
		return userDao.savePassword(password,userId,newPassword);
	}
	
	public void isXTAdmin(String userId) {
		userDao.isXTAdmin(userId);
	}

	public void isAdmin(String userId) {
		userDao.isAdmin(userId);
	}
	
	public void isNormalUser(String userId) {
		userDao.isNormalUser(userId);
	}
	
	public Map getMyMoney(HttpServletRequest request) {
		return userDao.getMyMoney(request);
	}
	
	public double reMoney(HttpServletRequest request) {
		return userDao.reMoney(request);
	}
}