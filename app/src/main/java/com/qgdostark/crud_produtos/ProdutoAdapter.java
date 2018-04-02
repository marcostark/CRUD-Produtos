package com.qgdostark.crud_produtos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qgdostark.crud_produtos.model.Produto;

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Produto produto = mProduto.get(position);

        holder.nomeProduto.setText(produto.getNome());
        holder.valorProduto.setText(String.valueOf(produto.getValor()));

    }

    public void addListItem(Produto produto, int position) {
        mProduto.add(produto);
        notifyItemInserted(position);
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
}

