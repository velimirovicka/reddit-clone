package com.velimirovicka.redditclone.service;

import com.velimirovicka.redditclone.dto.CommentDto;
import com.velimirovicka.redditclone.dto.NotificationEmail;
import com.velimirovicka.redditclone.mapper.CommentMapper;
import com.velimirovicka.redditclone.model.Comment;
import com.velimirovicka.redditclone.model.Post;
import com.velimirovicka.redditclone.model.User;
import com.velimirovicka.redditclone.repository.CommentRepository;
import com.velimirovicka.redditclone.repository.PostRepository;
import com.velimirovicka.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = this.commentMapper.map(commentDto, post, authService.getCurrentUser());
        this.commentRepository.save(comment);

        String message = this.mailContentBuilder.build(post.getUser().getUserName() + " posted a comment on " +
                "your post");

        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        NotificationEmail mail = new NotificationEmail(
                authService.getCurrentUser().getUserName() + "Commented on your post",
                user.getEmail(),
                message
        );

        this.mailService.sendMail(mail);
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
       return this.commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentsForUser(String userName) {
        User user = this.userRepository.findByUserName(userName).orElseThrow(()->new RuntimeException("User not found"));
        return this.commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
