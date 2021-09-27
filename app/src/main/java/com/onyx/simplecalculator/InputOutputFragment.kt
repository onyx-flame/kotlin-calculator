package com.onyx.simplecalculator

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.onyx.simplecalculator.databinding.FragmentInputOutputBinding
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat
import kotlin.math.min

class InputOutputFragment : Fragment() {

    private var _binding: FragmentInputOutputBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputOutputBinding.inflate(inflater, container, false)
        val view = binding.root
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            binding.inputLine.setShowSoftInputOnFocus(false);
        } else { // API 11-20
            binding.inputLine.setTextIsSelectable(true);
        }
        binding.inputLine.addTextChangedListener {
            val inputString = binding.inputLine.text.toString()
            val result = calculateInput(inputString)
            if (result != "NaN") {
                binding.outputLine.text = result
            } else if (inputString == "") {
                binding.outputLine.text = ""
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            val selection = binding.inputLine.selectionStart
            val pos = if (selection != -1) min(selection, binding.inputLine.text!!.length) else binding.inputLine.text!!.length - 1;
            binding.inputLine.text!!.insert(pos, it)
        })
        viewModel.action.observe(viewLifecycleOwner, Observer {
            when(it) {
                "calculate" -> showResult()
                "delete" -> deleteSymbol()
                "erase" -> clearAll()
            }
        })
        // TODO: Use the ViewModel
    }

    private fun deleteSymbol() {
        val selection = binding.inputLine.selectionStart
        val pos = if (selection != -1) min(selection, binding.inputLine.text!!.length) else binding.inputLine.text!!.length - 1;
        if (pos > 0)
            binding.inputLine.text!!.delete(pos - 1, pos).toString();
    }

    private fun clearAll() {
        binding.outputLine.text = ""
        binding.inputLine.setText("")
    }

    private fun calculateInput(inputString: String): String {
        return try {
            val result = Expression(inputString).calculate()
            DecimalFormat("0.######").format(result)
        } catch (e: Exception) {
            "NaN"
        }
    }

    fun showResult() {
        val expression = binding.inputLine.text.toString()
        val result = calculateInput(expression)
        if (result != "NaN") {
            binding.inputLine.text?.clear()
            binding.inputLine.text?.append(result)
            binding.outputLine.text = ""
        } else {
            binding.outputLine.text = "Bad input!"
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}