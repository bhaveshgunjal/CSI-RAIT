package google.bhavesh.csi.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import google.bhavesh.csi.R;
import google.bhavesh.csi.childFragments.FragmentCSICOUNCIL;
import google.bhavesh.csi.childFragments.FragmentCSIRAITCoreCommittee;
import google.bhavesh.csi.childFragments.FragmentJuniorCommittee;
import google.bhavesh.csi.childFragments.FragmentTechknow;
import google.bhavesh.csi.childFragments.FragmentTechmate;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class FragmentCSIRAIT extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    public static int int_items = 2 ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);



        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new TechAdapter(getChildFragmentManager()));



        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        // tabLayout.setupWithViewPager(viewPager);
        return x;

    }

    class TechAdapter extends FragmentPagerAdapter {

        public TechAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {

            switch (position){
                case 0 :return new FragmentCSIRAITCoreCommittee();

                case 1: return new FragmentJuniorCommittee();


            }
            return  new FragmentCSIRAITCoreCommittee();
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Core Committee";

                case 1:
                    return "Junior Committee";
                default:return null;

            }

        }
    }


}
