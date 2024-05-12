package com.example.apexcrud.configuration.securityHelper;


import com.example.apexcrud.exceptions.ResourceNotFoundException;
import com.example.apexcrud.model.User;
import com.example.apexcrud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = this.userRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId" + userId, 0l));
        return user;
    }
}
