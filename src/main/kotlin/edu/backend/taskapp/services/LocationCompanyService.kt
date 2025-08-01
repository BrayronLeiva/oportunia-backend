package edu.backend.taskapp.services

import edu.backend.taskapp.CompanyRepository
import edu.backend.taskapp.LocationCompanyRepository
import edu.backend.taskapp.dtos.LocationCompanyInput
import edu.backend.taskapp.dtos.LocationCompanyOutput
import edu.backend.taskapp.mappers.LocationCompanyMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface LocationCompanyService {
    fun findAll(): List<LocationCompanyOutput>?
    fun findById(id: Long): LocationCompanyOutput?
    fun create(locationCompanyInput: LocationCompanyInput): LocationCompanyOutput?
    fun update(locationCompanyInput: LocationCompanyInput): LocationCompanyOutput?
    fun deleteById(id: Long)
}

@Service
class AbstractLocationCompanyService(
    @Autowired
    val companyRepository: CompanyRepository,
    @Autowired
    val locationCompanyRepository: LocationCompanyRepository,
    @Autowired
    val locationCompanyMapper: LocationCompanyMapper,
) : LocationCompanyService {

    override fun findAll(): List<LocationCompanyOutput>? {
        return locationCompanyMapper.locationCompanyListToLocationCompanyOutputList(
            locationCompanyRepository.findAll()
        )
    }

    override fun findById(id: Long): LocationCompanyOutput? {
        val locationCompany = locationCompanyRepository.findById(id)
        if (locationCompany.isEmpty) {
            throw NoSuchElementException("The location company with the id: $id not found!")
        }
        return locationCompanyMapper.locationCompanyToLocationCompanyOutput(locationCompany.get())
    }

    override fun create(locationCompanyInput: LocationCompanyInput): LocationCompanyOutput? {
        val company = companyRepository.findById(locationCompanyInput.companyId!!)
            .orElseThrow { NoSuchElementException("Company with ID ${locationCompanyInput.companyId} not found") }

        val locationCompany = locationCompanyMapper.locationCompanyInputToLocationCompany(locationCompanyInput, company)
        locationCompany.company = company

        return locationCompanyMapper.locationCompanyToLocationCompanyOutput(
            locationCompanyRepository.save(locationCompany)
        )
    }

    override fun update(locationCompanyInput: LocationCompanyInput): LocationCompanyOutput? {
        val locationCompany = locationCompanyRepository.findById(locationCompanyInput.idLocationCompany!!)
        if (locationCompany.isEmpty) {
            throw NoSuchElementException("The location company with the id: ${locationCompanyInput.idLocationCompany} not found!")
        }
        val updated = locationCompany.get()
        locationCompanyMapper.locationCompanyInputToLocationCompany(locationCompanyInput, updated)
        return locationCompanyMapper.locationCompanyToLocationCompanyOutput(
            locationCompanyRepository.save(updated)
        )
    }

    override fun deleteById(id: Long) {
        if (!locationCompanyRepository.findById(id).isEmpty) {
            locationCompanyRepository.deleteById(id)
        } else {
            throw NoSuchElementException("The location company with the id: $id not found!")
        }
    }
}