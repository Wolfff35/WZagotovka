package com.wolff.wzagotovka.localdb;

/**
 * Created by wolff on 11.04.2017.
 */

public class DbSchema {
    public static final class WItemTable{
        public static final String TABLE_NAME = "witems";

        public static final class Cols{
            public static final String ID = "_id";
            public static final String UUID = "uuid";
            public static final String TITLE = "title";

        }
    }
}
