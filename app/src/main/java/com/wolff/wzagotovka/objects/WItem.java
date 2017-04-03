package com.wolff.wzagotovka.objects;

import java.util.UUID;

/**
 * Created by wolff on 03.04.2017.
 */

public class WItem {
    private UUID mId;
    private String mName;

    public WItem(){
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
