package org.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Exercise11OptionsMethodVerification {
    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            URL url = new URI("https://apichallenges.eviltester.com/sim/entities").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("OPTIONS");

            int status = conn.getResponseCode();
            System.out.println("Código de status HTTP (OPTIONS): " + status);

            String allowHeader = conn.getHeaderField("Allow");
            if (allowHeader != null) {
                System.out.println("Métodos permitidos (Allow): " + allowHeader);
            } else {
                System.out.println("Cabeçalho 'Allow' não encontrado na resposta.");
            }

            conn.disconnect();
        } catch (IOException | URISyntaxException e) {
            System.out.println("Erro ao enviar requisição OPTIONS.");
            e.printStackTrace();
        }
    }
}
