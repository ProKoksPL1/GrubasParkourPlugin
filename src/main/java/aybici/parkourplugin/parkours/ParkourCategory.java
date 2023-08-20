package aybici.parkourplugin.parkours;

import org.bukkit.Material;

public class ParkourCategory {

    private String name;
    private String displayName;
    private Material categoryMaterial;

    private int bookPosition;
    private int xpMultiplier;

    public String getName(){
        return name;
    }

    public String getDisplayName(){return displayName;}
    public int getXpMultiplier(){
        return xpMultiplier;
    }

    public Material getCategoryMaterial(){
        return categoryMaterial;
    }

    public int getBookPosition(){
        return bookPosition;
    }

    public ParkourCategory(String name, String displayName, int xpMultiplier, Material categoryMaterial, int bookPosition){
        this.name = name;
        this.displayName = displayName;
        this.xpMultiplier = xpMultiplier;
        this.categoryMaterial = categoryMaterial;
        this.bookPosition = bookPosition;
    }
}
