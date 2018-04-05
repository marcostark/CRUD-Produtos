package com.qgdostark.crud_produtos;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qgdostark.crud_produtos.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder> {

    private List<Produto> mProduto;
    private Context context;

    public ProdutoAdapter(Context context, List<Produto> lc){
        this.context = context;
        this.mProduto = lc;
    }

    //Chamado quando necessario criar uma nova lista
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row_produto,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Produto produto = mProduto.get(position);

        holder.nomeProduto.setText(produto.getNome());
        holder.valorProduto.setText(String.valueOf(produto.getValor()));
        holder.opcoesProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.opcoesProduto, produto);
            }
        });

    }

    public void addListItem(Produto produto, int position) {
        mProduto.add(produto);
        notifyItemInserted(position);
    }

    public void setFilter(List<Produto> produtos){
        mProduto = new ArrayList<>();
        mProduto.addAll(produtos);
        notifyDataSetChanged();
    }

    //Tamanho da lista
    @Override
    public int getItemCount() {
        return mProduto.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nomeProduto;
        private TextView valorProduto;
        private ImageView thumbProduto;
        private ImageView opcoesProduto;

        public MyViewHolder(View itemView) {
            super(itemView);

            nomeProduto = itemView.findViewById(R.id.lista_produto_nome);
            valorProduto = itemView.findViewById(R.id.lista_produto_valor);
            opcoesProduto = (ImageView) itemView.findViewById(R.id.lista_produto_opcoes);

        }
    }

    private void showPopupMenu(final View view, Produto pd){
        final Produto produto = pd;
        //final ProdutoDAO produtoDAO = new ProdutoDAO(context);

        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_produtos, popupMenu.getMenu());
        //popupMenu.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_editar_produto:
                        Toast.makeText(context, "Editar " + produto.getNome().toString() , Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.menu_excluir_produto:
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Confirmação")
                                .setMessage("Deseja realmente excluir este produto?")
                                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        /*boolean sucesso = produtoDAO.excluir(produto);
                                        if(sucesso) {
                                            //removerProduto(produto);
                                            Snackbar.make(view, "Excluiu!", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }else{
                                            Snackbar.make(view, "Erro ao excluir o produto!", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }*/
                                    }
                                })
                                .setNegativeButton("Cancelar", null)
                                .create()
                                .show();
                        return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}

