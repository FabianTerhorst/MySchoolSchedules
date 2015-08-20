package fabianterhorst.github.io.schoolschedules.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.adapter.ClassGroupsAdapter;
import fabianterhorst.github.io.schoolschedules.models.ClassGroup;

public class ClassGroupsFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rowView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        final SuperRecyclerView recyclerView = (SuperRecyclerView) rowView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        getDataStore().getOwnClassGroups(new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.obj != null) {
                    recyclerView.setAdapter(new ClassGroupsAdapter((ArrayList<ClassGroup>) msg.obj));
                }
                return false;
            }
        }));
        return rowView;
    }
}
