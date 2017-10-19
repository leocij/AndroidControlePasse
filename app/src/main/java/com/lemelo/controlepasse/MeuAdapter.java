package com.lemelo.controlepasse;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by leoci on 16/10/2017.
 */

public class MeuAdapter extends BaseAdapter {

    private final List<Passe> passes;
    private final Activity act;

    public MeuAdapter(List<Passe> passes, Activity act) {
        this.passes = passes;
        this.act = act;
    }

    @Override
    public int getCount() {
        return passes.size();
    }

    @Override
    public Object getItem(int position) {
        return passes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.meu_list_view, parent, false);
        Passe passe = passes.get(position);

        TextView textView1 = (TextView) view.findViewById(R.id.meu_list_view_textView1);
        textView1.setText(passe.toString());
        return view;
    }
}
