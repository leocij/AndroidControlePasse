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

import java.util.List;

/**
 * Created by leoci on 30/10/2017.
 */

public class HistoricoPasseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_historico_passe, container, false);
        getActivity().setTitle("Histórico dos Passes");

        imprime(view);

        return view;
    }

    private void imprime(View view) {
        SQLiteDatabase db;
        FabricaConexao fabrica = new FabricaConexao(getContext());
        db = fabrica.getWritableDatabase();
        PrincipalDao principalDao = new PrincipalDao(db);
        List<Principal> list = principalDao.listAll();
        ArrayAdapter<Principal> principalArrayAdapter = new ArrayAdapter<Principal>(getActivity(), android.R.layout.simple_list_item_1, list);

        final ListView lvImprimeHistoricoPasse = (ListView) view.findViewById(R.id.lvImprimeHistoricoPasse);
        lvImprimeHistoricoPasse.setAdapter(principalArrayAdapter);
    }
}
