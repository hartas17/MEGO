package com.example.noubyte.mego.DB;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.noubyte.mego.Models.Graphics;
import com.example.noubyte.mego.Models.RNA;
import com.example.noubyte.mego.Models.Users;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by Noubyte on 28/03/2017.
 */

public class DB extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "MeGo.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Users, Integer> userDao;
    private Dao<Graphics, Integer> graphicsDao;
    private Dao<RNA, Integer> rnaDao;

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try{
            TableUtils.createTableIfNotExists(connectionSource,Users.class);
            TableUtils.createTableIfNotExists(connectionSource,Graphics.class);
            TableUtils.createTableIfNotExists(connectionSource,RNA.class);
        }catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
