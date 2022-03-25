package api.database.SQLDatabase;

import api.database.ICannDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CannDAOsql implements ICannDAO {

    DBConnector connector = new DBConnector();

    @Override
    public void addCann(String name, String strain, float thc_cbd_ratio, Connection connection) throws CannException, SQLException {

        //Check if the strain already exists in the database.
        PreparedStatement cannAlreadyExists = connection.prepareStatement("SELECT name FROM cannabis_strains WHERE name = ?");
        cannAlreadyExists.setString(1, name);
        ResultSet rs = cannAlreadyExists.executeQuery();
        if(rs.next()){
            connection.rollback();
            throw new CannException(name);
        }

        //If it doesn't, add the strain to the database.
        PreparedStatement addCann = connection.prepareStatement("INSERT INTO cannabis_strains (name, strain, thc_cbd_ratio) VALUES (?, ?, ?)");
        addCann.setString(1, name);
        addCann.setString(2, strain);
        addCann.setFloat(3, thc_cbd_ratio);
        addCann.execute();
    }

    @Override
    public void addEffects(String name, List<String> effects, Connection connection) throws CannException, SQLException {
        //Add effect if effectList is in json.
        PreparedStatement addEffect = connection.prepareStatement("INSERT INTO effects (name, effect) VALUES (?, ?)");
        addEffect.setString(1, name);

        for(int i = 0; i < effects.size(); i++){
            addEffect.setString(2, effects.get(i));
            if(!addEffect.execute()){
                throw new CannException(name);
            }
        }
        //Check to see if strain
    }

    @Override
    public void addSideEffects(String name, List<String> sideEffects, Connection connection) throws CannException, SQLException {
        PreparedStatement addSideEffect = connection.prepareStatement("INSERT INTO side_effects (name, side_effect) VALUES (?, ?)");
        addSideEffect.setString(1, name);

        for(int i = 0; i < sideEffects.size(); i++){
            addSideEffect.setString(2, sideEffects.get(i));
            if(!addSideEffect.execute()){
                throw new CannException(name);
            }
        }
    }
}
