package google.bhavesh.csi.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import google.bhavesh.csi.Adapters.TechKnowadapter;
import google.bhavesh.csi.PojoData.Gallery;
import google.bhavesh.csi.PojoData.TechKnow;
import google.bhavesh.csi.R;
import google.bhavesh.csi.VolleyConnections.VolleySingleton;
import google.bhavesh.csi.childFragments.FragmentTechknow;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWhats extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    RecyclerView.LayoutManager mlayoutLayoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView mreRecyclerView;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader mImageLoader;
    private GalleryAdapter mTechKnowAdapter;
    Gallery techknow;
    ArrayList<Gallery> techKnowArrayList = new ArrayList<>();



    public FragmentWhats() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTechknow.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWhats newInstance(String param1, String param2) {
        FragmentWhats fragment = new FragmentWhats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
        volleySingleton = VolleySingleton.getInstance();
        mImageLoader = volleySingleton.getImageLoader();

        requestQueue = volleySingleton.getRequestQueue();

        //RequestQueue mRequestQueue=AppController.getInstance().getRequestQueue();
        String tag_json_obj = "json_obj_req2";

        String url2 = "https://api.myjson.com/bins/48iff";//techknow events

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
                        techKnowArrayList = parseJsonResponse(response);
                        mTechKnowAdapter.setListEvents(techKnowArrayList);
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
        // AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        requestQueue.add(jsonObjReq);


    }
    public ArrayList<Gallery> parseJsonResponse(JSONObject response) {
        if (response == null || response.length() == 0) {
            return null;
        }
        try {
            StringBuilder data = new StringBuilder();
            if (response.has("hotnews")) {
                JSONArray arrayknow = response.getJSONArray("hotnews");
                for (int i = 0; i < arrayknow.length(); i++) {
                    JSONObject currentTechknow = arrayknow.getJSONObject(i);
                    //  String image=currentContact.getString(Keys.EndpointContacts.image);
                    String name = currentTechknow.getString("ename");
                 //  String description = currentTechknow.getString("escription");
                    String urlThumbnail = currentTechknow.getString("humbnail");

//                long phoneMobile=0;
//                if(phoneNumbers.has(Keys.EndpointContacts.phone_no))
//                {
//                   phoneMobile=phoneNumbers.getLong(Keys.EndpointContacts.mobile);
//                }
//                else{
//
//
//                }


                    data.append(name + urlThumbnail + "" + "\n");
                    Log.d(data.toString(), "pohochla");
                    techknow= new Gallery();
                    //  techmate.setImage(image);
                    techknow.setName(name);


                    techknow.setImage(urlThumbnail);

                    techKnowArrayList.add(techknow);
                }

            }
            //  Toast.makeText(getActivity(),techmateArrayList.toString(),Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return techKnowArrayList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_techmate, container, false);
        mreRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mlayoutLayoutManager = new LinearLayoutManager(getActivity());
        mreRecyclerView.setLayoutManager(mlayoutLayoutManager);
        mTechKnowAdapter = new GalleryAdapter(getActivity());
        // mreRecyclerView.smoothScrollBy(0,20);
        mreRecyclerView.smoothScrollToPosition(0);

        mreRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator()));

        mreRecyclerView.setAdapter(mTechKnowAdapter);

        mTechKnowAdapter.SetOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialogtechfest);
                //   dialog.getWindow().setBackgroundDrawableResource(R.color.cardview_light_background);

                Gallery currentEvent = techKnowArrayList.get(position);
                final ImageView iv = (ImageView) dialog.findViewById(R.id.image);
                final TextView name = (TextView) dialog.findViewById(R.id.name);

                String urlThumbnail=currentEvent.getImage();
                switch (position) {

                    case 0:


                        FragmentManager mFragmentManager=getFragmentManager();
                        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                        xfragmentTransaction.addToBackStack("");
                        xfragmentTransaction.replace(R.id.containerView,new FragmentTechknow()).commit();


                        break;
                    case 1:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());


                        if(urlThumbnail!=null)
                        {
                            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                                @Override
                                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                    iv.setImageBitmap(response.getBitmap());
                                }

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                        }

                        break;

                    case 2:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());


                        if(urlThumbnail!=null)
                        {
                            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                                @Override
                                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                    iv.setImageBitmap(response.getBitmap());
                                }

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                        }

                        break;


                    default:
                        break;
                }





            }
        });

        return view;

    }

}
