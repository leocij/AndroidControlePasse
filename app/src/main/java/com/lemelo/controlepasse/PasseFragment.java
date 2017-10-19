package com.lemelo.controlepasse;


import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

/**
 * Created by leoci on 13/10/2017.
 */

public class PasseFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_passe, container, false);
        getActivity().setTitle("Todos os passes");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.passeFab);

        Toast.makeText(getContext(), "Passei aqui", Toast.LENGTH_LONG).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CadastraPasseFragment cadastra = new CadastraPasseFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_content, cadastra);
                ft.commit();
            }
        });

        try {
            imprime(view);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void imprime(View view) throws ParseException {
        // Fecha Teclado

        SQLiteDatabase db;

        FabricaConexao fabrica = new FabricaConexao(getContext());
        db = fabrica.getWritableDatabase();
        PasseDao passeDao = new PasseDao(db);
        List<Passe> list = passeDao.listAll();

        MeuAdapter meuAdapter = new MeuAdapter(list,getActivity());
        ListView lvImprimePasses = (ListView) view.findViewById(R.id.lvImprimePasses);
        lvImprimePasses.setAdapter(meuAdapter);

        lvImprimePasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Passe selecionado = (Passe) parent.getItemAtPosition(position);
                trataSelecionado(selecionado);
            }
        });
    }

    private void trataSelecionado(final Passe selecionado) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
        dialogo.setTitle("Editar / Apagar?");
        dialogo.setMessage(selecionado.toString());

        dialogo.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                bundle.putString("id", selecionado.getId().toString());
                bundle.putString("data", selecionado.getData());
                bundle.putString("valor", selecionado.getValor());

                EditaPasseFragment edita = new EditaPasseFragment();
                edita.setArguments(bundle);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_content, edita);
                ft.commit();
            }
        });

        dialogo.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlertDialog.Builder dialogDel = new AlertDialog.Builder(getActivity());
                dialogDel.setTitle("Deseja realmente apagar?");
                dialogDel.setMessage(selecionado.toString());

                dialogDel.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogDel, int which) {
                        SQLiteDatabase db;
                        Integer id = selecionado.getId();
                        FabricaConexao fabrica = new FabricaConexao(getContext());
                        db = fabrica.getWritableDatabase();
                        PasseDao passeDao = new PasseDao(db);
                        passeDao.delete(id);
                        try {
                            imprime(view);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

                dialogDel.setPositiveButton("Não", null);
                dialogDel.show();
            }
        });

        dialogo.setNeutralButton("Cancelar", null);
        dialogo.show();
    }
}