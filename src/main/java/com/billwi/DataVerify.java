package com.billwi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @billwi on 12/31/17.
 * Licensed under the GNU/GPL v3 public license
 */
class DataVerify {
    static void refreshData(HashMap<Integer, int[][]> teams){

    }

    static void checkForChange(int channel, HashMap<Integer, int[][]> teams) throws IOException {
        Set<Integer> teamIds = teams.keySet();

        for (Integer teamNum : teamIds) {
            String tn = teamNum + "";
            if (tn.length() == 3){
                tn = " " + tn;
            }
            int[][] teamScore = teams.get(teamNum);
            String toSend = "10-" + tn + ": " + teamScore[1][0] + "\n";
            boolean hasChanged = false;
            if (teamScore[0][1] != teamScore[1][1]) {
                hasChanged = true;
                toSend += "Server 08: " + teamScore[0][1] + " -> " + teamScore[1][1] + "\n";
                teamScore[0][1] = teamScore[1][1];
            }
            if (teamScore[0][2] != teamScore[1][2]) {
                hasChanged = true;
                toSend += "Windows 10: " + teamScore[0][2] + " -> " + teamScore[1][2] + "\n";
                teamScore[0][2] = teamScore[1][2];
            }
            if (teamScore[0][3] != teamScore[1][3]) {
                hasChanged = true;
                toSend += "Ubuntu 14.04: " + teamScore[0][3] + " -> " + teamScore[1][3] + "\n";
                teamScore[0][3] = teamScore[1][3];
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
