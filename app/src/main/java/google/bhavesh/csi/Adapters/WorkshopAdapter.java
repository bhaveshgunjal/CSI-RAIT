package google.bhavesh.csi.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import google.bhavesh.csi.PojoData.Techmate;
import google.bhavesh.csi.PojoData.Workshop;
import google.bhavesh.csi.R;
import google.bhavesh.csi.VolleyConnections.VolleySingleton;

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.WorkshopViewHolder> {
    LayoutInflater mLayoutInflater;

    private VolleySingleton volleySingleton;
    OnItemClickListener mItemClickListener;
    private ImageLoader imageLoader;
    public ArrayList<Workshop> listEvents=new ArrayList<>();
    public WorkshopAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);

        volleySingleton= VolleySingleton.getInstance();
        imageLoader=volleySingleton.getImageLoader();
    }
        @Override
    public WorkshopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= mLayoutInflater.inflate(R.layout.custom_workshop_row,parent,false);
        WorkshopViewHolder workshopViewHolder=new WorkshopViewHolder(v);
        return  workshopViewHolder;
    }
    public void setListWorkshops(ArrayList<Workshop> listEvents)
    {
        this.listEvents=listEvents;
        notifyItemRangeChanged(0,listEvents.size());
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(final WorkshopViewHolder holder, int position) {
                 Workshop currentWorkshop=listEvents.get(position);
        holder.name.setText(currentWorkshop.getName());
        holder.date.setText(currentWorkshop.getDate());
       holder.description.setText(currentWorkshop.getDescription());
        Workshop currentEvent=listEvents.get(position);
        String urlThumbnail=currentEvent.getImage();
        if(urlThumbnail!=null)
        {
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

    public class WorkshopViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        public ImageView image;
        public TextView name;
        public TextView description;
        public TextView date;



        public WorkshopViewHolder(View itemView) {
            super(itemView);

            name= (TextView) itemView.findViewById(R.id.name);
            date= (TextView) itemView.findViewById(R.id.date);
            description= (TextView) itemView.findViewById(R.id.description);

            image= (ImageView) itemView.findViewById(R.id.thumbnail);
        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());

            }

        }


    }
}
