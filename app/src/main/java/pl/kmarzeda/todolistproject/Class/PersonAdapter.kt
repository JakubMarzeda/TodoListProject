package pl.kmarzeda.todolistproject.Class

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.kmarzeda.todolistproject.R

class PersonAdapter(
    private val personList: MutableList<Person>,
    private val onDeleteClick: (Person) -> Unit
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.nameText)
        val details: TextView = view.findViewById(R.id.detailsText)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = personList[position]
        holder.name.text = "${person.firstName} ${person.lastName}"
        holder.details.text = "Wiek: ${person.age}, Wzrost: ${person.height} cm, Waga: ${person.weight} kg"
        holder.deleteButton.setOnClickListener {
            onDeleteClick(person)
        }
    }

    override fun getItemCount(): Int = personList.size

    companion object {
        fun getPersonList(sharedPreferences: SharedPreferences): List<Person> {
            val json = sharedPreferences.getString("personList", null) ?: return emptyList()
            val type = object : TypeToken<List<Person>>() {}.type
            return Gson().fromJson(json, type)
        }

        fun savePersonList(sharedPreferences: SharedPreferences, personList: List<Person>) {
            val editor = sharedPreferences.edit()
            val json = Gson().toJson(personList)
            editor.putString("personList", json)
            editor.apply()
        }
    }
}
