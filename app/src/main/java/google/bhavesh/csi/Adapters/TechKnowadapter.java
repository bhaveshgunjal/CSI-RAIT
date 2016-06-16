package google.bhavesh.csi.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import google.bhavesh.csi.PojoData.TechKnow;
import google.bhavesh.csi.R;
import google.bhavesh.csi.VolleyConnections.VolleySingleton;

/**
 * Created by Archana on 3/11/2016.
 */
public class TechKnowadapter extends RecyclerView.Adapter<TechKnowadapter.ViewHolderTechKnow> {
    public ArrayList<TechKnow> listEvents = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    OnItemClickListener mItemClickListener;
    private VolleySingleton volleySingleton;
     ImageLoader imageLoader;

    public TechKnowadapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();

    }

    @Override
    public ViewHolderTechKnow onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= mLayoutInflater.inflate(R.layout.custom_techfest_row,parent,false);
        ViewHolderTechKnow viewHolderTechKnow=new ViewHolderTechKnow(v);

        return viewHolderTechKnow;
    }

    @Override
    public void onBindViewHolder(final ViewHolderTechKnow holder, int position) {
        int lastPosition = -1;

        TechKnow currentEvent = listEvents.get(position);
        holder.tname.setText(currentEvent.getName());
        holder.tdescription.setText(currentEvent.getDescription());
        String urlThumbnail = currentEvent.getImage();
        if (urlThumbnail != null) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.image.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public void setListEvents(ArrayList<TechKnow> listEvents) {
        this.listEvents = listEvents;
        notifyItemRangeChanged(0, listEvents.size());
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    class ViewHolderTechKnow extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final  ImageView image;
        private TextView tname;
        private TextView tdescription;



        public ViewHolderTechKnow(View itemView) {
            super(itemView);
            tname= (TextView) itemView.findViewById(R.id.name);
            tdescription= (TextView) itemView.findViewById(R.id.id);
            image= (ImageView) itemView.findViewById(R.id.thumbnail);


            // phoneno= (TextView) itemView.findViewById(R.id.phone);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v){
            if(mItemClickListener!=null)
            {
                mItemClickListener.onItemClick(v,getPosition());

            }

        }
    }
}
