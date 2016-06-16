package google.bhavesh.csi.DialogFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import google.bhavesh.csi.PojoData.TechFestDialog;
import google.bhavesh.csi.PojoData.Techmate;
import google.bhavesh.csi.R;
import google.bhavesh.csi.URLendpoints.Keys;

/**
 * Created by Archana on 3/8/2016.
 */
public class TechFestDialogFragment extends DialogFragment {
    int position;
    ImageView imageView;
    TextView textView;
  //  ArrayList<TechFestDialog> techFestDialogArrayList = new ArrayList<>();

    public  TechFestDialogFragment() {
           }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogtechfest, container, false);
        imageView = (ImageView) v.findViewById(R.id.image);
        textView = (TextView) v.findViewById(R.id.title);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        imageView = (ImageView) view.findViewById(R.id.image);
        textView = (TextView) view.findViewById(R.id.title);


    }
}