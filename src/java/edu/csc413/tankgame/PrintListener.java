package edu.csc413.tankgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PrintListener implements ActionListener {
        public static final String START_BUTTON_ACTION_COMMAND = "start_ac";
        public static final String EXIT_BUTTON_ACTION_COMMAND = "exit_ac";


        @Override
        public void actionPerformed(ActionEvent event) {
            String actionCommand = event.getActionCommand();
            if (actionCommand.equals(START_BUTTON_ACTION_COMMAND)) {
                GameDriver.pressedStart();
                System.out.println("Start button is pressed");



            } else if (actionCommand.equals(EXIT_BUTTON_ACTION_COMMAND)) {
                GameDriver.pressedExit();
                System.out.println(" exit is pressed");
            }

        }
    }

