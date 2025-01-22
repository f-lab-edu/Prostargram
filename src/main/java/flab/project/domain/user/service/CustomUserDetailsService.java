package flab.project.domain.user.service;

import flab.project.domain.user.model.UserForAuth;
import flab.project.domain.user.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthenticationMapper authenticationMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserForAuth user = authenticationMapper.getUser(username);

        if (user == null) {
            throw new BadCredentialsException("아이디 혹은 패스워드가 틀렸습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getUserType().name()));

        return new User(String.valueOf(user.getUserId()), user.getPassword(), grantedAuthorities);
    }
}