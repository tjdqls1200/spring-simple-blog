package me.sungbin.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sungbin.blog.common.exception.article.ArticleNotFoundException;
import me.sungbin.blog.controller.dto.AddArticleRequest;
import me.sungbin.blog.service.BlogService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BlogRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    BlogService blogService;

    @Transactional
    @DisplayName("게시글 작성에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest articleRequest = new AddArticleRequest(title, content);

        final String requestBody = objectMapper.writeValueAsString(articleRequest);

        var result = mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON_VALUE)
                .content(requestBody));


        result.andExpect(status().isCreated());

        var articles = blogService.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @Transactional
    @DisplayName("게시글 목록 조회에 성공한다.")
    @Test
    void findArticles() throws Exception {
        //given
        final String url = "/api/articles";

        //when

        blogService.save(new AddArticleRequest("제목1", "내용1"));


        var result = mockMvc.perform(get(url)
                .accept(APPLICATION_JSON));


        //then
        result.andExpectAll(
                status().isOk(),
                jsonPath("$.size()").value(1),
                jsonPath("$[0].title").isString(),
                jsonPath("$[0].content").isString()
        );
    }

    @DisplayName("게시글 단건 조회 실패시 ArticleNotFoundException 예외가 발생한다.")
    @Test
    public void findArticleException() throws Exception {
        assertThatThrownBy(() -> blogService.findOne(100000L))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @Transactional
    @DisplayName("게시글 단건 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        //given
        final String url = "/api/articles/%d";
        final String title = "제목1";
        final String content = "내용1";

        //when
        final Long id = blogService.save(new AddArticleRequest(title, content));

        var result = mockMvc.perform(get(String.format(url, id))
                .contentType(APPLICATION_JSON));

        //then
        result.andExpectAll(
                status().isOk(),
                jsonPath("$.title").value(title),
                jsonPath("$.content").value(content)
        );
    }

    @DisplayName("게시글이 정상적으로 삭제된다.")
    @Test
    public void deleteArticleSuccessTest() throws Exception {
        //given
        final Long id = blogService.save(new AddArticleRequest("제목1", "내용1"));

        assertDoesNotThrow(() -> blogService.deleteArticle(id));
        assertThat(blogService.findAll()).isEmpty();
    }
}
















