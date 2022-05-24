import java.util.ArrayList;

public class Room {
    String id;
    boolean isGameStarted;
    boolean isNextWeek;
    boolean isGameFull;
    ArrayList<User> users;
    Game game;
    int countOfReady;

    public Room(String id){
        this.id = id;
        users = new ArrayList<>();
        isGameFull = false;
        isGameStarted = false;
        countOfReady = 0;
    }

    public int[] findInvitationForCountry(int idOfCountry){
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < users.size(); ++i){
            Alliance alliance = users.get(i).country.alliance;
            if (alliance != null && alliance.idOfOwner == users.get(i).country.id){
                if (alliance.invitationsFromMe.contains(idOfCountry)){
                    result.add(users.get(i).country.id);
                }
            }
        }
        int[] idk = result.stream().mapToInt(Integer::intValue).toArray();
        return idk;
    }

    public int addUser(){
        System.out.println("Added one person");
        int code = users.size();
        User user = new User(code);
        if (game != null){
            user.country = game.getEmptyCountry();
        }
        if (users.size() == 1){
            game = new Game();
            isGameStarted = true;
            for (int i = 0; i < users.size(); ++i){
                User u = users.get(i);
                u.country = game.getEmptyCountry();
                users.set(i, u);
            }
            user.country = game.getEmptyCountry();
        }
        if (users.size() == 7){
            isGameFull = true;
        }
        users.add(user);
        return code;
    }

    public void setUserReady(int userCode){
        User user = users.get(userCode);
        user.readyToStart = true;
        users.set(userCode, user);
    }

    public void setPlayerName(int userCode, String name){
        User user = users.get(userCode);
        user.name = name;
        users.set(userCode, user);
    }

    public int[] getUsers(){
        int[] result = new int[users.size()];
        for (int i = 0; i < users.size(); ++i){
            result[i] = users.get(i).country.id;
        }
        return result;
    }

    public String[] getNameOfUsers(){
        String[] result = new String[users.size()];
        for (int i = 0; i < users.size(); ++i){
            result[i] = users.get(i).name;
        }
        return result;
    }
}
