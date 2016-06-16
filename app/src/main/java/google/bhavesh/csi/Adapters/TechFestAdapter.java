package google.bhavesh.csi.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import google.bhavesh.csi.Activities.MainActivity;
import google.bhavesh.csi.PojoData.TechKnow;
import google.bhavesh.csi.PojoData.Techmate;
import google.bhavesh.csi.R;
import google.bhavesh.csi.VolleyConnections.VolleySingleton;

/**
 * Created by Archana on 3/8/2016.
 */
public class TechFestAdapter extends RecyclerView.Adapter<TechFestAdapter.ViewHolderTechFest> {
    public ArrayList<Techmate> listEvents = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    OnItemClickListener mItemClickListener;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader1;

    public TechFestAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader1 = volleySingleton.getImageLoader();

    }

    @Override
    public ViewHolderTechFest onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.custom_techfest_row, parent, false);
        ViewHolderTechFest viewHolderTechFest = new ViewHolderTechFest(v);


        return viewHolderTechFest;
    }

    public void setListEvents(ArrayList<Techmate> listEvents) {
        this.listEvents = listEvents;
        notifyItemRangeChanged(0, listEvents.size());
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolderTechFest holder, int position) {
        int lastPosition = -1;

        Techmate currentEvent = listEvents.get(position);
        holder.name.setText(currentEvent.getName());
        holder.description.setText(currentEvent.getDescription());
        String urlThumbnail = currentEvent.getImage();
        if (urlThumbnail != null) {
            imageLoader1.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.image.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }


//        Animation animation;
//        animation = AnimationUtils.loadAnimation(,
//                (position > lastPosition) ? R.anim.up_from_bottom
//                        : R.anim.down_from_top);
//        holder.itemView.startAnimation(animation);
//        lastPosition = position;
//
//


        // holder.phoneno.setText((int) currentEvent.getPhoneno());


    }


    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    class ViewHolderTechFest extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;
        private TextView name;
        private TextView description;


        public ViewHolderTechFest(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.id);
            image = (ImageView) itemView.findViewById(R.id.thumbnail);


            // phoneno= (TextView) itemView.findViewById(R.id.phone);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());

            }

        }
    }
}
