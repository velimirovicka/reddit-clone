package com.velimirovicka.redditclone.security;

import com.velimirovicka.redditclone.model.User;
import com.velimirovicka.redditclone.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        final Optional<User> userOptional = userRepository.findByUserName(username);
        User user = userOptional.orElseThrow(
            () -> new UsernameNotFoundException("No user")
        );

        return new org.springframework.security.core.userdetails.User(
            user.getUserName(),
            user.getPassword(),
            user.getEnabled(),
            true,
            true,
            true,
            List.of(new SimpleGrantedAuthority("USER"))
        );
    }
}
