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
	
	public List<Map> queryUser4sel(HttpServletRequest request){
		List<Map> list = userDao.queryUser4sel(request);
		return list;
	}
	
	public Map saveMyUser(HttpServletRequest request){
		return userDao.saveMyUser(request);
	}
	
	public List<Map> querySysDict(HttpServletRequest request){
		List<Map> list = userDao.querySysDict(request);
		return list;
	}
	
	public Map saveDict(HttpServletRequest request){
		return userDao.saveDict(request);
	}
	
	public void delDict(HttpServletRequest request){
		userDao.delDict(request);
	}
	
	public Map getDictInfo(String userId){
		return userDao.getDictInfo(userId);
	}
}