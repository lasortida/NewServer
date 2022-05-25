
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static GameServer gameServer;
    public static void main(String[] args) {
        gameServer = new GameServer();
        System.out.println("Server start!");
        try{
            ServerSocket serverSocket = new ServerSocket(80);
            Socket socket = null;
            while (true){
                try {
                    socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintStream print = new PrintStream(socket.getOutputStream());
                    System.out.println("Wait");
                    try{
                        String header = in.readLine();
                        System.out.println(header);
                        if (header.startsWith("POST")){
                            String query = header.split(" ")[1].substring(1);
                            if (query.startsWith("theking/data")){
                                String[] queries = query.split("/")[1].substring(5).split("&");
                                String idOfRoom = queries[0].substring(3);
                                int userCode = Integer.parseInt(queries[1].substring(10));
                                int length = 0;
                                String row = "";
                                while (!(row = in.readLine()).equals("")){
                                    if (row.startsWith("Content-Length: ")){
                                        length = Integer.parseInt(row.substring(16));
                                    }
                                }
                                StringBuilder body = new StringBuilder();
                                for (int i = 0; i < length; ++i){
                                    int c = in.read();
                                    body.append((char)c);
                                }
                                String bd = body.toString();
                                bd = bd.replaceAll("%20", " ");
                                bd = bd.replaceAll("%7B", "{");
                                bd = bd.replaceAll("%7D", "}");
                                bd = bd.replaceAll("%22", "\"");
                                bd = bd.replaceAll("%3A", ":");
                                bd = bd.replaceAll("%2C", ",");
                                System.out.println(bd);
                                postToServer(idOfRoom, userCode, print, bd.substring(5));
                            }
                        }
                        else {
                            String query = header.split(" ")[1].substring(1);
                            if (query.startsWith("theking")){
                                String[] queries = query.split("/");
                                if (queries.length <= 1){
                                    sendIdOfRoom(print);
                                }
                                if (queries.length == 2){
                                    query = queries[1];
                                    if (query.startsWith("room")){
                                        queries = query.substring(5).split("&");
                                        if (queries.length <= 1){
                                            String idOfRoom = query.substring(8);
                                            addUserAndGetCode(idOfRoom, print);
                                        }
                                        if (queries.length == 2){
                                            String idOfRoom = query.substring(8, 14);
                                            int userCode = Integer.parseInt(queries[1].substring(10));
                                            getStartStatus(idOfRoom, userCode, print);
                                        }
                                        if (queries.length == 3){
                                            String idOfRoom = query.substring(8, 14);
                                            int userCode = Integer.parseInt(queries[1].substring(10));
                                            String userName = queries[2].substring(10);
                                            setUserName(idOfRoom, userCode, userName, print);
                                        }
                                    }
                                    if (query.startsWith("start")){
                                        queries = query.substring(6).split("&");
                                        if (queries.length == 2){
                                            String idOfRoom = query.substring(9, 15);
                                            int userCode = Integer.parseInt(queries[1].substring(10));
                                            getRoomInform(idOfRoom, userCode, print);
                                        }
                                    }
                                    if (query.startsWith("next")){
                                        queries = query.substring(6).split("&");
                                        if (queries.length == 2){
                                            String idOfRoom = queries[0].substring(2);
                                            int userCode = Integer.parseInt(queries[1].substring(10));
                                            System.out.println("ID: " + idOfRoom + " user-code: " + userCode);
                                            continueGame(idOfRoom, userCode, print);
                                        }
                                    }
                                    if (query.startsWith("clean")){
                                        String key = query.substring(10);
                                        if (key.equals("25052022")){
                                            removeAllRooms();
                                        }    
                                    }
                                }
                            }
                            else if (query.startsWith("docs")){
                                FileCreator creator = new FileCreator();
                                File file = creator.createDocFile(gameServer);
                                sendFile(file, print, "html", false);
                            }
                            else{
                                FileCreator creator = new FileCreator(header);
                                File file = creator.createFile();
                                String type = creator.getType();
                                sendFile(file, print, type, creator.isError());
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                        System.out.println(e.toString());
                    }
                    print.flush();
                    print.close();
                    socket.close();
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("blin");
                    socket.close();
                }
            }
        } catch (Exception e){
            System.out.println("main");
            e.printStackTrace();
        }
    }
    public static void continueGame(String idOfRoom, int userCode, PrintStream print){
        JSONCreator creator = new JSONCreator();
        try{
            if (gameServer.isRightId(idOfRoom) && userCode < gameServer.getRoom(idOfRoom).users.size()){
                File file = creator.getContinue(gameServer.getRoom(idOfRoom), userCode);
                sendFile(file, print, "json", false);
            }
            else{
                File file = creator.getError();
                sendFile(file, print, "json", false);
            }
        } catch (Exception e){
            System.out.println("continueGame");
            e.printStackTrace();
        }
    }

    public static void postToServer(String idOfRoom, int userCode, PrintStream print, String json){
        JSONCreator creator = new JSONCreator();
        try{
            if (gameServer.isRightId(idOfRoom) && userCode < gameServer.getRoom(idOfRoom).users.size()){
                if (!gameServer.getRoom(idOfRoom).users.get(userCode).readyToNext) {
                    Gson gson = new Gson();
                    Post post = gson.fromJson(json, Post.class);
                    gameServer.setChanges(idOfRoom, userCode, post);
                    if (gameServer.getRoom(idOfRoom).countOfReady == gameServer.getRoom(idOfRoom).users.size()){
                        gameServer.nextWeek(idOfRoom);
                    }
                }
                File file = creator.getOK();
                sendFile(file, print, "json", false);
            }
            else{
                File file = creator.getError();
                sendFile(file, print, "json", false);
            }
        } catch (Exception e){
            System.out.println("postToServer");
            e.printStackTrace();
        }
    }

    public static void getStartStatus(String idOfRoom, int userCode, PrintStream printStream){
        JSONCreator creator = new JSONCreator();
        try {
            if (gameServer.isRightId(idOfRoom) && userCode < gameServer.getRoom(idOfRoom).users.size()){
                File file = creator.getWaiting(gameServer.getRoom(idOfRoom).isGameStarted, false);
                sendFile(file, printStream, "json", false);
            }
            else {
                File file = creator.getWaiting(false, true);
                sendFile(file, printStream, "json", false);
            }
        } catch (Exception e){
            System.out.println("getStartStatus");
            e.printStackTrace();
        }
    }
    
    public static void removeAllRooms(){
        gameServer = new GameServer();
    }

    public static void sendFile(File file, PrintStream print, String type, boolean error){
        if (error){
            print.println("HTTP/1.1 404 OK");
        } else {
            print.println("HTTP/1.1 200 OK");
        }
        print.println("Connection: close");
        if (type.equals("html")){
            print.println("Connection-Type: text/HTML \n");
        }
        if (type.equals("png")) {
            print.println("Content-Type: image/png \n");
        }
        if (type.equals("jpg")) {
            print.println("Content-Type: image/jpg \n");
        }
        if (type.equals("gif")) {
            print.println("Content-Type: image/gif \n");
        }
        if (type.equals("json")) {
            print.println("Content-Type: application/json \n");
        }
        try {
            FileInputStream stream = new FileInputStream(file);
            byte[] data = stream.readAllBytes();
            print.write(data);
        } catch (Exception e) {
            System.out.println("sendFile");
            e.printStackTrace();
        }
    }

    public static void sendIdOfRoom(PrintStream print){
        JSONCreator creator = new JSONCreator();
        String idOfRoom = gameServer.getIdOfRoom();
        try {
            File file = creator.getIdOfRoom(idOfRoom);
            sendFile(file, print, "json", false);
        } catch (Exception e) {
            System.out.println("sendIdOfRoom");
            e.printStackTrace();
        }
    }

    public static void addUserAndGetCode(String idOfRoom, PrintStream printStream){
        JSONCreator creator = new JSONCreator();
        try{
            File file;
            if (gameServer.isRightId(idOfRoom) && !gameServer.getRoom(idOfRoom).isGameFull) {
                int userCode = gameServer.addUser(idOfRoom);
                file = creator.getUserCode(userCode, false);
            } else{
                file = creator.getUserCode(0, true);
            }
            sendFile(file, printStream, "json", false);
        } catch (Exception e) {
            System.out.println("addUserAndGetCode");
            e.printStackTrace();
        }
    }

    public static void setUserName(String idOfRoom, int userCode, String userName, PrintStream printStream){
        JSONCreator creator = new JSONCreator();
        try {
            if (gameServer.isRightId(idOfRoom) && userCode < gameServer.getRoom(idOfRoom).users.size()){
                gameServer.setUserName(idOfRoom, userCode, userName);
                File file = creator.getOK();
                sendFile(file, printStream, "json", false);
            }
            else{
                File file = creator.getError();
                sendFile(file, printStream, "json",false);
            }
        } catch (Exception e){
            System.out.println("setUserName");
            e.printStackTrace();
        }
    }

    public static void getRoomInform(String idOfRoom, int userCode, PrintStream printStream){
        JSONCreator creator = new JSONCreator();
        try{
            File file;
            if (gameServer.isRightId(idOfRoom) && userCode < gameServer.getRoom(idOfRoom).users.size()){
                int index = gameServer.idOfRooms.indexOf(idOfRoom);
                Room room = gameServer.rooms.get(index);
                if (room.game.numberOfWeek <= 1){
                    file = creator.getGeneralData(room, userCode, false);
                }
                else{
                    file = creator.getGeneralAndNext(gameServer, idOfRoom, userCode);
                }
            }
            else{
                file = creator.getError();
            }
            sendFile(file, printStream, "json", false);
        } catch(Exception e){
            System.out.println("getRoomInform");
            e.printStackTrace();
        }
    }
}
