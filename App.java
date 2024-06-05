import webserver.WebServer;

public class App
{
    public static void main(String[] args) throws Exception
    {
        WebServer server = new WebServer();
        server.listen(8080);
    }
}
