package com.example.easyfood.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.easyfood.model.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var Instance: MealDatabase? = null

        fun getDatabase(context: Context): MealDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, MealDatabase::class.java, "meal.db").fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
}