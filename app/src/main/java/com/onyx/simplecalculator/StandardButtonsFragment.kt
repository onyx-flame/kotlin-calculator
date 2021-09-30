package com.onyx.simplecalculator

import android.content.pm.ActivityInfo
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onyx.simplecalculator.databinding.FragmentStandardButtonsBinding
import android.widget.Button

import java.util.ArrayList

class StandardButtonsFragment : Fragment() {

    private var _binding: FragmentStandardButtonsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStandardButtonsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val allBasicButtons: ArrayList<View>? = binding.standardButtonsLayout.touchables
        for (view in allBasicButtons!!) {
            (view as Button).setOnClickListener {
                when(view.id) {
                    binding.button0.id,
                    binding.button1.id,
                    binding.button2.id,
                    binding.button3.id,
                    binding.button4.id,
                    binding.button5.id,
                    binding.button6.id,
                    binding.button7.id,
                    binding.button8.id,
                    binding.button9.id,
                    binding.buttonLeftBracket.id,
                    binding.buttonRightBracket.id,
                    binding.buttonDot.id,
                    binding.buttonPlus.id,
                    binding.buttonMinus.id -> printButtonText(view.text.toString())
                    binding.buttonMultiply.id -> printButtonText("*")
                    binding.buttonDivide.id -> printButtonText("/")
                    binding.buttonErase.id -> doAction("delete")
                    binding.buttonEquals.id -> doAction("calculate")
                    binding.buttonRotate.id -> changeOrientation()
                }
            }
        }
        binding.buttonErase.setOnLongClickListener {
            doAction("erase")
            true
        }
    }

    private fun changeOrientation() {
        val currentOrientation = activity?.resources?.configuration?.orientation
        if (currentOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun printButtonText(s: String) {
        viewModel.sendText(s)
    }

    private fun doAction(s: String) {
        viewModel.sendAction(s)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}