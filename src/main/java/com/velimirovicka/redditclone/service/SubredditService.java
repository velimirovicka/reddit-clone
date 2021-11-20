package com.velimirovicka.redditclone.service;

import com.velimirovicka.redditclone.dto.SubredditDto;
import com.velimirovicka.redditclone.mapper.SubredditMapper;
import com.velimirovicka.redditclone.model.Subreddit;
import com.velimirovicka.redditclone.repository.SubredditRepository;
import com.velimirovicka.redditclone.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(
            this.subredditMapper.mapSubredditDtoToSubreddit(subredditDto)
        );
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository
            .findAll()
            .stream()
            .map(subreddit -> this.subredditMapper.mapSubredditToDto(subreddit))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow();
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
