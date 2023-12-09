package br.edu.ifsp.agendaroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.agendaroom.databinding.FragmentDetailBinding
import br.edu.ifsp.agendaroom.viewmodel.ContactViewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactViewModel

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
                binding.commonLayout.apply {
                    editTextName.setText(it.name)
                    editTextPhone.setText(it.phone)
                    editTextEmail.setText(it.email)
                }
            }
        }
    }
}