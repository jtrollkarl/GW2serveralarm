package com.moducode.gw2serveralarm.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.data.ServerModel;

import java.util.List;

/**
 * Created by Jay on 2017-08-20.
 */

public class ServerListAdapter extends ArrayAdapter<ServerModel>{


    public ServerListAdapter(@NonNull Context context, @NonNull List<ServerModel> objects) {
        super(context, R.layout.server_list_row, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ServerModel model = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.server_list_row, parent, false);
        }

        TextView serverName = convertView.findViewById(R.id.server_row_name);

        assert model != null;
        serverName.setText(model.getName());

        TextView population = convertView.findViewById(R.id.server_row_population);
        population.setText(model.getPopulation());

        return convertView;
    }


}
