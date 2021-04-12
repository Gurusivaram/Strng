package data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "USER")
data class SUser(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Int? = null,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "contacts") var contacts: MutableList<SContactsModel>
)