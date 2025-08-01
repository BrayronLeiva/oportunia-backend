package edu.backend.taskapp.entities

import jakarta.persistence.*

@Entity
@Table(name = "certifications")
data class Certification(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certifications_seq")
    @SequenceGenerator(
        name = "certifications_seq",
        sequenceName = "certifications_id_seq",
        allocationSize = 1
    )
    @Column(name = "id_certification")
    val idCertification: Long? = null,

    @Column(name = "name_certification")
    var nameCertification: String,

    var provider: String,

    @Column(name = "file_path")
    var filePath: String? = "",

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id_student")
    var student: Student

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Certification) return false
        if (idCertification != other.idCertification) return false
        return true
    }

    override fun hashCode(): Int = idCertification?.hashCode() ?: 0

    override fun toString(): String {
        return "Certification(idCertification=$idCertification, nameCertification='$nameCertification', provider='$provider', student=$student)"
    }
}