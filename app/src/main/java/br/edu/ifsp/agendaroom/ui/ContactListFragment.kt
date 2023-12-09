package br.edu.ifsp.agendaroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.agendaroom.R
import br.edu.ifsp.agendaroom.adapter.ContactAdapter
import br.edu.ifsp.agendaroom.data.Contact
import br.edu.ifsp.agendaroom.databinding.FragmentContactListBinding
import br.edu.ifsp.agendaroom.viewmodel.ContactViewModel

class ContactListFragment : Fragment() {
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_contactListFragment_to_registerFragment)
        }
        configureRecyclerView()
        return binding.root
    }

    private fun configureRecyclerView() {
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        viewModel.allContacts.observe(viewLifecycleOwner) {
            it?.let {
                contactAdapter.updateList(it as ArrayList<Contact>)
            }
        }
        contactAdapter = ContactAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactAdapter
        }
    }
}