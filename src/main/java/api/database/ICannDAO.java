package api.database;

import api.services.CannService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ICannDAO {

    void addCann(String name, String strain, float thc_cbd_ratio, Connection connection) throws CannException, SQLException;

    void addEffects(String name, List<String> effects, Connection connection) throws CannException, SQLException;

    void addSideEffects(String name, List<String> sideEffects, Connection connection) throws CannException, SQLException;


    // ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    // Exceptions

    class CannException extends Exception {
        public CannException(String msg){
            super(msg);
        }
    }


}