package flab.project.domain.feed.model;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostIdsAndHasNext {
    List<Long> postIds;
    boolean hasNext;

    public List<Long> getPostIds() {
        return postIds;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public boolean isEmpty(){
        return postIds.isEmpty();
    }
}
