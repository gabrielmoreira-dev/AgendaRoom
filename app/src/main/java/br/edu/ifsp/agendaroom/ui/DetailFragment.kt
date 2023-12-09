package br.edu.ifsp.agendaroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.agendaroom.R
import br.edu.ifsp.agendaroom.data.Contact
import br.edu.ifsp.agendaroom.databinding.FragmentDetailBinding
import br.edu.ifsp.agendaroom.viewmodel.ContactViewModel
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactViewModel
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contactId = requireArguments().getInt("contactId")
        viewModel.getContactById(contactId)
        viewModel.contact.observe(viewLifecycleOwner) {
            it?.let {
                contact = it
                binding.commonLayout.apply {
                    editTextName.setText(it.name)
                    editTextPhone.setText(it.phone)
                    editTextEmail.setText(it.email)
                }
            }
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.detail_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem) = when(menuItem.itemId) {
                R.id.actionUpdateContact -> {
                    binding.commonLayout.apply {
                        val contact = Contact(
                            contactId,
                            editTextName.text.toString(),
                            editTextPhone.text.toString(),
                            editTextEmail.text.toString()
                        )
                        viewModel.update(contact)
                    }
                    Snackbar.make(binding.root, "Contact updated", Snackbar.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    true
                }
                R.id.actionDeleteContact -> {
                    viewModel.delete(contact)
                    Snackbar.make(binding.root, "Contact deleted", Snackbar.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}