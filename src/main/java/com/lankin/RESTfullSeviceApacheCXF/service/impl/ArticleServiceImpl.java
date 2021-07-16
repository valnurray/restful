package com.lankin.RESTfullSeviceApacheCXF.service.impl;

import com.lankin.RESTfullSeviceApacheCXF.exception.NotFoundArticleException;
import com.lankin.RESTfullSeviceApacheCXF.mappers.ArticleMapper;
import com.lankin.RESTfullSeviceApacheCXF.model.Article;
import com.lankin.RESTfullSeviceApacheCXF.repository.ArticleRepository;
import com.lankin.RESTfullSeviceApacheCXF.service.api.ArticleService;
import com.lankin.RESTfullSeviceApacheCXF.service.api.models.request.ArticleRequest;
import com.lankin.RESTfullSeviceApacheCXF.service.api.models.response.ArticleResponse;
import lombok.Data;

import javax.ws.rs.core.Response;
import java.util.List;

@Data
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

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
        articleRepository.findById(id).orElseThrow(NotFoundArticleException::new);
        /*Getting*/
        return articleMapper.ArticleToArticleResponse(articleRepository.findById(id).get());
    }

    /*@DELETE*/
    @Override
    public Response deleteArticleByID(long id) {
        /* we need to check whether Article with given id is exist in DB or not */
        articleRepository.findById(id).orElseThrow(NotFoundArticleException::new);
        /*Deleting*/
        ArticleResponse articleResponse = articleMapper.ArticleToArticleResponse(articleRepository.findById(id).get());
        articleRepository.deleteById(id);
        return Response.status(204).build();
    }

    /*@PUT*/
    @Override
    public ArticleResponse updateArticleByID(long id, ArticleRequest articleRequest) {
        /* we need to check whether Article with given id is exist in DB or not */
        Article article = articleRepository.findById(id).orElseThrow(NotFoundArticleException::new);

        Article updatedArticle = new Article();

        if(articleRequest.getTitle() != null) {
            updatedArticle.setTitle(articleRequest.getTitle());
        }else {
            updatedArticle.setTitle(article.getTitle());
        }

        if(articleRequest.getAuthor() != null){
            updatedArticle.setAuthor(articleRequest.getAuthor());
        }else {
            updatedArticle.setAuthor(article.getAuthor());
        }

        if(articleRequest.getBody() != null) {
            updatedArticle.setBody(articleRequest.getBody());
        }else {
            updatedArticle.setBody(article.getBody());
        }

        articleRequest = articleMapper.ArticleToArticleRequest(updatedArticle);
        updatedArticle = articleMapper.updateArt(article, articleRequest);
        articleRepository.save(article);
        return articleMapper.ArticleToArticleResponse(article);

    }
}
