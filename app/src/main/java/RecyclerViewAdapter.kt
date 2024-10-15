import android.annotation.SuppressLint
import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.example.tugas5kelasa.R

    class RecyclerViewAdapter(private val itemList: MutableList<String>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

        // Membuat ViewHolder untuk menghubungkan item_view.xml
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val itemName: TextView = view.findViewById(R.id.itemName)
        }

        // Menghubungkan item_view.xml ke RecyclerView
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
            return ViewHolder(view)
        }

        // Menghubungkan data dengan view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = itemList[position]
            holder.itemName.text = item
        }

        // Menentukan jumlah item yang akan ditampilkan
        override fun getItemCount(): Int {
            return itemList.size
        }
    }
