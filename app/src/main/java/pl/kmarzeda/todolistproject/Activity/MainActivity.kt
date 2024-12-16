package pl.kmarzeda.todolistproject.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.kmarzeda.todolistproject.R
import pl.kmarzeda.todolistproject.Class.Person

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("personData", MODE_PRIVATE)

        val firstNameInput = findViewById<EditText>(R.id.firstNameInput)
        val lastNameInput = findViewById<EditText>(R.id.lastNameInput)
        val ageInput = findViewById<EditText>(R.id.ageInput)
        val heightInput = findViewById<EditText>(R.id.heightInput)
        val weightInput = findViewById<EditText>(R.id.weightInput)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val showListButton = findViewById<Button>(R.id.showListButton)

        saveButton.setOnClickListener {
            val firstName = firstNameInput.text.toString()
            val lastName = lastNameInput.text.toString()
            val age = ageInput.text.toString().toIntOrNull()
            val height = heightInput.text.toString().toIntOrNull()
            val weight = weightInput.text.toString().toIntOrNull()

            if (firstName.isEmpty() || lastName.isEmpty() || age == null || height == null || weight == null) {
                Toast.makeText(this, "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (age <= 0 || height !in 50..250 || weight !in 3..200) {
                Toast.makeText(this, "Podaj poprawne dane", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val person = Person(firstName, lastName, age, height, weight)
            savePerson(person)
            Toast.makeText(this, "Osoba zapisana", Toast.LENGTH_SHORT).show()

            firstNameInput.text.clear()
            lastNameInput.text.clear()
            ageInput.text.clear()
            heightInput.text.clear()
            weightInput.text.clear()
        }

        showListButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    private fun savePerson(person: Person) {
        val editor = sharedPreferences.edit()
        val personList = getPersonList().toMutableList()
        personList.add(person)
        val json = Gson().toJson(personList)
        editor.putString("personList", json)
        editor.apply()
    }

    private fun getPersonList(): List<Person> {
        val json = sharedPreferences.getString("personList", null) ?: return emptyList()
        val type = object : TypeToken<List<Person>>() {}.type
        return Gson().fromJson(json, type)
    }
}
