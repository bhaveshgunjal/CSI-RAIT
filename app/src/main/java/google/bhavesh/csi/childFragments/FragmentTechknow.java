package google.bhavesh.csi.childFragments;


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


import google.bhavesh.csi.Adapters.TechKnowadapter;
import google.bhavesh.csi.PojoData.TechKnow;
import google.bhavesh.csi.PojoData.Techmate;
import google.bhavesh.csi.R;
import google.bhavesh.csi.URLendpoints.Keys;
import google.bhavesh.csi.VolleyConnections.AppController;
import google.bhavesh.csi.VolleyConnections.VolleySingleton;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTechknow#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTechknow extends Fragment {
    private static final String ARG_PARAM2 = "param2";
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
    private TechKnowadapter mTechKnowAdapter;
    TechKnow techknow;
    ArrayList<TechKnow> techKnowArrayList = new ArrayList<>();



    public FragmentTechknow() {
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
    public static FragmentTechknow newInstance(String param1, String param2) {
        FragmentTechknow fragment = new FragmentTechknow();
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

        String url2 = "https://api.myjson.com/bins/59f1z";//techknow events

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
    public ArrayList<TechKnow> parseJsonResponse(JSONObject response) {
        if (response == null || response.length() == 0) {
            return null;
        }
        try {
            StringBuilder data = new StringBuilder();
            if (response.has("techKnowevents")) {
                JSONArray arrayknow = response.getJSONArray("techKnowevents");
                for (int i = 0; i < arrayknow.length(); i++) {
                    JSONObject currentTechknow = arrayknow.getJSONObject(i);
                    //  String image=currentContact.getString(Keys.EndpointContacts.image);
                    String name = currentTechknow.getString("ename");
                    String description = currentTechknow.getString("escription");
                    String urlThumbnail = currentTechknow.getString("humbnail");
                    String brief=currentTechknow.getString("brief");
                    String fees=currentTechknow.getString("fees");
                    String cashprice=currentTechknow.getString("Cash prize");
//                JSONObject phoneNumbers=currentContact.getJSONObject(Keys.EndpointContacts.phone_no);
//                long phoneMobile=0;
//                if(phoneNumbers.has(Keys.EndpointContacts.phone_no))
//                {
//                   phoneMobile=phoneNumbers.getLong(Keys.EndpointContacts.mobile);
//                }
//                else{
//
//
//                }


                    data.append(name + description + urlThumbnail + brief + fees + cashprice + "" + "\n");
                    Log.d(data.toString(), "pohochla");
                    techknow = new TechKnow();
                    //  techmate.setImage(image);
                    techknow.setName(name);
                    techknow.setDescription(description);
                    techknow.setImage(urlThumbnail);
                    techknow.setBrief(brief);
                    techknow.setFees(fees);
                    techknow.setCashprice(cashprice);

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
        mTechKnowAdapter = new TechKnowadapter(getActivity());
        // mreRecyclerView.smoothScrollBy(0,20);
        mreRecyclerView.smoothScrollToPosition(0);

        mreRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator()));

        mreRecyclerView.setAdapter(mTechKnowAdapter);

        mTechKnowAdapter.SetOnItemClickListener(new TechKnowadapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialogtechfest);
                //   dialog.getWindow().setBackgroundDrawableResource(R.color.cardview_light_background);

                TechKnow currentEvent = techKnowArrayList.get(position);
                final ImageView iv = (ImageView) dialog.findViewById(R.id.image);
                final TextView name = (TextView) dialog.findViewById(R.id.name);
                final TextView description= (TextView) dialog.findViewById(R.id.description);
                final TextView brief = (TextView) dialog.findViewById(R.id.brief);

                TextView cashprice = (TextView) dialog.findViewById(R.id.cashprice);
                String urlThumbnail=currentEvent.getImage();
                switch (position) {

                    case 0:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());


                        brief.setText(currentEvent.getBrief());
                        description.setText(currentEvent.getDescription());

                        cashprice.setText(currentEvent.getCashprice());

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
                    case 1:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());


                        brief.setText(currentEvent.getBrief());
                       // fees.setText(currentEvent.getFees());
                        cashprice.setText(currentEvent.getCashprice());
                        description.setText(currentEvent.getDescription());

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


                        brief.setText(currentEvent.getBrief());
                        //fees.setText(currentEvent.getFees());
                        cashprice.setText(currentEvent.getCashprice());
                        description.setText(currentEvent.getDescription());
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

                    case 3:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());


                        brief.setText(currentEvent.getBrief());
                       // fees.setText(currentEvent.getFees());

                        cashprice.setText(currentEvent.getCashprice());
                        description.setText(currentEvent.getDescription());
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

                    case 4:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());


                        brief.setText(currentEvent.getBrief());
                       // fees.setText(currentEvent.getFees());
                        cashprice.setText(currentEvent.getCashprice());
                        description.setText(currentEvent.getDescription());
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

                    case 5:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());


                        brief.setText(currentEvent.getBrief());
                       // fees.setText(currentEvent.getFees());
                        cashprice.setText(currentEvent.getCashprice());
                        description.setText(currentEvent.getDescription());
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

                    case 6:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());


                        brief.setText(currentEvent.getBrief());
                       // fees.setText(currentEvent.getFees());
                        cashprice.setText(currentEvent.getCashprice());
                        description.setText(currentEvent.getDescription());
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

                    case 7:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());


                        brief.setText(currentEvent.getBrief());
                        //fees.setText(currentEvent.getFees());
                        cashprice.setText(currentEvent.getCashprice());
                        description.setText(currentEvent.getDescription());
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

                    case 8:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());


                        brief.setText(currentEvent.getBrief());
                       // fees.setText(currentEvent.getFees());
                        cashprice.setText(currentEvent.getCashprice());
                        description.setText(currentEvent.getDescription());
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



//

                    default:
                        break;
                }

                dialog.show();



            }
        });

        return view;

    }

}
