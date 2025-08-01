package edu.backend.taskapp

import edu.backend.taskapp.entities.Certification
import edu.backend.taskapp.entities.Internship
import edu.backend.taskapp.entities.InternshipLocation
import edu.backend.taskapp.entities.LocationCompany
import edu.backend.taskapp.entities.Company
import edu.backend.taskapp.entities.Qualification
import edu.backend.taskapp.entities.Question
import edu.backend.taskapp.entities.RatingCompanyStudent
import edu.backend.taskapp.entities.Recommendation
import edu.backend.taskapp.entities.Request
import edu.backend.taskapp.entities.Student
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(
    statements = [
        "DELETE FROM public.certifications",
        "DELETE FROM public.recommendations",
        "DELETE FROM public.ratings_companies_students",
        "DELETE FROM public.requests",
        "DELETE FROM public.students",
        "DELETE FROM public.qualifications",
        "DELETE FROM public.questions",
        "DELETE FROM public.internships_locations",
        "DELETE FROM public.locations_company",
        "DELETE FROM public.companies",
        "DELETE FROM public.user_role",
        "DELETE FROM public.users",
        "DELETE FROM public.internships",
        "DELETE FROM public.role_privilege",
        "DELETE FROM public.role",
        "DELETE FROM public.privilege"
    ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = ["/import-role.sql","/import-users.sql", "/import-user-role.sql", "/import-internships.sql", "/import-students.sql", "/import-certifications.sql", "/import-qualifications.sql"
        ,"/import-companies.sql", "/import-locations-company.sql", "/import-internships-locations.sql","/import-questions.sql", "/import-recommendations.sql"
        ,"/import-ratings-company-students.sql", "/import-requests.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class LoadInitData(
    @Autowired
    val roleRepository: RoleRepository,
    @Autowired
    val privilegeRepository: PrivilegeRepository,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val internshipRepository: InternshipRepository,
    @Autowired
    val certificationRepository: CertificationRepository,
    @Autowired
    val studentRepository: StudentRepository,
    @Autowired
    val qualificationRepository: QualificationRepository,
    @Autowired
    val questionRepository: QuestionRepository,
    @Autowired
    val companyRepository: CompanyRepository,
    @Autowired
    val recommendationRepository: RecommendationRepository,
    @Autowired
    val locationCompanyRepository: LocationCompanyRepository,
    @Autowired
    val internshipLocationRepository: InternshipLocationRepository,
    @Autowired
    val ratingCompanyStudentRepository: RatingCompanyStudentRepository,
    @Autowired
    val requestRepository: RequestRepository

) {

    //TEST DE CERTIFICATIONS

    @Test
    fun `findAll students`() {
        val certs = studentRepository.findAll()
        Assertions.assertEquals(3, certs.size)
    }

    @Test
    fun `findAll returns the expected certifications`() {
        val certs = certificationRepository.findAll()
        Assertions.assertEquals(3, certs.size)
    }

    @Test
    fun `findById returns the correct certification`() {
        val cert = certificationRepository.findById(1).orElseThrow()
        Assertions.assertAll(
            { Assertions.assertEquals(1, cert.idCertification) },
            { Assertions.assertEquals("Java SE 11 Developer", cert.nameCertification) },
            { Assertions.assertEquals("Oracle", cert.provider) },
            { Assertions.assertEquals(1, cert.student.idStudent) }
        )
    }

    @Test
    fun `saving new certification increments the total`() {
        val student1 = studentRepository.findById(1).orElseThrow()
        val nueva = Certification(
            nameCertification = "Spring Professional",
            provider = "VMware",
            filePath = "/files/springpro.pdf",
            student = student1)

        certificationRepository.save<Certification>(nueva)

        val total = certificationRepository.count()
        Assertions.assertEquals(4, total)
    }

    @Test
    fun `deleting certification decreases the total`() {
        certificationRepository.deleteById(3)
        Assertions.assertFalse(certificationRepository.existsById(3))
        Assertions.assertEquals(2, certificationRepository.count())
    }


    //TESTS PARA QUALIFICATIONS

    @Test
    fun `findAll returns the expected skills`() {
        val skills = qualificationRepository.findAll()
        Assertions.assertEquals(4, skills.size)
    }

    @Test
    fun `findById returns the correct skill`() {
        val skill = qualificationRepository.findById(1).orElseThrow()
        Assertions.assertAll(
            { Assertions.assertEquals(1, skill.idQualification) },
            { Assertions.assertEquals("English", skill.nameQualification) }
        )
    }

    @Test
    fun `saving new skill increments the total`() {
        val nueva = Qualification(
            nameQualification = "Spanish"
        )

        qualificationRepository.save<Qualification>(nueva)

        val total = qualificationRepository.count()
        Assertions.assertEquals(5, total)
    }

    @Test
    fun `deleting skill decreases the total`() {
        qualificationRepository.deleteById(3)
        Assertions.assertFalse(qualificationRepository.existsById(3))
        Assertions.assertEquals(3, qualificationRepository.count())
    }


    //TEST PARA QUESTIONS

    @Test
    fun `findAll returns the expected questions`() {
        val skills = questionRepository.findAll()
        Assertions.assertEquals(4, skills.size)
    }

    @Test
    fun `findById returns the correct question`() {
        val skill = questionRepository.findById(1).orElseThrow()
        Assertions.assertAll(
            { Assertions.assertEquals(1, skill.idQuestion) },
            { Assertions.assertEquals("Do you offer night shifts?", skill.question) },
            { Assertions.assertEquals("Yes, just choose the timezone in preferences.", skill.answer) }
        )
    }

    @Test
    fun `saving new question increments the total`() {

        val company1 = companyRepository.findById(1).orElseThrow()
        val nueva = Question(
            question = "English is required?", answer = "No", company = company1
        )

        questionRepository.save<Question>(nueva)

        val total = questionRepository.count()
        Assertions.assertEquals(5, total)
    }

    @Test
    fun `deleting question decreases the total`() {
        questionRepository.deleteById(3)
        Assertions.assertFalse(questionRepository.existsById(3))
        Assertions.assertEquals(3, questionRepository.count())
    }

    // TEST DE RECOMMENDATION

    @Test
    fun `findAll returns the expected recommendations`() {
        val recommds = recommendationRepository.findAll()
        Assertions.assertEquals(3, recommds.size)
    }

    @Test
    fun `findById returns the correct recommendation`() {
        val recommendation = recommendationRepository.findById(3).orElseThrow()
        Assertions.assertAll(
            { Assertions.assertEquals(3, recommendation.idRecommendation) },
            { Assertions.assertEquals("Excelente actitud, proactividad y habilidades tecnicas destacables.",
                recommendation.details) },
            { Assertions.assertEquals(3, recommendation.student.idStudent) },
            { Assertions.assertEquals(2, recommendation.company.idCompany)}
        )
    }

    @Test
    fun `saving new recommendation increments the total`() {

        val company1 = companyRepository.findById(1).orElseThrow()
        val student1 = studentRepository.findById(1).orElseThrow()
        val nueva = Recommendation(details = "He was so responsable", student = student1, company = company1)

        recommendationRepository.save<Recommendation>(nueva)

        val total = recommendationRepository.count()
        Assertions.assertEquals(4, total)
    }

    @Test
    fun `deleting recommendation decreases the total`() {
        recommendationRepository.deleteById(3)
        Assertions.assertFalse(recommendationRepository.existsById(3))
        Assertions.assertEquals(2, recommendationRepository.count())
    }

    //TESTS INTERNSHIPS
    @Test
    fun `findAll returns the expected internships`() {
        val internships = internshipRepository.findAll()
        Assertions.assertEquals(3, internships.size)
    }

    @Test
    fun `findById returns the correct internship`() {
        val internship = internshipRepository.findById(3).orElseThrow()
        Assertions.assertAll(
            { Assertions.assertEquals(3, internship.idInternship) },
            { Assertions.assertEquals("Internship3", internship.details) }
        )
    }

    @Test
    fun `saving new internship increments the total`() {
        val nueva = Internship(
            details = "Internship4"
        )
        internshipRepository.save(nueva)

        val total = internshipRepository.count()
        Assertions.assertEquals(4, total)
    }

    @Test
    fun `deleting internship decreases the total`() {
        internshipRepository.deleteById(2)
        Assertions.assertFalse(internshipRepository.existsById(2))
        Assertions.assertEquals(2, internshipRepository.count())
    }

    // TESTS COMPANIES
    @Test
    fun `findAll returns the expected companies`() {
        val companies = companyRepository.findAll()
        Assertions.assertEquals(3, companies.size)
    }

    @Test
    fun `findById returns the correct company`() {
        val company = companyRepository.findById(2).orElseThrow()
        Assertions.assertAll(
            { Assertions.assertEquals(2, company.idCompany) },
            { Assertions.assertEquals("GreenFuture Corp", company.nameCompany) }
        )
    }

    @Test
    fun `saving new company increments the total`() {
        val user = userRepository.findById(4).orElseThrow()

        val newCompany = Company(
            nameCompany = "Company4",
            description = "A new tech company",
            history = "Founded in 2025",
            mision = "Innovate everywhere",
            vision = "Future-oriented",
            corporateCultur = "Collaborative",
            contactCompany = 88889999,
            ratingCompany = 4.7,
            internshipType = "Remote",
            imageProfile = "",
            user = user
        )

        companyRepository.save(newCompany)

        val total = companyRepository.count()
        Assertions.assertEquals(4, total)
    }

    @Test
    fun `deleting company decreases the total`() {
        companyRepository.deleteById(3)
        Assertions.assertFalse(companyRepository.existsById(3))
        Assertions.assertEquals(2, companyRepository.count())
    }

    //TEST LOCATION COMPANY
    @Test
    fun `findAll returns expected location companies`() {
        val all = locationCompanyRepository.findAll()
        Assertions.assertTrue(all.isNotEmpty())
    }

    @Test
    fun `findById returns correct location company`() {
        val location = locationCompanyRepository.findById(1).orElseThrow()
        Assertions.assertEquals(1, location.idLocationCompany)
    }

    @Test
    fun `saving new location company increments total`() {
        val company = companyRepository.findById(1).orElseThrow()
        val nueva = LocationCompany(
            latitude = 10.0,
            longitude = -84.0,
            email = "test@company.com",
            contactLocation = 12345678,
            company = company
        )
        locationCompanyRepository.save(nueva)
        val total = locationCompanyRepository.count()
        Assertions.assertEquals(4, total)
    }

    @Test
    fun `deleting location company decreases total`() {
        locationCompanyRepository.deleteById(1)
        Assertions.assertFalse(locationCompanyRepository.existsById(1))
    }

    //TEST INTERNSHIP LOCATION
    @Test
    fun `findAll returns expected internship locations`() {
        val all = internshipLocationRepository.findAll()
        Assertions.assertTrue(all.isNotEmpty())
    }

    @Test
    fun `findById returns correct internship location`() {
        val location = internshipLocationRepository.findById(1).orElseThrow()
        Assertions.assertEquals(1, location.idInternshipLocation)
    }

    @Test
    fun `saving new internship location increments total`() {
        val internship = internshipRepository.findById(1).orElseThrow()
        val locationCompany = locationCompanyRepository.findById(1).orElseThrow()

        val nueva = InternshipLocation(
            internship = internship,
            locationCompany = locationCompany
        )
        internshipLocationRepository.save(nueva)
        val total = internshipLocationRepository.count()
        Assertions.assertEquals(4, total)
    }

    @Test
    fun `deleting internship location decreases total`() {
        internshipLocationRepository.deleteById(1)
        Assertions.assertFalse(internshipLocationRepository.existsById(1))
    }

    //TEST RATING COMPANY STUDENT
    @Test
    fun `findAll returns expected ratings`() {
        val ratings = ratingCompanyStudentRepository.findAll()
        Assertions.assertTrue(ratings.isNotEmpty())
    }

    @Test
    fun `findById returns correct rating`() {
        val rating = ratingCompanyStudentRepository.findById(1).orElseThrow()
        Assertions.assertEquals(1, rating.idRating)
    }

    @Test
    fun `saving new rating increments total`() {
        val student = studentRepository.findById(1).orElseThrow()
        val company = companyRepository.findById(1).orElseThrow()

        val nueva = RatingCompanyStudent(
            rating = 4.2,
            type = "Final",
            comment = "Well structured program with clear goals",
            student = student,
            company = company
        )

        ratingCompanyStudentRepository.save(nueva)
        val total = ratingCompanyStudentRepository.count()
        Assertions.assertEquals(4, total)
    }

    @Test
    fun `deleting rating decreases total`() {
        ratingCompanyStudentRepository.deleteById(2)
        Assertions.assertFalse(ratingCompanyStudentRepository.existsById(2))
    }

    //TEST STUDENTS
    @Test
    fun `findAll returns expected students`() {
        val students = studentRepository.findAll()
        Assertions.assertTrue(students.isNotEmpty())
    }

    @Test
    fun `findById returns correct student`() {
        val student = studentRepository.findById(1).orElseThrow()
        Assertions.assertEquals(1, student.idStudent)
    }

    @Test
    fun `findAll returns the number expected of students`() {
        val students = studentRepository.findAll()
        Assertions.assertEquals(4, students.size)
    }

    @Test
    fun `findById returns the correct data of student`() {
        val student = studentRepository.findById(3).orElseThrow()
        val user5 = userRepository.findById(5).orElseThrow()

        Assertions.assertAll(
            { Assertions.assertEquals(3, student.idStudent) },
            { Assertions.assertEquals("Andres",student.nameStudent) },
            { Assertions.assertEquals("3333", student.identification) },
            { Assertions.assertEquals("None",student.personalInfo)},
            { Assertions.assertEquals("None",student.experience)},
            { Assertions.assertEquals(0.0,student.ratingStudent)},
            { Assertions.assertEquals(user5,student.user)}
        )
    }

    @Test
    fun `saving new student increments total`() {
        val user = userRepository.findById(3).orElseThrow()

        val nuevo = Student(
            nameStudent = "Carlos",
            identification = "2222",
            personalInfo = "Some info",
            experience = "Some experience",
            ratingStudent = 0.0,
            imageProfile = "",
            homeLatitude = 1.0,
            homeLongitude = 1.0,
            user = user
        )

        studentRepository.save(nuevo)
        val total = studentRepository.count()
        Assertions.assertEquals(5, total)
    }

    @Test
    fun `deleting student decreases total`() {
        studentRepository.deleteById(4)
        Assertions.assertFalse(studentRepository.existsById(4))
    }

    //TEST REQUEST
    @Test
    fun `findAll returns expected requests`() {
        val requests = requestRepository.findAll()
        Assertions.assertTrue(requests.isNotEmpty())
    }

    @Test
    fun `findById returns correct request`() {
        val request = requestRepository.findById(1).orElseThrow()
        Assertions.assertEquals(1, request.idRequest)
    }

    @Test
    fun `saving new request increments total`() {
        val student = studentRepository.findById(1).orElseThrow()
        val internshipLocation = internshipLocationRepository.findById(1).orElseThrow()

        val nuevo = Request(
            state = true,
            student = student,
            internshipLocation = internshipLocation
        )

        requestRepository.save(nuevo)
        val total = requestRepository.count()
        Assertions.assertEquals(4, total)
    }

    @Test
    fun `deleting request decreases total`() {
        requestRepository.deleteById(3)
        Assertions.assertFalse(requestRepository.existsById(3))
    }
}