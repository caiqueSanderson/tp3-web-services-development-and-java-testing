package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Exercise3GetNonexistentEntity {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int id = 13;
        try {
            URL url = new URI("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Accept", "application/json");

            int status = conn.getResponseCode();
            System.out.println("Código de status HTTP: " + status);

            BufferedReader reader;

            if(status == 404){
                System.out.println("Entidade com ID " + id + " não encontrada.");
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }

            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }

            reader.close();
            conn.disconnect();

            System.out.println("Corpo da resposta: ");
            System.out.println(content.toString());
        } catch (IOException | URISyntaxException e) {
            System.out.println("Erro ao tentar acessar a entidade ID " + id);
            e.printStackTrace();
        }
    }
}
