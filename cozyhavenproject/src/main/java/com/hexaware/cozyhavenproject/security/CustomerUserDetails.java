package com.hexaware.cozyhavenproject.security;
import java.util.Collection;
import java.util.List;

import com.hexaware.cozyhavenproject.entities.Role;
import com.hexaware.cozyhavenproject.entities.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public class CustomerUserDetails  implements UserDetails{

	
	 private final User user;

	    public CustomerUserDetails(User user) { this.user = user; }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        Role r = user.getRole();
	        String roleName = "ROLE_" + (r != null ? r.name() : "USER");
	        return List.of(new SimpleGrantedAuthority(roleName));
	    }

	    @Override public String getPassword() { return user.getPassword(); }
	    @Override public String getUsername() { return user.getEmail(); }
	    @Override public boolean isAccountNonExpired() { return true; }
	    @Override public boolean isAccountNonLocked() { return true; }
	    @Override public boolean isCredentialsNonExpired() { return true; }
	    @Override public boolean isEnabled() { return true; }

	    public Integer getUserId() { return user.getUserId(); }
}
