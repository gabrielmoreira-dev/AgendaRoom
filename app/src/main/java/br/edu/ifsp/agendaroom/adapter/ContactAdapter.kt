package br.edu.ifsp.agendaroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.agendaroom.data.Contact
import br.edu.ifsp.agendaroom.databinding.ContactCellBinding

class ContactAdapter: RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private lateinit var binding: ContactCellBinding
    private var contactList = ArrayList<Contact>()

    fun updateList(newList: ArrayList<Contact>) {
        contactList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        binding = ContactCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.name.text = contactList[position].name
        holder.phone.text = contactList[position].phone
    }

    override fun getItemCount() = contactList.size

    inner class ContactViewHolder(view: ContactCellBinding): RecyclerView.ViewHolder(view.root) {
        val name = view.name
        val phone = view.phone
    }
}