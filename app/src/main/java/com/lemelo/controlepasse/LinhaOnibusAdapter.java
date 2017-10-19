package com.lemelo.controlepasse;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by leoci on 17/10/2017.
 */

public class LinhaOnibusAdapter extends BaseAdapter {

    private final List<LinhaOnibus> linhas;
    private final Activity act;

    public LinhaOnibusAdapter(List<LinhaOnibus> linhas, Activity act) {
        this.linhas = linhas;
        this.act = act;
    }

    @Override
    public int getCount() {
        return linhas.size();
    }

    @Override
    public Object getItem(int position) {
        return linhas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.linha_onibus_list_view, parent, false);
        LinhaOnibus linhaOnibus = linhas.get(position);

        TextView textView = (TextView) view.findViewById(R.id.linha_onibus_list_view_textView1);
        textView.setText(linhaOnibus.toString());

        return view;
    }
}
