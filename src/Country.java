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

    public Country(String title, int id){
        this.title = title;
        this.id = id;
        moneyStatus = 0.5;
        armyStatus = 0.5;
        businessStatus = 0.5;
        workerStatus = 0.5;
        foodStatus = 0.5;
        occupied = false;
    }
}
