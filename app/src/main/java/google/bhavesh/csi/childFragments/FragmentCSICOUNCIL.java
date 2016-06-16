package google.bhavesh.csi.childFragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.siyamed.shapeimageview.CircularImageView;

import de.hdodenhof.circleimageview.CircleImageView;
import google.bhavesh.csi.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCSICOUNCIL#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCSICOUNCIL extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentCSICOUNCIL() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCSICOUNCIL.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCSICOUNCIL newInstance(String param1, String param2) {
        FragmentCSICOUNCIL fragment = new FragmentCSICOUNCIL();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_csicouncil, container, false);
        CircularImageView cc1= (CircularImageView) view.findViewById(R.id.imagev1);
        cc1.setOnClickListener(new CircularImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getActivity());
                dialog.setTitle("Dr. Leena Ragha");


            }
        });
        CircularImageView cc2= (CircularImageView) view.findViewById(R.id.imagev1);
        cc2.setOnClickListener(new CircularImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getActivity());
                dialog.setTitle("Mr. Tushar Ghorpade");


            }
        });


        return view;
    }

}
