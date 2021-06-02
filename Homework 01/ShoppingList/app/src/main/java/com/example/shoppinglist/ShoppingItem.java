/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 */

package com.example.shoppinglist;

/*
 * This class describes an object in the Shopping List
 */
public class ShoppingItem {
    private int itemID;
    private String category;
    private String name;
    private int cost;
    private String Description;
    private boolean purchased;

    /*
     * Constructors initializes a Shopping Item and sets the default ID value and purchased boolean value
     */
    public ShoppingItem() {
        this.itemID = -1;
        purchased = false;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCost_temp(String cost_temp) {
        double cst = Double.parseDouble(cost_temp);
        this.cost = (int) (cst * 100);
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
