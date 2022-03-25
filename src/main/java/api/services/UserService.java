package api.services;

import api.database.SQLDatabase.UserDAOsql;
import api.database.IUserDAO;
import api.database.User;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.json.JSONObject;

import java.sql.SQLException;

public class UserService {

    private final UserDAOsql dao = new UserDAOsql();

    public UserService(Javalin server){
        server.post("api/user", this::createUser);
        server.get("api/user/:username", this::getUser);
        server.delete("api/user/", this::deleteUser);
        server.post("api/user/:username/authenticate", this::authenticateUser);
    }

    private void createUser(Context context) throws IUserDAO.DALException, SQLException {
        JSONObject body = new JSONObject(context.body());
        User createdUser = User.fromJSONObject(body, true);
        dao.createUser(createdUser.getEmail(), createdUser.getUsername(), createdUser.getPassword());
        Response.setResult(context, 201, "User Created", createdUser.toJSONObject());
    }

    public void getUser(Context context) throws IUserDAO.DALException, SQLException{
        String username = context.pathParam("username");
        User requestedUser = dao.getUser(username);
        Response.setResult(context, 200, "User found", requestedUser.toJSONObject());
    }

    public void deleteUser(Context context) throws IUserDAO.DALException, SQLException {
        JSONObject body = new JSONObject(context.body());
        String username = body.getString("username");
        String password = body.getString("password");
        dao.deleteUser(username, password);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("something", "something");
        Response.setResult(context, 204, "User '" + username + "' was deleted", jsonObject);
    }

    private void updateUser(Context context) throws IUserDAO.DALException {

    }

    public void authenticateUser(Context context) throws IUserDAO.DALException, SQLException {
        String username = context.pathParam("username");
        String password = new JSONObject(context.body()).getString("password");
        dao.authenticateUser(username, password);
        Response.setResult(context, 204, "Successfuly authentication as '" + username + "'", null);
    }
}
