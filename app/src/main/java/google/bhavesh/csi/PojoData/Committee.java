package google.bhavesh.csi.PojoData;

/**
 * Created by Archana on 3/15/2016.
 */
public class Committee {
    public Committee()
    {}

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

    public String getfb() {
        return fb;
    }

    public void setfb(String fb) {
        this.fb = fb;
    }



    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }



    private  String image;
    private  String name;
    private String description;
    private String contact;
    private  String fb;




    public Committee(String image, String name,  String description, String contact, String fb)
    {
        this.image=image;
        this.name=name;
        this.description=description;
        this.contact=contact;
        this.fb=fb;

    }
    @Override
    public String toString() {
        return "\nID: " + image +
                "\nName " +name +
                "\ndescription"+description+
                "\ncontact"+contact+
                "\n";

    }

}
