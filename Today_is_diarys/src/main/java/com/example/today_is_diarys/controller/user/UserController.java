package com.example.today_is_diarys.controller.user;

import com.example.today_is_diarys.dto.user.request.UserInfoDto;
import com.example.today_is_diarys.entity.user.User;
import com.example.today_is_diarys.enums.Role;
import com.example.today_is_diarys.repository.user.UserRepository;
import com.example.today_is_diarys.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody UserInfoDto dto){
                 userRepository.save(User.builder()
                .email(dto.getEmail())
                .nickName(dto.getNickName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .introduce(dto.getIntroduce())
                .age(dto.getAge())
                .sex(dto.getSex())
                .role(Role.ROLE_USER).build());
    }

    @PostMapping("/ad/signup")
    public void adsignup(){
        userRepository.save(User.builder()
                .email("admin@gmail.com")
                .nickName("bearKing")
                .password(passwordEncoder.encode("bear"))
                .introduce("admin")
                .age(99L)
                .sex(1L)
                .role(Role.ROLE_ADMIN).build());
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect/logout";
    }

    @PostMapping("/login")
    public void login(@RequestBody UserInfoDto dto){
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("가입하지 않은 Email입니다 (ㅡ_ㅡ)"));
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new IllegalStateException("잘못된 비밀번호 입니다.");
        }
    }

    @DeleteMapping("/leave/{id}")
    public String leave(@PathVariable Long id){
        userRepository.deleteById(id);
        return "회원정보가 삭제되었습니다...";
    }

    @PatchMapping("/user/nicknames/{id}")
    public void setNk(@PathVariable Long id, @RequestBody UserInfoDto dto){
        userService.SetNk(dto, id);
    }

    @PatchMapping("/user/introduces/{id}")
    public void setIc(@PathVariable Long id, @RequestBody UserInfoDto dto){
        userService.SetIc(dto, id);
    }

    @GetMapping("/user/{id}")
    public String getUsers(@PathVariable Long id){
        return userService.getUser(id);
    }
}