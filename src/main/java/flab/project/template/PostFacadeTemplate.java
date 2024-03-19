package flab.project.template;

import flab.project.data.dto.model.AddPostRequest;
import flab.project.data.dto.model.BasePost;
import flab.project.service.FanOutService;
import flab.project.service.PostHashTagService;
import flab.project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class PostFacadeTemplate {

    private final PostService postService;
    private final PostHashTagService postHashTagService;
    private final FanOutService fanOutService;

    @Transactional
    public BasePost addPost(long userId, AddPostRequest post) {
        validateTypeOfPost(post);

        postService.addPost(userId, post);
        postHashTagService.saveAll(post.getPostId(), post.getHashTagNames());

        BasePost createdPost = handlePostMetadata(userId, post);

        if (doesNeedFanOut()) {
            fanOutService.fanOut(userId, post.getPostId());
        }

        return createdPost;
    }

    protected abstract void validateTypeOfPost(AddPostRequest post);
    protected abstract BasePost handlePostMetadata(long userId, AddPostRequest post);
    protected abstract boolean doesNeedFanOut();
}