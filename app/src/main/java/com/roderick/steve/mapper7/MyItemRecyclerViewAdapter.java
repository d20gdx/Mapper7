package com.roderick.steve.mapper7;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roderick.steve.mapper7.ItemFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Row} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private  List<Row> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<Row> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTimeView.setText(mValues.get(position).getTime());
        holder.mCounterView.setText(mValues.get(position).getCounter());
        holder.mDistanceView.setText(mValues.get(position).getDistance());
        holder.mLSNRView.setText(mValues.get(position).getLsnr());
       // holder.mDataRateView.setText(mValues.get(position).getDatarate());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void swapDataSet(List<Row> newdata) {
        mValues = newdata;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTimeView;
        public final TextView mCounterView;
        public final TextView mDistanceView;
        public final TextView mLSNRView;
//        public final TextView mDataRateView;

        public Row mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTimeView = (TextView) view.findViewById(R.id.time);
            mCounterView = (TextView) view.findViewById(R.id.counter);
            mDistanceView = (TextView) view.findViewById(R.id.distance);
            mLSNRView = (TextView) view.findViewById(R.id.lsnr);
          //  mDataRateView = (TextView) view.findViewById(R.id.datarate);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCounterView.getText() + "'";
        }
    }
}
