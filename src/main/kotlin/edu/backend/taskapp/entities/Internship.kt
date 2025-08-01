package edu.backend.taskapp.entities

import jakarta.persistence.*

@Entity
@Table(name = "internships")
data class Internship(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internship_seq")
    @SequenceGenerator(
        name = "internship_seq",
        sequenceName = "internships_id_seq",
        allocationSize = 1
    )
    @Column(name = "id_internship")
    val idInternship: Long? = null,

    var details: String,

    @OneToMany(mappedBy = "internship", cascade = [CascadeType.ALL], orphanRemoval = true)
    var internshipLocations: List<InternshipLocation> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Internship) return false
        return idInternship == other.idInternship
    }

    override fun hashCode(): Int {
        return idInternship?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Internship(idInternship=$idInternship, details='$details')"
    }
}