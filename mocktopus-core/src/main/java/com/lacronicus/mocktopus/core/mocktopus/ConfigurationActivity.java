package com.lacronicus.mocktopus.core.mocktopus;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.lacronicus.mocktopus.core.R;
import com.lacronicus.mocktopus.core.mocktopus.invocationhandler.MockInvocationHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by fdoyle on 7/10/14.
 * Allows the user to modify the current settings for an api
 */
public class ConfigurationActivity extends FragmentActivity {
    ViewPager vp;
    ArrayList<Type> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_settings);
        services = new ArrayList<Type>(Mocktopus.getInstance().getApiSet());
        vp = (ViewPager) findViewById(R.id.vp);
        ConfigFragmentAdapter adapter = new ConfigFragmentAdapter(getSupportFragmentManager(), this);
        vp.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    public MockInvocationHandler getHandlerForPosition(int position) {
        return Mocktopus.getInstance().getHandler(services.get(position));
    }

    public String getTitleForPosition(int position) {
        return ((Class) services.get(position)).getSimpleName();
    }
}
