import java.io.File;
import java.io.FileWriter;

public class FileCreator {
    String address;
    String typeOfFile;
    File file;
    boolean error = false;

    public FileCreator(String header){
        address = header.split(" ")[1].substring(1);
    }
    public FileCreator(){ }

    public File createDocFile(GameServer gameServer) throws Exception{
        File file = new File("docs/doc.txt");
        FileWriter writer = new FileWriter(file);
        writer.write("<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>");
        writer.write("<h1>The King</h1>");
        writer.write("<h1>My application</h1>");
        writer.write("<h2>Список комнат: </h2>");
        for (Room r: gameServer.rooms){
            writer.write("<h3 style=\"margin-top: 3%\">ID: " + r.id + "</h3>");
            writer.write("<h3>Игроков: " + r.users.size() + "</h3>");
            writer.write("<h3>Игра запущена: " + r.isGameStarted + "</h3>");
        }
        writer.flush();
        writer.close();
        return file;
    }

    public File createFile(String address){
        if (address.equals("")){
            typeOfFile = "html";
            file = new File("main/index.html");
            return file;
        }
        file = new File(address);
        if (!file.exists()){
            typeOfFile = "html";
            error = true;
            file = new File("error/index.html");
            return file;
        }
        if (file.isDirectory() && !address.equals("theking")){
            typeOfFile = "html";
            StringBuilder builder = new StringBuilder();
            builder.append(address).append("/index.html");
            file = new File(builder.toString());
            return file;
        }
        typeOfFile = address.split("\\.")[1];
        return file;
    }

    public File createFile(){
        return createFile(address);
    }

    public String getType(){
        return typeOfFile;
    }

    public boolean isError(){
        return error;
    }
}