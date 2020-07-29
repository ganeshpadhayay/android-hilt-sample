package com.example.android_hilt_sample.util

/***
 * maps the entity class to and fro domain model class
 */
interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity

}