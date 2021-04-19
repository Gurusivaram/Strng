package data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

@Entity(tableName = "USER")
data class SUserEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Int? = null,
        @ColumnInfo(name = "name") val name: String = "guru",
        @ColumnInfo(name = "contacts") var contacts: List<SContactModel>,
        @ColumnInfo(name = "profileImages") var profileImages: List<SUserImageModel>?
)

data class SContactModel(val name: String?, val number: String)
data class SUserImageModel(var uri: Uri?, var isPrimary: Boolean)

class SContactsTypeConverter {
    @TypeConverter
    fun listToJson(value: List<SContactModel>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<SContactModel>::class.java).toList()
}

class SUserImageTypeConverter {
    @TypeConverter
    fun listToJson(value: List<SUserImageModel>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<SUserImageModel>::class.java).toList()
}