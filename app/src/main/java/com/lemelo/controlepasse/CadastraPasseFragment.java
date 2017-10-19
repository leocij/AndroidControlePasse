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
 * Created by leoci on 13/10/2017.
 */

public class CadastraPasseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastra_passe, container, false);
        getActivity().setTitle("Cadastro de Passe.");
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollViewCadastraPasse);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //close keyboard
                new MyKeyboard().hideKeyboard(getActivity(), v);
                return false;
            }
        });

        final EditText txtData = (EditText) view.findViewById(R.id.txtData);
        final EditText txtValor = (EditText) view.findViewById(R.id.txtValor);

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        //final DateFormat data = DateFormat.getDateInstance();
        txtData.setText(sdf1.format(Calendar.getInstance().getTime()));

        txtValor.requestFocus();
        //open keyboard
        new MyKeyboard().showKeyboard(getActivity());

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        txtValor.setText(nf.format(0));
        txtValor.addTextChangedListener(new MoneyTextWatcher(txtValor));

        final Button btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            SQLiteDatabase db;

            @Override
            public void onClick(View v) {
                try {
                    String dataStr = txtData.getText().toString();
                    String valorStr = txtValor.getText().toString();
                    if(valorStr.equals("")){
                        NumberFormat nf = NumberFormat.getCurrencyInstance();
                        valorStr = nf.format(0);
                    }

                    FabricaConexao fabrica = new FabricaConexao(getContext());
                    db = fabrica.getWritableDatabase();
                    PasseDao passeDao = new PasseDao(db);

                    Passe passe = new Passe();
                    passe.setData(dataStr);
                    passe.setValor(valorStr);

                    passeDao.insert(passe);

                    Toast.makeText(getContext(), "Registro Salvo!", Toast.LENGTH_LONG).show();

                    //LIMPA O FORMULARIO
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    txtData.setText(sdf1.format(Calendar.getInstance().getTime()));
                    //close keyboard
                    new MyKeyboard().hideKeyboard(getActivity(), v);
                    //open keyboard
                    new MyKeyboard().showKeyboard(getActivity());

                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    txtValor.setText(nf.format(0));
                    txtValor.addTextChangedListener(new MoneyTextWatcher(txtValor));
                    txtValor.requestFocus();

                } catch (ParseException e) {
                    e.printStackTrace();
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
