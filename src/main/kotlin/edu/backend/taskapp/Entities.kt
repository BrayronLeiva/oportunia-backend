package edu.backend.taskapp

import edu.backend.taskapp.entities.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "reminder")
data class Reminder(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(name = "reminder_date")
    var reminderDate:Date,
    // Entity Relationship
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Share the same primary key between 2 tables
    var task: Task,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Reminder) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Reminder(id=$id, reminderDate=$reminderDate, task=$task)"
    }

}

@Entity
@Table(name = "task")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var title: String,
    var notes: String,
    @Temporal(TemporalType.DATE)
    var createDate: Date,
    @Temporal(TemporalType.DATE)
    var dueDate: Date,
    // Entity Relationship
    @ManyToOne
    @JoinColumn(name = "priority_id", nullable = false, referencedColumnName = "id")
    var priority: Priority,
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
    var status: Status,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    var user: User,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Task) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Task(id=$id, title='$title', notes='$notes', createDate=$createDate, dueDate=$dueDate, priority=$priority, status=$status, user=$user)"
    }

}


@Entity
@Table(name = "status")
data class Status(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var label: String,
    // Entity Relationship
    @OneToMany(mappedBy = "status")
    var taskList: List<Task>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Status) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Status(id=$id, label='$label', taskList=$taskList)"
    }

}

@Entity
@Table(name = "priority")
data class Priority(
    var label: String,
    // Entity Relationship
    @OneToMany(mappedBy = "priority")
    var taskList: List<Task>? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Priority) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Priority(label='$label', taskList=$taskList, id=$id)"
    }
}