package org.example;

import com.google.gson.Gson;
import org.example.dtos.PostItemIsbn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Exercise12ExperimentsSimpleApi {
    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            URL url = new URI("https://apichallenges.eviltester.com/simpleapi/items").toURL();

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

            /* {
             "id":21,
             "type":"book",
             "isbn13":"868-4-05-021213-7",
             "price":19.99,
             "numberinstock":10
             } */
            System.out.println("Corpo da resposta: ");
            System.out.println(content.toString());

            URL urlISBN = new URI("https://apichallenges.eviltester.com/simpleapi/randomisbn").toURL();

            HttpURLConnection connISBN = (HttpURLConnection) urlISBN.openConnection();
            connISBN.setRequestMethod("GET");
            connISBN.setRequestProperty("Accept", "application/json");

            int statusISBN = connISBN.getResponseCode();
            System.out.println("Código de status HTTP (randomisbn): " + statusISBN);

            BufferedReader readerISBN = new BufferedReader(new InputStreamReader(connISBN.getInputStream()));

            String inputLineISBN;
            StringBuilder contentISBN = new StringBuilder();

            while ((inputLineISBN = readerISBN.readLine()) != null) {
                contentISBN.append(inputLineISBN);
            }

            readerISBN.close();

            System.out.println("Corpo da resposta (randomisbn): ");
            System.out.println(contentISBN.toString());

            String isbn13 = contentISBN.toString().trim();

            URL urlPost = new URI("https://apichallenges.eviltester.com/simpleapi/items").toURL();

            HttpURLConnection connPost = (HttpURLConnection) urlPost.openConnection();
            connPost.setRequestMethod("POST");

            connPost.setRequestProperty("Content-Type", "application/json");
            connPost.setRequestProperty("Accept", "application/json");
            connPost.setDoOutput(true);

            Gson gsonPost = new Gson();
            var post = new PostItemIsbn("book", isbn13, 5.99,5);
            String jsonInput = gsonPost.toJson(post);

            try (DataOutputStream out = new DataOutputStream(connPost.getOutputStream())) {
                out.writeBytes(jsonInput);
                out.flush();
            }

            int statusPost = connPost.getResponseCode();
            System.out.println("Código de status HTTP: " + statusPost);

            BufferedReader readerPost = new BufferedReader(new InputStreamReader(connPost.getInputStream()));

            String inputLinePost;
            StringBuilder contentPost = new StringBuilder();

            while ((inputLinePost = readerPost.readLine()) != null) {
                contentPost.append(inputLinePost);
            }

            readerPost.close();
            connPost.disconnect();

            System.out.println("Corpo da resposta: ");
            System.out.println(contentPost.toString());

            URL urlPut = new URI("https://apichallenges.eviltester.com/simpleapi/items/" + isbn13).toURL();
            HttpURLConnection connPut = (HttpURLConnection) urlPut.openConnection();
            connPut.setRequestMethod("PUT");
            connPut.setRequestProperty("Content-Type", "application/json");
            connPut.setRequestProperty("Accept", "application/json");
            connPut.setDoOutput(true);

            var update = new PostItemIsbn("book", isbn13, 10.99, 20);
            String jsonUpdate = gsonPost.toJson(update);

            try (DataOutputStream outPut = new DataOutputStream(connPut.getOutputStream())) {
                outPut.writeBytes(jsonUpdate);
                outPut.flush();
            }

            int statusPut = connPut.getResponseCode();
            System.out.println("Código de status HTTP (PUT): " + statusPut);

            BufferedReader readerPut = new BufferedReader(new InputStreamReader(connPut.getInputStream()));
            StringBuilder contentPut = new StringBuilder();
            String inputLinePut;
            while ((inputLinePut = readerPut.readLine()) != null) {
                contentPut.append(inputLinePut);
            }
            readerPut.close();
            System.out.println("Corpo da resposta (PUT): " + contentPut);

            URL urlDelete = new URI("https://apichallenges.eviltester.com/simpleapi/items/" + isbn13).toURL();
            HttpURLConnection connDelete = (HttpURLConnection) urlDelete.openConnection();
            connDelete.setRequestMethod("DELETE");

            int statusDelete = connDelete.getResponseCode();
            System.out.println("Código de status HTTP (DELETE): " + statusDelete);

            BufferedReader readerDelete = new BufferedReader(new InputStreamReader(connDelete.getInputStream()));
            StringBuilder contentDelete = new StringBuilder();
            String inputLineDelete;
            while ((inputLineDelete = readerDelete.readLine()) != null) {
                contentDelete.append(inputLineDelete);
            }
            readerDelete.close();
            System.out.println("Corpo da resposta (DELETE): " + contentDelete);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
