package flab.project.domain.post.mapper;

import flab.project.domain.post.model.AddPostRequest;
import flab.project.domain.post.model.BasicPost;
import flab.project.domain.post.model.DebatePost;
import flab.project.domain.post.model.PollPost;
import flab.project.domain.post.model.PostTypeModel;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {

    void save(@Param("userId") long userId, @Param("post") AddPostRequest post);

    BasicPost getBasicPostDetail(@Param("postId") long postId, @Param("userId") long userId);

    DebatePost getDebatePostDetail(@Param("postId") long postId, @Param("userId") long userId);

    PollPost getPollPostDetail(@Param("postId") long postId, @Param("userId") long userId);

    List<BasicPost> getBasicPostsWhereIn(@Param("basicPostIds") List<Long> basicPostIds, @Param("userId") long userId);

    List<DebatePost> getDebatePostsWhereIn(@Param("debatePostIds") List<Long> debatePostIds, @Param("userId") long userId);

    List<PostTypeModel> findTypeByPostIds(@Param("postIds") List<Long> postIds);
}