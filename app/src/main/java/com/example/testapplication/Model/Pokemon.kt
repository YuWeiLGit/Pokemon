package com.example.testapplication.Model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "pokemon_table")
data class Pokemon(
    val name: String?,
    @PrimaryKey
    @SerializedName("id") val id: String = "",
    @SerializedName("imageurl") val imageUrl: String?,
    @SerializedName("xdescription") val xDescription: String?,
    @SerializedName("ydescription") val yDescription: String?,
    @SerializedName("height") val height: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("weight") val weight: String?,
    @SerializedName("typeofpokemon") val typeOfPokemon: List<String>?,
    @SerializedName("weaknesses") val weaknesses: List<String>?,
    @SerializedName("evolutions") val evolutions: List<String>?,
    @SerializedName("abilities") val abilities: List<String>?,
    @SerializedName("hp") val hp: Int,
    @SerializedName("attack") val attack: Int,
    @SerializedName("defense") val defense: Int,
    @SerializedName("special_attack") val specialAttack: Int,
    @SerializedName("special_defense") val specialDefense: Int,
    @SerializedName("speed") val speed: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("male_percentage") val malePercentage: String?,
    @SerializedName("female_percentage") val femalePercentage: String?,
    @SerializedName("genderless") val genderless: Int,
    @SerializedName("cycles") val cycles: String?,
    @SerializedName("egg_groups") val eggGroups: String?,
    @SerializedName("evolvedfrom") val evolvedFrom: String?,
    @SerializedName("reason") val reason: String?,
    @SerializedName("base_exp") val baseExp: String?,
    var isCollect: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(id)
        parcel.writeString(imageUrl)
        parcel.writeString(xDescription)
        parcel.writeString(yDescription)
        parcel.writeString(height)
        parcel.writeString(category)
        parcel.writeString(weight)
        parcel.writeStringList(typeOfPokemon)
        parcel.writeStringList(weaknesses)
        parcel.writeStringList(evolutions)
        parcel.writeStringList(abilities)
        parcel.writeInt(hp)
        parcel.writeInt(attack)
        parcel.writeInt(defense)
        parcel.writeInt(specialAttack)
        parcel.writeInt(specialDefense)
        parcel.writeInt(speed)
        parcel.writeInt(total)
        parcel.writeString(malePercentage)
        parcel.writeString(femalePercentage)
        parcel.writeInt(genderless)
        parcel.writeString(cycles)
        parcel.writeString(eggGroups)
        parcel.writeString(evolvedFrom)
        parcel.writeString(reason)
        parcel.writeString(baseExp)
        parcel.writeByte(if (isCollect) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }


}