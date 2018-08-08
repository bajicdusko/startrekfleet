package com.bajicdusko.data.interactor

import androidx.lifecycle.LiveData
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.repository.ShipClassRepository

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */
open class GetShipClasses constructor(private val shipClassRepository: ShipClassRepository) : UseCase<Unit, ShipClass> {

  override fun buildUseCase(param: Unit): LiveData<ResponseWrapper<List<ShipClass>>> = shipClassRepository.getShipClasses()
}