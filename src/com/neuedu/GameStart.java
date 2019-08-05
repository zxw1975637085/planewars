package com.neuedu;

import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;

public class GameStart {

    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        DataStore.put("gameFrame",gameFrame);
        gameFrame.init();
    }
}
