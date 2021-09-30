package com.onyx.simplecalculator

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
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
    ): View {
        _binding = FragmentInputOutputBinding.inflate(inflater, container, false)
        val view = binding.root
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21+
            binding.inputLine.showSoftInputOnFocus = false
        } else { // API 11-20
            binding.inputLine.setTextIsSelectable(true)
        }
        if (savedInstanceState != null) {
            binding.inputLine.text!!.clear()
            binding.inputLine.append(savedInstanceState.getString("input"))
            binding.outputLine.text = savedInstanceState.getString("output")
        }
        binding.inputLine.addTextChangedListener {
            val inputString = binding.inputLine.text.toString()
            val result = calculateInput(inputString)
            if (inputString == "" || inputString == result) {
                binding.outputLine.text = ""
            } else if (!result.contains("NaN")) {
                binding.outputLine.text = result
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        viewModel.text.observe(viewLifecycleOwner, {
            if (it != "") {
                insertSymbol(it)
            }
        })
        viewModel.action.observe(viewLifecycleOwner, {
            when(it) {
                "calculate" -> showResult()
                "delete" -> deleteSymbol()
                "erase" -> clearAll()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("input", binding.inputLine.text.toString())
        outState.putString("output", binding.outputLine.text.toString())
        viewModel.clearLiveData()
    }

    private fun insertSymbol(s: String) {
        val selection = binding.inputLine.selectionStart
        val pos = if (selection != -1) min(selection, binding.inputLine.text!!.length) else binding.inputLine.text!!.length - 1
        binding.inputLine.text!!.insert(pos, s)
    }

    private fun deleteSymbol() {
        val selection = binding.inputLine.selectionStart
        val pos = if (selection != -1) min(selection, binding.inputLine.text!!.length) else binding.inputLine.text!!.length - 1
        if (pos > 0)
            binding.inputLine.text!!.delete(pos - 1, pos).toString()
    }

    private fun clearAll() {
        binding.outputLine.text = ""
        binding.inputLine.setText("")
    }

    private fun calculateInput(inputString: String): String {
        if (inputString == "")
            return inputString
        return try {
            val result = Expression(inputString).calculate()
            DecimalFormat("0.#########").format(result)
        } catch (e: Exception) {
            "NaN"
        }
    }

    private fun showResult() {
        val expression = binding.inputLine.text.toString()
        val result = calculateInput(expression)
        if (result != "NaN") {
            binding.inputLine.text?.clear()
            binding.inputLine.text?.append(result)
            binding.outputLine.text = ""
        } else {
            binding.outputLine.text = getString(R.string.bad_input_warning)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}