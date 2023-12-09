package br.edu.ifsp.agendaroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.agendaroom.data.Contact
import br.edu.ifsp.agendaroom.data.ContactDatabase
import br.edu.ifsp.agendaroom.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ContactRepository
    lateinit var contact: LiveData<Contact>
    var allContacts: LiveData<List<Contact>>

    init {
        val dao = ContactDatabase.getDatabase(application).contactDAO()
        repository = ContactRepository(dao)
        allContacts = repository.getAllContacts()
    }

    fun insert(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(contact)
    }
}