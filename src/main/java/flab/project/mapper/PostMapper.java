package flab.project.mapper;

import flab.project.data.dto.model.AddPostRequest;
import flab.project.data.dto.model.BasicPost;
import flab.project.data.dto.model.DebatePost;
import flab.project.data.dto.model.PollPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {

    void save(@Param("userId") long userId, @Param("post") AddPostRequest post);

    BasicPost getBasicPostDetail(@Param("postId") long postId, @Param("userId") long userId);

    DebatePost getDebatePostDetail(@Param("postId") long postId, @Param("userId") long userId);

    PollPost getPollPostDetail(@Param("postId") long postId, @Param("userId") long userId);
}