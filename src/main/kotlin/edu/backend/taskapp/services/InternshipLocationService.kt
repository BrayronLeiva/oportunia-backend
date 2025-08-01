package edu.backend.taskapp.services

import edu.backend.taskapp.dtos.InternshipLocationInput
import edu.backend.taskapp.dtos.InternshipLocationOutput
import edu.backend.taskapp.mappers.InternshipLocationMapper
import edu.backend.taskapp.InternshipLocationRepository
import edu.backend.taskapp.InternshipRepository
import edu.backend.taskapp.LocationCompanyRepository
import edu.backend.taskapp.StudentRepository
import edu.backend.taskapp.dtos.InternshipLocationFlagOutput
import edu.backend.taskapp.dtos.InternshipLocationMatchFlagOutput
import edu.backend.taskapp.dtos.InternshipLocationMatchOutput
import edu.backend.taskapp.dtos.InternshipOutput
import edu.backend.taskapp.dtos.LocationCompanyOutput
import edu.backend.taskapp.dtos.LocationRequestDTO
import edu.backend.taskapp.mappers.CompanyMapper
import edu.backend.taskapp.mappers.InternshipMapper
import edu.backend.taskapp.mappers.LocationCompanyMapper
import edu.backend.taskapp.services.AIService.AIService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

interface InternshipLocationService {
    fun findAll(): List<InternshipLocationOutput>?
    fun findById(id: Long): InternshipLocationOutput?
    fun create(internshipLocationInput: InternshipLocationInput): InternshipLocationOutput?
    fun update(internshipLocationInput: InternshipLocationInput): InternshipLocationOutput?
    fun deleteById(id: Long)
    fun findByLocationCompanyId(id: Long): List<InternshipLocationOutput>
    fun findRecommendedInternshipsByStudent(id: Long): List<InternshipLocationMatchOutput>
    fun findByLocationCompanyIdAndRequestFlagByStudent(idLocationCompany: Long, idStudent: Long): List<InternshipLocationFlagOutput>
    fun findByRequestFlagByStudent(idStudent: Long): List<InternshipLocationFlagOutput>
    fun findRecommendedInternshipsFlagByStudent(studentId: Long): List<InternshipLocationMatchFlagOutput>
    fun findRecommendedInternshipsAvailableByStudent(studentId: Long): List<InternshipLocationMatchOutput>
    fun findAllAvailable(studentId: Long): List<InternshipLocationOutput>

}

