package me.sungbin.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sungbin.blog.common.exception.article.ArticleNotFoundException;
import me.sungbin.blog.controller.dto.AddArticleRequest;
import me.sungbin.blog.controller.dto.UpdateArticleRequest;
import me.sungbin.blog.service.BlogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
        final String url = "/api/articles/{id}";
        final String title = "제목1";
        final String content = "내용1";

        //when
        final Long id = blogService.save(new AddArticleRequest(title, content));

        var result = mockMvc.perform(get(url, id)
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

    @DisplayName("게시글 수정 시 정상적으로 변경이 된다.")
    @Test
    public void articleUpdateSuccessTest() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String updateTitle = "변경 후 제목";
        final String updateContent = "변경 후 제목";
        final String requestBody = objectMapper.writeValueAsString(new UpdateArticleRequest(updateTitle, updateContent));

        final Long id = blogService.save(new AddArticleRequest("변경 전 제목", "변경 전 내용"));


        //when
        var result = mockMvc.perform(put(url, id)
                .contentType(APPLICATION_JSON)
                .content(requestBody));

        var findArticle = blogService.findOne(id);

        //then
        result.andExpect(status().isOk());
        result.andExpect(header().string(HttpHeaders.LOCATION, "/api/articles/" + id));
        assertThat(findArticle.getTitle()).isEqualTo(updateTitle);
        assertThat(findArticle.getContent()).isEqualTo(updateContent);
    }
}
















