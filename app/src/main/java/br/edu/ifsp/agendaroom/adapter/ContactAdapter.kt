package br.edu.ifsp.agendaroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.agendaroom.data.Contact
import br.edu.ifsp.agendaroom.databinding.ContactCellBinding

class ContactAdapter: RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private lateinit var binding: ContactCellBinding
    var contactList = ArrayList<Contact>()
    private var listener: ContactListener? = null

    fun updateList(newList: ArrayList<Contact>) {
        contactList = newList
        notifyDataSetChanged()
    }

    fun setClickListener(listener: ContactListener) {
        this.listener = listener
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

        init {
            view.root.setOnClickListener {
                listener?.onClick(adapterPosition)
            }
        }
    }

    interface ContactListener {
        fun onClick(pos: Int)
    }
}