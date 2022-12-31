package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.UserBean;
import com.dao.UserDao;
import com.dto.LoginDto;
import com.service.MailService;
import com.service.Tokenservice;

@RestController
public class SessionController {
	@Autowired
	MailService mailerservise;
	@Autowired
	UserDao userDao;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	@Autowired
	Tokenservice tokenservice;
	//signup
	
	@PostMapping("/signup")
	public ResponseEntity<?>signup(@RequestBody UserBean user){ 
		UserBean dbUser =userDao.getUserByEmail(user.getEmail());
		if(dbUser!=null) {
				return ResponseEntity.unprocessableEntity().body(user);
		}
		else {
			String enPassword=bcrypt.encode(user.getPassword());
			user.setPassword(enPassword);
			userDao.addUser(user);
				return ResponseEntity.ok(user);
			}
	}
	//login
	@PostMapping("/login")
	public ResponseEntity<?>login(@RequestBody LoginDto login){
		UserBean user =userDao.getUserByEmail(login.getEmail());
		boolean isInvalid=false;
		if(user==null){
			isInvalid=true;
		}else if(bcrypt.matches(login.getPasword(), user.getPassword())==false)
			isInvalid=true;
		if(isInvalid=true) {
			return ResponseEntity.unprocessableEntity().body(login);
		}else {
			user.setAuthToken(tokenservice.generateToken(32));
			userDao.updataToken(user.getEmail(),user.getAuthToken());
			return ResponseEntity.ok(user);
		}
		
	}
	//forgetpassword
	
	
}
