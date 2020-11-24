package com.whitipet.test.likylist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.whitipet.test.likylist.data.entity.Medicine

@Database(
	entities = [
		Medicine::class
	],
	version = 1
)
abstract class Room : RoomDatabase() {

	companion object {

		@Volatile
		private var INSTANCE: Room? = null

		fun getInstance(context: Context): Room =
			INSTANCE ?: synchronized(this) {
				INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
			}

		private fun buildDatabase(context: Context) =
			androidx.room.Room.databaseBuilder(context.applicationContext, Room::class.java, "Room.db")
				.allowMainThreadQueries()
				.build()
	}

	abstract fun medicineDao(): MedicineDao
}