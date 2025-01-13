import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CentralServer {
    final private static ServerSocket server;

    static {
        try {
            server = new ServerSocket(7070);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws Exception{
        init();
    }
    private static void init() throws Exception{

        System.out.println("Server is up on port 7070....");
        while(true){
            final Socket client = server.accept();
            String response = """
                    HTTP/1.1 200 OK \r
                    \r
                    Hello World!
                    """;
            client.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));

            try(InputStreamReader is = new InputStreamReader(client.getInputStream());
                BufferedReader br = new BufferedReader(is))
            {
                String request = br.readLine();
                while(!request.isEmpty()){
                    System.out.println(request);
                    request = br.readLine();
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
            System.out.println("One done!");
        }

    }
}
