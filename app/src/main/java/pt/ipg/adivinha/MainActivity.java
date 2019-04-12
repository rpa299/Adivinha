package pt.ipg.adivinha;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static int TENTATIVAS_APOS_QUAIS_PERDE = 5;

    private GeradorNumerosAdivinhar geradorNumeros;
    private int numeroAdivinhar;
    private int tentativas;
    private int minTentativasGanhar = TENTATIVAS_APOS_QUAIS_PERDE;
    private int maxTentativasGanhar = 0;
    private int totalTentativasTodosJogos = 0;
    private int jogos = 0;
    private int vitorias = 0;
    private int derrotas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adivinha();
            }
        });

        geradorNumeros = new GeradorNumerosAdivinhar();
        novoJogo();
    }

    private void novoJogo() {
        numeroAdivinhar = geradorNumeros.getProximoNumeroAdivinhar();
        tentativas = 0;
        Toast.makeText(this, getString(R.string.novo_jogo), Toast.LENGTH_LONG).show();
    }

    private void adivinha() {
        EditText editTextNumero = (EditText) findViewById(R.id.editTextNumero);
        String textoNumero = editTextNumero.getText().toString();

        if (textoNumero.isEmpty()) {
            editTextNumero.setError(getString(R.string.introduza_numero));
            editTextNumero.requestFocus();
            return;
        }

        int numero;

        try {
            numero = Integer.parseInt(textoNumero);
        } catch (NumberFormatException e) {
            editTextNumero.setError(getString(R.string.numero_invalido));
            editTextNumero.requestFocus();
            return;
        }

        if (numero < 1 || numero > 10) {
            editTextNumero.setError(getString(R.string.numero_invalido));
            editTextNumero.requestFocus();
            return;
        }

        verificaAcertou(numero);
    }

    private void verificaAcertou(int numero) {
        tentativas++;

        if (numero == numeroAdivinhar) {
            acertou();
            return;
        }

        if (tentativas >= TENTATIVAS_APOS_QUAIS_PERDE) {
            perdeu();
            return;
        }


        else if (numero < numeroAdivinhar) {
            Toast.makeText(this, getString(R.string.numero_maior), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.numero_menor), Toast.LENGTH_LONG).show();
        }
    }

    private void PerguntaQuerJogarOutraVez(int recursoTitulo, String mensagem) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(recursoTitulo);
        alertDialogBuilder.setMessage(mensagem);

        alertDialogBuilder.setPositiveButton(
                getString(R.string.sim),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        novoJogo();
                    }
                }
        );

        alertDialogBuilder.setNegativeButton(
                getString(R.string.nao),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }
        );

        alertDialogBuilder.show();
    }

    private void acertou() {
        String mensagem = getString(R.string.acertou_ao_fim_de) + " " + tentativas +
                        " " + getString(R.string.tentativas) + ". " +
                        getString(R.string.jogar_novamente);

        PerguntaQuerJogarOutraVez(R.string.acertou, mensagem);
    }

    private void perdeu() {
        PerguntaQuerJogarOutraVez(R.string.perdeu, getString(R.string.jogar_novamente));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, getString(R.string.versao), Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_novo) {
            actionNovo();
            return true;
        } else if (id == R.id.action_estatisticas) {
            actionEstatisticas();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void actionEstatisticas() {
        // todo: mostrar estatísticas
    }

    private void actionNovo() {
        // todo: perguntar se tem a certeza e iniciar novo jogo
    }
}
