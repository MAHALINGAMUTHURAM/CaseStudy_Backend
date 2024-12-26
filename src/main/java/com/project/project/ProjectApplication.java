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

@SpringBootApplication(scanBasePackages={"com.controller","com.service","com.filter,com.initializer,com.exception"})
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
	                
	             // POST requests
	                .requestMatchers(HttpMethod.POST, "/api/reservation/post").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.POST, "/api/rooms/post").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.POST, "/api/roomAmenity/post").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.POST, "/api/review/post").hasAnyRole("USER")
	                .requestMatchers(HttpMethod.POST, "/api/hotelamenity/post").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.POST, "/api/amenity/post").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.POST, "/api/RoomType/post").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.POST, "/api/hotels/post").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.POST, "/api/payment/post").hasAnyRole("ADMIN", "MANAGER", "USER")

	                // GET requests
	                .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/area/all").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/room/all").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/room/*").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/rooms/available/*").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/rooms/location/*").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/rooms/amenities/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/rooms/hotels/*").hasAnyRole("ADMIN", "MANAGER","USER")
	                .requestMatchers(HttpMethod.GET, "/api/rooms/*").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/reservation/*").hasAnyRole("ADMIN", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/reservation/date-range/*/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/reservation/all").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/review/all").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/review/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/review/reviews/rating/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/review/reviews/recent").hasAnyRole("USER", "ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/amenity/all").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/amenity/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/amenity/hotel/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/amenity/room/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/RoomType/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/hotels/all").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/hotels/location/*").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/hotels/*").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/hotels/amenity/*").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/hotels/area/*").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/payment/all").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.GET, "/api/payment/*").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/payment/status/*").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.GET, "/api/payment/total-revenue").hasAnyRole("ADMIN", "MANAGER", "USER")

	                // PUT requests
	                .requestMatchers(HttpMethod.PUT, "/api/room/update/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.PUT, "/api/reservation/update/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.PUT, "/api/manager/register/*").hasRole("ADMIN")
	                .requestMatchers(HttpMethod.PUT, "/api/review/update/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.PUT, "/api/amenity/update/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.PUT, "/api/RoomType/update/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.PUT, "/api/hotels/update/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.PUT, "/api/user/register").hasAnyRole("ADMIN", "USER")

	                // DELETE requests
	                .requestMatchers(HttpMethod.DELETE, "/api/tasks/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.DELETE, "/api/reservation/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.DELETE, "/api/review/delete/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.DELETE, "/api/amenity/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.DELETE, "/api/RoomType/delete/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.DELETE, "/api/users/delete/*").hasAnyRole("ADMIN", "MANAGER")
	                .requestMatchers(HttpMethod.DELETE, "/api/hotels/delete/*").hasAnyRole("ADMIN", "MANAGER", "USER")
	                .requestMatchers(HttpMethod.DELETE, "/api/payment/*").hasRole("ADMIN")
	             

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