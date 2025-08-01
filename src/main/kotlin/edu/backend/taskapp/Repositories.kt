package edu.backend.taskapp

import edu.backend.taskapp.entities.Certification
import edu.backend.taskapp.entities.Company
import edu.backend.taskapp.entities.Internship
import edu.backend.taskapp.entities.InternshipLocation
import edu.backend.taskapp.entities.LocationCompany
import edu.backend.taskapp.entities.Privilege
import edu.backend.taskapp.entities.Qualification
import edu.backend.taskapp.entities.Question
import edu.backend.taskapp.entities.RatingCompanyStudent
import edu.backend.taskapp.entities.Recommendation
import edu.backend.taskapp.entities.Student
import edu.backend.taskapp.entities.Request
import edu.backend.taskapp.entities.Role
import edu.backend.taskapp.entities.User
import edu.backend.taskapp.entities.UserRole
import edu.backend.taskapp.entities.UserRoleId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional


@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
}

@Repository
interface PriorityRepository: JpaRepository<Priority, Long>

@Repository
interface TaskRepository: JpaRepository<Task, Long>

@Repository
interface StudentRepository: JpaRepository<Student, Long> {

    @Query("""
        SELECT DISTINCT r.student
        FROM Request r
        JOIN r.internshipLocation il
        JOIN il.locationCompany lc
        JOIN lc.company c
        WHERE c.idCompany = :companyId
    """)
    fun findStudentsRequestingByCompanyId(@Param("companyId") companyId: Long): List<Student>

    fun findByUserId(userId: Long): Optional<Student>

    fun findByIdStudent(@Param("studentId") studentId: Long): Optional<Student>
}

@Repository
interface QuestionRepository: JpaRepository<Question, Long>

@Repository
interface RecommendationRepository: JpaRepository<Recommendation, Long>

@Repository
interface CertificationRepository: JpaRepository<Certification, Long>

@Repository
interface QualificationRepository: JpaRepository<Qualification, Long>

@Repository
interface InternshipRepository: JpaRepository<Internship, Long>{
    @Query("""
        SELECT DISTINCT i
        FROM Internship i
        JOIN i.internshipLocations il
        WHERE il.locationCompany.idLocationCompany = :locationId
    """)
    fun findByLocationCompanyId(@Param("locationId") locationId: Long): List<Internship>
}

@Repository
interface CompanyRepository: JpaRepository<Company, Long>{
    fun findByUserId(userId: Long): Optional<Company>
}

@Repository
interface LocationCompanyRepository: JpaRepository<LocationCompany, Long> {
    @Query(
        """
    SELECT l FROM LocationCompany l
    WHERE 
        (6371 * acos(
            cos(radians(:latitude)) * cos(radians(l.latitude)) *
            cos(radians(l.longitude) - radians(:longitude)) +
            sin(radians(:latitude)) * sin(radians(l.latitude))
        )) < :radiusKm
    """
    )
    fun findLocationsNear(
        @Param("latitude") latitude: Double,
        @Param("longitude") longitude: Double,
        @Param("radiusKm") radiusKm: Double = 30.0
    ): List<LocationCompany>

}

@Repository
interface InternshipLocationRepository: JpaRepository<InternshipLocation, Long>{
    fun findByLocationCompany(locationCompany: LocationCompany): List<InternshipLocation>
    fun findByLocationCompany_IdLocationCompany(id: Long): List<InternshipLocation>
}

@Repository
interface RatingCompanyStudentRepository: JpaRepository<RatingCompanyStudent, Long>

@Repository
interface RequestRepository: JpaRepository<Request, Long>{
    fun findByStudent_IdStudent(studentId: Long): List<Request>
    fun existsRequestByStudent_IdStudentAndInternshipLocation_IdInternshipLocation(studentId: Long, internshipLocationId: Long): Boolean
    @Query("""
        SELECT r FROM Request r 
        WHERE r.student.idStudent = :studentId 
        AND r.internshipLocation.locationCompany.company.idCompany = :companyId
    """)
    fun findByStudentIdAndCompanyId(
        @Param("studentId") studentId: Long,
        @Param("companyId") companyId: Long
    ): List<Request>

    fun deleteByInternshipLocation_IdInternshipLocationAndStudent_IdStudent(idInternshipLocation: Long, idStudent: Long): Unit
    fun findByInternshipLocation_IdInternshipLocationAndStudent_IdStudent(idInternshipLocation: Long, internshipLocation: Long): Optional<Request>
}

@Repository
interface RoleRepository: JpaRepository<Role, Long> {
    fun findByName(name: String): Optional<Role>
}

@Repository
interface PrivilegeRepository: JpaRepository<Privilege, Long>

@Repository
interface UserRoleRepository: JpaRepository<UserRole, UserRoleId>

