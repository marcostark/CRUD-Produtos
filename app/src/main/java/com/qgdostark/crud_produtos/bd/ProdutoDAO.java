package com.qgdostark.crud_produtos.bd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.qgdostark.crud_produtos.model.Produto;

import java.util.List;

/**
 * Created by Marcos on 01/04/18.
 */

@Dao
public interface ProdutoDAO {

    @Insert
    public void insert(Produto... produtos);

    @Update
    public void update(Produto... produtos);

    @Delete
    public void delete(Produto produtos);

    @Query("SELECT * FROM produto")
    public List<Produto> getProdutos();

}
