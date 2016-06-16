package google.bhavesh.csi.childFragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
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
import org.w3c.dom.Text;

import java.util.ArrayList;

import google.bhavesh.csi.Adapters.CommitteeAdapter;
import google.bhavesh.csi.Adapters.TechFestAdapter;
import google.bhavesh.csi.Adapters.TechKnowadapter;
import google.bhavesh.csi.PojoData.Committee;
import google.bhavesh.csi.PojoData.TechKnow;
import google.bhavesh.csi.PojoData.Techmate;
import google.bhavesh.csi.R;
import google.bhavesh.csi.URLendpoints.Keys;
import google.bhavesh.csi.VolleyConnections.VolleySingleton;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link FragmentCSIRAITCoreCommittee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentJuniorCommittee extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mreRecyclerView;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader mImageLoader;
    private CommitteeAdapter mTechFestAdapter;
    Committee techmate;
    ArrayList<Committee> techmateArrayList = new ArrayList<>();


    public FragmentJuniorCommittee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCSIRAITCoreCommittee.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCSIRAITCoreCommittee newInstance(String param1, String param2) {
        FragmentCSIRAITCoreCommittee fragment = new FragmentCSIRAITCoreCommittee();
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

        String url2 = "https://api.myjson.com/bins/410d7";//junior committee

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

    public ArrayList<Committee> parseJsonResponse(JSONObject response) {
        if (response == null || response.length() == 0) {
            return null;
        }
        try {
            StringBuilder data = new StringBuilder();
            if (response.has("juniorcommittee")) {
                JSONArray arrayContacts = response.getJSONArray("juniorcommittee");
                for (int i = 0; i < arrayContacts.length(); i++) {
                    JSONObject currentContact = arrayContacts.getJSONObject(i);

                    String name = currentContact.getString("name");
                    String description = currentContact.getString("description");
                    String contact = currentContact.getString("contact");
                    String fb = currentContact.getString("fb");
                    String urlThumbnail = currentContact.getString("thumbnail");
//

                    data.append(name + description + urlThumbnail + contact + fb + "" + "\n");
                    Log.d(data.toString(), "");
                    techmate = new Committee();
                    techmate.setName(name);
                    techmate.setDescription(description);
                    techmate.setImage(urlThumbnail);
                    techmate.setContact(contact);
                    techmate.setfb(fb);

                    techmateArrayList.add(techmate);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return techmateArrayList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_techmate, container, false);
        mreRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        LinearLayoutManager mlayoutLayoutManager = new LinearLayoutManager(getActivity());
        mreRecyclerView.setLayoutManager(mlayoutLayoutManager);
        mTechFestAdapter = new CommitteeAdapter(getActivity());
        mreRecyclerView.smoothScrollBy(0, 20);
        // mreRecyclerView.smoothScrollToPosition(0);

        mreRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator()));

        mreRecyclerView.setAdapter(mTechFestAdapter);
        mTechFestAdapter.SetOnItemClickListener(new CommitteeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_committee);
                //   dialog.getWindow().setBackgroundDrawableResource(R.color.cardview_light_background);

                Committee currentEvent = techmateArrayList.get(position);
                final ImageView iv = (ImageView) dialog.findViewById(R.id.image);
                final TextView name = (TextView) dialog.findViewById(R.id.name);
                final TextView description = (TextView) dialog.findViewById(R.id.description);
                final TextView contact = (TextView) dialog.findViewById(R.id.contact);
                final TextView coonectFb = (TextView) dialog.findViewById(R.id.connectfb);
                final TextView fb = (TextView) dialog.findViewById(R.id.fb);

                //  TextView cashprice = (TextView) dialog.findViewById(R.id.cashprice);
                String urlThumbnail = currentEvent.getImage();
                switch (position) {

                    case 0:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());


                        if (urlThumbnail != null) {
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
                        //  description.setText(currentEvent.getDescription());

                        description.setText(currentEvent.getDescription());
                        fb.setText(currentEvent.getfb());
                        contact.setText(currentEvent.getContact());
                        if (urlThumbnail != null) {
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
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                        //  description.setText(currentEvent.getDescription());

                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());
                        if (urlThumbnail != null) {
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
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 9:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 10:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 11:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 12:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 13:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 14:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 15:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 16:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 17:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 18:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 19:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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
                    case 20:

                        dialog.getWindow().getAttributes().windowAnimations =

                                R.style.dialog_animation;
                        name.setText(currentEvent.getName());
                        //  description.setText(currentEvent.getDescription());
                        description.setText(currentEvent.getDescription());
                        contact.setText(currentEvent.getContact());
                        fb.setText(currentEvent.getfb());

                        if (urlThumbnail != null) {
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



                    default:break;
                }
                dialog.show();

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event

}