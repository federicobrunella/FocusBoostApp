package com.example.focusboostapp.stats

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
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
                 val completedSession: Boolean = false
) : Parcelable {

}