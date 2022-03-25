package api.services;

import api.database.IUserDAO;
import io.javalin.Javalin;
import io.javalin.plugin.openapi.annotations.ContentType;

import org.json.JSONException;

import java.sql.SQLException;

public class API {

    public static void initialize(Javalin server){
        server.before("api/*", ctx -> {
            ctx.result(new Response().toJSONString());
            ctx.contentType(ContentType.JSON);
        });

        // Setup exception mappers
        server.exception(JSONException.class, (e, ctx) -> {
            Response.setError(ctx, 400, e.getMessage());
        });

        server.exception(IUserDAO.WrongPasswordException.class, (e, ctx) -> {
            Response.setError(ctx, 401, e.getMessage());
        });

        server.exception(IUserDAO.UnknownUserException.class, (e, ctx) -> {
            Response.setError(ctx, 404, e.getMessage());
        });

        server.exception(IUserDAO.UserExistsException.class, (e, ctx) -> {
            Response.setError(ctx, 403, e.getMessage());
        });

        server.exception(IUserDAO.DALException.class, (e, ctx) -> {
            Response.setError(ctx, 500, "Unknown server error: \""+e.getMessage() +"\"" );
        });

        server.exception(SQLException.class, (e, ctx) -> {
           Response.setError(ctx, 1000, "Unknown database error: \"" + e.getMessage() + "\"");
        });

        UserService userService = new UserService(server);
        CannService cannService = new CannService(server);
    }
}