@Service
class AbstractInternshipLocationService(
    @Autowired
    val internshipRepository: InternshipRepository,
    @Autowired
    val locationCompanyRepository: LocationCompanyRepository,
    @Autowired
    val internshipLocationRepository: InternshipLocationRepository,
    @Autowired
    val internshipLocationMapper: InternshipLocationMapper,
    @Autowired
    val locationCompanyMapper: LocationCompanyMapper,
    @Autowired
    val internshipMapper: InternshipMapper,
    @Autowired
    val studentService: StudentService,
    @Autowired
    val requestService: RequestService,
    @Autowired
    val aiService: AIService,
    @Autowired
    val studentRepository: StudentRepository
) : InternshipLocationService {

    override fun findAll(): List<InternshipLocationOutput>? {
        return internshipLocationMapper.internshipLocationListToInternshipLocationOutputList(
            internshipLocationRepository.findAll()
        )
    }

    override fun findById(id: Long): InternshipLocationOutput? {
        val entity = internshipLocationRepository.findById(id)
        if (entity.isEmpty) {
            throw NoSuchElementException("The internship location with the id: $id not found!")
        }
        return internshipLocationMapper.internshipLocationToInternshipLocationOutput(entity.get())
    }

    override fun create(internshipLocationInput: InternshipLocationInput): InternshipLocationOutput? {
        val internship = internshipRepository.findById(internshipLocationInput.internshipId!!)
            .orElseThrow { NoSuchElementException("Internship with ID ${internshipLocationInput.internshipId} not found") }

        val locationCompany = locationCompanyRepository.findById(internshipLocationInput.locationCompanyId!!)
            .orElseThrow { NoSuchElementException("LocationCompany with ID ${internshipLocationInput.locationCompanyId} not found") }

        val entity = internshipLocationMapper.internshipLocationInputToInternshipLocation(
            internshipLocationInput,
            locationCompany,
            internship
        )
        entity.internship = internship
        entity.locationCompany = locationCompany

        return internshipLocationMapper.internshipLocationToInternshipLocationOutput(
            internshipLocationRepository.save(entity)
        )
    }

    override fun update(internshipLocationInput: InternshipLocationInput): InternshipLocationOutput? {
        val existing = internshipLocationRepository.findById(internshipLocationInput.idInternshipLocation!!)
        if (existing.isEmpty) {
            throw NoSuchElementException("The internship location with the id: ${internshipLocationInput.idInternshipLocation} not found!")
        }
        val entity = existing.get()
        internshipLocationMapper.internshipLocationInputToInternshipLocation(internshipLocationInput, entity)
        return internshipLocationMapper.internshipLocationToInternshipLocationOutput(
            internshipLocationRepository.save(entity)
        )
    }

    override fun deleteById(id: Long) {
        if (!internshipLocationRepository.findById(id).isEmpty) {
            internshipLocationRepository.deleteById(id)
        } else {
            throw NoSuchElementException("The internship location with the id: $id not found!")
        }
    }

    override fun findByLocationCompanyId(id: Long): List<InternshipLocationOutput> {
        val entities = internshipLocationRepository.findByLocationCompany_IdLocationCompany(id)
        return internshipLocationMapper.internshipLocationListToInternshipLocationOutputList(entities)
    }

    /**
     * Get recommended students by company
     * @param id of the Task
     * @return the Task found
     */
    @Throws(java.util.NoSuchElementException::class)
    override fun findRecommendedInternshipsByStudent(
        studentId: Long
    ): List<InternshipLocationMatchOutput> {

        val student = studentRepository.findByIdStudent(studentId)
        val studentDto = studentService.findById(studentId)

        val nearbyLocations = locationCompanyRepository.findLocationsNear(
            student.get().homeLatitude,
            student.get().homeLongitude,
            radiusKm = 30.0
        )

        val internshipLocationList = nearbyLocations
            .flatMap { location ->
                internshipLocationRepository.findByLocationCompany(location)
            }

        val internshipLocationListOutput = internshipLocationMapper.internshipLocationListToInternshipLocationOutputList(
            internshipLocationList
        )

        return aiService.recommendInternshipsForStudent(studentDto!!, internshipLocationListOutput)
    }

    @Throws(java.util.NoSuchElementException::class)
    override fun findRecommendedInternshipsAvailableByStudent(
        studentId: Long
    ): List<InternshipLocationMatchOutput> {

        val student = studentRepository.findByIdStudent(studentId)

        val nearbyLocations = locationCompanyRepository.findLocationsNear(
            student.get().homeLatitude,
            student.get().homeLongitude,
            radiusKm = 30.0
        )

        val studentDto = studentService.findById(studentId)

        val internshipLocationList = nearbyLocations
            .flatMap { location ->
                internshipLocationRepository.findByLocationCompany(location)
            }

        val studentRequests = try {
            requestService.findByStudentId(studentId)
        } catch (e: Exception) {
            emptyList()
        }

        val studentRequestsIdsInternLocations = studentRequests
            ?.map { it.internshipLocation.idInternshipLocation }
            ?.toSet()

        val filteredInternshipLocations = internshipLocationList.filterNot {
            studentRequestsIdsInternLocations?.contains(it.idInternshipLocation) == true
        }

        val internshipLocationListOutput = internshipLocationMapper
            .internshipLocationListToInternshipLocationOutputList(filteredInternshipLocations)

        return aiService.recommendInternshipsForStudent(studentDto!!, internshipLocationListOutput)
    }


    /**
     * Get recommended students by company
     * @param id of the Task
     * @return the Task found
     */
    @Throws(java.util.NoSuchElementException::class)
    override fun findRecommendedInternshipsFlagByStudent(
        studentId: Long): List<InternshipLocationMatchFlagOutput> {


        val student = studentRepository.findByIdStudent(studentId)

        val nearbyLocations = locationCompanyRepository.findLocationsNear(
            student.get().homeLatitude,
            student.get().homeLongitude,
            radiusKm = 30.0
        )

        val studentDto = studentService.findById(studentId)

        val internshipLocationList = nearbyLocations
            .flatMap { location ->
                internshipLocationRepository.findByLocationCompany(location)
            }

        val internshipLocationListOutput = internshipLocationMapper.internshipLocationListToInternshipLocationOutputList(
            internshipLocationList
        )

        val matchInternships = aiService.recommendInternshipsForStudent(studentDto!!, internshipLocationListOutput)

        val studentRequests = try {
            requestService.findByStudentId(studentId)
        } catch (e: Exception) {
            emptyList()
        }

        val studentRequestsIdsInternLocations = studentRequests
            ?.map { it.internshipLocation.idInternshipLocation }
            ?.toSet()


        return matchInternships.map { il ->
            InternshipLocationMatchFlagOutput(
                idInternshipLocation = il.idInternshipLocation,
                locationCompany = il.locationCompany,
                internship = il.internship,
                score = il.score,
                reason = il.reason,
                requested = studentRequestsIdsInternLocations?.contains(il.idInternshipLocation)!!
            )
        }
    }

    override fun findByLocationCompanyIdAndRequestFlagByStudent(idLocationCompany: Long, idStudent: Long): List<InternshipLocationFlagOutput> {
        val interLocations = internshipLocationRepository.findByLocationCompany_IdLocationCompany(idLocationCompany)
        val studentRequests = try {
            requestService.findByStudentId(idStudent)
        } catch (e: Exception) {
            emptyList()
        }

        val studentRequestsIdsInternLocations = studentRequests
            ?.map { it.internshipLocation.idInternshipLocation }
            ?.toSet()
        
        
        return interLocations.map { il ->
            InternshipLocationFlagOutput(
                idInternshipLocation = il.idInternshipLocation!!,
                locationCompany = locationCompanyMapper.locationCompanyToLocationCompanyOutput(il.locationCompany),
                internship = internshipMapper.internshipToInternshipOutput(il.internship),
                requested = studentRequestsIdsInternLocations?.contains(il.idInternshipLocation)!!
            )
        }

    }

    override fun findByRequestFlagByStudent(idStudent: Long): List<InternshipLocationFlagOutput> {
        val interLocations = internshipLocationRepository.findAll()
        val studentRequests = try {
            requestService.findByStudentId(idStudent)
        } catch (e: Exception) {
            emptyList()
        }


        val studentRequestsIdsInternLocations = studentRequests
            ?.mapNotNull { it.internshipLocation.idInternshipLocation }
            ?.toSet()


        return interLocations.map { il ->
            InternshipLocationFlagOutput(
                idInternshipLocation = il.idInternshipLocation!!,
                locationCompany = locationCompanyMapper.locationCompanyToLocationCompanyOutput(il.locationCompany),
                internship = internshipMapper.internshipToInternshipOutput(il.internship),
                requested = studentRequestsIdsInternLocations?.contains(il.idInternshipLocation)!!
            )
        }

    }

    override fun findAllAvailable(studentId: Long): List<InternshipLocationOutput> {
        val allInternshipLocations = internshipLocationRepository.findAll()

        val studentRequests = try {
            requestService.findByStudentId(studentId)
        } catch (e: Exception) {
            emptyList()
        }

        val requestedIds = studentRequests
            ?.map { it.internshipLocation.idInternshipLocation }
            ?.toSet()

        val filteredLocations = allInternshipLocations.filterNot {
            requestedIds?.contains(it.idInternshipLocation) == true
        }

        return internshipLocationMapper.internshipLocationListToInternshipLocationOutputList(filteredLocations)
    }

}