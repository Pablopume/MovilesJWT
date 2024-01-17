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
import com.example.plantillaexamen.domain.modelo.Comida
import com.example.plantillaexamen.framework.pantallamain.SwipeGesture

class ComidaAdapter (
    val context: Context,
    val actions: PersonaActions
) :
    ListAdapter<Comida, ComidaAdapter.ItemViewholder>(DiffCallback()) {

    interface PersonaActions {
        fun onDelete(customer: Comida)
        fun onStartSelectMode(customer: Comida)
        fun itemHasClicked(customer: Comida)
        fun onClickItem(customerId: Int)
    }
    private var selectedMode: Boolean = false
    private var selectedPersonas = mutableSetOf<Comida>()

    fun startSelectMode() {
        selectedMode = true
        notifyDataSetChanged()
    }


    fun resetSelectMode() {
        selectedMode = false
        selectedPersonas.clear()
        notifyDataSetChanged()
    }

    fun setSelectedItems(personasSeleccionadas: List<Comida>){
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

        fun bind(item: Comida) {

            itemView.setOnClickListener {
                actions.onClickItem(item.id)
            }


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

        private fun handleLongClick(item: Comida) {
            if (!selectedMode) {
                actions.onStartSelectMode(item)
            }
        }

        private fun handleClick(item: Comida) {
            if (selectedMode) {
                handleSelectedModeClick(item)
            }
        }

        private fun handleSelectedModeClick(item: Comida) {
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

        private fun setupViews(item: Comida) {
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

    class DiffCallback : DiffUtil.ItemCallback<Comida>() {
        override fun areItemsTheSame(oldItem: Comida, newItem: Comida): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comida, newItem: Comida): Boolean {
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