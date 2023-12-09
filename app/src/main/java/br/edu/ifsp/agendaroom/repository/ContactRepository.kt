package br.edu.ifsp.agendaroom.repository

import androidx.lifecycle.LiveData
import br.edu.ifsp.agendaroom.data.Contact
import br.edu.ifsp.agendaroom.data.ContactDao

class ContactRepository(private val contactDao: ContactDao) {
    suspend fun insert(contact: Contact) = contactDao.insert(contact)

    suspend fun update(contact: Contact) = contactDao.update(contact)

    suspend fun delete(contact: Contact) = contactDao.delete(contact)

    fun getAllContacts() = contactDao.getAllContacts()

    fun getContactById(id: Int) = contactDao.getContactById(id)
}