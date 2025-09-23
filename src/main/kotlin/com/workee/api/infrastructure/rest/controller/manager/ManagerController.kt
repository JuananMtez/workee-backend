package com.workee.api.infrastructure.rest.controller.manager

import com.workee.api.domain.service.manager.ManagerService
import com.workee.api.infrastructure.rest.controller.manager.message.request.CreateManagerRequest
import com.workee.api.infrastructure.rest.controller.manager.message.response.ManagerResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/manager")
class ManagerController(
    private val managerService: ManagerService,
    private val managerControllerMapper: ManagerControllerMapper
) {

    @PostMapping
    fun createManager(@Validated @RequestBody createManagerRequest: CreateManagerRequest): ResponseEntity<ManagerResponse> {
        val createManagerDTO = managerControllerMapper.asCreateManagerDTO(createManagerRequest)
        val manager = managerService.createManager(createManagerDTO)
        val managerResponse = managerControllerMapper.asManagerResponse(manager)

        return ResponseEntity.ok(managerResponse)
    }

}