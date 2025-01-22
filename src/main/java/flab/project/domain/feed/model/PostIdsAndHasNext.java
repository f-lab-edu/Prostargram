package flab.project.domain.feed.model;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostIdsAndHasNext {

    public static final int FIRST_INDEX = 0;
    List<Long> postIds;
    boolean hasNext;
    int pageSize;

    public PostIdsAndHasNext(List<Long> postIds, int pageSize) {
        this.postIds = postIds;
        this.pageSize = pageSize;
        this.hasNext = postIds.size() >= pageSize;
    }

    public List<Long> getPostIds() {
        if (hasNext) {
            return postIds.subList(FIRST_INDEX, pageSize);
        }

        return postIds;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public boolean isEmpty() {
        return postIds.isEmpty();
    }
}
