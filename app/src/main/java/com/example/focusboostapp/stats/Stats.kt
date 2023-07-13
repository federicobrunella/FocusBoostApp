package com.example.focusboostapp.stats

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
@Parcelize
data class Stats(val user: String = "user",
                 val startDateTime: String ?= null,
                 val stopDateTime: String ?= null,
                 val sessionLengthHours: Int ?= null,
                 val sessionLengthMinutes: Int ?= null,
                 val sessionLengthSeconds: Int ?= null,
                 val DND: Boolean = false,
                 val immersiveMode: Boolean = false,
                 val completedSession: Boolean = false,
                 val timestamp: Timestamp?= null,
                 val advancedSettings: Boolean ?= null,
                 val userDistractions: ArrayList<Timestamp> ?= null
) : Parcelable {
}