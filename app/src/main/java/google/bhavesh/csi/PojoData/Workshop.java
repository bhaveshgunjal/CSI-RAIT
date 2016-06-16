package google.bhavesh.csi.PojoData;

/**
 * Created by Archana on 3/11/2016.
 */
public class Workshop {
    int _id;
    String _name;
    String description;
    String image;
    String date;

    // Empty constructor
    // constructor
    public Workshop(int id, String name, String description,String image,String date){
        this._id = id;
        this._name = name;
        this.description=description;
        this.image=image;
        this.date=date;
    }
    public Workshop()
    {}

    // constructor
    public Workshop(String name, String description){
        this._name = name;
        this.description = description;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getDescription(){
        return this.description;
    }

    // setting phone number
    public void setDescription(String description){
        this.description = description;
    }

   public String getImage()
   {
       return this.image;
   }
    public void setImage(String image) {
        this.image = image;
    }

    public String getDate(){ return this.date;}
    public void setDate(String date){
        this.date=date;
    }
}
