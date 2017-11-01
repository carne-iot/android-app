package ar.edu.itba.iot.iot_android.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ar.edu.itba.iot.iot_android.R;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> devicesNames;
    private List<String> currentTemps;
    private List<String> targetTemps;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;
        public TextView nameTextView;
        public TextView currTempTextView;
        public TextView targetTempTextView;
        public ViewHolder(CardView mCardView, TextView nameTextView, TextView currTempTextView, TextView targetTempTextView) {
            super(mCardView);
            this.mCardView = mCardView;
            this.nameTextView = nameTextView;
            this.currTempTextView = currTempTextView;
            this.targetTempTextView = targetTempTextView;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<String> devicesNames, List<String> currentTemps, List<String> targetTemps) {
        this.devicesNames = devicesNames;
        this.currentTemps = currentTemps;
        this.targetTemps = targetTemps;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        CardView mCardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_card_view, parent, false);
        TextView nameTextView = (TextView) mCardView.findViewById(R.id.device_name);
        TextView currTempTextView = (TextView) mCardView.findViewById(R.id.device_temperature);
        TextView targetTempTextView = (TextView) mCardView.findViewById(R.id.target_temperature);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(mCardView, nameTextView, currTempTextView, targetTempTextView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameTextView.setText(devicesNames.get(position));
        holder.currTempTextView.setText(currentTemps.get(position));
        holder.targetTempTextView.setText(targetTemps.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return devicesNames.size();
    }
}