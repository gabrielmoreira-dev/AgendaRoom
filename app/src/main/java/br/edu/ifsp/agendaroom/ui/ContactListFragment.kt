package br.edu.ifsp.agendaroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
                val searchView = menu.findItem(R.id.actionSearch).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        contactAdapter.filter.filter(newText)
                        return true
                    }
                })
            }
//
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
        setOnClickItemListener()
    }

    private fun setOnClickItemListener() = object : ContactAdapter.ContactListener {
        override fun onClick(pos: Int) = onItemClicked(pos)
    }.apply {
        contactAdapter.setClickListener(this)
    }

    private fun onItemClicked(pos: Int) = Bundle().let {
        it.putInt("contactId", contactAdapter.contactListFilterable[pos].id)
        findNavController().navigate(R.id.action_contactListFragment_to_detailFragment, it)
    }
}