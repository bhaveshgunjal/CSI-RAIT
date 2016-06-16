package google.bhavesh.csi.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import google.bhavesh.csi.URLendpoints.Keys;
import google.bhavesh.csi.VolleyConnections.VolleySingleton;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGallery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGallery extends Fragment {

    RecyclerView mreRecyclerView;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader mImageLoader;
    Gallery gallery;
    ArrayList<Gallery> galleryArrayList = new ArrayList<>();
    private GalleryAdapter mGalleryAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentGallery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGallery.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGallery newInstance(String param1, String param2) {
        FragmentGallery fragment = new FragmentGallery();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        volleySingleton = VolleySingleton.getInstance();
        mImageLoader = volleySingleton.getImageLoader();

        requestQueue = volleySingleton.getRequestQueue();

        //RequestQueue mRequestQueue=AppController.getInstance().getRequestQueue();
        String tag_json_obj = "json_obj_req2";

        String url2 = "https://api.myjson.com/bins/1q56r";//gallery events

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url2, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("successful", response.toString());
                        // Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_SHORT).show();
                        galleryArrayList = parseJsonResponse(response);
                        mGalleryAdapter.setListEvents(galleryArrayList);
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
        //AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        requestQueue.add(jsonObjReq);


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

                    String urlThumbnail = currentContact.getString("thumbnail");
//

                    data.append(urlThumbnail + "" + "\n");
                    Log.d(data.toString(), "");
                    gallery = new Gallery();
                    gallery.setImage(urlThumbnail);

                    galleryArrayList.add(gallery);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return galleryArrayList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_gallery, container, false);
        mreRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        mreRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        mGalleryAdapter = new GalleryAdapter(getActivity());
    //    mreRecyclerView.smoothScrollBy(0, 20);
        // mreRecyclerView.smoothScrollToPosition(0);

       // mreRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator()));

        mreRecyclerView.setAdapter(mGalleryAdapter);

        mGalleryAdapter.SetOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialogtechfest);
                final ImageView imageView = (ImageView) dialog.findViewById(R.id.image);

                Gallery techmate1 = galleryArrayList.get(position);


            }


        });
        return view;
    }
}