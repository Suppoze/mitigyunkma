package hu.suppoze.mitigyunkma.entity

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.util.*

open class Drink (

        @PrimaryKey
        open var name: String = "",

        open var index: Double = .0,
        open var percent: Double = .0,
        open var capacity: Double  = .0,
        open var price: Double = .0,

        open var lastmod: Date = Date()

) : RealmObject() { }
