package com.lacronicus.mocktopus.core.mocktopus;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by fdoyle on 7/24/14.
 */
public class ConfigFragmentAdapter extends FragmentStatePagerAdapter {

    Mocktopus mocktopus;
    ConfigurationActivity activity;

    public ConfigFragmentAdapter(FragmentManager fm, ConfigurationActivity activity) {
        super(fm);
        this.activity = activity;
        mocktopus = Mocktopus.getInstance();//todo singleton?
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return activity.getTitleForPosition(position);
    }

    @Override
    public ConfigurationFragment getItem(int position) {
        Bundle b = new Bundle();
        b.putInt(ConfigurationFragment.POSITION, position);
        ConfigurationFragment fragment = new ConfigurationFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        if(mocktopus == null) {
            return 0;
        } else {
            return mocktopus.getApiCount();
        }
    }
}
