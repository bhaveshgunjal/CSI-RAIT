package google.bhavesh.csi.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import google.bhavesh.csi.PojoData.Gallery;
import google.bhavesh.csi.R;
import google.bhavesh.csi.VolleyConnections.VolleySingleton;

/**
 * Created by Archana on 3/16/2016.
 */
public class MagazineAdapter extends RecyclerView.Adapter<MagazineAdapter.ViewHolderTechFest>{
    public ArrayList<Gallery> listEvents=new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    OnItemClickListener mItemClickListener;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    public MagazineAdapter(Context context)
    {mLayoutInflater=LayoutInflater.from(context);
        volleySingleton=VolleySingleton.getInstance();
        imageLoader=volleySingleton.getImageLoader();

    }
    @Override
    public ViewHolderTechFest onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= mLayoutInflater.inflate(R.layout.custom_gallery_row,parent,false);
        ViewHolderTechFest viewHolderTechFest=new ViewHolderTechFest(v);


        return viewHolderTechFest;
    }
    public void setListEvents(ArrayList<Gallery> listEvents)
    {
        this.listEvents=listEvents;
        notifyItemRangeChanged(0,listEvents.size());
    }
    public interface OnItemClickListener{public void onItemClick(View v,int position);}
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener)
    {
        this.mItemClickListener=mItemClickListener;
    }

    @Override
    public void onBindViewHolder( final ViewHolderTechFest holder, int position) {
        int lastPosition=-1;

        Gallery currentEvent=listEvents.get(position);
        holder.textView.setText(currentEvent.getName());
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
        public TextView textView;



        public ViewHolderTechFest(View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.image);
            textView= (TextView) itemView.findViewById(R.id.imagename);


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
