package google.bhavesh.csi.PojoData;

/**
 * Created by Archana on 3/14/2016.
 */
public class Gallery {
    private  String image;
    private String name;
    public Gallery()
    {}
   Gallery(String image ,String name){
           this.name=name;

    this.image=image;
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


}
