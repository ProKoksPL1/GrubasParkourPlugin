package aybici.parkourplugin;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UUIDList {
    private final String directory = "dataBase"+File.separator + "uuidList.txt";
    private final String directory2 = "dataBase"+File.separator +"uuidHashset.txt";
    private final String directory3 = "dataBase"+File.separator +"playerNames.txt";
    private List<UUID> identifiers = new ArrayList<UUID>();
    private HashMap<UUID,String> playersHashmap = new HashMap<UUID,String>();

    public void addIdentifier(UUID identifier){
        identifiers.add(identifier);
        saveIdentifierInfile(identifier);
    }
    public boolean contains(UUID identifier){
        return identifiers.contains(identifier);
    }
    public short getShortIdentifier(UUID identifier){
        return (short) (identifiers.indexOf(identifier) - 32767);
    }
    public UUID getUUIDFromShort(short index){
        return identifiers.get(index + 32767);
    }
    public String getNameFromUUID(UUID uuid){
        if (Bukkit.getOfflinePlayer(uuid).getName() != null){
            return Bukkit.getOfflinePlayer(uuid).getName();
        }

        if (playersHashmap.containsKey(uuid)){
            return playersHashmap.get(uuid);
        }
        else return uuid.toString().substring(0,10);
    }
    public int getLength(){
        return identifiers.size();
    }

    public void loadPlayerNames(){
        if (!new File(directory3).exists()) return;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(directory3));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String currentLine = null;
        boolean lineExists = true;
        while (lineExists) {
            try {
                currentLine = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (currentLine == null) lineExists = false;
            else {
                UUID offlineUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + currentLine).getBytes());
                playersHashmap.put(offlineUUID, currentLine);
                //System.out.println(currentLine + "," + offlineUUID);
            }
        }
    }

    public void saveIdentifierInfile(UUID identifier){
        if (!new File(directory).exists()) FileCreator.createFile(directory);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(directory, true));
            writer.write(identifier.getMostSignificantBits() + "," + identifier.getLeastSignificantBits() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void savePlayerUUIDAndNameInFile(OfflinePlayer player){
        if (!new File(directory2).exists()) FileCreator.createFile(directory2);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(directory2, true));
            writer.write(player.getName() +", " + player.getUniqueId() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadList(){
        if (!new File(directory).exists()) return;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(directory));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String currentLine = null;
        boolean lineExists = true;
        while (lineExists) {
            try {
                currentLine = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (currentLine == null) lineExists = false;
            else {
                long mostSignBits = Long.parseLong(currentLine.substring(0, currentLine.indexOf(',')));
                long leastSignBits = Long.parseLong(currentLine.substring(currentLine.indexOf(',') + 1));

                identifiers.add(new UUID(mostSignBits, leastSignBits));
            }
        }
    }
}
