package suts.desigenpattens.learnnote.data.db;

import android.content.Context;
import android.util.Log;


import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

import suts.desigenpattens.learnnote.data.db.greendao.DaoMaster;
import suts.desigenpattens.learnnote.di.ApplicationContext;
import suts.desigenpattens.learnnote.di.DatabaseInfo;

/**
 * Created by sutingshuai on 2019-08-26
 * Describe:
 */
@Singleton
public class DbOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);

        Log.d("DbOpenHelper", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
                //db.execSQL("ALTER TABLE " + UserDao.TABLENAME + " ADD COLUMN "
                // + UserDao.Properties.Name.columnName + " TEXT DEFAULT 'DEFAULT_VAL'");
        }
    }
}
