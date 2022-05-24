import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Country {
    String title;
    int id;
    boolean occupied;

    double moneyStatus;
    double armyStatus;
    double businessStatus;
    double workerStatus;
    double foodStatus;

    Alliance alliance;
    ArrayList<Integer> offersFromCountries;
    HashMap<Integer, Integer> states;

    public Country(String title, int id){
        this.title = title;
        this.id = id;
        moneyStatus = 0.5;
        armyStatus = 0.5;
        businessStatus = 0.5;
        workerStatus = 0.5;
        foodStatus = 0.5;
        occupied = false;
        offersFromCountries = new ArrayList<>();
        states = new HashMap<>();
    }

    public int[] getTrade(){
        int[] result = offersFromCountries.stream().mapToInt(Integer::intValue).toArray();
        return result;
    }

    public int[] getTradeAway(){
        int[] result = states.keySet().stream().mapToInt(Integer::intValue).toArray();
        return result;
    }

    public int[] getTradeToMe(){
        int[] result = states.values().stream().mapToInt(Integer::intValue).toArray();
        return result;
    }
}
