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
 * Created by leoci on 19/10/2017.
 */

public class RecargaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_recarga, container, false);
        getActivity().setTitle("Recarga de passe");
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollViewRecarga);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Fecha teclado ao clicar
                new MyKeyboard().hideKeyboard(getActivity(), v);
                return false;
            }
        });

        final EditText txtData = (EditText) view.findViewById(R.id.txtData);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        txtData.setText(sdf1.format(Calendar.getInstance().getTime()));

        final EditText txtQuantidade = (EditText) view.findViewById(R.id.txtQuantidade);
        txtQuantidade.requestFocus();
        //Abre teclado automático
        new MyKeyboard().showKeyboard(getActivity());

        final EditText txtValorPasse = (EditText) view.findViewById(R.id.txtValorPasse);
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        txtValorPasse.setText(nf.format(0));
        txtValorPasse.addTextChangedListener(new MoneyTextWatcher(txtValorPasse));

        final Button btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            SQLiteDatabase db;
            @Override
            public void onClick(View v) {
                try {
                    String dataStr = txtData.getText().toString();
                    String quantidadeStr = txtQuantidade.getText().toString();
                    String valorPasseStr = txtValorPasse.getText().toString();
                    if(valorPasseStr.equals("")){
                        NumberFormat nf = NumberFormat.getCurrencyInstance();
                        valorPasseStr = nf.format(0);
                    }
                    FabricaConexao fabrica = new FabricaConexao(getContext());
                    db = fabrica.getWritableDatabase();
                    RecargaDao recargaDao = new RecargaDao(db);

                    Recarga recarga = new Recarga();
                    recarga.setData(dataStr);
                    recarga.setQuantidade(quantidadeStr);
                    recarga.setValorPasse(valorPasseStr);

                    recargaDao.insert(recarga);

                    Toast.makeText(getContext(), "Registro Salvo!", Toast.LENGTH_LONG).show();

                    //Limpa formulário
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    txtData.setText(sdf1.format(Calendar.getInstance().getTime()));

                    //fecha teclado e abre teclado
                    new MyKeyboard().hideKeyboard(getActivity(),v);
                    new MyKeyboard().showKeyboard(getActivity());
                    txtQuantidade.setText("");
                    txtQuantidade.requestFocus();

                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    txtValorPasse.setText(nf.format(0));
                    txtValorPasse.addTextChangedListener(new MoneyTextWatcher(txtValorPasse));

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
