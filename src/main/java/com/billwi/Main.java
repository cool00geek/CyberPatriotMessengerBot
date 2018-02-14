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

    private final static int REFRESH_TIMEOUT = 200;

    public static void main(String[] args) throws IOException {
        DataVerify verify = new DataVerify();

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
            randomTeams.put(teamNum, new int[4][5]);
        }

        HashMap<Integer, int[][]> favTeams = new HashMap<>();
        favTeams.put(0000, new int[2][5]);

        HashMap<Integer, int[][]> schoolTeams = new HashMap<>();
        schoolTeams.put(0000, new int[2][5]);


        HashMap<Integer, int[][]> generalTeams = new HashMap<>();
        generalTeams.put(0000, new int[2][5]);

        SlackBot.sendMessage(3, "Bot started!");

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer created!");
                try {
                    System.out.println("Refreshing data");
                    verify.refreshData(randomTeams);
                    Thread.sleep(25000);
                    verify.refreshData(favTeams);
                    Thread.sleep(25000);
                    verify.refreshData(schoolTeams);
                    Thread.sleep(25000);
                    verify.refreshData(generalTeams);
                    verify.checkForChange(0, randomTeams);
                    verify.checkForChange(1, favTeams);
                    verify.checkForChange(2, schoolTeams);
                    verify.checkForChange(3, generalTeams);
                } catch (IOException | InterruptedException ex) { // Catch the exception from the database conn
                    System.out.println(ex);
                }
            }
        }, 0, REFRESH_TIMEOUT * 1000);
    }
}
