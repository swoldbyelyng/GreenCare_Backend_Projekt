package api.database;

import java.sql.SQLException;

public interface IUserDAO {

    void createUser(String email, String username, String password) throws DALException, SQLException;

    User getUser(String username) throws DALException, SQLException;

    void authenticateUser(String username, String password) throws DALException, SQLException;

    void deleteUser(String username, String password) throws DALException, SQLException;


    // ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    // Exceptions

    class DALException extends Exception {
        public DALException(String msg){ super(msg); }
    }

    class WrongPasswordException extends DALException {
        private final String username;

        public WrongPasswordException(String username){
            super("Wrong password for user '" + username + "'");
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }


    class UserExistsException extends DALException {
        private final String username;

        public UserExistsException(String username){
            super("Unknown user '" + username + "'");
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }


    class UnknownUserException extends DALException {
        private final String username;

        public UnknownUserException(String username){
            super("Unknown user '" + username + "'");
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }
}
