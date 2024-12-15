//package com.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.dao.UserDAO;
//import com.model.UserEntity;
//
//public class CustomerUserDetailService implements UserDetailsService {
//	
//	@Autowired
//	UserDAO userDAO;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		
//		UserEntity user=userDAO.findById(username).get();
//		
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), List.of(authority));
//	}
//
//}
