package org.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Exercise5PostCreateEntity {
    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            URL url = new URI("https://apichallenges.eviltester.com/sim/entities").toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInput = """
                    {
                        "name": "aluno"
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

            reader.close();
            conn.disconnect();

            System.out.println("Corpo da resposta: ");
            System.out.println(content.toString());

            if(content.toString().contains("\"id\":")){
                int index = content.indexOf("\"id\":") + 5;
                int endIndex = content.indexOf(",", index);
                String id = content.substring(index, endIndex != -1 ? endIndex : content.length()).replaceAll("[^0-9]", "");
                System.out.println("ID gerado: " + id);
            }
        } catch (IOException | URISyntaxException e) {
            System.out.println("Erro na requisição com parâmetros.");
            e.printStackTrace();
        }
    }
}
