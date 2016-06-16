package google.bhavesh.csi.childFragments;


import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;
import android.support.v7.widget.LinearLayoutManager.*;

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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import google.bhavesh.csi.Adapters.TechFestAdapter;
import google.bhavesh.csi.Adapters.TechKnowadapter;
import google.bhavesh.csi.Databases.DatabaseHandler;
import google.bhavesh.csi.DialogFragments.TechFestDialogFragment;
import google.bhavesh.csi.PojoData.TechFestDialog;
import google.bhavesh.csi.PojoData.TechKnow;
import google.bhavesh.csi.PojoData.Techmate;
import google.bhavesh.csi.PojoData.Workshop;
import google.bhavesh.csi.R;
import google.bhavesh.csi.URLendpoints.Keys;
import google.bhavesh.csi.URLendpoints.URLendpointclass;
import google.bhavesh.csi.VolleyConnections.AppController;
import google.bhavesh.csi.VolleyConnections.VolleySingleton;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTechmate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTechmate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView mreRecyclerView;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader mImageLoader;
    private TechFestAdapter mTechFestAdapter;
    Techmate techmate;
    ArrayList<Techmate> techmateArrayList = new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView.LayoutManager mlayoutLayoutManager;
    DatabaseHandler db;


    public FragmentTechmate() {

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
    public static FragmentTechmate newInstance(String param1, String param2) {
        FragmentTechmate fragment = new FragmentTechmate();
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
        String url2="https://api.myjson.com/bins/59f1z";

//        String url2 = "https://api.myjson.com/bins/spk7";//techmate events

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
                        techmateArrayList = parseJsonResponse(response);
                        mTechFestAdapter.setListEvents(techmateArrayList);
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

    public ArrayList<Techmate> parseJsonResponse(JSONObject response) {
        if (response == null || response.length() == 0) {
            return null;
        }
        try {
            StringBuilder data = new StringBuilder();
            if (response.has("techKnowevents")) {
                JSONArray arrayContacts = response.getJSONArray("techKnowevents");
                for (int i = 0; i < arrayContacts.length(); i++) {
                    JSONObject currentContact = arrayContacts.getJSONObject(i);

                    String name = currentContact.getString("ename");
                    String description = currentContact.getString("escription");
                    String urlThumbnail = currentContact.getString("humbnail");
                    String brief=currentContact.getString("brief");
                    String fees=currentContact.getString("fees");
                    String cashprice=currentContact.getString("Cash prize");
//
//

                    data.append(name + description + urlThumbnail + brief + fees + cashprice + "" + "\n");
                    Log.d(data.toString(), "");
                    techmate = new Techmate();
                    techmate.setName(name);
                    techmate.setDescription(description);
                    techmate.setImage(urlThumbnail);
                    techmate.setBrief(brief);
                    techmate.setFees(fees);
                    techmate.setCashprice(cashprice);


                    techmateArrayList.add(techmate);
                }

            }


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
        mTechFestAdapter = new TechFestAdapter(getActivity());
         mreRecyclerView.smoothScrollBy(0, 20);
       // mreRecyclerView.smoothScrollToPosition(0);

        mreRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator()));

mreRecyclerView.setAdapter(mTechFestAdapter);

        mTechFestAdapter.SetOnItemClickListener(new TechFestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialogtechfest);
                 final ImageView imageView= (ImageView) dialog.findViewById(R.id.image);
                TextView tv = (TextView) dialog.findViewById(R.id.name);
                TextView tv1 = (TextView) dialog.findViewById(R.id.brief);
                TextView tv2 = (TextView) dialog.findViewById(R.id.feestag);
                TextView tv3 = (TextView) dialog.findViewById(R.id.fees);

                Techmate techmate1=techmateArrayList.get(position);
                String urlThumbnail=techmate1.getImage();
                switch(position){
                    case 0:
                        dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;

                        tv.setText(techmate1.getName());

                        tv2.setText(techmate1.getBrief());

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
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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

                    case 2:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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

                    case 3:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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

                    case 4:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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

                    case 5:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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

                    case 6:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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

                    case 7:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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

                    case 8:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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

                    case 9:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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

                    case 10:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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

                    case 11:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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

                    case 12:  dialog.getWindow().getAttributes().windowAnimations =

                            R.style.dialog_animation;
                        tv.setText(techmate1.getName());
                        tv1.setText(techmate1.getDescription());
                        tv2.setText(techmate1.getBrief());

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
