package com.inhouse.cleannytimesapp.base

import com.inhouse.cleannytimesapp.domain.model.Model

/**
 * Maps from domain model M to presentation model MI
 */
interface ItemMapper<M : Model, MI : ModelItem> {
    fun mapToPresentation(model: M): ModelItem

    fun mapToDomain(modelItem: MI): Model
}