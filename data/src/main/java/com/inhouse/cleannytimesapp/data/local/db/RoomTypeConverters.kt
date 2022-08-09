package com.inhouse.cleannytimesapp.data.local.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inhouse.cleannytimesapp.data.model.MediaEntity
import com.inhouse.cleannytimesapp.data.model.MediaMetadataEntity
import java.lang.reflect.Type
import javax.inject.Inject

@ProvidedTypeConverter
class RoomTypeConverters @Inject constructor(
    private val gson: Gson
) {
    @TypeConverter
    fun fromMediaList(data: List<MediaEntity?>?): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun toMediaList(json: String?): List<MediaEntity> {
        json?.let {
            val listType: Type = object :
                TypeToken<List<MediaEntity>>() {}.type
            return gson.fromJson(it, listType)
        } ?: run {
            return emptyList()
        }
    }

    @TypeConverter
    fun fromMediaMetadataList(data: List<MediaMetadataEntity?>?): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun toMediaMetadataList(json: String?): List<MediaMetadataEntity> {
        json?.let {
            val listType: Type = object :
                TypeToken<List<MediaMetadataEntity>>() {}.type
            return gson.fromJson(it, listType)
        } ?: run {
            return emptyList()
        }
    }

    @TypeConverter
    fun fromDesFacetList(data: List<String?>?): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun toDesFacetList(json: String?): List<String> {
        json?.let {
            val listType: Type = object :
                TypeToken<List<String>>() {}.type
            return gson.fromJson(it, listType)
        } ?: run {
            return emptyList()
        }
    }
}
