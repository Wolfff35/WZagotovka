package com.wolff.wzagotovka.localdb;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.wolff.wzagotovka.localdb.DbSchema.WItemTable;
import com.wolff.wzagotovka.objects.WItem;

import java.util.UUID;

/**
 * Created by wolff on 11.04.2017.
 */

public class DbCursorWrapper extends CursorWrapper {

    public DbCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public WItem getWItem(){
        String uuidString = getString(getColumnIndex(WItemTable.Cols.UUID));
        String title = getString(getColumnIndex(WItemTable.Cols.TITLE));

        WItem item = new WItem(UUID.fromString(uuidString));
        item.setTitle(title);

        return item;
    }
}
