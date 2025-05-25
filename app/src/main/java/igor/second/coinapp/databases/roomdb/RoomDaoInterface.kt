package igor.second.coinapp.databases.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDaoInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(roomData: RoomData)

    @Delete
    suspend fun deleteItem(roomData: RoomData)

    @Query("SELECT * FROM RoomData")
    fun getAllItems(): Flow<List<RoomData>>

}