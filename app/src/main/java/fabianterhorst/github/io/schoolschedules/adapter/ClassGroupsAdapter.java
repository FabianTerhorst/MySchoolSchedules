package fabianterhorst.github.io.schoolschedules.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.models.ClassGroup;

public class ClassGroupsAdapter extends RecyclerView.Adapter<ClassGroupsAdapter.ViewHolder> {

    private List<ClassGroup> classGroups;

    public ClassGroupsAdapter(List<ClassGroup> data) {
        this.classGroups = data;
    }

    @Override
    public ClassGroupsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classgroup, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassGroupsAdapter.ViewHolder holder, int position) {
        ClassGroup classGroup = classGroups.get(position);
        holder.name.setText(classGroup.getName());
        holder.shortname.setText(classGroup.getShortname());
    }

    @Override
    public int getItemCount() {
        return classGroups.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, shortname;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_classgroup_name);
            shortname = (TextView) itemView.findViewById(R.id.item_classgroup_shortname);
        }
    }
}
