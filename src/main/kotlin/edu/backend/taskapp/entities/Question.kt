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
@Table(name = "questions")
data class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_seq")
    @SequenceGenerator(
        name = "questions_seq",
        sequenceName = "questions_id_seq",
        allocationSize = 1
    )
    @Column(name = "id_question")
    val idQuestion: Long? = null,

    var question: String,

    var answer: String,

    // Entity Relationship
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false, referencedColumnName = "id_company")
    var company: Company,


    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Question) return false

        if (idQuestion != other.idQuestion) return false

        return true
    }

    override fun hashCode(): Int {
        return idQuestion?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Question(idQuestion=$idQuestion, question='$question', answer='$answer')"
    }
}