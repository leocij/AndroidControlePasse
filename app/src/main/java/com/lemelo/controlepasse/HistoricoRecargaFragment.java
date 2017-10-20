package com.lemelo.controlepasse;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.util.List;

/**
 * Created by leoci on 19/10/2017.
 */

public class HistoricoRecargaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_historico_recarga, container, false);
        getActivity().setTitle("Histórico de Recargas");

        try {
            imprime(view);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void imprime(View view) throws ParseException {
        SQLiteDatabase db;
        FabricaConexao fabrica = new FabricaConexao(getContext());
        db = fabrica.getWritableDatabase();
        RecargaDao recargaDao = new RecargaDao(db);
        List<Recarga> list = recargaDao.listAll();
        ArrayAdapter<Recarga> recargaArrayAdapter = new ArrayAdapter<Recarga>(getActivity(), android.R.layout.simple_list_item_1,list);

        final ListView lvImprimeHistoricoRecarga = (ListView) view.findViewById(R.id.lvImprimeHistoricoRecarga);
        lvImprimeHistoricoRecarga.setAdapter(recargaArrayAdapter);

    }
}
