package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.UserBean;

@Repository
public class UserDao {
	@Autowired
	JdbcTemplate stmt;
	
	public void addUser(UserBean user) {
		//stmt.update("insert into users (firstName,password,email)"+ " values (?,?,?)",user.getFirstName(),user.getPassword(),user.getEmail());
		stmt.update("insert into users (firstname , email , password, createdAt,role) values (?,?,?,?,?)", user.getFirstName(),
				user.getEmail(), user.getPassword(),user.getCreatedAt(),user.getRole());
	}

	public UserBean getUserByEmail(String email) {
		// TODO Auto-generated method stub 
		try {
			return stmt.queryForObject("select * form users where email = ? ", 
					new BeanPropertyRowMapper<UserBean>(UserBean.class),new Object[] {email});
		}catch(Exception e){
			System.out.println("User not found with => "+ email);
			return null;
			
		}		
	}

	public void updataToken(String email,String authToken) {
		stmt.update("update users set authToken = ? where email = ? ",authToken,email);
		
	}
	public void updateOtp(String email, String otp) {
		stmt.update("update users set otp = ? where email = ?",otp,email);
	}
	public void updatePass(String email, String enc) {
		stmt.update("update users set password = ? where email = ?",enc,email);
	}
	
	
}
