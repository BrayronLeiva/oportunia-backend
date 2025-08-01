package edu.backend.taskapp.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "recommendations")
data class Recommendation(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommendations_seq")
    @SequenceGenerator(
        name = "recommendations_seq",
        sequenceName = "recommendations_id_seq",
        allocationSize = 1
    )
    @Column(name = "id_recommendation")
    val idRecommendation: Long? = null,

    var details: String,


    // Entity Relationship
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id_student")
    var student: Student,

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false, referencedColumnName = "id_company")
    var company: Company,


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Recommendation) return false

        if (idRecommendation != other.idRecommendation) return false

        return true
    }

    override fun hashCode(): Int {
        return idRecommendation?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Recommendation(idRecommendation=$idRecommendation, details='$details', student=$student)"
    }


}