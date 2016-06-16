package google.bhavesh.csi.PojoData;

import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Archana on 3/9/2016.
 */
public class TechFestDialog extends Fragment {
    private String image;
    private String name;
    private String description;

    public String getImage() {
        return image;
    }

    public String setImage(String image) {
        this.image = image;
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
