import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plantillaexamen.R
import com.example.plantillaexamen.databinding.ViewCustomerBinding
import com.example.plantillaexamen.domain.modelo.Ingrediente
import com.example.plantillaexamen.framework.pantallamain.SwipeGesture

class IngredienteAdapter (
    val context: Context,
    val actions: PersonaActions
) :
    ListAdapter<Ingrediente, IngredienteAdapter.ItemViewholder>(DiffCallback()) {

    interface PersonaActions {
        fun onDelete(customer: Ingrediente)
        fun onStartSelectMode(customer: Ingrediente)
        fun itemHasClicked(customer: Ingrediente)

    }
    private var selectedMode: Boolean = false
    private var selectedPersonas = mutableSetOf<Ingrediente>()

    fun startSelectMode() {
        selectedMode = true
        notifyDataSetChanged()
    }


    fun resetSelectMode() {
        selectedMode = false
        selectedPersonas.clear()
        notifyDataSetChanged()
    }

    fun setSelectedItems(personasSeleccionadas: List<Ingrediente>){
        selectedPersonas.clear()
        selectedPersonas.addAll(personasSeleccionadas)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {

        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_comida, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {

        val item = getItem(position)
        bind(item)

    }

    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ViewCustomerBinding.bind(itemView)

        fun bind(item: Ingrediente) {




            itemView.setOnLongClickListener {
                handleLongClick(item)
                true
            }

            with(binding) {
                selected.setOnClickListener {
                    handleClick(item)
                }

                setupViews(item)
            }
        }

        private fun handleLongClick(item: Ingrediente) {
            if (!selectedMode) {
                actions.onStartSelectMode(item)
            }
        }

        private fun handleClick(item: Ingrediente) {
            if (selectedMode) {
                handleSelectedModeClick(item)
            }
        }

        private fun handleSelectedModeClick(item: Ingrediente) {
            with(binding) {
                if (selected.isChecked) {
                    item.isSelected = true
                    itemView.setBackgroundColor(Color.GREEN)
                    selectedPersonas.add(item)
                } else {
                    item.isSelected = false
                    itemView.setBackgroundColor(Color.WHITE)
                    selectedPersonas.remove(item)
                }

                actions.itemHasClicked(item)
            }
        }

        private fun setupViews(item: Ingrediente) {
            with(binding) {
                tvNombre.text = item.nombre
                tvId.text = item.id.toString()

                selected.visibility = if (selectedMode) View.VISIBLE else View.GONE

                if (selectedPersonas.contains(item)) {
                    itemView.setBackgroundColor(Color.GREEN)
                    selected.isChecked = true
                } else {
                    itemView.setBackgroundColor(Color.WHITE)
                    selected.isChecked = false
                }
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Ingrediente>() {
        override fun areItemsTheSame(oldItem: Ingrediente, newItem: Ingrediente): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ingrediente, newItem: Ingrediente): Boolean {
            return oldItem == newItem
        }
    }

    val swipeGesture = object : SwipeGesture(context) {

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val position = viewHolder.bindingAdapterPosition
                    selectedPersonas.remove(currentList[position])
                    actions.onDelete(currentList[position])
                    if (selectedMode)
                        actions.itemHasClicked(currentList[position])
                }
            }
        }

    }

}