package br.edu.ifsp.agendaroom.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {
    @Insert
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY name")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact WHERE id=:id")
    fun getContactById(id: Int): LiveData<Contact>
}