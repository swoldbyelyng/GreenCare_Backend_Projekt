package api.services;

import api.database.ICannDAO;
import api.database.SQLDatabase.CannDAOsql;
import api.database.SQLDatabase.DBConnector;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CannService {


    private final ICannDAO dao = new CannDAOsql();

    public CannService(Javalin server){
        server.post("api/cann", this::addCann);
    }

    private void addCann(Context context) throws ICannDAO.CannException, SQLException {
        //Setup sql transaction
        DBConnector connector = new DBConnector();
        Connection connection = connector.connectToRemoteDB();
        connection.setAutoCommit(false);

        JSONObject body = new JSONObject(context.body());
        String name = body.getString("name");
        String strain;
        String ratioSTR;
        float ratio;
        if (body.has("strain") && body.has("ratio")){
            strain = body.getString("strain");
            ratioSTR = body.getString("ratio");
            ratio = Float.parseFloat(ratioSTR);
            dao.addCann(name, strain, ratio, connection);
        }if(body.has("effects")){
            JSONArray jsonArray = body.getJSONArray("effects");
            List<String> effectsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++){
                effectsList.add(jsonArray.getJSONObject(i).getString("effect"));
            }
            if(effectsList.size() > 0){
                dao.addEffects(name, effectsList, connection);
            }
        }if(body.has("side_effects")){
            JSONArray jsonArray = body.getJSONArray("side_effects");
            List<String> sideEffectsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++){
                sideEffectsList.add(jsonArray.getJSONObject(i).getString("sideEffect"));
            }
            if(sideEffectsList.size() > 0){
                dao.addSideEffects(name, sideEffectsList, connection);
            }
        }
        connection.commit();
        Response.setResult(context, 201, "Cann Created", null);
    }
}
