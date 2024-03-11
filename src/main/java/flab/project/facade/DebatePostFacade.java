package flab.project.facade;

import flab.project.data.dto.model.*;
import flab.project.service.DebatePostOptionService;
import flab.project.service.PostHashTagService;
import flab.project.service.PostService;
import flab.project.service.RabbitMQProducer;
import flab.project.template.PostFacadeTemplate;
import flab.project.template.PostOptionServiceTemplate;
import flab.project.utils.FollowerRedisUtil;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DebatePostFacade extends PostFacadeTemplate {

    private final PostOptionServiceTemplate postOptionServiceTemplate;

    public DebatePostFacade(
            PostService postService,
            PostHashTagService postHashTagService,
            DebatePostOptionService debatePostOptionService
    ) {
        super(postService, postHashTagService);
        this.postOptionServiceTemplate = debatePostOptionService;
    }

    @Override
    protected void validateTypeOfPost(AddPostRequest post) {
        if (!(post instanceof AddDebatePostRequest)) {
            throw new RuntimeException();
        }
    }

    @Override
    protected BasePost handlePostMetadata(long userId, AddPostRequest post) {
        AddDebatePostRequest debatePostRequest = (AddDebatePostRequest) post;
        Set<Option> options = postOptionServiceTemplate.savePostOptions(debatePostRequest.getPostId(), debatePostRequest.getOptionContents());

        return new DebatePost(debatePostRequest, userId, options);
    }
}