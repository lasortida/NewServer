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

        room.countries[user.idOfCountry].moneyStatus = post.moneyStatus;
        room.countries[user.idOfCountry].armyStatus = post.armyStatus;
        room.countries[user.idOfCountry].businessStatus = post.businessStatus;
        room.countries[user.idOfCountry].workerStatus = post.workerStatus;
        room.countries[user.idOfCountry].foodStatus = post.foodStatus;

        if (post.tradeWith != -1){
            room.countries[post.tradeWith].tradeWith.add(user.idOfCountry);
            room.countries[post.tradeWith].tradeAway.add(post.tradeToMe);
            room.countries[post.tradeWith].tradeToMe.add(post.tradeAway);
        }

        if (post.isTradeAccepted != null && post.isTradeAccepted.length != 0){
            for (int i = 0; i < post.isTradeAccepted.length; ++i){
                room.countries[post.confirmation[i]].isTradeAccepted = post.isTradeAccepted[i];
            }
        }

        if (post.isOfferAccepted != null && post.isOfferAccepted.length != 0){
            for (int i = 0; i < post.isOfferAccepted.length; ++i){
                if (post.isOfferAccepted[i]){
                    Alliance alliance = room.alliances.get(post.allianceIdConfirmation[i]);
                    int idOfCountry = room.users.get(userCode).idOfCountry;
                    alliance.countries.add(idOfCountry);
                    room.idsOfCountry.add(idOfCountry);
                    room.idsOfAlliance.add(alliance.idOfAlliance);
                    room.alliances.set(post.allianceIdConfirmation[i], alliance);
                    room.countries[idOfCountry].idOfAlliance = post.allianceIdConfirmation[i];
                }
            }
        }

        if (post.nameOfNewAlliance != null){
            Alliance alliance = new Alliance(post.nameOfNewAlliance, room.users.get(userCode).idOfCountry, post.description, post.avatar);
            int idOfAlliance = room.alliances.size();
            alliance.idOfAlliance = idOfAlliance;
            room.countries[room.users.get(userCode).idOfCountry].idOfAlliance = idOfAlliance;
            room.alliances.add(alliance);
        }

        if (post.idOfCountryOffer != -1){
            int idOfAlliance = room.countries[room.users.get(userCode).idOfCountry].idOfAlliance;
            room.countries[post.idOfCountryOffer].offerFromAlliance.add(idOfAlliance);
        }
        if (post.end){
            room.users.remove(userCode);
        }

        user.readyToNext = true;
        room.countOfReady++;
        room.users.set(userCode, user);
        rooms.set(index, room);
    }

    public void pauseRoom(String idOfRoom){
        int index = idOfRooms.indexOf(idOfRoom);
        Room room = rooms.get(index);
        room.countOfReady = 0;
        room.isNextWeek = false;
        room.idsOfAlliance = new ArrayList<>();
        room.idsOfCountry = new ArrayList<>();
        for (int i = 0; i < room.users.size(); ++i){
            User u = room.users.get(i);
            u.readyToNext = false;
            room.users.set(i, u);
        }
        for (int i = 0; i < room.countries.length; ++i){
            room.countries[i].tradeWith = new ArrayList<>();
            room.countries[i].tradeAway = new ArrayList<>();
            room.countries[i].tradeToMe = new ArrayList<>();
            room.countries[i].isTradeAccepted = false;
            room.countries[i].offerFromAlliance = new ArrayList<>();
        }
        for (int i = 0; i < room.alliances.size(); ++i){
            Alliance alliance = room.alliances.get(i);
            alliance.isNew = false;
            room.alliances.set(i, alliance);
        }

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
            if (!r.isGameFull && !r.isGameStarted){
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
