package com.inhouse.cleannytimesapp.data.base

import com.inhouse.cleannytimesapp.domain.model.Model

/**
 * Maps from domain model M to entity model MI
 */
interface EntityMapper<M : Model, ME : ModelEntity> {
    fun mapToDomain(entity: ME): M

    fun mapToEntity(model: M): ME
}
