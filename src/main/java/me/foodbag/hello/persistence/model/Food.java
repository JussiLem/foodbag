package me.foodbag.hello.persistence.model

import lombok.Data
import javax.persistence.*

@Data
@Entity
class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long

    @Column(name = "name")
    private val name: String

    var comment: String? = null
        set(comment) {
            field = this.comment
        }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    var user: User? = null
        set(user) {
            field = this.user
        }

    /**
     * Constructor for the mock tests. If changed then mocks will fail.
     * @param id food id
     * @param name name of the food
     */
    constructor(id: Long, name: String) {
        this.id = id
        this.name = name
    }

    constructor() {
        //for hibernate
    }
}
