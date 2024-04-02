package com.example.room.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.room.R
import com.example.room.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentList: List<Word>  // Список для хранения текущих слов
    private val regexFilter = "[a-zA-Zа-яА-Я-']+"

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    return MainViewModel(Repository(context?.applicationContext)) as T  // Создание ViewModel
                } else {
                    throw IllegalArgumentException("")  // Ошибка, если класс не соответствует ожидаемому
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        viewModel.getList().asLiveData().observe(viewLifecycleOwner) { list ->  // Наблюдение за списком слов
            currentList = list  // Обновление текущего списка слов
            binding.wordsList.text = getString(R.string.repeated_the_word)  // Заполнение поля текстом
            list.forEach { word ->  // Перебор слов в списке
                val wordsList = "${word.value} - (${word.repetition})\n"  // Формирование строки с словом и количеством повторений
                binding.wordsList.append(wordsList)  // Добавление строки в список
            }
        }
        binding.addButton.setOnClickListener {  // Обработчик клика по кнопке добавления слова
            val matchResult = Regex(regexFilter).matches(binding.editText.text)  // Проверка текста на соответствие регулярному выражению
            when (matchResult) {
                true -> {
                    val word = Word(binding.editText.text.toString(), 1)  // Создание объекта слова
                    binding.editText.text.clear()  // Очистка текстового поля
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.insertOrUpdate(word)  // Вставка или обновление слова в базе данных
                    }
                }
                false -> Toast.makeText(
                    requireContext(),
                    getString(R.string.regex_toast_message),
                    Toast.LENGTH_SHORT
                ).show()  // Показ сообщения об ошибке, если текст не соответствует регулярному выражению
            }
        }
        binding.clearButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.clearDb()  // Очистка базы данных
            }
        }
        return binding.root  // Возвращение корневого View элемента
    }
}