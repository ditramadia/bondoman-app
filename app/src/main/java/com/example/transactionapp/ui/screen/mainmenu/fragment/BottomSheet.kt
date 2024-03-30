package com.example.transactionapp.ui.screen.mainmenu.fragment

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.transactionapp.databinding.FragmentBottomSheetBinding
import com.example.transactionapp.domain.db.model.Transaction
import com.example.transactionapp.ui.viewmodel.transaction.TransactionViewModel
import com.example.transactionapp.utils.createExcelFile
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheet : BottomSheetDialogFragment() {
    private val db: TransactionViewModel by activityViewModels()
    private lateinit var transactionList: MutableList<Transaction>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        transactionList = mutableListOf()
        db.transaction.observe(requireActivity()){
            transactionList.addAll(it)
        }

        binding.xlsxButton.setOnClickListener {
            createExcelFile(transactionList, "xlsx", requireContext())
            Toast.makeText(requireContext(), "File saved to ${requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()}", Toast.LENGTH_SHORT).show()
        }

        binding.xlsButton.setOnClickListener {
            createExcelFile(transactionList, "xls", requireContext())
            Toast.makeText(requireContext(), "File saved to ${requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()}", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        db.getTransactions("desc")
    }

}