public class Post {
    public double moneyStatus;
    public double armyStatus;
    public double businessStatus;
    public double workerStatus;
    public double foodStatus;

    public String nameOfOwnAlliance; // ��� �������, ������� �� �������
    public int idOfOwnerAnotherCountry = -1; // id ������, ���������� �������, � ������� �� ������ ��������
    public int myAllianceInvitation = -1; // ����� ������ �� ��������� �����������?
    public boolean isAllianceAccepted; // ������� �� �� ����������� � ������?

    public int tradeWith = -1; // id ������������, �������� �� ��������� trade
    public int tradeInvitation; // id ������������, ������� �������� trade  !!!!!!!!!!!!!!
    public boolean isTradeAccepted; // ������� �� �� ����������� �� �����    !!!!!!!!!!!!!!!
    public int stateUp; // ��, ��� �� ������
    public int stateDown; //��, ��� �� ���������;
}
