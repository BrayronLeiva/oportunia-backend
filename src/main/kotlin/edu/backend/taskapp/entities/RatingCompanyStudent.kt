package edu.backend.taskapp.entities

import jakarta.persistence.*

@Entity
@Table(name = "ratings_companies_students")
data class RatingCompanyStudent(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ratings_seq")
    @SequenceGenerator(
        name = "ratings_seq",
        sequenceName = "ratings_id_seq",
        allocationSize = 1
    )
    @Column(name = "id_rating")
    val idRating: Long? = null,

    var rating: Double,

    var type: String,

    var comment: String,

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id_student")
    var student: Student,

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false, referencedColumnName = "id_company")
    var company: Company,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RatingCompanyStudent) return false

        if (idRating != other.idRating) return false

        return true
    }

    override fun hashCode(): Int {
        return idRating?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "RatingCompanyStudent(id=$idRating, rating=$rating, type='$type', comment='$comment')"
    }
}