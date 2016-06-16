package google.bhavesh.csi.PojoData;

/**
 * Created by Archana on 3/14/2016.
 */
public class Techtalk {
    public Techtalk()
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
    public void setDate(String date)
    {
        this.date= date;
    }
    public String getDate()
    {
        return date;
    }
    public void setDescription(String description)
    {
        this.description= description;
    }

    private  String image;
    private  String name;
    private String description;
    private String date;




    public Techtalk(String image, String name,  String description, String date)
    {
        this.image=image;
        this.name=name;
        this.description=description;
        this.date=date;

    }
    @Override
    public String toString() {
        return "\nID: " + image +
                "\nName " +name +
                "\ndescription"+description+
                "\ndate"+date+
                "\n";

    }

}
