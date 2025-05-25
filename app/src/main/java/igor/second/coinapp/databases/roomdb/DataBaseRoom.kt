package igor.second.coinapp.databases.roomdb

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class App: Application(){
    val database by lazy {
        DataBaseRoom.createDataBase(this)
    }
}

@Database(
    entities = [RoomData:: class], version = 1)
abstract class DataBaseRoom: RoomDatabase() {
    abstract val dao: RoomDaoInterface
    companion object {
        fun createDataBase(context: Context): DataBaseRoom {
            return Room.databaseBuilder(
                context,
                DataBaseRoom::class.java,
                "MainDb"
            ).build()
        }
    }
}
