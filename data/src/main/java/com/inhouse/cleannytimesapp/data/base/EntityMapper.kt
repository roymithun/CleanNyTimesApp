package com.inhouse.cleannytimesapp.data.base

import com.inhouse.cleannytimesapp.domain.model.Model

interface EntityMapper<M : Model, ME : ModelEntity> {
    fun mapToDomain(entity: ME): M

    fun mapToEntity(model: M): ME
}