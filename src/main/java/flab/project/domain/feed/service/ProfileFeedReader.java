package flab.project.domain.feed.service;

import flab.project.domain.feed.model.PostIdsAndHasNext;
import flab.project.domain.post.mapper.PostMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileFeedReader implements FeedIdsReader {

    private final PostMapper postMapper;

    @Override
    public PostIdsAndHasNext getPostIds(Long targetUserId, Long lastPostId) {
        List<Long> postIds = postMapper.getProfileFeedIds(targetUserId, lastPostId, PAGE_SIZE + 1);

        return new PostIdsAndHasNext(postIds, PAGE_SIZE);
    }
}
