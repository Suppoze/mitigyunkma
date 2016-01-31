package hu.suppoze.mitigyunkma.model

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

public open class Drink (

        @PrimaryKey
        public open var name: String = "",

        public open var index: Double = .0,
        public open var percent: Double = .0,
        public open var capacity: Double  = .0,
        public open var price: Double = .0

) : RealmObject() {

    @Ignore val NAME: String = "name"
    @Ignore val INDEX: String = "index"
    @Ignore val PERCENT: String = "percent"
    @Ignore val CAPACITY: String = "capacity"
    @Ignore val PRICE: String = "price"

}
