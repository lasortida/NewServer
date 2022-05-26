import java.util.ArrayList;

public class Country {
    String title;
    int id;
    boolean occupied;

    double moneyStatus;
    double armyStatus;
    double businessStatus;
    double workerStatus;
    double foodStatus;

    int idOfAlliance = -1;
    ArrayList<Integer> offerFromAlliance;
    ArrayList<Integer> tradeWith;
    ArrayList<Integer> tradeAway;
    ArrayList<Integer> tradeToMe;
    boolean isTradeAccepted;

    public Country(String title, int id){
        this.title = title;
        this.id = id;
        moneyStatus = 0.5;
        armyStatus = 0.5;
        businessStatus = 0.5;
        workerStatus = 0.5;
        foodStatus = 0.5;
        occupied = false;
        tradeWith = new ArrayList<>();
        tradeAway = new ArrayList<>();
        tradeToMe = new ArrayList<>();
        offerFromAlliance = new ArrayList<>();
    }

    public int[] getOffersFromAlliances(){
        int[] result = new int[offerFromAlliance.size()];
        for (int i = 0; i < result.length; ++i){
            result[i] = offerFromAlliance.get(i);
        }
        return result;
    }

    public int[] getTrade(){
        int[] result = tradeWith.stream().mapToInt(Integer::intValue).toArray();
        return result;
    }

    public int[] getTradeAway(){
        int[] result = tradeAway.stream().mapToInt(Integer::intValue).toArray();
        return result;
    }

    public int[] getTradeToMe(){
        int[] result = tradeToMe.stream().mapToInt(Integer::intValue).toArray();
        return result;
    }
}
