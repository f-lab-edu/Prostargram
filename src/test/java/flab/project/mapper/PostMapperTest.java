package flab.project.mapper;

import static flab.project.domain.post.enums.PostType.BASIC;
import static flab.project.domain.post.enums.PostType.DEBATE;
import static flab.project.domain.post.enums.PostType.POLL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import flab.project.domain.post.mapper.PollMetadataMapper;
import flab.project.domain.post.mapper.PostOptionMapper;
import flab.project.domain.post.model.AddBasicPostRequest;
import flab.project.domain.post.model.AddDebatePostRequest;
import flab.project.domain.post.model.AddPollPostRequest;
import flab.project.domain.post.model.BasePost;
import flab.project.domain.post.model.BasicPost;
import flab.project.domain.post.model.DebatePost;
import flab.project.domain.post.model.PollPost;
import flab.project.domain.post.model.PostTypeModel;
import flab.project.domain.post.enums.PostType;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import flab.project.domain.post.mapper.PostMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostOptionMapper postOptionMapper;

    @Autowired
    private PollMetadataMapper pollMetadataMapper;

    @DisplayName("postId 목록을 이용해 각 게시물의 타입(일반,통계,토론)을 알 수 있다.")
    @Test
    void findPostTypeByPostIds() {
        List<Long> postIds = List.of(1L, 2L, 3L);
        AddBasicPostRequest basicPost = createBasicPost(1L);
        AddDebatePostRequest debatePost = createDebatePost(2L);
        AddPollPostRequest pollPost = createPollPost(3L);

        postMapper.save(1L, basicPost);
        postMapper.save(1L, debatePost);
        postMapper.save(1L, pollPost);

        List<PostTypeModel> postTypes = postMapper.findTypeByPostIds(postIds);

        Optional<PostType> postTypeOptional1 = extractPostTypeByPostId(postTypes, 1);
        Optional<PostType> postTypeOptional2 = extractPostTypeByPostId(postTypes, 2);
        Optional<PostType> postTypeOptional3 = extractPostTypeByPostId(postTypes, 3);

        assertTrue(postTypeOptional1.isPresent());
        assertEquals(BASIC, postTypeOptional1.get());
        assertTrue(postTypeOptional2.isPresent());
        assertEquals(PostType.DEBATE, postTypeOptional2.get());
        assertTrue(postTypeOptional3.isPresent());
        assertEquals(PostType.POLL, postTypeOptional3.get());
    }

    @DisplayName("일반 게시물 상세보기")
    @Test
    void getBasicPostDetail() {
        // given
        long postId = 1L;
        long writerId = 1L;
        int imageCount = 1;
        String content = "content";

        AddBasicPostRequest basicPost = AddBasicPostRequest.builder()
            .postId(postId)
            .content(content)
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .hashTagNames(Set.of("hashTagName1"))
            .imageCount(imageCount)
            .build();

        postMapper.save(writerId, basicPost);

        // when
        BasicPost post = postMapper.getBasicPostDetail(postId, writerId);

        // then
        assertThat(post.getPostId()).isEqualTo(postId);
        assertThat(post.getUserId()).isEqualTo(writerId);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getPostType()).isEqualTo(BASIC);
    }

    @DisplayName("토론 게시물 상세보기")
    @Test
    void getDebatePostDetail() {
        // given
        long postId = 1L;
        long writerId = 1L;
        String content = "content";

        AddDebatePostRequest debatePost = AddDebatePostRequest.builder()
            .postId(postId)
            .content(content)
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .hashTagNames(Set.of("hashTagName1"))
            .build();

        postMapper.save(writerId, debatePost);
        postOptionMapper.saveAll(postId, Set.of("option1", "option2"));

        // when
        DebatePost post = postMapper.getDebatePostDetail(postId, writerId);

        // then
        assertThat(post.getPostId()).isEqualTo(postId);
        assertThat(post.getUserId()).isEqualTo(writerId);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getPostType()).isEqualTo(DEBATE);
    }

    @DisplayName("통계 게시물 상세보기")
    @Test
    void getPollPostDetail() {
        // given
        long postId = 1L;
        long writerId = 1L;
        String content = "content";
        Set<String> options = Set.of("option1", "option2");

        AddPollPostRequest pollPost = AddPollPostRequest.builder()
            .postId(postId)
            .content(content)
            .subject("통계 subject")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(1))
            .optionContents(options)
            .allowMultipleVotes(true)
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .hashTagNames(Set.of("hashTagName1"))
            .build();

        postMapper.save(writerId, pollPost);
        pollMetadataMapper.save(pollPost);
        postOptionMapper.saveAll(postId, options);

        // when
        PollPost post = postMapper.getPollPostDetail(postId, writerId);

        // then
        assertThat(post.getPostId()).isEqualTo(postId);
        assertThat(post.getUserId()).isEqualTo(writerId);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getPostType()).isEqualTo(POLL);
    }

    @DisplayName("postIds를 통해 BasicPost List를 받아올 수 있다.")
    @Test
    void getBasicPostsWhereIn() {
        List<Long> postIds = List.of(1L, 2L, 3L);
        long userId = 1L;
        AddBasicPostRequest basicPost1 = createBasicPost(1L);
        AddBasicPostRequest basicPost2 = createBasicPost(2L);
        AddBasicPostRequest basicPost3 = createBasicPost(3L);

        postMapper.save(userId, basicPost1);
        postMapper.save(userId, basicPost2);
        postMapper.save(userId, basicPost3);

        List<BasicPost> basicPosts = postMapper.getBasicPostsWhereIn(postIds, userId);

        long[] retrievedPostIds = basicPosts.stream().mapToLong(BasePost::getPostId).toArray();
        assertThat(retrievedPostIds).contains(1, 2, 3);
    }

    @DisplayName("postIds를 통해 DebatePost List를 받아올 수 있다.")
    @Test
    void getDebatePostsWhereIn() {
        List<Long> postIds = List.of(1L, 2L, 3L);
        long userId = 1L;
        AddDebatePostRequest debatePost1 = createDebatePost(1L);
        AddDebatePostRequest debatePost2 = createDebatePost(2L);
        AddDebatePostRequest debatePost3 = createDebatePost(3L);

        postMapper.save(userId, debatePost1);
        postMapper.save(userId, debatePost2);
        postMapper.save(userId, debatePost3);

        List<DebatePost> debatePosts = postMapper.getDebatePostsWhereIn(postIds, userId);

        long[] retrievedPostIds = debatePosts.stream().mapToLong(DebatePost::getPostId).toArray();
        assertThat(retrievedPostIds).contains(1, 2, 3);
    }

    private Optional<PostType> extractPostTypeByPostId(List<PostTypeModel> postTypes, int postId) {
        return postTypes.stream()
            .filter(postTypeModel -> postTypeModel.getPostId() == postId)
            .map(PostTypeModel::getPostType)
            .findFirst();
    }

    private AddDebatePostRequest createDebatePost(long postId) {
        return AddDebatePostRequest.builder()
            .postId(postId)
            .content("dd")
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .hashTagNames(Set.of("test1"))
            .optionContents(Set.of("option1", "option2"))
            .build();
    }

    private AddPollPostRequest createPollPost(long postId) {
        return AddPollPostRequest.builder()
            .postId(postId)
            .content("dummy")
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .hashTagNames(Set.of("test1"))
            .allowMultipleVotes(true)
            .endDate(LocalDate.now())
            .startDate(LocalDate.now())
            .subject("subject")
            .optionContents(Set.of("option1", "option2"))
            .build();
    }

    private AddBasicPostRequest createBasicPost(long postId) {
        return AddBasicPostRequest.builder()
            .postId(postId)
            .content("dummy")
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .hashTagNames(Set.of("test1"))
            .imageCount(1)
            .build();
    }
}