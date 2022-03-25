package api.database.SQLDatabase;

import api.database.IUserDAO;
import api.database.Passwords;
import api.database.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOsql implements IUserDAO {

    DBConnector connector = new DBConnector();
    Passwords passwords = new Passwords();


    @Override
    public void createUser(String email, String username, String password) throws DALException, SQLException {

        //Hash password
        String salt = passwords.createSalt();
        String newPassword = password + salt;
        String hashedPassword = passwords.createHash(newPassword.toCharArray());

        Connection connection = connector.connectToRemoteDB();

        //Check to see if user already exists in database. If so, throw DALException.
        PreparedStatement checkUser = connection.prepareStatement("SELECT username FROM users WHERE username = ?");
        checkUser.setString(1, username);
        ResultSet rs = checkUser.executeQuery();
        if (rs.next()) {
            throw new UserExistsException(username);
        }


        //Add user to database.
        PreparedStatement addUser = connection.prepareStatement("INSERT INTO users (username, email, password, salt)" +
                " VALUES (?, ?, ?, ?);");
        addUser.setString(1, username);
        addUser.setString(2, email);
        addUser.setString(3, hashedPassword);
        addUser.setString(4, salt);
        addUser.execute();
    }

    @Override
    public User getUser(String username) throws DALException, SQLException {

        User user = new User();
        Connection connection = connector.connectToRemoteDB();
        PreparedStatement getUser = connection.prepareStatement("SELECT (username, email) FROM users WHERE username = ?");
        getUser.setString(1, username);
        ResultSet rs = getUser.executeQuery();
        if (rs.next()) {
            user.setUsername(rs.getString(1));
            user.setEmail(rs.getString(2));
        } else {
            throw new UnknownUserException(username);
        }
        return user;
    }

    @Override
    public void authenticateUser(String username, String inputPass) throws DALException, SQLException {
        Boolean result = false;

        Connection connection = connector.connectToRemoteDB();
        result = authenticate(username, inputPass, connection);
        if (!result) throw new WrongPasswordException(username);
    }

    private boolean authenticate(String username, String inputPass, Connection connection) throws DALException, SQLException {
        boolean result = false;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT password, salt FROM users WHERE username = ?");
        preparedStatement.setString(1, username);
        ResultSet rs = preparedStatement.executeQuery();
        String dbHash;
        char[] psChar;
        if (rs.next()) {
            dbHash = rs.getString(1);
            String dbSalt = rs.getString(2);
            String ps = inputPass + dbSalt;
            psChar = new char[ps.length()];
            for (int i = 0; i < ps.length(); i++) {
                psChar[i] = ps.charAt(i);
            }
        } else {
            throw new WrongPasswordException(username);
        }

        //Verify passwords
        Argon2 argon2 = Argon2Factory.create();
        result = argon2.verify(dbHash, psChar);
        return result;
    }


    @Override
    public void deleteUser(String username, String password) throws DALException, SQLException {
        Connection connection = connector.connectToRemoteDB();
        if (authenticate(username, password, connection)) {
            PreparedStatement deleteUser = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            deleteUser.setString(1, username);
            deleteUser.execute();
        } else {
            throw new WrongPasswordException(username);
        }
    }


}
