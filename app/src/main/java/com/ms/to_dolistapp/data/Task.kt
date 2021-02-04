package com.ms.to_dolistapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

/**
 * Created by Mehedi Hasan on 2/2/2021.
 */

@Entity(tableName = "task_table")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val important: Boolean = false,
    val completed: Boolean = false,
    val created: Long = System.currentTimeMillis()
) : Parcelable {
    val createdDateFormatted: String get() = DateFormat.getDateTimeInstance().format(created)
}