package com.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.UserBean;
import com.dao.UserDao;
import com.dto.ForgetPasswordDto;
import com.dto.LoginDto;
import com.service.MailService;
import com.service.Tokenservice;

@RestController
public class SessionController {
	@Autowired
	UserDao userDao;
	
	@Autowired
	Tokenservice tokenservice;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	
	@Autowired
	MailService mailerservise;

//signup
	
	@PostMapping("/signup")
	public ResponseEntity<?>signup(@RequestBody UserBean user){ 
		UserBean dbUser =userDao.getUserByEmail(user.getEmail());
		if(dbUser!=null) {
				return ResponseEntity.unprocessableEntity().body(user);
		}
		else {
			System.out.println(user.getEmail());
			String encodedPasword = bcrypt.encode(user.getPassword());
			user.setPassword(encodedPasword);
			LocalDate d = LocalDate.now();
			user.setCreatedAt(d.toString());
			user.setRole(UserBean.Role.USER.getRoleId());
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
	
	@PostMapping("/forgetpassword")
	public ResponseEntity forgetpassword(@RequestBody LoginDto loginDto) {
		UserBean user = userDao.getUserByEmail(loginDto.getEmail());

		if (user != null) {
			String otp = tokenservice.generateToken(6);
			user.setOtp(otp);
			userDao.updateOtp(user.getEmail(), otp);
			mailerservise.sendMail(user);
		}
		return ResponseEntity.ok(loginDto);
	}
	// update password
		@PostMapping("/updatepassword")
		public ResponseEntity updatePassword(@RequestBody ForgetPasswordDto fdto) {
			UserBean user = userDao.getUserByEmail(fdto.getEmail());

			if (user != null) {
				// db check
				if (user.getOtp().equals(fdto.getOtp())) {
					userDao.updateOtp(user.getEmail(), "");
					String enc = bcrypt.encode(fdto.getPassword());
					userDao.updatePass(user.getEmail(), enc);
				}
				return ResponseEntity.ok(fdto);

			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fdto);
		}
}
