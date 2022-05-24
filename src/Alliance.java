import java.util.ArrayList;

public class Alliance {
    String name;
    int idOfOwner;
    ArrayList<Integer> invitationsFromCountries; // ������ ����� �� ���
    ArrayList<Integer> invitationsFromMe; // � ��������� ������

    public Alliance(String name, int idOfOwner){
        this.name = name;
        this.idOfOwner = idOfOwner;
        invitationsFromCountries = new ArrayList<>();
        invitationsFromMe = new ArrayList<>();
    }

    public int[] getInvitations(){
        int[] result = invitationsFromCountries.stream().mapToInt(Integer::intValue).toArray();
        return result;
    }
}
