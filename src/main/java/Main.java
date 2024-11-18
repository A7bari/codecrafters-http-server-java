import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    ArgsParser argsParser = new ArgsParser(args);
    
    Router router = new Router();

    router.get("/", (req, res) -> {
      res.send();
    });

    router.get("/echo/{str}", (req, res) -> {
      String str = req.getPathParam("str");
      System.out.println("Echoing: " + str);

      res.setBody(str)
        .setHeader("Content-Type", "text/plain")
        .send();
    });

    router.get("/user-agent", (req, res) -> {
      String userAgent = req.getHeader("User-Agent");

      res.setBody(userAgent)
        .setHeader("Content-Type", "text/plain")
        .send();
    });

    router.get("/files/{filename}", (req, res) -> {
      String filename = req.getPathParam("filename");
      String dir = argsParser.get("--directory");

      try {
        String fileContents = FileUtil.readFile(dir + filename);
        res.setBody(fileContents.trim())
          .setHeader("Content-Type", "application/octet-stream")
          .send();
      } catch (IOException e) {
        res.setStatus(404, "Not Found")
          .send();
      }
    });

    // router.post("/files/{filename}", (req, res) -> {
    //   String filename = req.getPathParam("filename");
    //   String dir = argsParser.get("--directory");
    //   String body = req.getBody();
      
      
    //   try {
    //     FileUtil.writeFile(dir + filename, body);
    //     res.setStatus(201, "Created")
    //       .send();
    //   } catch (IOException e) {
    //     res.setStatus(500, "Internal Server Error")
    //       .send();
    //   }
    // });


    HttpServer server = new HttpServer(router, 4221);

    try {
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
