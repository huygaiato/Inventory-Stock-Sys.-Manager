package model;

/**
 *
 * @author Huy G To
 * The OutSourced class inherits from Part and carries on all the methods from Part, but also introduces a new field "companyName", */
public class OutSoured extends Part {

    /**
     * string containing the company name
     */
    private String companyName;


    /**
     * constructor for outsoruced with the same parameters as part including the company name
     */
    public OutSoured(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }


    /**
     * getter for company name, returns company name
     */
    public String getCompanyName() {

        return companyName;
    }

    /**
     * setter for company name, sets the company name for the intended object
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}
