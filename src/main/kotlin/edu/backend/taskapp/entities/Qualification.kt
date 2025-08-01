package edu.backend.taskapp.entities

import jakarta.persistence.*

@Entity
@Table(name = "qualifications")
data class Qualification(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qualifications_seq")
    @SequenceGenerator(
        name = "qualifications_seq",
        sequenceName = "qualifications_id_seq",
        allocationSize = 1
    )
    @Column(name = "id_qualification")
    val idQualification: Long? = null,

    @Column(name = "name_qualification")
    var nameQualification: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Qualification) return false

        if (idQualification != other.idQualification) return false

        return true
    }

    override fun hashCode(): Int {
        return idQualification?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Qualification(idQualification=$idQualification, nameQualification='$nameQualification')"
    }
}