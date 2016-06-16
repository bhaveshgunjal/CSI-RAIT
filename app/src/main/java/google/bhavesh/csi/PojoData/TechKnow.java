package google.bhavesh.csi.PojoData;

/**
 * Created by Archana on 3/11/2016.
 */
public class TechKnow {
    public TechKnow()
    {

    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description= description;
    }






    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief =brief;
    }
    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees=fees;
    }
    public String getCashprice() {
        return cashprice;
    }

    public void setCashprice(String cashprice) {
        this.cashprice= cashprice;
    }

    private  String image;
    private String brief;
    private String fees;
    private String cashprice;
    private  String name;
    private String description;




    public TechKnow(String image, String name,  String description, String brief, String fees, String cashprice)
    {
        this.image=image;
        this.name=name;
        this.description=description;
        this.brief=brief;
        this.fees=fees;
        this.cashprice=cashprice;

    }
    @Override
    public String toString() {
        return "\nID: " + image +
                "\nName " +name +
                "\ndescription"+description+
                "\nbrief"+brief+
                 "\nfees"+fees+
                "\ncashprice"+cashprice+
                "\n";

    }

}
