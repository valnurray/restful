package com.lankin.RESTfullSeviceApacheCXF.service.impl;

import com.lankin.RESTfullSeviceApacheCXF.MockData;
import com.lankin.RESTfullSeviceApacheCXF.mappers.ArticleMapper;
import com.lankin.RESTfullSeviceApacheCXF.model.Article;
import com.lankin.RESTfullSeviceApacheCXF.repository.ArticleRepository;
import com.lankin.RESTfullSeviceApacheCXF.service.api.ArticleService;
import com.lankin.RESTfullSeviceApacheCXF.service.api.models.request.ArticleRequest;
import com.lankin.RESTfullSeviceApacheCXF.service.api.models.response.ArticleResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ArticleServiceImplTest {

    private MockData mockData;

    private ArticleService articleService;

    @MockBean
    private ArticleRepository articleRepository;

    private ArticleMapper articleMapper;

    @BeforeEach
    void setup(){
        articleMapper = Mappers.getMapper(ArticleMapper.class);
        articleService = new ArticleServiceImpl(articleRepository, articleMapper);
        mockData = new MockData();
    }

    @Test
    void createArticleResponse() {
        Article article = mockData.getFirstArticle();

        doReturn(article).when(articleRepository).save(any());
        // Execute the service call
        ArticleResponse articleResponse1 =  articleService.createArticleResponse(articleMapper.ArticleToArticleRequest(article));

        // Assert the response
        Assertions.assertNotNull(articleResponse1, "The saved widget should not be null");
        Assertions.assertEquals(1L,articleResponse1.getId(), "should be some");
        org.assertj.core.api.Assertions.assertThat(article.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test findAll")
    void getArticleResponses() throws Exception {
        // Setup our mock repository
        Article article1 = mockData.getFirstArticle();
        Article article2 = mockData.getSecondArticle();

        doReturn(Arrays.asList(article1, article2)).when(articleRepository).findAll();

        // Execute the service call
        List<ArticleResponse> articleList = articleService.getArticleResponses();

        // Assert the response
        Assertions.assertEquals(2, articleList.size(), "findAll should return 2 articles");
        org.assertj.core.api.Assertions.assertThat(articleList.size()).isGreaterThan(1);
        org.assertj.core.api.Assertions.assertThat(articleList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test findById Success")
    public void testGetArticleResponseById() {

        // Setup our mock repository
        Article article = mockData.getFirstArticle();
        doReturn(Optional.of(article)).when(articleRepository).findById(1L);

        ArticleResponse returnedResponse = articleService.getArticleResponse(1L);
        Assertions.assertEquals(1L, returnedResponse.getId());
        Assertions.assertNotNull(returnedResponse, "Should not be null");

    }

    @Test
    @DisplayName("Test delete article by id")
    public void deleteArticleByID() {
        // Setup our mock repository
        Article article = mockData.getFirstArticle();
        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));
        articleService.deleteArticleByID(article.getId());
        verify(articleRepository).deleteById(article.getId());

    }

    @Test
    void updateArticleByID() {
        // Setup our mock repository
        Article article = mockData.getFirstArticle();
        ArticleRequest newArticle = mockData.getFirstArticleRequest();
        given(articleRepository.findById(article.getId())).willReturn(Optional.of(article));

        //update Repository
        articleService.updateArticleByID(article.getId(), newArticle);

        //Compare
        Assertions.assertTrue(newArticle.getAuthor().equalsIgnoreCase(article.getAuthor()));
        Assertions.assertTrue(newArticle.getBody().equalsIgnoreCase(article.getBody()));
        Assertions.assertTrue(newArticle.getTitle().equalsIgnoreCase(article.getTitle()));

        verify(articleRepository).findById(article.getId());

    }
}