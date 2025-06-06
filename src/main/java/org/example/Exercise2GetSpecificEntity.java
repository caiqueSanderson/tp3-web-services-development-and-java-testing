package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Exercise2GetSpecificEntity {
    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            int id = 1;
            while(id <= 8) {
                URL url = new URI("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept", "application/json");

                int status = conn.getResponseCode();
                System.out.println("Código de status HTTP: " + status);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    content.append(inputLine);
                }

                reader.close();
                conn.disconnect();

                System.out.println("Corpo da resposta: ");
                System.out.println(content.toString());

                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
