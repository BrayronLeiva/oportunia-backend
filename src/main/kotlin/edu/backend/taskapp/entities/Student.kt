package edu.backend.taskapp.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table


@Entity
@Table(name = "students")
data class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "students_seq")
    @SequenceGenerator(
        name = "students_seq",
        sequenceName = "students_id_seq",
        allocationSize = 1
    )
    @Column(name = "id_student")
    val idStudent: Long? = null,

    var nameStudent: String,

    var identification: String,

    var personalInfo: String,

    var experience: String,

    var ratingStudent: Double,

    @Column(name = "image_profile")
    var imageProfile: String,

    @Column(name = "home_latitude")
    var homeLatitude: Double,

    @Column(name = "home_longitude")
    var homeLongitude: Double,


    // Entity Relationship
    @ManyToMany
    @JoinTable(
        name = "student_qualification",
        joinColumns = [JoinColumn(name = "student_id")],
        inverseJoinColumns = [JoinColumn(name = "qualification_id")]
    )
    var qualifications: MutableSet<Qualification> = mutableSetOf(),

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true, referencedColumnName = "id")
    var user: User? = null,

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], orphanRemoval = true)
    var requests: MutableList<Request> = mutableListOf(),

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], orphanRemoval = true)
    var ratings: MutableList<RatingCompanyStudent> = mutableListOf(),

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Student) return false

        if (idStudent != other.idStudent) return false

        return true
    }

    override fun hashCode(): Int {
        return idStudent?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Student(idStudent=$idStudent, nameStudent='$nameStudent', identification='$identification', personalInfo='$personalInfo', experience='$experience', ratingStudent=$ratingStudent, user=$user)"
    }


}