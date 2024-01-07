import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.io.*;
import java.net.Socket;

public class ProxyClient {

    public static void main(String[] args) {
        
    	String server_addy = "localhost";
        int serverport_num = 8080;
        
        try (
            Socket socket = new Socket(server_addy, serverport_num);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // asks user to enter a URL
            System.out.print("Enter URL: ");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String url = input.readLine();

            // forwards URL to server
            out.println(url);

            String message = in.readLine();
  
           
            if (message.startsWith("Valid URL:")) {  
            	appendURL(url, "my-urls.html");
                System.out.println("URL has been successfully stored in the HTML file");
            } else {
     
                System.out.println("Error: Server has detected an INVALID URL, please enter a valid URL");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendURL(String url, String file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("<h1>New URL:</h2>");
            writer.write("<p><a href=\"" + url + "\">" + url + "</a></p>");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

