package com.wolff.wzagotovka.objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wolff.wzagotovka.localdb.DbCursorWrapper;
import com.wolff.wzagotovka.localdb.DbHelper;
import com.wolff.wzagotovka.localdb.DbSchema;
import com.wolff.wzagotovka.localdb.DbSchema.WItemTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wolff on 03.04.2017.
 */

public class WItemLab {
    private static WItemLab sWItemLab;

    private List<WItem> mWItemList;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private WItemLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new DbHelper(mContext).getWritableDatabase();

        mWItemList = new ArrayList<>();
     /*   for(int i=0;i<5;i++){
            WItem item = new WItem();
            item.setTitle("Item # "+i);
            mDatabase.insert(WItemTable.TABLE_NAME,null,getContentValues(item));
                mWItemList.add(item);
        }
        */
    }

    public static WItemLab get(Context context){
        if(sWItemLab==null){
            sWItemLab = new WItemLab(context);
        }
        return sWItemLab;
    }
    public List<WItem> getWItems(){
        DbCursorWrapper cursor = queryWItem(null,null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                mWItemList.add(cursor.getWItem());
                cursor.moveToNext();
            }

        }
        finally {
            cursor.close();
        }
        return mWItemList;
    }
    public WItem getWItem(UUID id){
 //       for(WItem item:mWItemList){
 //           if(item.getId().equals(id)){
 //               return item;
 //           }
 //       }
 //       return null;
        DbCursorWrapper cursor = queryWItem(WItemTable.Cols.UUID+" = ?",new String[]{id.toString()});
        try{
            if (cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getWItem();
        }
        finally {
            cursor.close();
        }

    }

    private static ContentValues getContentValues(WItem item){
        ContentValues values = new ContentValues();
        values.put(WItemTable.Cols.UUID,item.getId().toString());
        values.put(WItemTable.Cols.TITLE,item.getTitle());
        return values;
    }
    public void addWItem(WItem item){
        ContentValues values = getContentValues(item);
        mDatabase.insert(WItemTable.TABLE_NAME,null,values);
    }
    public void updateWItem(WItem item){
        ContentValues values = getContentValues(item);
        mDatabase.update(WItemTable.TABLE_NAME,values,WItemTable.Cols.UUID+" = ?",new String[]{item.getId().toString()});
    }
    private DbCursorWrapper queryWItem(String whereClause,
                                              String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                WItemTable.TABLE_NAME,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
                 );
        return new DbCursorWrapper(cursor);
    }

}
