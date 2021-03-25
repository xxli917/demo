package com.lixiaoxue.demolxx.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * author : lixiaoxue
 * date   : 2021/3/2/5:58 PM
 */
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
