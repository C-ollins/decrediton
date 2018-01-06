package com.decrediton.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.decrediton.R;
import com.decrediton.data.Connection;

import java.util.List;

/**
 * Created by Macsleven on 05/01/2018.
 */

public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionAdapter.MyViewHolder> {
        private List<Connection> connectionsList;
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView connection;


            public MyViewHolder(View view) {
                super(view);
                connection = view.findViewById(R.id.connection_get_peers);
            }
        }
        public ConnectionAdapter(List<Connection> connectionListList) {
            this.connectionsList = connectionListList;

        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView =LayoutInflater.from(parent.getContext()).inflate(R.layout.connection_list_row,parent,false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            Connection connection = connectionsList.get(position);
            holder.connection.setText(connection.getConnection());
        }

        @Override
        public int getItemCount() {
            return  connectionsList.size();
        }
    }
