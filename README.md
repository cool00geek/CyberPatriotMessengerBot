# CyberPatriotMessengerBot

CyberPatriot Messenger Bot is a Slack bot that monitors teams either pre-defined or passed as an argument.

## Features

- Slack integration
- Sends message every time a team changes point value

## Usage

1. In `SlackBot.java`, change the API values for the different channels. You can get an API URL for each channel [here](https://api.slack.com/apps) by clicking the new app and registering an app.
1. In `Main.java`, in the appropriate section, add the teams you want to monitor (`schoolTeams.put(247, new int[4][4]);`, `favTeams.put(0247, new int[4][4]);`, or `schoolTeams.put(0247, new int[4][4]`)
1. Compile it with `mvn build` and run the generated jar (`java -jar {JAR_NAME.jar}`)
1. Pass any arguments such as other teams after the jar (`java -jar {JAR_NAME.jar} 0247`)

## Note

This was a quick n dirty project, there are gonna be type-Os and other mistakes. Just create an issue and there is no guarantee that I'll fix it.
