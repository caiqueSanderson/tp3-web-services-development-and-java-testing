package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Exercise10DeleteInvalidEntity {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int id = 2;
        try {
            URL url = new URI("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");

            int status = conn.getResponseCode();
            System.out.println("Código de status HTTP (DELETE): " + status);

            BufferedReader reader;
            if (status >= 400) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }

            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }

            System.out.println("Corpo da resposta: ");
            System.out.println(content.toString());

            reader.close();
            conn.disconnect();

            URL getUrl = new URI("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();
            HttpURLConnection getConn = (HttpURLConnection) getUrl.openConnection();
            getConn.setRequestMethod("GET");
            getConn.setRequestProperty("Accept", "application/json");

            int getStatus = getConn.getResponseCode();
            System.out.println("Código de status HTTP (GET): " + getStatus);

            BufferedReader getReader;
            if (getStatus >= 400) {
                getReader = new BufferedReader(new InputStreamReader(getConn.getErrorStream()));
            } else {
                getReader = new BufferedReader(new InputStreamReader(getConn.getInputStream()));
            }

            StringBuilder getContent = new StringBuilder();
            String getLine;
            while ((getLine = getReader.readLine()) != null) {
                getContent.append(getLine);
            }

            System.out.println("Resposta do GET:");
            System.out.println(getContent);
            getReader.close();
            getConn.disconnect();
        } catch (IOException | URISyntaxException e) {
            System.out.println("Erro na requisição com parâmetros.");
            e.printStackTrace();
        }
    }
}
