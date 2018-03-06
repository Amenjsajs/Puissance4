package Evenementielle.AJS.Puissance4.Utils;

import Evenementielle.AJS.Puissance4.PS4Init;

import javax.swing.*;

public class Utils {
    public static ImageIcon getAppIcon(String nomIcon) {
        return new ImageIcon(PS4Init.class.getResource("/Evenementielle/AJS/Puissance4/Image/"+nomIcon));
    }
    public static int getRandomIntInclusive(int min, int max) {
        min = (int) Math.ceil(min);
        max = (int) Math.floor(max);
        return (int) (Math.floor(Math.random() * (max - min + 1)) + min); //Le maximum et le minimum sont inclus
    }
}
