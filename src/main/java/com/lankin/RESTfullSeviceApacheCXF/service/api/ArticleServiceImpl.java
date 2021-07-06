package com.lankin.RESTfullSeviceApacheCXF.service.api;

import com.lankin.RESTfullSeviceApacheCXF.exception.ResourceNotFoundException;
import com.lankin.RESTfullSeviceApacheCXF.mappers.ArticleMapper;
import com.lankin.RESTfullSeviceApacheCXF.model.Article;
import com.lankin.RESTfullSeviceApacheCXF.repository.ArticleRepository;
import com.lankin.RESTfullSeviceApacheCXF.service.ArticleService;
import com.lankin.RESTfullSeviceApacheCXF.service.models.request.ArticleRequest;
import com.lankin.RESTfullSeviceApacheCXF.service.models.response.ArticleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import java.util.List;


public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    /*@POST*/
    @Override
    public ArticleResponse createArticleResponse(ArticleRequest articleRequest) {
        return articleMapper.ArticleToArticleResponse(articleRepository.save(
                articleMapper.ArticleRequestToArticle(articleRequest)));
    }

    /*@GET*/
    @Override
    public List<ArticleResponse> getArticleResponses() {
        return articleMapper.ArticleToArticleResponse(articleRepository.findAll());
    }

    /*@GET*/
    @Override
    public ArticleResponse getArticleResponse(long id) {
        /* we need to check whether Article with given id is exist in DB or not */
        articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        /*Getting*/
        return articleMapper.ArticleToArticleResponse(articleRepository.findById(id).get());

    }
    /*@DELETE*/
    @Override
    public ArticleResponse deleteArticleByID(long id) {
        /* we need to check whether Article with given id is exist in DB or not */
        articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        /*Deleting*/
        ArticleResponse articleResponse = articleMapper.ArticleToArticleResponse(articleRepository.findById(id).get());
        articleRepository.deleteById(id);
        return articleResponse;

    }

    /*@PUT*/
    @Override
    public ArticleResponse updateArticleByID(long id, ArticleRequest articleRequest) {
        /* we need to check whether Article with given id is exist in DB or not */
        Article article = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
         articleMapper.updateArticleResponseFromArticleRequest(articleRequest, article);
         articleRepository.save(article);
        return articleMapper.ArticleToArticleResponse(article);
    }
}
