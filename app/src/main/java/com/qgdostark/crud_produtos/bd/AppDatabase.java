package com.qgdostark.crud_produtos.bd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.qgdostark.crud_produtos.model.Produto;

/**
 * Created by Marcos on 01/04/18.
 */

@Database(entities = {Produto.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract ProdutoDAO getProdutoDAO();

}
