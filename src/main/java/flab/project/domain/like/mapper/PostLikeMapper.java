package flab.project.domain.like.mapper;

import flab.project.domain.like.model.PostLike;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostLikeMapper {

    int addPostLike(@Param("post_id") long postId, @Param("user_id") long userId);

    boolean hasLike(@Param("postId") long postId, @Param("userId") long userId);

    void cancelLike(@Param("postId") long postId, @Param("userId") long userId);

    Set<Long> getPostsHavingLike(@Param("postIds") List<Long> postIds, @Param("userId") long userId);

    List<PostLike> getPostLikeCount(@Param("postIds") List<Long> postIds);
}