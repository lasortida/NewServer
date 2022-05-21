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
        object.put("error", error);
        object.put("start", room.isGameStarted);
        object.put("usersCount", room.users.size());
        object.put("countryId", room.users.get(userCode).country.id);
        object.put("users", room.getUsers());
        object.put("userNames", room.getNameOfUsers());
        object.put("numberOfWeek", room.game.numberOfWeek);
        object.put("moneyStatus", room.users.get(userCode).country.moneyStatus);
        object.put("armyStatus", room.users.get(userCode).country.armyStatus);
        object.put("businessStatus", room.users.get(userCode).country.businessStatus);
        object.put("workerStatus", room.users.get(userCode).country.workerStatus);
        object.put("foodStatus", room.users.get(userCode).country.foodStatus);
        File file = new File("theking/answer.json");
        FileWriter writer = new FileWriter(file);
        writer.write(object.toString());
        writer.flush();
        writer.close();
        return file;
    }

    public File getWaiting(boolean start, boolean error) throws Exception {
        object.put("error", error);
        object.put("start", start);
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
