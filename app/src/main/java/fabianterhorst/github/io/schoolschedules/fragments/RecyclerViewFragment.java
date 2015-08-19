package fabianterhorst.github.io.schoolschedules.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialize.util.UIUtils;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.activities.AddActivity;
import fabianterhorst.github.io.schoolschedules.callbacks.DataChangeCallback;

public class RecyclerViewFragment extends BaseFragment {

    private SuperRecyclerView mSuperRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton mFloatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rowView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mFloatingActionButton = (FloatingActionButton) rowView.findViewById(R.id.fab);

        mSuperRecyclerView = (SuperRecyclerView) rowView.findViewById(R.id.recycler);

        mSwipeRefreshLayout = mSuperRecyclerView.getSwipeToRefresh();

        mSuperRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSuperRecyclerView.getRecyclerView().setPadding(0, 0, 0, UIUtils.getNavigationBarHeight(getActivity()));

        mSuperRecyclerView.setAdapter(getAdapter());

        getDataStore().registerDataChangeCallback(getDataChangeCallback());

        ((TextView) mSuperRecyclerView.getEmptyView().findViewById(R.id.textView)).setText(getEmptyViewMessage());

        mFloatingActionButton.setVisibility(hasFloatingActionButton() ? View.VISIBLE : View.GONE);

        mFloatingActionButton.setImageDrawable(new IconicsDrawable(getActivity(), GoogleMaterial.Icon.gmd_add).color(Color.WHITE).sizePx(72).paddingDp(4));

        int fabMargin = Math.round(UIUtils.convertDpToPixel(10, getActivity()));

        setMargins(mFloatingActionButton, 0, 0, fabMargin, Math.round(UIUtils.getNavigationBarHeight(getActivity()) + fabMargin));

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getAddIntent());
            }
        });

        return rowView;
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public Intent getAddIntent() {
        return null;
    }

    public SuperRecyclerView getSuperRecyclerView() {
        return mSuperRecyclerView;
    }

    @SuppressWarnings("unused")
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    @SuppressWarnings("unused")
    public FloatingActionButton getFloatingActionButton() {
        return mFloatingActionButton;
    }

    public RecyclerView.Adapter getAdapter() {
        return null;
    }

    public DataChangeCallback getDataChangeCallback() {
        return null;
    }

    public String getEmptyViewMessage() {
        return null;
    }

    public boolean hasFloatingActionButton() {
        return true;
    }

    public Intent createAddIntent(AddActivity.type type) {
        Intent intent = new Intent(getActivity(), AddActivity.class);
        intent.putExtra(AddActivity.TYPE, type.toString().toUpperCase());
        return intent;
    }
}
