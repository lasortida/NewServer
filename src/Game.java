import java.util.ArrayList;

public class Game {
    ArrayList<Mail> postOffice;
    int numberOfWeek;
    Country[] countries;

    public Game(){
        numberOfWeek = 1;
        countries = new Storage().countries;
    }

    public Country getEmptyCountry(){
        int value = (int)(Math.random() * countries.length);
        while(countries[value].occupied){
            value = (int)(Math.random() * countries.length);
        }
        countries[value].occupied = true;
        return countries[value];
    }

}
