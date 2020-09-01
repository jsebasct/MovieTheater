package com.platzi.javatests;

import java.io.*;
import java.math.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


class Result {

    public static StringBuffer makeRequest(String urlString) throws IOException {
        System.out.println(urlString);
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        //System.out.println(content);
        return content;
    }

    /*
     * Complete the 'getUsernames' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts INTEGER threshold as parameter.
     *
     * URL for cut and paste
     * https://jsonmock.hackerrank.com/api/article_users?page=<pageNumber>
     */

    public static List<String> getUsernames(int threshold) throws IOException {
        List<String> most = new ArrayList<>();

        int startPage = 1;
        String request = "https://jsonmock.hackerrank.com/api/article_users?page=";
        StringBuffer res = makeRequest(request+startPage);

        int startTotalPages = res.indexOf("total_pages");
        int endTotalPages = res.indexOf(",", startTotalPages);
        String pagesLetter = res.substring(startTotalPages+13, endTotalPages);
        int limit = Integer.parseInt(pagesLetter) - 1;
        System.out.println(limit);

        startPage++;
        while (limit > 0) {
            res.append(makeRequest(request+startPage));
            limit--;
            startPage++;
            System.out.println(res);
        }

        return most;
    }

}
public class Solution {
    public static void main(String[] args) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//
//        int threshold = Integer.parseInt(bufferedReader.readLine().trim());
        int threshold = 1;

        List<String> result = Result.getUsernames(threshold);

//        bufferedWriter.write(
//                result.stream()
//                        .collect(joining("\n"))
//                        + "\n"
//        );
//
//        bufferedReader.close();
//        bufferedWriter.close();
    }
}

