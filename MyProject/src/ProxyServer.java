import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class ProxyServer {
	
    public static void main(String[] args) {
        int port_num = 8080;

        try (ServerSocket server_socket = new ServerSocket(port_num)) {
            System.out.println("Proxy server is operating successfully");

            while (true) {
                Socket client_socket = server_socket.accept();

                // new thread is made for every successful client connection
                new ProxyThread(client_socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ProxyThread extends Thread {
    private Socket clientSocket;

    public ProxyThread(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // reads URL from client
            String url = in.readLine();

            // checks to verify whether or not URL is valid
            boolean URL_Valid = URL_Validation(url);
            if (URL_Valid) {
                // URL will be will forwarded to client due to it being valid
                out.println("Valid URL: " + url);
            } else {
                System.out.println(url + "is an invalid URL");
                // error message will be forwarded by server to client due to invalid URL
                out.println("Error: Invalid URL");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    private boolean URL_Validation(String urlString) {
    	
        return urlString != null && (urlString.startsWith("http://") || urlString.startsWith("https://"));
    }
}
