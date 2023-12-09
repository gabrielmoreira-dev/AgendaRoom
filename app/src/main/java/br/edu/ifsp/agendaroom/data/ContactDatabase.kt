package br.edu.ifsp.agendaroom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDAO(): ContactDao

    companion object {
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ContactDatabase::class.java,
                "agendaroom.db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}