package igor.second.coinapp.databases.roomdb

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch

class MainViewModel(private val database: DataBaseRoom): ViewModel() {

    val itemList = database.dao.getAllItems()

    val newUnique = mutableIntStateOf(0)

    var roomData: RoomData? = null

    fun insertItem() = viewModelScope.launch {
        val roomItem = roomData?.copy(
            unique = newUnique.intValue,
        ) ?: RoomData(
            unique = newUnique.intValue,
        )
        database.dao.insertItem(roomItem)
        roomData = null
        newUnique.intValue = 0
    }

    fun deleteItem(item: RoomData) = viewModelScope.launch {
        database.dao.deleteItem(item)
    }

    companion object{
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return MainViewModel(database) as T
            }
        }
    }
}