package br.edu.ifsp.agendaroom.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ContactDao {
    @Insert
    suspend fun insert(contact: Contact)
}