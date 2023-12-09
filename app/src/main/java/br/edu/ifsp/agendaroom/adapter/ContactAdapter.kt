package br.edu.ifsp.agendaroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.agendaroom.data.Contact
import br.edu.ifsp.agendaroom.databinding.ContactCellBinding

class ContactAdapter: RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(), Filterable {
    private lateinit var binding: ContactCellBinding
    var contactList = ArrayList<Contact>()
    var contactListFilterable = ArrayList<Contact>()
    private var listener: ContactListener? = null

    fun updateList(newList: ArrayList<Contact>) {
        contactList = newList
        contactListFilterable = contactList
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
        holder.name.text = contactListFilterable[position].name
        holder.phone.text = contactListFilterable[position].phone
    }

    override fun getItemCount() = contactListFilterable.size

    override fun getFilter() = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            contactListFilterable = if (p0.toString().isEmpty()) {
                contactList
            } else {
                ArrayList(contactList.filterIndexed { _, contact ->
                    contact.name.lowercase().contains(p0.toString().lowercase())
                })
            }
            FilterResults().let {
                it.values = contactListFilterable
                return it
            }
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            contactListFilterable = p1?.values as ArrayList<Contact>
            notifyDataSetChanged()
        }
    }

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