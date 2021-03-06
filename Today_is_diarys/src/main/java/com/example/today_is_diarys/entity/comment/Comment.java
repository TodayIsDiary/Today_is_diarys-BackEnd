package com.example.today_is_diarys.entity.comment;

import com.example.today_is_diarys.entity.post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reply")
    private Long reply;

    @ManyToOne
    @JoinColumn(name = "PostId")
    @JsonBackReference
    private Post post;

    @Column(name = "comment")
    private String comments;

    @Column(name = "commentDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd-ttt")
    private LocalDateTime commentDate;

    @Column(name = "nickName")
    private String nickName;

    @PrePersist
    public void prePersist(){
        this.commentDate = LocalDateTime.now();
    }

    public Comment(String comments, Post post, Long reply){
        this.comments = comments;
        this.post = post;
        this.reply = reply;
    }

    public void set(String comments){
        this.comments = comments;
    }
}
