package google.bhavesh.csi.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import google.bhavesh.csi.Adapters.GalleryAdapter;
import google.bhavesh.csi.Adapters.TechFestAdapter;
import google.bhavesh.csi.PojoData.Gallery;
import google.bhavesh.csi.PojoData.Techmate;
import google.bhavesh.csi.R;
import google.bhavesh.csi.SpacesItemDecoration;
import google.bhavesh.csi.URLendpoints.Keys;
import google.bhavesh.csi.VolleyConnections.AppController;
import google.bhavesh.csi.VolleyConnections.VolleySingleton;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class GalleryActivity extends AppCompatActivity {
    RecyclerView mreRecyclerView;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader mImageLoader;
    private GalleryAdapter mGalleryAdapter;
    Context context;
    Dialog dialog;
    Gallery gallery;
    LinearLayoutManager mlayoutLayoutManager;
    ArrayList<Gallery> techmateArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        volleySingleton = VolleySingleton.getInstance();
        mImageLoader = volleySingleton.getImageLoader();

        requestQueue = volleySingleton.getRequestQueue();
        dialog=new Dialog(this);

        //RequestQueue mRequestQueue=AppController.getInstance().getRequestQueue();
        String tag_json_obj = "json_obj_req";

        String url = "https://api.myjson.com/bins/3nzrr";//techmate events

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...please wait");
        pDialog.show();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("successful", response.toString());
                        // Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_SHORT).show();
                        techmateArrayList = parseJsonResponse(response);
                        mGalleryAdapter.setListEvents(techmateArrayList);
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("error", "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        mreRecyclerView = (RecyclerView) findViewById(R.id.recycler);

//        mreRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mlayoutLayoutManager=new LinearLayoutManager(this);
//        mreRecyclerView.setLayoutManager(mlayoutLayoutManager);

      //  SpacesItemDecoration spc=new SpacesItemDecoration(16);
//        mreRecyclerView.addItemDecoration(spc);
        mGalleryAdapter = new GalleryAdapter(this);
//        mreRecyclerView.smoothScrollBy(0, 20);
        context=getApplicationContext();
        // mreRecyclerView.smoothScrollToPosition(0);

     //   mreRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator()));

        mreRecyclerView.setAdapter(mGalleryAdapter);

        mGalleryAdapter.SetOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                dialog.setContentView(R.layout.dialogtechfest);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.image);
                Gallery techmate1 = techmateArrayList.get(position);
                switch (position) {
                    case 0:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        imageView.setImageResource(Integer.parseInt(techmate1.getImage()));

                        break;
                    case 1:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;

                    case 2:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                                   case 3:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                       case 4:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;

                    case 5:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;

                    case 6:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                                          case 7:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;

                    case 8:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;

                    case 9:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                                          case 10:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                                          case 11:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;

                    case 12:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;

                    default:
                        break;
                }
                dialog.show();
            }

//

            //   dialog.getWindow().setBackgroundDrawableResource(R.color.cardview_light_background);


        });



    }
    public ArrayList<Gallery> parseJsonResponse(JSONObject response) {
        if (response == null || response.length() == 0) {
            return null;
        }
        try {
            StringBuilder data = new StringBuilder();
            if (response.has("gallery")) {
                JSONArray arrayContacts = response.getJSONArray("gallery");
                for (int i = 0; i < arrayContacts.length(); i++) {
                    JSONObject currentContact = arrayContacts.getJSONObject(i);

                    String urlThumbnail = currentContact.getString("image");
//

                    data.append(urlThumbnail + "" + "\n");
                    Log.d(data.toString(), "");
                    gallery= new Gallery();

                    gallery.setImage(urlThumbnail);

                    techmateArrayList.add(gallery);
                }

            }
            //  Toast.makeText(getActivity(),techmateArrayList.toString(),Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return techmateArrayList;
    }

}
