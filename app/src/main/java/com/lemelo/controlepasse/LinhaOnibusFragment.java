package com.lemelo.controlepasse;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by leoci on 17/10/2017.
 */

public class LinhaOnibusFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_add_linha_onibus, container, false);
        getActivity().setTitle("Adiciona Linha de Ônibus");

        //fecha teclado ao clicar
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollViewAddLinhaOnibus);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new MyKeyboard().hideKeyboard(getActivity(), v);
                return false;
            }
        });

        final EditText txtLetra = (EditText) view.findViewById(R.id.txtLetra);
        txtLetra.requestFocus();
        new MyKeyboard().showKeyboard(getActivity());

        final EditText txtNumero = (EditText) view.findViewById(R.id.txtNumero);
        final EditText txtDescricao = (EditText) view.findViewById(R.id.txtDescricao);


        final Button btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            SQLiteDatabase db;

            @Override
            public void onClick(View v) {
                try {
                    String letra = txtLetra.getText().toString();
                    String numero = txtNumero.getText().toString();
                    String descricao = txtDescricao.getText().toString();

                    FabricaConexao fabrica = new FabricaConexao(getContext());
                    db = fabrica.getWritableDatabase();
                    LinhaOnibusDao linhaOnibusDaoDao = new LinhaOnibusDao(db);

                    LinhaOnibus linhaOnibus = new LinhaOnibus();
                    linhaOnibus.setLetra(letra);
                    linhaOnibus.setNumero(Integer.valueOf(numero));
                    linhaOnibus.setDescricao(descricao);

                    linhaOnibusDaoDao.insert(linhaOnibus);

                    Toast.makeText(getContext(), "Registro Salvo!", Toast.LENGTH_LONG).show();

                    //LIMPA O FORMULARIO
                    txtLetra.setText("");
                    txtLetra.requestFocus();
                    txtNumero.setText("");
                    txtDescricao.setText("");
                } finally {
                    if (db != null && db.isOpen()) {
                        db.close();
                    }
                }
            }
        });

        return view;
    }
}
