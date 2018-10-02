package com.moducode.gw2serveralarm.data

import android.graphics.Color

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServerModel(@field:SerializedName("id")
                  @field:Expose
                  val id: Int,
                  @field:SerializedName("name")
                  @field:Expose
                  val name: String,
                  @field:SerializedName("population")
                  @field:Expose
                  val population: String) : Comparable<ServerModel> {

    private val populationLevel: Int
        get() {
            return when (population) {
                "Low" -> 0
                "Medium" -> 1
                "High" -> 2
                "VeryHigh" -> 3
                "Full" -> 4
                else -> 0
            }
        }

    val populationColor: Int
        get() {
            return when (population) {
                "Low" -> Color.parseColor("#C6E0B4")
                "Medium" -> Color.parseColor("#A9D08E")
                "High" -> Color.parseColor("#FFC000")
                "VeryHigh" -> Color.parseColor("#ED7D31")
                "Full" -> Color.parseColor("#FF0000")
                else -> Color.parseColor("#C6E0B4")
            }
        }

    val idString: String
        get() = this.id.toString()

    override fun compareTo(other: ServerModel): Int {
        return other.populationLevel - this.populationLevel
    }

    companion object {
        val fakeServer: ServerModel
            get() = ServerModel(6969, "TestServer", "null")
    }
}
