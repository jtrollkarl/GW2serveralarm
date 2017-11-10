package com.moducode.gw2serveralarm.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.data.ServerModel;

import java.util.List;

/**
 * Created by Jay on 2017-08-20.
 */

public class ServerListAdapter extends RecyclerView.Adapter<ServerListAdapter.ViewHolder>{

    private List<ServerModel> serverModelList;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onServerClick(ServerModel server);
    }

    public void setData(List<ServerModel> serverModels){
        this.serverModelList = serverModels;
    }

    public ServerListAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.server_list_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ServerModel serverModel = serverModelList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onServerClick(serverModel);
            }
        });

        holder.serverName.setText(serverModel.getName());
        holder.serverPopulation.setText(serverModel.getPopulation());
        holder.serverPopulation.setTextColor(serverModel.getPopulationColor());
    }

    @Override
    public int getItemCount() {
        if(serverModelList != null){
            return serverModelList.size();
        }else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView serverName;
        TextView serverPopulation;

        ViewHolder(View itemView) {
            super(itemView);
            serverName = itemView.findViewById(R.id.server_row_name);
            serverPopulation = itemView.findViewById(R.id.server_row_population);
        }
    }

}
