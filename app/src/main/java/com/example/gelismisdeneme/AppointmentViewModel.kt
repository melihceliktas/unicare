package com.example.gelismisdeneme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {
    private val database = Room.databaseBuilder(
        application,
        AppointmentDatabase::class.java,
        "appointments_db"
    ).build()
    
    private val dao = database.appointmentDao()
    private val _appointments = MutableStateFlow<List<AppointmentEntity>>(emptyList())
    val appointments: StateFlow<List<AppointmentEntity>> = _appointments

    init {
        loadAppointments()
    }

    private fun loadAppointments() {
        viewModelScope.launch {
            dao.getAllAppointments().collect {
                _appointments.value = it
            }
        }
    }

    fun addAppointment(date: String, time: String, reason: String) {
        viewModelScope.launch {
            val appointment = AppointmentEntity(
                date = date,
                time = time,
                reason = reason
            )
            dao.insertAppointment(appointment)
        }
    }

    fun deleteAppointment(appointment: AppointmentEntity) {
        viewModelScope.launch {
            dao.deleteAppointment(appointment)
        }
    }
} 