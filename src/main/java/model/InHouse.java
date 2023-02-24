package model;
/**
 *
 * @author Huy G To
 * The InHouse class inherits from Part and carries on all the methods from Part, but also introduces a new field "machineID", */
public class InHouse extends Part {

    /**
     * unique filed for InHouse which is the machineId of an InHousePart
     */
    private int machineId;

    /**
     * Declare Constructors of in house w/ super from parent class Part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {


        super(id, name, price, stock, min, max);
        this.machineId = machineId;

    }

    /**
     * getter of machineID
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Setter of machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
