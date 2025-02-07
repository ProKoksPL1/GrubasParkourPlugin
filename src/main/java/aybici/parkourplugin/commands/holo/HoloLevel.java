package aybici.parkourplugin.commands.holo;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.users.User;
import aybici.parkourplugin.users.UserFile;
import aybici.parkourplugin.users.UserManager;
import aybici.parkourplugin.utils.ChatUtil;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.stream.Collectors;

public class HoloLevel {

    public static void placeHoloExp(Location location) {
        Map<String, Integer> playersMap = new HashMap<>();
        FileConfiguration configFile = UserFile.levelFile.getData();
        for (String line : configFile.getConfigurationSection("Users").getKeys(false)) {
            User user = UserManager.getUserByName(line);
            int number = user.getLevel();
            playersMap.put(line, number);
        }
        Map<String, Integer> posortowanaMapaGraczy = playersMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer numberOne, Integer numberTwo) {
                        return -(numberOne.compareTo(numberTwo));
                    }
                })).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Hologram hologram = HolographicDisplaysAPI.get(ParkourPlugin.getInstance()).createHologram(location);
        hologram.getLines().appendText(ChatUtil.fixColor("&b=-= &a&lTop 10 Level &b=-="));
        List<String> listLine = new ArrayList<>();
        int index = 0;
        try {
            for (int i = 0; i < 11; i++) {
                Integer punkty = (Integer) posortowanaMapaGraczy.values().toArray()[i];
                String playerLine = (String) posortowanaMapaGraczy.keySet().toArray()[i];
                if (index == 0) {
                    listLine.add("§b" + playerLine + " §f- §a" + punkty + "lvl");
                    index++;
                    continue;
                }
                index++;
                listLine.add("§b" + playerLine + " §f- §a" + punkty + "lvl");
            }
        } catch (Exception ex) {
            index++;
            listLine.add(ChatUtil.fixColor("&cBrak danych"));
        } finally {
            int pozostalo = 11 - index;
            index++;
            for (int i = 0; i < pozostalo - 1; i++) {
                int to = index++;
                listLine.add(ChatUtil.fixColor("&cBrak danych"));
            }
        }

        for (int i = 0; i < listLine.size(); i++) {
            hologram.getLines().appendText(ChatUtil.fixColor(listLine.get(i)));
        }

        ParkourPlugin.hologramLevel = hologram;
    }
}
