package com.spring.practice.configsecurity;

import com.spring.practice.Entity.User;
import com.spring.practice.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("******************");
        User user = repo.findByUserName(username);
        if(user != null){
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName()).password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0])).build();
            return userDetails;
        }
        throw new UsernameNotFoundException("User not found "+username);

//        return null;
    }
}
