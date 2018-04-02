package com.qgdostark.crud_produtos;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.qgdostark.crud_produtos.bd.AppDatabase;
import com.qgdostark.crud_produtos.bd.ProdutoDAO;
import com.qgdostark.crud_produtos.model.Produto;

/**
 * Created by Marcos on 01/04/18.
 */

public class CadastroProdutoActivity extends AppCompatActivity {

    private EditText nomeProduto;
    private EditText descricaoProduto;
    private EditText quantidadeProduto;
    private EditText valorProduto;
    private ProdutoDAO mProdutoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        setTitle("Cadastro de Produtos");

        mProdutoDAO = Room.databaseBuilder(this, AppDatabase.class, "db-contacts")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build()
                .getProdutoDAO();

        loadComponentes();
    }

    public void loadComponentes(){
        nomeProduto = findViewById(R.id.cadastro_produto_nome);
        descricaoProduto = findViewById(R.id.cadastro_produto_descricao);
        quantidadeProduto = findViewById(R.id.cadastro_produto_quantidade);
        valorProduto = findViewById(R.id.cadastro_produto_valor);
    }

    /*Criando Menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.form_item_salvar:

                Produto produto = criarProduto();

                //Inserir no banco de dados
                try {
                    mProdutoDAO.insert(produto);
                    setResult(RESULT_OK);
                    finish();
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(getContext(), "Erro ao salvar o produto.", Toast.LENGTH_SHORT).show();
                }

            case android.R.id.home :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Context getContext(){
        return this;
    }

    public Produto criarProduto(){
        Produto produto = new Produto();
        produto.setNome(nomeProduto.getText().toString());
        produto.setDescricao(descricaoProduto.getText().toString());
        produto.setQuantidade(Double.valueOf(quantidadeProduto.getText().toString()));
        produto.setValor(Double.valueOf(valorProduto.getText().toString()));

        return produto;
    }
}
