package Evenementielle.AJS.Puissance4.Utils;

public class Utils {
    public static int getRandomIntInclusive(int min, int max) {
        min = (int) Math.ceil(min);
        max = (int) Math.floor(max);
        return (int) (Math.floor(Math.random() * (max - min + 1)) + min); //The maximum is inclusive and the minimum is inclusive
    }
}
