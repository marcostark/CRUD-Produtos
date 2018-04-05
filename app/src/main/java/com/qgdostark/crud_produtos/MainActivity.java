package com.qgdostark.crud_produtos;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.qgdostark.crud_produtos.bd.AppDatabase;
import com.qgdostark.crud_produtos.bd.ProdutoDAO;
import com.qgdostark.crud_produtos.model.Produto;
import com.qgdostark.crud_produtos.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos on 01/04/18.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewItens;
    private List<Produto> listProduto;
    private ProdutoAdapter mProdutoAdapter;
    private ProdutoDAO mProdutoDAO;
    private ActionBar action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.action = getSupportActionBar();
        this.action.setTitle("Produtos");

        mProdutoDAO = Room.databaseBuilder(this, AppDatabase.class, "db-contacts")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build()
                .getProdutoDAO();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        loadRecyclerView();
    }

    public void initViews(){
        mRecyclerViewItens = findViewById(R.id.rv);
    }

    public void loadRecyclerView(){

        listProduto = mProdutoDAO.getProdutos();
        mProdutoAdapter = new ProdutoAdapter(this,listProduto);

        RecyclerView.LayoutManager mLayoutManager =  new LinearLayoutManager(getApplicationContext());
        mRecyclerViewItens.setLayoutManager(mLayoutManager);

        mRecyclerViewItens.setHasFixedSize(true);
        mRecyclerViewItens.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewItens.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerViewItens.setAdapter(mProdutoAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(onSearch());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Produto> filteredModelList = filter(listProduto, newText);
                mProdutoAdapter.setFilter(filteredModelList);
                return false;
            }
        };
    }

    private List<Produto> filter(List<Produto> listProduto, String query) {
        query = query.toLowerCase();
        final List<Produto> filteredModelList = new ArrayList<>();
        for (Produto model : listProduto) {
            final String text = model.getNome().toLowerCase();
            //final String descricao = model.getDescricao().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
