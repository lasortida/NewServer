import java.util.ArrayList;

public class GameServer {
    ArrayList<Room> rooms;
    ArrayList<String> idOfRooms;

    public GameServer(){
        rooms = new ArrayList<>();
        idOfRooms = new ArrayList<>();
        String id = formId();
        rooms.add(new Room(id));
        idOfRooms.add(id);
        System.out.println("Created Room: Sum = " + rooms.size());
    }

    public void setChanges(String idOfRoom, int userCode, Post post){
        int index = idOfRooms.indexOf(idOfRoom);
        Room room = rooms.get(index);
        User user = room.users.get(userCode);

        user.country.moneyStatus = post.moneyStatus;
        user.country.armyStatus = post.armyStatus;
        user.country.businessStatus = post.businessStatus;
        user.country.workerStatus = post.workerStatus;
        user.country.foodStatus = post.foodStatus;

        if (post.nameOfOwnAlliance != null){
            user.country.alliance = new Alliance(post.nameOfOwnAlliance, user.country.id);
        }
        if (post.idOfOwnerAnotherCountry != -1){
            for (int i = 0; i < room.users.size(); ++i){
                User u = room.users.get(i);
                if (u.country.id == post.idOfOwnerAnotherCountry){
                    u.country.alliance.invitationsFromCountries.add(user.country.id);
                    room.users.set(i, u);
                    break;
                }
            }
        }
        if (post.myAllianceInvitation != -1){
            user.country.alliance.invitationsFromMe.add(post.myAllianceInvitation);
        }
        if (post.tradeWith != -1){
            for (int i = 0; i < room.users.size(); ++i){
                User u = room.users.get(i);
                if (u.country.id == post.tradeWith){
                   u.country.offersFromCountries.add(user.country.id);
                   u.country.states.put(post.stateDown, post.stateUp);
                   room.users.set(i, u);
                   break;
                }
            }
        }

        user.readyToNext = true;
        room.countOfReady++;
        room.users.set(userCode, user);
        rooms.set(index, room);
    }

    public void nextWeek(String idOfRoom){
        int index = idOfRooms.indexOf(idOfRoom);
        Room room = rooms.get(index);
        room.game.numberOfWeek++;
        room.isNextWeek = true;
        rooms.set(index, room);
    }

    public String getIdOfRoom(){
        for(Room r: rooms){
            if (!r.isGameFull){
                return r.id;
            }
        }
        String id = formId();
        rooms.add(new Room(id));
        idOfRooms.add(id);
        System.out.println("Created Room: Sum = " + rooms.size());
        return id;
    }

    public String formId(){
        char[] words = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'G', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; ++i){
            double wordsOrNumbers = Math.random();
            if (wordsOrNumbers >= 0.5) {
                char word = words[(int)(Math.random() * words.length)];
                builder.append(word);
            }
            else {
                char number = numbers[(int)(Math.random() * numbers.length)];
                builder.append(number);
            }
        }
        String result = builder.toString();
        return result;
    }

    public boolean isRightId(String id){
        return idOfRooms.contains(id);
    }

    public Room getRoom(String id){
        int index = idOfRooms.indexOf(id);
        return rooms.get(index);
    }

    public int addUser(String idOfRoom){
        int index = idOfRooms.indexOf(idOfRoom);
        Room room = rooms.get(index);
        int code = room.addUser();
        rooms.set(index, room);
        return code;
    }

    public void setUserName(String idOfRoom, int userCode, String name){
        int index =idOfRooms.indexOf(idOfRoom);
        Room room = rooms.get(index);
        room.setPlayerName(userCode, name);
        rooms.set(index, room);
    }
}
