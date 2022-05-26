
public class Game {
    int numberOfWeek;
    Country[] countries;

    public Game(){
        numberOfWeek = 1;
        countries = new Storage().countries;
    }

    public int getEmptyCountry(){
        int value = (int)(Math.random() * countries.length);
        while(countries[value].occupied){
            value = (int)(Math.random() * countries.length);
        }
        countries[value].occupied = true;
        return value;
    }

}
