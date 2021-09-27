package com.onyx.simplecalculator

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.onyx.simplecalculator.databinding.FragmentScientificButtonsBinding
import com.onyx.simplecalculator.databinding.FragmentStandardButtonsBinding

class ScientificButtonsFragment : Fragment() {

    private var _binding: FragmentScientificButtonsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScientificButtonsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val allScientificButtons: ArrayList<View?> = binding.scientificButtonsLayout.touchables
        for (view in allScientificButtons!!) {
            (view as Button).setOnClickListener {
                when(view.id) {
                    binding.buttonInv.id -> invertButtons()
                    binding.buttonPowerMinus1.id -> printButtonText("^-1")
                    binding.buttonFactorial.id -> printButtonText(view.text.toString())
                    binding.buttonSin.id -> printButtonText(if (binding.buttonSin.text.endsWith("n")) "sin(" else "asin(")
                    binding.buttonCos.id -> printButtonText(if (binding.buttonCos.text.endsWith("s")) "cos(" else "acos(")
                    binding.buttonTan.id -> printButtonText(if (binding.buttonTan.text.endsWith("n")) "tan(" else "atan(")
                    binding.buttonLn.id -> printButtonText(if (binding.buttonLn.text.endsWith("n")) "ln(" else "e^")
                    binding.buttonLog.id -> printButtonText(if (binding.buttonLog.text.endsWith("g")) "log10(" else "10^")
                    binding.buttonSqrt.id -> printButtonText(if (binding.buttonSqrt.text.endsWith("√")) "sqrt(" else "^2")
                    binding.buttonPi.id -> printButtonText("pi")
                    binding.buttonE.id,
                    binding.buttonPower.id -> printButtonText(view.text.toString())
                }
            }
        }
        // TODO: Use the ViewModel
    }

    private fun invertButtons() {
        if (binding.buttonSin.text == "sin") {
            binding.buttonSin.text = "sin⁻¹"
            binding.buttonCos.text = "cos⁻¹"
            binding.buttonTan.text = "tan⁻¹"
            binding.buttonLn.text = "eˣ"
            binding.buttonLog.text = "10ˣ"
            binding.buttonSqrt.text = "x²"
        } else {
            binding.buttonSin.text = "sin"
            binding.buttonCos.text = "cos"
            binding.buttonTan.text = "tan"
            binding.buttonLn.text = "ln"
            binding.buttonLog.text = "log"
            binding.buttonSqrt.text = "√"
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