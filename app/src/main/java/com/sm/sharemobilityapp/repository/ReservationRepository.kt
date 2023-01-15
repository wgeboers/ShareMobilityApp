package com.sm.sharemobilityapp.repository

import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.data.reservation.Reservation
import com.sm.sharemobilityapp.data.reservation.asDomainModel
import com.sm.sharemobilityapp.model.ReservationModel
import com.sm.sharemobilityapp.network.ReservationInfo
import com.sm.sharemobilityapp.network.ShareMobilityApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ReservationRepository(private val database: SMRoomDatabase) {
    val reservations: Flow<List<ReservationModel>> =
        database.reservationDao.getAll().map { it.asDomainModel() }

    suspend fun refreshReservations() {
        withContext(Dispatchers.IO) {
            val reservationList: List<ReservationInfo> =
                ShareMobilityApi.retrofitService.getReservations()
            val reservations = prepareReservations(reservationList)
            database.reservationDao.deleteAllReservations()
            database.reservationDao.insertAll(reservations)
        }
    }

    suspend fun insertReservation(reservation: Reservation) {
        ShareMobilityApi.retrofitService.postReservation(reservation)
    }

    suspend fun getReservationsByUser(userId: Int): Flow<List<ReservationModel>>{
        val reservations: Flow<List<ReservationModel>> =
            database.reservationDao.getReservationsByUserId(userId).map { it.asDomainModel() }

        return reservations
    }

    private fun prepareReservations(reservationInfo: List<ReservationInfo>): List<Reservation> {
        var reservationMapping: List<Reservation> = reservationInfo.map {
            Reservation(
                id = it.id,
                carId = it.carInfo.id!!,
                userId = it.userInfo.id!!,
                startReservation = it.startReservation,
                endReservation = it.endReservation
            )
        }

        return reservationMapping
    }
}