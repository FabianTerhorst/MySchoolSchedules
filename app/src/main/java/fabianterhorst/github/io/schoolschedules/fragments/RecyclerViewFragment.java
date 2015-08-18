package fabianterhorst.github.io.schoolschedules.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.mikepenz.materialize.util.UIUtils;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.callbacks.DataChangeCallback;

public class RecyclerViewFragment extends BaseFragment {

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

        mSuperRecyclerView.setAdapter(getAdapter());

        getDataStore().registerDataChangeCallback(getDataChangeCallback());

        ((TextView)mSuperRecyclerView.getEmptyView().findViewById(R.id.textView)).setText(getEmptyViewMessage());

        return rowView;
    }

    public SuperRecyclerView getSuperRecyclerView() {
        return mSuperRecyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout(){
        return mSwipeRefreshLayout;
    }

    public RecyclerView.Adapter getAdapter(){
        return null;
    }

    public DataChangeCallback getDataChangeCallback(){
        return null;
    }

    public String getEmptyViewMessage(){
        return null;
    }
}
