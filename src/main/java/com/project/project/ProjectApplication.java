package com.project.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.dao.UserDAO;
import com.filter.JwtFilter;
import com.service.CustomUserDetailsService;

@SpringBootApplication(scanBasePackages={"com.controller","com.service","com.filter,com.initializer"})
@EntityScan("com.model")
@EnableJpaRepositories("com.dao")
@EnableWebSecurity

public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
		
	}
	 
	
	@Bean
	@DependsOn("userDetailsService")
	public DaoAuthenticationProvider daoAuthenticationProvider() {
	    CustomUserDetailsService service=userDetailsService();

	  
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    
	    provider.setUserDetailsService(service);
	    provider.setPasswordEncoder(passwordEncoder());
	    return provider;
	}

	@Bean
	public CustomUserDetailsService userDetailsService() {
		
	 
	    return new CustomUserDetailsService(); // Implement your own user details service
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(); // Use a password encoder of your choice
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    System.out.println("invoked");
		http
	        .csrf().disable() // Disable CSRF protection
	        
	        .authorizeRequests()
	            .requestMatchers("/api/auth").permitAll()
	            
	                .requestMatchers(HttpMethod.PUT,"/api/manager/register/*").hasRole("ADMIN")
	                .requestMatchers("/api/user/register").permitAll() 
	                .requestMatchers("/api/manager/register/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.POST,"/api/reservation/post").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.POST,"/api/reservation/post").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.POST,"/api/reservation/post").hasRole("USER")
		            .requestMatchers(HttpMethod.POST,"/api/rooms/post").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.POST,"/api/rooms/post").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.GET,"/api/rooms/all").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.GET,"/api/rooms/all").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.GET,"/api/rooms/all").hasRole("USER")
		            .requestMatchers(HttpMethod.GET,"/room/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.GET,"/room/*").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.GET,"/room/*").hasRole("USER")
		            .requestMatchers(HttpMethod.GET,"/rooms/available/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.GET,"/rooms/available/*").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.GET,"/rooms/available/*").hasRole("USER")
		            .requestMatchers(HttpMethod.GET,"/rooms/location/").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.GET,"/rooms/location/").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.GET,"/rooms/location/").hasRole("USER")
		            .requestMatchers(HttpMethod.GET,"rooms/amenities/*").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.GET,"rooms/amenities/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.PUT,"/room/update/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.PUT,"/room/update/*").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.DELETE,"/tasks/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.DELETE,"/tasks/*").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.GET,"/api/reservation/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.GET,"/api/reservation/*").hasRole("USER")
		            .requestMatchers(HttpMethod.DELETE,"/api/reservation/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.DELETE,"/api/reservation/*").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.PUT,"/api/reservation/update/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.PUT,"/api/reservation/update/*").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.GET, "/api/reservation/date-range/*/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.GET, "/api/reservation/date-range/*/*").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.GET,"/api/reservation/*").hasRole("USER")
		            .requestMatchers(HttpMethod.GET,"/api/reservation/all").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.GET,"/api/reservation/all").hasRole("MANAGER")
		            .requestMatchers(HttpMethod.PUT,"/api/manager/register/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.POST,"/api/roomAmenity/post").hasAnyRole("MANAGER","ADMIN")
		            .requestMatchers(HttpMethod.POST,"/api/review/post").hasAnyRole("USER")
		            .requestMatchers(HttpMethod.GET,"/api/review/all").hasAnyRole("MANAGER","ADMIN")
		            .requestMatchers(HttpMethod.GET,"/api/review/*").hasAnyRole("MANAGER","ADMIN")
		            .requestMatchers(HttpMethod.GET,"/api/review/reviews/rating/*").hasAnyRole("MANAGER","ADMIN")
		            .requestMatchers(HttpMethod.PUT,"/api/review/update/*").hasAnyRole("MANAGER","ADMIN")
		            .requestMatchers(HttpMethod.DELETE,"/api/review/delete/*").hasAnyRole("MANAGER","ADMIN")
		            .requestMatchers(HttpMethod.GET,"/api/review/reviews/recent").hasAnyRole("USER","MANAGER","ADMIN")
		            .requestMatchers(HttpMethod.POST,"/api/hotelamenity/post").hasAnyRole("MANAGER","ADMIN")
		            .requestMatchers(HttpMethod.POST,"/api/review/post").hasRole("USER")
		            .requestMatchers(HttpMethod.POST,"/api/amenity/post").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.GET,"/api/amenity/all").hasAnyRole("ADMIN","MANAGER","USER")
		            .requestMatchers(HttpMethod.GET,"/api/amenity/*").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.PUT,"/api/amenity/update/*").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.DELETE,"/api/amenity/*").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.GET,"/api/amenity/hotel/*").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.GET,"/api/amenity/room/*").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.POST,"/api/RoomType/post").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.GET,"/api/RoomType/*").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.DELETE,"/api/RoomType/delete/*").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.PUT,"/api/RoomType/update/*").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.PUT,"/api/manager/register/*").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.PUT,"/api/user/registert").hasAnyRole("ADMIN","USER")
		            .requestMatchers(HttpMethod.POST,"/api/hotels/post").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.POST,"/api/hotels/all").hasAnyRole("ADMIN","MANAGER","USER")
		            .requestMatchers(HttpMethod.GET,"/api/hotels/location/*").hasAnyRole("ADMIN","MANAGER","USER")
		            .requestMatchers(HttpMethod.GET,"/api/hotels/*").hasAnyRole("ADMIN","MANAGER","USER")
		            .requestMatchers(HttpMethod.GET,"/api/hotels/amenity/*").hasAnyRole("ADMIN","MANAGER","USER")
		            .requestMatchers(HttpMethod.PUT,"/api/hotels/update/*").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.POST,"/api/payment/post").hasAnyRole("ADMIN","MANAGER","USER")
		            .requestMatchers(HttpMethod.GET,"/api/payment/all").hasAnyRole("ADMIN","MANAGER")
		            .requestMatchers(HttpMethod.GET,"/api/payment/*").hasAnyRole("ADMIN","MANAGER","USER")
		            .requestMatchers(HttpMethod.GET,"/api/payment/status/*").hasAnyRole("ADMIN","MANAGER","USER")
		            .requestMatchers(HttpMethod.GET,"/api/payment/total-revenue").hasAnyRole("ADMIN","MANAGER","USER")
		            .requestMatchers(HttpMethod.DELETE,"/api/payment/*").hasAnyRole("ADMIN")
	             

	            .anyRequest().authenticated()
	            .and()
	             .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
	           
	        .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and()
	            .sessionManagement().disable()
	            	
	            
	                
	        .authenticationManager(new ProviderManager(daoAuthenticationProvider()));
	        
	
	        
	    return http.build();
	}
}