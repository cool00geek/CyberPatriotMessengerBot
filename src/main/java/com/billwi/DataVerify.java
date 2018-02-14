package com.billwi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @billwi on 12/31/17.
 * Licensed under the GNU/GPL v3 public license
 */
class DataVerify {
    private WebDriver myDriver;

    public DataVerify()
    {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.home") + "/chromedriver");
        myDriver = new ChromeDriver();
    }

    void refreshData(HashMap<Integer, int[][]> teams){
        Set<Integer> teamIds = teams.keySet();
        for (Integer teamNum : teamIds)
        {
            String teamStr = teamNum + "";
            while (teamStr.length() <= 3)
            {
                teamStr = "0" + teamStr;
            }
            System.out.println(teamStr);

            try {
                myDriver.get("http://scoreboard.uscyberpatriot.org/team.php?team=10-" + teamStr);
                int[][] currTeamInfo = teams.get(teamNum);
                currTeamInfo[1][1] = Integer.parseInt(myDriver.findElement(By.xpath("/html/body/div[2]/div/table[2]/tbody/tr[2]/td[6]")).getText());
                currTeamInfo[1][2] = Integer.parseInt(myDriver.findElement(By.xpath("/html/body/div[2]/div/table[2]/tbody/tr[3]/td[6]")).getText());
                currTeamInfo[1][3] = Integer.parseInt(myDriver.findElement(By.xpath("/html/body/div[2]/div/table[2]/tbody/tr[4]/td[6]")).getText());
                currTeamInfo[1][4] = Integer.parseInt(myDriver.findElement(By.xpath("/html/body/div[2]/div/table[2]/tbody/tr[5]/td[6]")).getText());
                currTeamInfo[1][0] = currTeamInfo[1][1] + currTeamInfo[1][2] + currTeamInfo[1][3] + currTeamInfo[1][4];
                teams.put(teamNum, currTeamInfo);
            } catch (Exception e){
                System.out.println(e);
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }



    void checkForChange(int channel, HashMap<Integer, int[][]> teams) throws IOException {
        Set<Integer> teamIds = teams.keySet();

        for (Integer teamNum : teamIds) {
            String tn = teamNum + "";
            while (tn.length() <= 3){
                tn = "0" + tn;
            }
            int[][] teamScore = teams.get(teamNum);
            String toSend = "10-" + tn + ": " + teamScore[1][0] + "\n";
            boolean hasChanged = false;
            if (teamScore[0][1] != teamScore[1][1]) {
                hasChanged = true;
                toSend += "Server 16: " + teamScore[0][1] + " -> " + teamScore[1][1] + "\n";
                teamScore[0][1] = teamScore[1][1];
            }
            if (teamScore[0][2] != teamScore[1][2]) {
                hasChanged = true;
                toSend += "Ubuntu 16.04: " + teamScore[0][2] + " -> " + teamScore[1][2] + "\n";
                teamScore[0][2] = teamScore[1][2];
            }
            if (teamScore[0][3] != teamScore[1][3]) {
                hasChanged = true;
                toSend += "Windows 7: " + teamScore[0][3] + " -> " + teamScore[1][3] + "\n";
                teamScore[0][3] = teamScore[1][3];
            }
            if (teamScore[0][4] != teamScore[1][4]) {
                hasChanged = true;
                toSend += "Windows 10: " + teamScore[0][4] + " -> " + teamScore[1][4] + "\n";
                teamScore[0][4] = teamScore[1][4];
            }
            if (hasChanged) {
                toSend += "Net change: " + (teamScore[1][0] - teamScore[0][0]) + "\n";
                toSend += "http://scoreboard.uscyberpatriot.org/team.php?team=10-" + tn;
                teamScore[0][0] = teamScore[1][0];
                SlackBot.sendMessage(channel, toSend);
            }
            teams.put(teamNum, teamScore);
        }

    }
}

