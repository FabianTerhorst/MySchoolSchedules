package fabianterhorst.github.io.schoolschedules.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.mikepenz.materialize.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import fabianterhorst.github.io.schoolschedules.DataStore;
import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.adapter.RepresentationAdapter;
import fabianterhorst.github.io.schoolschedules.callbacks.DataChangeCallback;
import fabianterhorst.github.io.schoolschedules.models.Representation;
import fabianterhorst.github.io.schoolschedules.models.SchoolClass;

public class RepresentationsFragment extends BaseFragment {

    private SuperRecyclerView mSuperRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rowView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mSuperRecyclerView = (SuperRecyclerView) rowView.findViewById(R.id.recycler);

        mSwipeRefreshLayout = mSuperRecyclerView.getSwipeToRefresh();

        mSuperRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSuperRecyclerView.getRecyclerView().setPadding(0, 0, 0, UIUtils.getNavigationBarHeight(getActivity()));

        getDataStore().registerSchoolClassesDataChangeCallback(new DataStore.SchoolClassesDataChangeCallback() {
            @Override
            public void onSchoolClassesDataChange() {
                setAdapter(true);
            }
        });

        mSuperRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataStore().refreshSchoolClasses();
            }
        });
        getDataStore().refreshSchoolClasses();

        setAdapter();

        return rowView;
    }

    private void setAdapter(final boolean refreshed) {
        SchoolClass schoolClass = getDataStore().getCurrentSchoolClass();
        if (schoolClass != null) {
            addItems();
            getDataStore().registerDataChangeCallbackForCurrentSchoolClass(new DataChangeCallback() {
                @Override
                public void onRepresentationsDataChange(List<Representation> representations) {
                    if (mSwipeRefreshLayout.isRefreshing())
                        mSwipeRefreshLayout.setRefreshing(false);
                    addItems(refreshed);
                }
            });
            //setRefreshing();
            getDataStore().refreshClassRepresentations(schoolClass);
        } else if (refreshed) {
            mSuperRecyclerView.setAdapter(new RepresentationAdapter(new ArrayList<Representation>()));
        } else {
            //setRefreshing();
        }
    }

    private void setRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    private void setAdapter() {
        setAdapter(false);
    }

    private void addItems(boolean refreshed) {
        List<Representation> representations = getDataStore().getRepresentationsFromCurrentSchoolClass();
        if ((representations != null && representations.size() > 0) || refreshed)
            mSuperRecyclerView.setAdapter(new RepresentationAdapter(representations));
    }

    private void addItems(){
        addItems(false);
    }

}
