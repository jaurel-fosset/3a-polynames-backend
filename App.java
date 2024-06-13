import controllers.GameController;
import webserver.WebServer;

public class App
{
    public static void main(String[] args) throws Exception
    {
        WebServer server = new WebServer();

        server.getRouter().post("/api/newGame", GameController::newGame);
        server.getRouter().post("/api/addPlayer", GameController::addPlayer);
        server.getRouter().post("/api/availableRole", GameController::availableRole);

        server.listen(8080);
    }
}
