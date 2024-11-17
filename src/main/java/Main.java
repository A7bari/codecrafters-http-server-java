import java.io.IOException;

public class Main {
  public static void main(String[] args) {

    Router router = new Router();

    router.get("/", (req, res) -> {
      res.send();
    });

    router.get("/echo/{str}", (req, res) -> {
      String str = req.getPathParam("str");
      res.setBody(str)
        .setHeader("Content-Type", "text/plain")
        .send();
      
    });


    HttpServer server = new HttpServer(router, 4221);

    try {
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
