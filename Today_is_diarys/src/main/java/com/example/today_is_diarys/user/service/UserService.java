package com.example.today_is_diarys.user.service;

import com.example.today_is_diarys.user.dto.UserInfoDto;
import com.example.today_is_diarys.user.entity.User;
import com.example.today_is_diarys.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
    }

    public Long save(UserInfoDto dto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        dto.setPassword(encoder.encode(dto.getPassword()));

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .nickName(dto.getNickName())
                .password(dto.getPassword())
                .age(dto.getAge())
                .sex(dto.getSex())
                .role("ROLE_USER").build()).getId();
    }

    public Long adsave(UserInfoDto dto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        dto.setPassword(encoder.encode(dto.getPassword()));

        return userRepository.save(User.builder()
                .email("admin@gmail.com")
                .nickName("bearKing")
                .password("bear")
                .age(99L)
                .sex(1L)
                .role("ROLE_ADMIN,ROLE_USER").build()).getId();
    }
}
