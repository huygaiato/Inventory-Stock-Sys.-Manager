package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author Huy G To
 * The product class contains an observable list that has the associated parts that make up a product, along with methods and fields to modify a product
 * such as getting any paremeter of a product, updating a product, removing a product. */
public class Product {

    /**
     * observable containing the associated parts that are related to a product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     *int containing the id for an associated part
     */
    private int id;
    /**
     * string containing the name for an associated part
     */
    private String name;
    /**
     * double containing the price for an associated part
     */
    private double price;
    /**
     * int containing the stock for an associated part
     */
    private int stock;
    /**
     * int containing the minimum value for an associated part
     */
    private int min;
    /**
     * int containing the maximum value for an associated part
     */
    private int max;

    /**
     * constructor for product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        int whatever = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {

        this.max = max;
    }

    public void addAssociatedPart(ObservableList<Part> part){

        this.associatedParts.addAll(part);
    }
    public boolean deleteAssociatedPart(Part selectAssociatedPart){

        return associatedParts.remove(selectAssociatedPart);
    }
    public ObservableList<Part> getAllAssociatedParts(){

        return associatedParts;
    }
}