package com.green.robot.rickandmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.green.robot.rickandmorty.data.database.entity.CharacterDb

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    suspend fun getCharacters(): List<CharacterDb>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterDb

    @Query("""
        SELECT * FROM characters
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
        AND (:status IS NULL OR status = :status)
        AND (:species IS NULL OR species LIKE '%' || :species || '%')
        AND (:gender IS NULL OR gender = :gender)
    """)
    suspend fun searchCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ): List<CharacterDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterDb>)

    @Update
    suspend fun updateCharacter(character: CharacterDb)

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()

    @Query("DELETE FROM characters WHERE id = :id")
    suspend fun deleteCharacterById(id: Int)

}