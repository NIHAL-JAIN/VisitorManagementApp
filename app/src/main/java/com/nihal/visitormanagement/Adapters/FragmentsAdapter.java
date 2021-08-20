package com.nihal.visitormanagement.Adapters;
/*
* Manage the tabLayout
 */

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nihal.visitormanagement.Fragments.CheckInFragment;
import com.nihal.visitormanagement.Fragments.CheckOutFragment;

public class FragmentsAdapter extends FragmentPagerAdapter {

    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }



    //Fragment Position
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new CheckInFragment();
            case 1 : return new CheckOutFragment();


            default: return new CheckInFragment();
        }
    }

    //Fragment Count
    @Override
    public int getCount() {
        return 2;
    }

    //Page Title
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "CheckIn";
        }
        if (position == 1) {
            title = "CheckOut";
        }

        return title;
    }
}
