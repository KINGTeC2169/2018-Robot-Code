package com.team2169.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class GameInfo {
   private static String GAMEDATA_IP = "10.0.100.44";
   private static int GAMEDATA_PORT = 5555;

   public GameInfo() {
   }

   public static String getGameSpecificMessage_WeekZero() throws IOException {
       Socket s = new Socket(GAMEDATA_IP, GAMEDATA_PORT);
       BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
       String gamedata_msg = input.readLine();
       s.close();
       return gamedata_msg;
   }
}
