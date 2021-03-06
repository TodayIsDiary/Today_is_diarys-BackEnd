package com.example.today_is_diarys.entity.user;

import com.example.today_is_diarys.security.auth.enums.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "sex")
    private Long sex;

    @Column(nullable = false,name="age", length = 101)
    private Long age;

    @Column(nullable = false, name = "nickName")
    private String nickName;

    @Column(nullable = false,name = "password")
    @Setter
    private String password;

    @Column(nullable = false, name = "email",unique = true)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 30)
    private String introduce;

    public void setUser(String nickName, String introduce){
        this.nickName = nickName;
        this.introduce = introduce;
    }

    @Builder
    public User(String nickName, Long sex, Long age, String password, String email ,String introduce, Role role){
        this.email = email;
        this.password = password;
        this.age = age;
        this.sex = sex;
        this.nickName = nickName;
        this.introduce = introduce;
        this.role = role;
    }

}
