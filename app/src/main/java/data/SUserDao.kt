package data

import androidx.room.*

@Dao
interface SUserDao {
    @Query("SELECT * FROM USER WHERE name == :name")
    fun getUserInfo(name: String = "guru"): SUserEntity

    @Insert
    fun insertUserInfo(userEntity: SUserEntity?)

}