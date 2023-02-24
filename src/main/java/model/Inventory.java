package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author Huy G To
 * The Inventory class is where Parts and Products are stored as ObservableLists and treated as Arrays,
 * alsoc ontains the methods to update parts/products, remove parts/products, and lookup parts/products. */
public class Inventory {

    /**
     * Observable list for allParts which contains the array of all the parts in inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Observable list for products which contains the array of all the products in inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Adds part to inventory
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }
    /**
     * Adds product to inventory
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }
    /**
     * lookup the part in inventory by the partId
     */
    public static void lookupPart(int partID){

    }
    /**
     * lookup the product in inventory by productId
     */
    public static void lookupProduct(int productID){

    }
    /**
     * lookup part by name
     */
    public static ObservableList<Part> lookupPart(String partName){

        // Return part name
        return null;

    }

    /**
     * look up product by name
     */
    public static ObservableList<Product> lookupProduct(String productName){

        // Return product name
        return null;

    }
    /**
     * updates part by its index
     */
    public static void updatePart(int index, Part selectedPart){
        //Updates Part
        allParts.set(index, selectedPart);
    }
    /**
     * updates product by its index
     */
    public static void updateProduct(int index, Product newProduct){
        //Updates Part
    }

    /**
     * deletes part based on the selection
     */
    public static boolean deletePart(Part selectedPart){

        return allParts.remove(selectedPart);
    }
    /**
     * deletes product based on selection
     */
    public static boolean deleteProduct(Product selectedProduct){
        //deletes product
        return false;
    }
    /**
     * gets all the parts from the observable list
     */
    public static ObservableList<Part> getAllParts() {

        return  allParts;
    }

    /**
     * gets all product from the observable list
     */
    public static ObservableList<Product> getAllProducts() {

        return  allProducts;
    }
}
