package com.example.gelismisdeneme

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val time: String,
    val reason: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments ORDER BY createdAt DESC")
    fun getAllAppointments(): Flow<List<AppointmentEntity>>

    @Insert
    suspend fun insertAppointment(appointment: AppointmentEntity)

    @Delete
    suspend fun deleteAppointment(appointment: AppointmentEntity)
}

@Database(entities = [AppointmentEntity::class], version = 1)
abstract class AppointmentDatabase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao
}