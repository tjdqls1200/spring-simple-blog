package me.sungbin.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sungbin.blog.controller.dto.AddArticleRequest;
import me.sungbin.blog.domain.Article;
import me.sungbin.blog.repository.BlogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogRestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    BlogRepository blogRepository;

    @DisplayName("게시글 작성에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest articleRequest = new AddArticleRequest(title, content);

        final String requestBody = objectMapper.writeValueAsString(articleRequest);

        var result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));


        result.andExpect(status().isCreated());

        final List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("게시글 목록 조회에 성공한다.")
    @Test
    void readArticles() throws Exception {
        //given
        final String url = "/api/articles";

        //when
        var result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));


        //then
        result.andExpectAll(
                status().isOk(),
                jsonPath("$.size()").value(3)
        );
    }

}
















