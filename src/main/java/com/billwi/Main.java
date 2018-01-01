package com.billwi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by @billwi
 * Licensed under the GNU/GPL v3 public license
 */
public class Main {

    private final static int REFRESH_TIMEOUT = 60;

    public static void main(String[] args) throws IOException {
        HashMap<Integer, int[][]> randomTeams = new HashMap<>();
        for(String team : args){
            int teamNum = 0;
            try{
                teamNum = Integer.parseInt(team);
            } catch (Exception e){
                System.out.println("Invalid parameter: " + team);
                System.exit(1);
            }
            System.out.println("New team added: " + teamNum);
            randomTeams.put(teamNum, new int[4][4]);
        }

        HashMap<Integer, int[][]> favTeams = new HashMap<>();
        favTeams.put(0, new int[4][4]);

        HashMap<Integer, int[][]> schoolTeams = new HashMap<>();


        HashMap<Integer, int[][]> generalTeams = new HashMap<>();
        schoolTeams.put(247, new int[4][4]);

        //Messenger messenger = Bot.initializeBot();
        SlackBot.sendMessage(3, "Bot started!");

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer created!");
                try {
                    System.out.println("Refreshing data");
                    DataVerify.refreshData(randomTeams);
                    DataVerify.refreshData(favTeams);
                    DataVerify.refreshData(schoolTeams);
                    DataVerify.refreshData(generalTeams);
                    DataVerify.checkForChange(0, randomTeams);
                    DataVerify.checkForChange(1, favTeams);
                    DataVerify.checkForChange(2, schoolTeams);
                    DataVerify.checkForChange(3, generalTeams);
                } catch (IOException ex) { // Catch the exception from the database conn
                    System.out.println(ex);
                }
            }
        }, 0, REFRESH_TIMEOUT * 1000);
    }
}
