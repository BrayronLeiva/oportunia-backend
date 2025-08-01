package edu.backend.taskapp.entities

import jakarta.persistence.*

@Entity
@Table(name = "requests")
data class Request(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requests_seq")
    @SequenceGenerator(
        name = "requests_seq",
        sequenceName = "requests_id_seq",
        allocationSize = 1
    )
    @Column(name = "id_request")
    val idRequest: Long? = null,

    var state: Boolean,

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id_student")
    var student: Student,

    @ManyToOne
    @JoinColumn(name = "internship_location_id", nullable = false, referencedColumnName = "id_internship_location")
    var internshipLocation: InternshipLocation,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Request) return false
        if (idRequest != other.idRequest) return false
        return true
    }

    override fun hashCode(): Int = idRequest?.hashCode() ?: 0

    override fun toString(): String {
        return "Request(idRequest=$idRequest, student=$student, internship=$internshipLocation, state=$state)"
    }
}