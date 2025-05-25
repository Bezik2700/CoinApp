package igor.second.coinapp.databases.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val unique: Int
)
