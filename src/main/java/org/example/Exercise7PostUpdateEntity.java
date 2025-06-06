package org.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Exercise7PostUpdateEntity {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int id = 10;
        try {
            URL url = new URI("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");

            conn.setDoOutput(true);

            String jsonInput = """
                    {
                        "name": "atualizado"
                    }
                    """;

            try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())){
                out.writeBytes(jsonInput);
                out.flush();
            }

            int status = conn.getResponseCode();
            System.out.println("Código de status HTTP: " + status);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

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

            BufferedReader getReader = new BufferedReader(new InputStreamReader(getConn.getInputStream()));
            StringBuilder getContent = new StringBuilder();
            String getLine;
            while ((getLine = getReader.readLine()) != null) {
                getContent.append(getLine);
            }

            System.out.println("Resposta do GET:");
            System.out.println(getContent);

            getConn.disconnect();
        } catch (IOException | URISyntaxException e) {
            System.out.println("Erro na requisição com parâmetros.");
            e.printStackTrace();
        }
    }
}
