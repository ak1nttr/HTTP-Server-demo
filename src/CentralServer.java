import java.io.*;
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

            try(InputStreamReader is = new InputStreamReader(client.getInputStream());
                BufferedReader br = new BufferedReader(is);
                OutputStream os = client.getOutputStream())
            {
                String request = br.readLine();

                if (request == null || request.isEmpty()){
                    sendNotFound(os);
                    return;
                }
                String filePath = request.split(" ")[1]; // ---> /file.html

                if (filePath.equals("/"))
                    filePath = "/index.html";

                String fullPath = "public"+ filePath;

                sendFile(os,fullPath,detectContentType(fullPath));

                while(!request.isEmpty()){
                    System.out.println(request);
                    request = br.readLine();
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

    }
    /**
    * @implNote function to display the index.html for web page
    * */
    private static void sendFile(OutputStream os, String filePath, String fileType) throws IOException{
        File fl = new File(filePath);
        if (!fl.exists() || filePath.contains("..")) {
            sendNotFound(os);
            return;
        }

        String header = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + fileType + "\r\n" +
                "Content-Length: " + fl.length() + "\r\n" +
                "\r\n";
        os.write(header.getBytes(StandardCharsets.UTF_8));

        try(FileInputStream fis = new FileInputStream(fl)){
            byte[] buffer = new byte[1024];
            int byteRead;
            while((byteRead = fis.read(buffer)) != -1){
                os.write(buffer,0,byteRead);
            }

        }
    }
    /**
    * @implNote  function to generate response for invalid or null requests
    * */

    private static void sendNotFound(OutputStream os){
        String notFoundResponse = "HTTP/1.1 404 Not Found\r\n"
                + "Content-Type: text/plain\r\n"
                + "\r\n"
                + "404 - File Not Found";
        try {
            os.write(notFoundResponse.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.getLocalizedMessage();
        }
    }

    /**
    * @apiNote function to detect the requested file type
    * */
    private static String detectContentType(String filePath) {
        if (filePath.endsWith(".html"))
            return "text/html";
        if (filePath.endsWith(".css"))
            return "text/css";
        if (filePath.endsWith(".js"))
            return "application/javascript";
        if (filePath.endsWith(".png"))
            return "image/png";
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg"))
            return "image/jpeg";

        return "application/octet-stream";
    }
}
