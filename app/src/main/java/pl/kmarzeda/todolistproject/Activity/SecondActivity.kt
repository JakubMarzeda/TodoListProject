package pl.kmarzeda.todolistproject.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.kmarzeda.todolistproject.R
import pl.kmarzeda.todolistproject.Class.Person
import pl.kmarzeda.todolistproject.Class.PersonAdapter

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPreferences = getSharedPreferences("personData", MODE_PRIVATE)
        val personList = PersonAdapter.getPersonList(sharedPreferences).toMutableList()

        val adapter = PersonAdapter(personList) { person ->
            personList.remove(person)
            PersonAdapter.savePersonList(sharedPreferences, personList)
            recyclerView.adapter?.notifyDataSetChanged()
        }

        recyclerView.adapter = adapter
    }
}
