package com.example.prakt_16_2
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var textViewResult: TextView
    private lateinit var buttonSave: Button
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val KEY_USERNAME = "username"
        private const val KEY_IS_FIRST_RUN = "isFirstRun"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация элементов
        editTextName = findViewById(R.id.editTextName)
        textViewResult = findViewById(R.id.textViewResult)
        buttonSave = findViewById(R.id.buttonSave)

        // Инициализация SharedPreferences (встроено в Android)
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        // Проверка первого запуска
        val isFirstRun = sharedPreferences.getBoolean(KEY_IS_FIRST_RUN, true)
        val savedUsername = sharedPreferences.getString(KEY_USERNAME, "") ?: ""

        android.util.Log.d("MyApp", "isFirstRun = $isFirstRun")
        android.util.Log.d("MyApp", "savedUsername = $savedUsername")

        if (isFirstRun) {
            // Первый запуск - показываем поля для ввода
            setupFirstRun()
        } else if (savedUsername.isNotEmpty()) {
            // Последующие запуски с сохраненным именем
            showSavedUsername(savedUsername)
        } else {
            // Если имя не было сохранено
            showNoName()
        }
    }

    private fun setupFirstRun() {
        // Делаем видимыми и активными все элементы для ввода
        editTextName.visibility = View.VISIBLE
        editTextName.isEnabled = true
        editTextName.isFocusable = true
        editTextName.isFocusableInTouchMode = true
        editTextName.requestFocus() // Устанавливаем фокус на поле ввода

        buttonSave.visibility = View.VISIBLE
        buttonSave.isEnabled = true

        // Сообщение пользователю
        textViewResult.text = "Введите ваше имя:"
        textViewResult.textSize = 20f

        // Обработчик нажатия кнопки
        buttonSave.setOnClickListener {
            val name = editTextName.text.toString().trim()
            if (name.isNotEmpty()) {
                // Сохраняем имя
                val editor = sharedPreferences.edit()
                editor.putString(KEY_USERNAME, name)
                editor.putBoolean(KEY_IS_FIRST_RUN, false)
                editor.apply()

                // Отображаем сохраненное имя
                showSavedUsername(name)
            } else {
                // Если имя не введено
                showNoName()
            }
        }
    }

    private fun showSavedUsername(username: String) {
        // Скрываем поле ввода и кнопку
        editTextName.visibility = View.GONE
        buttonSave.visibility = View.GONE

        // Отображаем сохраненное имя
        textViewResult.text = "Ваше имя: $username"
        textViewResult.textSize = 20f
    }

    private fun showNoName() {
        // Скрываем поле ввода и кнопку
        editTextName.visibility = View.GONE
        buttonSave.visibility = View.GONE

        // Отображаем сообщение
        textViewResult.text = "Имя не введено"
        textViewResult.textSize = 20f
    }
}