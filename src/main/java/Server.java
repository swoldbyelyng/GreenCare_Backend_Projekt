import api.services.API;
import io.javalin.Javalin;

public class Server {

    private static final int PORT = 20000;
    private static final boolean TESTMODE = false;

    private static Javalin server = null;

    public static void main(String[] args) {

        if(TESTMODE){
            //start in test mode
        }

        start();
    }

    private static void start(){

        //Initialize Javalin Server
        server = Javalin.create(config -> {
            config.contextPath = "/";
            config.enableCorsForAllOrigins();
            //config.addStaticFiles("webapp");
        });

        server.start(PORT);

        //Initialize api
        API.initialize(server);


    }
}
