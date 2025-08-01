package edu.backend.taskapp.entities

import jakarta.persistence.*

@Entity
@Table(name = "internships_locations")
data class InternshipLocation(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internshiplocation_seq")
    @SequenceGenerator(
        name = "internshiplocation_seq",
        sequenceName = "internshiplocation_id_seq",
        allocationSize = 1
    )
    @Column(name = "id_internship_location")
    val idInternshipLocation: Long? = null,

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false, referencedColumnName = "id_location_company")
    var locationCompany: LocationCompany,

    @ManyToOne
    @JoinColumn(name = "internship_id", nullable = false, referencedColumnName = "id_internship")
    var internship: Internship,

    @OneToMany(mappedBy = "internshipLocation", cascade = [CascadeType.ALL], orphanRemoval = true)
    var requests: List<Request> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InternshipLocation) return false
        return idInternshipLocation == other.idInternshipLocation
    }

    override fun hashCode(): Int {
        return idInternshipLocation?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "InternshipLocation(idInternshipLocation=$idInternshipLocation)"
    }
}