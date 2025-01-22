package flab.project.domain.feed.service;

import flab.project.domain.feed.model.PostIdsAndHasNext;

public interface FeedIdsReader {
    int PAGE_SIZE = 10;
    PostIdsAndHasNext getPostIds(Long userId, Long paginationKey);
}
