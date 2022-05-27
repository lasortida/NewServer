import java.util.ArrayList;

public class Room {
    String id;
    boolean isGameStarted;
    boolean timerStart;
    int secondsReminder;
    boolean isNextWeek;
    boolean isGameFull;
    ArrayList<User> users;
    Country[] countries;
    ArrayList<Alliance> alliances;
    Game game;
    int countOfReady;
    ArrayList<Integer> idsOfAlliance;
    ArrayList<Integer> idsOfCountry;
    ArrayList<Integer> idsOfLoser;
    int currentPlayers = 0;

    public Room(String id){
        this.id = id;
        users = new ArrayList<>();
        isGameFull = false;
        isGameStarted = false;
        countOfReady = 0;
        countries = new Storage().countries;
        alliances = new ArrayList<>();
        idsOfAlliance = new ArrayList<>();
        idsOfCountry = new ArrayList<>();
        idsOfLoser = new ArrayList<>();
    }

    public int[] getIdsOfAlliance(){
        return idsOfAlliance.stream().mapToInt(Integer::intValue).toArray();
    }

    public int[] getIdsOfCountry(){
        return idsOfCountry.stream().mapToInt(Integer::intValue).toArray();
    }

    public String[] getNewNameAlliance(){
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < alliances.size(); ++i){
            if (alliances.get(i).isNew){
                strings.add(alliances.get(i).name);
            }
        }
        return strings.toArray(new String[0]);
    }

    public int[] getNewAlliancesIds(){
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < alliances.size(); ++i){
            if (alliances.get(i).isNew){
                res.add(alliances.get(i).idOfAlliance);
            }
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    public int[] getNewAlliancesIdsOfOwner(){
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < alliances.size(); ++i){
            if (alliances.get(i).isNew){
                res.add(alliances.get(i).idOfOwner);
            }
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    public int[] getNewAlliancesAvatars(){
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < alliances.size(); ++i){
            if (alliances.get(i).isNew){
                res.add(alliances.get(i).avatar);
            }
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    public String[] getNewAlliancesDescription(){
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < alliances.size(); ++i){
            if (alliances.get(i).isNew){
                strings.add(alliances.get(i).description);
            }
        }
        return strings.toArray(new String[0]);
    }

    public int addUser(){
        System.out.println("Added one person");
        int code = users.size();
        User user = new User(code);
        currentPlayers++;
        if (game != null){
            user.idOfCountry = game.getEmptyCountry();
        }
        if (users.size() == 1){
            timerStart = true;
            secondsReminder = 15;
            Thread thread = new Thread(){
                @Override
                public void run() {
                    while (true){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        secondsReminder--;
                        if (secondsReminder == 0){
                            break;
                        }
                    }
                    game = new Game();
                    isGameStarted = true;
                    for (int i = 0; i < users.size(); ++i){
                        User u = users.get(i);
                        u.idOfCountry = game.getEmptyCountry();
                        users.set(i, u);
                    }
                    user.idOfCountry = game.getEmptyCountry();
                }
            };

            thread.start();
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
            result[i] = users.get(i).idOfCountry;
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
