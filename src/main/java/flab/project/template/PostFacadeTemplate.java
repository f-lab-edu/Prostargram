package flab.project.template;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.AddPostRequest;
import flab.project.service.PostHashTagService;
import flab.project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class PostFacadeTemplate {

    private final PostService postService;
    private final PostHashTagService postHashTagService;

    @Transactional
    public SuccessResponse addPost(long userId, AddPostRequest post) {
        postService.addPost(userId, post);

        postHashTagService.saveAll(post.getPostId(), post.getHashTagNames());

        specializedMethod(userId, post);

        return new SuccessResponse();
    }

    protected abstract void specializedMethod(long userId, AddPostRequest post);
}