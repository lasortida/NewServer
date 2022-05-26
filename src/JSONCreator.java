
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;

public class JSONCreator {

    JSONObject object;

    public JSONCreator(){
        object = new JSONObject();
    }

    public File getIdOfRoom(String id) throws Exception{
        object.put("id", id);
        File file = new File("theking/answer.json");
        FileWriter writer = new FileWriter(file);
        writer.write(object.toString());
        writer.flush();
        writer.close();
        return file;
    }

    public File getUserCode(int code, boolean error) throws Exception {
        object.put("userCode", code);
        object.put("error", error);
        File file = new File("theking/answer.json");
        FileWriter writer = new FileWriter(file);
        writer.write(object.toString());
        writer.flush();
        writer.close();
        return file;
    }

    public File getGeneralData(Room room, int userCode, boolean error) throws Exception{
        object.put("id", room.id);
        object.put("error", error);
        object.put("start", room.isGameStarted);
        object.put("usersCount", room.users.size());
        object.put("countryId", room.users.get(userCode).idOfCountry);
        object.put("users", room.getUsers());
        object.put("userNames", room.getNameOfUsers());
        object.put("numberOfWeek", room.game.numberOfWeek);
        object.put("moneyStatus", room.countries[room.users.get(userCode).idOfCountry].moneyStatus);
        object.put("armyStatus", room.countries[room.users.get(userCode).idOfCountry].armyStatus);
        object.put("businessStatus", room.countries[room.users.get(userCode).idOfCountry].businessStatus);
        object.put("workerStatus", room.countries[room.users.get(userCode).idOfCountry].workerStatus);
        object.put("foodStatus", room.countries[room.users.get(userCode).idOfCountry].foodStatus);
        File file = new File("theking/answer.json");
        FileWriter writer = new FileWriter(file);
        writer.write(object.toString());
        writer.flush();
        writer.close();
        return file;
    }

    public File getContinue(Room room, int userCode) throws Exception{
        object.put("next", room.isNextWeek);
        object.put("numberOfWeek",room.game.numberOfWeek);
        object.put("moneyStatus", room.countries[room.users.get(userCode).idOfCountry].moneyStatus);
        object.put("armyStatus", room.countries[room.users.get(userCode).idOfCountry].armyStatus);
        object.put("businessStatus", room.countries[room.users.get(userCode).idOfCountry].businessStatus);
        object.put("workerStatus", room.countries[room.users.get(userCode).idOfCountry].workerStatus);
        object.put("foodStatus", room.countries[room.users.get(userCode).idOfCountry].foodStatus);
        object.put("tradeWith", room.countries[room.users.get(userCode).idOfCountry].getTrade());
        object.put("tradeAway", room.countries[room.users.get(userCode).idOfCountry].getTradeAway());
        object.put("tradeToMe", room.countries[room.users.get(userCode).idOfCountry].getTradeToMe());
        object.put("isTradeAccepted", room.countries[room.users.get(userCode).idOfCountry].isTradeAccepted);
        object.put("newAllianceNames", room.getNewNameAlliance());
        object.put("newAllianceIds", room.getNewAlliancesIds());
        object.put("newAllianceIdsOfOwner", room.getNewAlliancesIdsOfOwner());
        object.put("newAllianceAvatars", room.getNewAlliancesAvatars());
        object.put("newAllianceDescription", room.getNewAlliancesDescription());
        object.put("offersFromAlliances", room.countries[room.users.get(userCode).idOfCountry].getOffersFromAlliances());
        object.put("newIdsOfCountry", room.getIdsOfCountry());
        object.put("newIdsOfAlliance", room.getIdsOfAlliance());
        if (room.users.size() == 1){
            object.put("win", true);
        }
        else{
            object.put("win", false);
        }
        File file = new File("theking/answer.json");
        FileWriter writer = new FileWriter(file);
        writer.write(object.toString());
        System.out.println("JSON: " + object.toString());
        writer.flush();
        writer.close();
        return file;
    }

    public File getWaiting(Room room, boolean error) throws Exception {
        boolean start = room.isGameStarted;
        boolean timerStart = room.timerStart;
        int timerReminder = room.secondsReminder;
        object.put("error", error);
        object.put("start", start);
        object.put("timerStart", timerStart);
        object.put("timerReminder", timerReminder);
        File file = new File("theking/answer.json");
        FileWriter writer = new FileWriter(file);
        writer.write(object.toString());
        writer.flush();
        writer.close();
        return file;
    }

    public File getOK() throws Exception{
        object.put("error", false);
        File file = new File("theking/answer.json");
        FileWriter writer = new FileWriter(file);
        writer.write(object.toString());
        writer.flush();
        writer.close();
        return file;
    }

    public File getError() throws Exception{
        object.put("error", true);
        File file = new File("theking/answer.json");
        FileWriter writer = new FileWriter(file);
        writer.write(object.toString());
        writer.flush();
        writer.close();
        return file;
    }
}
