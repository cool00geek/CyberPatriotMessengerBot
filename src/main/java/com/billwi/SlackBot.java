package com.billwi;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by @billwi on 12/31/17.
 * Licensed under the GNU/GPL v3 public license
 */
class SlackBot {

    private static final String[] Channels = {
            "", // Random
            "", // Favorite
            "", // School
            ""}; // General

    static void sendMessage(int channel, String text) throws IOException {


        String charset = "UTF-8";
        URL url = new URL(Channels[channel]);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.connect();
        try (OutputStream output = conn.getOutputStream()) {
            String textToSend = "{\"text\":\"" + text + "\"}";
            output.write(textToSend.getBytes(charset));
        }
        System.out.println("Slack message sent! " + text);
        InputStream response = conn.getInputStream();
        try (BufferedReader rdr = new BufferedReader(new InputStreamReader(response))) {
            rdr.read();
        }

    }
}
