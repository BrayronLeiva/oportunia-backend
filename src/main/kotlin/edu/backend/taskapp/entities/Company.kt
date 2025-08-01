package edu.backend.taskapp.entities

import jakarta.persistence.*

@Entity
@Table(name = "companies")
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    @SequenceGenerator(
        name = "company_seq",
        sequenceName = "companies_id_seq",
        allocationSize = 1
    )
    @Column(name = "id_company")
    val idCompany: Long? = null,

    @Column(name = "name_company")
    var nameCompany: String,

    var description: String,

    var history: String,

    var mision: String,

    var vision: String,

    @Column(name = "corporate_cultur")
    var corporateCultur: String,

    @Column(name = "contact_company")
    var contactCompany: Int,

    @Column(name = "rating_company")
    var ratingCompany: Double,

    @Column(name = "internship_type")
    var internshipType: String,

    @Column(name = "image_profile", nullable = true)
    var imageProfile: String? = null,

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true, referencedColumnName = "id")
    var user: User? = null,

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], orphanRemoval = true)
    var locationCompanies: List<LocationCompany> = mutableListOf(),

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], orphanRemoval = true)
    var ratingCompanyStudents: List<RatingCompanyStudent> = mutableListOf(),

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], orphanRemoval = true)
    var recommendations: List<Recommendation> = mutableListOf(),

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], orphanRemoval = true)
    var questions: List<Question> = mutableListOf()

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Company) return false
        return idCompany == other.idCompany
    }

    override fun hashCode(): Int = idCompany?.hashCode() ?: 0

    override fun toString(): String {
        return "Company(idCompany=$idCompany, nameCompany='$nameCompany', description='$description', contactCompany=$contactCompany, ratingCompany=$ratingCompany)"
    }
}