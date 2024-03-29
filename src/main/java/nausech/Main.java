package nausech;

import javax.swing.*;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        int menuOption  = -1;
        String[] buttons = {"1. Show Cats", "2. Show Favorites ", "3. Exit"};
        do{
            String option = (String) JOptionPane.showInputDialog(null, "Main menu", "Cats app", JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
            for (int i = 0; i < buttons.length; i++) {
                if (option.equals(buttons[i])) {
                    menuOption = i;
                    break;
                }
            }
            switch (menuOption){
                case 0:
                    CatsService.showCats();
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }while (menuOption != 1);
    }
}