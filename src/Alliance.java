import java.util.ArrayList;

public class Alliance {
    String name;
    int idOfOwner;
    int idOfAlliance;
    ArrayList<Integer> countries;
    boolean isNew;
    String description;
    int avatar;

    public Alliance(String name, int idOfOwner, String description, int avatar){
        this.name = name;
        this.idOfOwner = idOfOwner;
        this.description = description;
        this.avatar = avatar;
        countries = new ArrayList<>();
        isNew = true;
    }

}
