package com.onyx.simplecalculator

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.onyx.simplecalculator.databinding.FragmentScientificButtonsBinding

class ScientificButtonsFragment : Fragment() {

    private var _binding: FragmentScientificButtonsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScientificButtonsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val allScientificButtons: ArrayList<View?> = binding.scientificButtonsLayout.touchables
        for (view in allScientificButtons) {
            (view as Button).setOnClickListener {
                when(view.id) {
                    binding.buttonInv.id -> invertButtons()
                    binding.buttonPowerMinus1.id -> printButtonText("^-1")
                    binding.buttonSin.id -> printButtonText(if (binding.buttonSin.text.endsWith("n")) "sin(" else "asin(")
                    binding.buttonCos.id -> printButtonText(if (binding.buttonCos.text.endsWith("s")) "cos(" else "acos(")
                    binding.buttonTan.id -> printButtonText(if (binding.buttonTan.text.endsWith("n")) "tan(" else "atan(")
                    binding.buttonLn.id -> printButtonText(if (binding.buttonLn.text.endsWith("n")) "ln(" else "e^")
                    binding.buttonLog.id -> printButtonText(if (binding.buttonLog.text.endsWith("g")) "log10(" else "10^")
                    binding.buttonSqrt.id -> printButtonText(if (binding.buttonSqrt.text.endsWith("√")) "sqrt(" else "^2")
                    binding.buttonPi.id -> printButtonText("pi")
                    binding.buttonE.id,
                    binding.buttonPower.id,
                    binding.buttonFactorial.id -> printButtonText(view.text.toString())
                }
            }
        }
        // TODO: Use the ViewModel
    }

    private fun invertButtons() {
        if (binding.buttonSin.text == "sin") {
            binding.buttonSin.text = getString(R.string.asin_function)
            binding.buttonCos.text = getString(R.string.acos_function)
            binding.buttonTan.text = getString(R.string.atan_function)
            binding.buttonLn.text = getString(R.string.exp_power_function)
            binding.buttonLog.text = getString(R.string.ten_power_function)
            binding.buttonSqrt.text = getString(R.string.square_function)
        } else {
            binding.buttonSin.text = getString(R.string.sin_function)
            binding.buttonCos.text = getString(R.string.cos_function)
            binding.buttonTan.text = getString(R.string.tan_function)
            binding.buttonLn.text = getString(R.string.ln_function)
            binding.buttonLog.text = getString(R.string.log_function)
            binding.buttonSqrt.text = getString(R.string.sqrt_function)
        }
    }

    private fun printButtonText(s: String) {
        println(s)
        viewModel.sendText(s)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}