package com.lankin.RESTfullSeviceApacheCXF.service;

import com.lankin.RESTfullSeviceApacheCXF.model.Article;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/article")
public interface ArticleREST {

    @POST
    @Produces({"application/xml","application/json"})
//    @Consumes("application/xml")
    @Consumes({"application/xml","application/json","application/x-www-form-urlencoded"})
    Response createArticle(Article article);

    @GET
    @Produces({"application/xml","application/json"})
    List<Article> getArticles() ;

    @GET
    @Path("{id}")
    @Produces({"application/xml","application/json"})
    Article getArticle(@PathParam("id") long id);

    @DELETE
    @Path("{id}")
    @Consumes({"application/xml","application/json","application/x-www-form-urlencoded"})
    @Produces({"application/xml","application/json"})
    Response deleteArticleByID(@PathParam("id") long id);

    @PUT
    @Path("{id}")
    @Consumes({"application/xml","application/json","application/x-www-form-urlencoded"})
    @Produces({"application/xml","application/json"})
    Response updateArticleByID(@PathParam("id") long id, Article article);

}
