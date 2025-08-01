package edu.backend.taskapp.entities

import jakarta.persistence.*

@Entity
@Table(name = "locations_company")
data class LocationCompany(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationcompany_seq")
    @SequenceGenerator(
        name = "locationcompany_seq",
        sequenceName = "locationcompany_id_seq",
        allocationSize = 1
    )
    @Column(name = "id_location_company")
    val idLocationCompany: Long? = null,

    var latitude: Double,

    var longitude: Double,

    var email: String,

    @Column(name = "contact_location")
    var contactLocation: Int,

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false, referencedColumnName = "id_company")
    var company: Company,

    @OneToMany(mappedBy = "locationCompany", cascade = [CascadeType.ALL], orphanRemoval = true)
    var internshipLocations: MutableList<InternshipLocation> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LocationCompany) return false
        if (idLocationCompany != other.idLocationCompany) return false
        return true
    }

    override fun hashCode(): Int {
        return idLocationCompany?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "LocationCompany(idLocationCompany=$idLocationCompany, email='$email', latitude=$latitude, longitude=$longitude, contactLocation=$contactLocation)"
    }
}