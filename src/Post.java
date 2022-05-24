public class Post {
    public double moneyStatus;
    public double armyStatus;
    public double businessStatus;
    public double workerStatus;
    public double foodStatus;

    public String nameOfOwnAlliance; // им€ аль€нса, который вы создали
    public int idOfOwnerAnotherCountry = -1; // id страны, основател€ јль€нса, в который вы хотите вступить
    public int myAllianceInvitation = -1; // какой стране вы отправили приглашение?
    public boolean isAllianceAccepted; // прин€ли ли вы приглашение в јль€нс?

    public int tradeWith = -1; // id пользовател€, которому вы отправили trade
    public int tradeInvitation; // id пользовател€, который отправил trade  !!!!!!!!!!!!!!
    public boolean isTradeAccepted; // прин€ли ли вы предложение на обмен    !!!!!!!!!!!!!!!
    public int stateUp; // то, что вы отдаЄте
    public int stateDown; //то, что вы получаете;
}
