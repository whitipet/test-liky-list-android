package com.whitipet.test.likylist.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.whitipet.test.likylist.data.entity.Medicine

@Dao
interface MedicineDao {

	@Query("SELECT * FROM medicine")
	fun getAll(): LiveData<List<Medicine>>

	@Query("SELECT * FROM medicine WHERE id LIKE :id LIMIT 1")
	fun getById(id: String): LiveData<Medicine>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(medicine: Medicine)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(medicines: List<Medicine>)
}