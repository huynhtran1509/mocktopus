package com.lacronicus.mocktopus.core.mocktopus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.lacronicus.mocktopus.core.R;
import com.lacronicus.mocktopus.core.mocktopus.invocationhandler.MockInvocationHandler;

import javax.sql.ConnectionPoolDataSource;


/**
 * Created by fdoyle on 7/24/14.
 */
public class ConfigurationFragment extends Fragment {
    ExpandableListView lv;
    public static final String POSITION = "position";
    int position;

    public void setContent(int position) {
        this.position = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mock_settings, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConfigurationActivity activity = (ConfigurationActivity) getActivity();
        final MockInvocationHandler handler = activity.getHandlerForPosition(position);
        lv = (ExpandableListView) view.findViewById(R.id.lv);
        final OptionsAdapter adapter = new OptionsAdapter(getActivity());
        adapter.setContent(handler.getFlattenedOptions(), handler.getSettings());
        lv.setAdapter(adapter);
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Settings settings = handler.getSettings();
                switch (adapter.getGroup(groupPosition).getType()) {
                    case FlattenedOptions.FlatOptionsItem.TYPE_FIELD: {
                        FlattenedOptions.MethodFieldItem item = adapter.getGroup(groupPosition).methodFieldItem; //currently the
                        settings.put(item.method, item.field, item.fieldOptions.get(childPosition));
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case FlattenedOptions.FlatOptionsItem.TYPE_OBSERVABLE: {
                        FlattenedOptions.ObservableObjectItem item = adapter.getGroup(groupPosition).observableObjectItem;
                        settings.putObservableOption(item.method, item.observableOptions.get(childPosition));
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    default:
                        break;

                }
                return true;
            }
        });
    }
}
