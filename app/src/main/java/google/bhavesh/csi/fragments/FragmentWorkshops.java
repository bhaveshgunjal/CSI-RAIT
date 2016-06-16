package google.bhavesh.csi.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import google.bhavesh.csi.Adapters.TechtalkAdapter;
import google.bhavesh.csi.Adapters.WorkshopAdapter;

import google.bhavesh.csi.Databases.DatabaseHandler;
import google.bhavesh.csi.PojoData.Techtalk;
import google.bhavesh.csi.PojoData.Workshop;
import google.bhavesh.csi.R;
import google.bhavesh.csi.URLendpoints.Keys;
import google.bhavesh.csi.URLendpoints.URLendpointclass;
import google.bhavesh.csi.VolleyConnections.AppController;
import google.bhavesh.csi.VolleyConnections.VolleySingleton;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWorkshops#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWorkshops extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView mreRecyclerView;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader mImageLoader;
    private WorkshopAdapter mTechtalkAdapter;
    Workshop techmate;
    ArrayList<Workshop> techmateArrayList = new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView.LayoutManager mlayoutLayoutManager;
    DatabaseHandler db;


    public FragmentWorkshops() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTechmate.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWorkshops newInstance(String param1, String param2) {
        FragmentWorkshops fragment = new FragmentWorkshops();
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
        String tag_json_obj = "json_obj_req";

        String url = "https://api.myjson.com/bins/4dxkf";//workshops

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
                        mTechtalkAdapter.setListWorkshops(techmateArrayList);
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
       // requestQueue.add(jsonObjReq);
        //  db = new DatabaseHandler(getActivity());

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
//        Log.d("Insert: ", "Inserting ..");
//        db.addWorkshop(new Workshop("BHavesh", "9100000000"));
//        db.addWorkshop(new Workshop("Srinivas", "9199999999"));
//        db.addWorkshop(new Workshop("Tommy", "9522222222"));
//        db.addWorkshop(new Workshop("Karthik", "9533333333"));
//
//        Log.d("Reading: ", "Reading all contacts..");
//



    }

    public ArrayList<Workshop> parseJsonResponse(JSONObject response) {
        if (response == null || response.length() == 0) {
            return null;
        }
        try {
            StringBuilder data = new StringBuilder();
            if (response.has("workshops")) {
                JSONArray arrayContacts = response.getJSONArray("workshops");
                for (int i = 0; i < arrayContacts.length(); i++) {
                    JSONObject currentContact = arrayContacts.getJSONObject(i);

                    String name = currentContact.getString("name");
                    String date = currentContact.getString("date");
                    String description = currentContact.getString("description");

                    String thumbnail=currentContact.getString("thumbnail");
//

                    data.append(name + date + description + thumbnail + "" + "\n");
                    Log.d(data.toString(), "");
                    techmate= new Workshop();
                    techmate.setName(name);

                    techmate.setImage(thumbnail);
                    techmate.setDate(date);
                    techmate.setDescription(description);

                    techmateArrayList.add(techmate);
                }

            }
            //  Toast.makeText(getActivity(),techmateArrayList.toString(),Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return techmateArrayList;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_techmate, container, false);
        mreRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mlayoutLayoutManager = new LinearLayoutManager(getActivity());
        mreRecyclerView.setLayoutManager(mlayoutLayoutManager);
        mTechtalkAdapter = new WorkshopAdapter(getActivity());
        mreRecyclerView.smoothScrollBy(0, 20);
        // mreRecyclerView.smoothScrollToPosition(0);

        mreRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator()));

        mreRecyclerView.setAdapter(mTechtalkAdapter);

        mTechtalkAdapter.SetOnItemClickListener(new WorkshopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialogtechfest);
                final ImageView imageView= (ImageView) dialog.findViewById(R.id.image);
                TextView tv = (TextView) dialog.findViewById(R.id.title);
                TextView tv1 = (TextView) dialog.findViewById(R.id.name);
                Workshop techmate1=techmateArrayList.get(position);
                String urlThumbnail=techmate1.getImage();
                switch(position){
                    case 0:
                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        if(urlThumbnail!=null)
                        {
                            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                                @Override
                                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                    imageView.setImageBitmap(response.getBitmap());
                                }

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                        }


                        break;
                    case 1:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        if(urlThumbnail!=null)
                        {
                            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                                @Override
                                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                    imageView.setImageBitmap(response.getBitmap());
                                }

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                        }
break;

                    default:break;
                }
                dialog.show();
            }

//

            //   dialog.getWindow().setBackgroundDrawableResource(R.color.cardview_light_background);


        });

        return view;
    }


    public static String getRequestURL(int limit) {
        return URLendpointclass.KEY_CONTACTS;

    }



}
