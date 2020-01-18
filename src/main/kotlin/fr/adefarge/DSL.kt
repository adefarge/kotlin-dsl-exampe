package fr.adefarge


class GeojsonBuilder {
    val features = mutableListOf<Feature>()

    inline fun point(init: PointBuilder.() -> Unit) {
        val builder = PointBuilder()
        builder.init()
        val pointFeature = builder.build()
        features += pointFeature
    }

    inline fun linestring(init: LineStringBuilder.() -> Unit) {
        val builder = LineStringBuilder()
        builder.init()
        val pointFeature = builder.build()
        features += pointFeature
    }

    inline fun polygon(init: PolygonBuilder.() -> Unit) {
        val builder = PolygonBuilder()
        builder.init()
        val pointFeature = builder.build()
        features += pointFeature
    }

    fun build(): FeatureCollection {
        return FeatureCollection(features)
    }
}

class PointBuilder : WithProperties {
    var lat: Double? = null
    var lng: Double? = null
    override var properties: Map<String, String> = emptyMap()

    fun build(): Feature {
        return Feature(
            geometry = Geometry.Point(Coordinates(lat = lat!!, lng = lng!!)),
            properties = properties
        )
    }
}

class LineStringBuilder : WithProperties {
    var coordinates: List<Coordinates>? = null
    override var properties: Map<String, String> = emptyMap()

    inline fun coordinates(init: CoordinatesListBuilder.() -> Unit) {
        val builder = CoordinatesListBuilder()
        builder.init()
        coordinates = builder.build()
    }

    fun build() = Feature(
        geometry = Geometry.Linestring(coordinates!!),
        properties = properties
    )
}

class PolygonBuilder : WithProperties {

    var outer: List<Coordinates>? = null
    val inners = mutableListOf<List<Coordinates>>()
    override var properties: Map<String, String> = emptyMap()

    inline fun outer(init: CoordinatesListBuilder.() -> Unit) {
        val builder = CoordinatesListBuilder()
        builder.init()
        outer = builder.build()
    }

    inline fun inner(init: CoordinatesListBuilder.() -> Unit) {
        val builder = CoordinatesListBuilder()
        builder.init()
        inners += builder.build()
    }

    fun build() = Feature(
        geometry = Geometry.Polygon(listOf(outer!!) + inners),
        properties = properties
    )

}

class CoordinatesListBuilder {
    val coordinates = mutableListOf<Coordinates>()

    inline fun coord(init: CoordinatesBuilder.() -> Unit) {
        val builder = CoordinatesBuilder()
        builder.init()
        coordinates += builder.build()
    }

    fun build(): List<Coordinates> = coordinates
}

class CoordinatesBuilder {
    var lat: Double? = null
    var lng: Double? = null

    fun build(): Coordinates = Coordinates(lat = lat!!, lng = lng!!)
}

interface WithProperties {
    var properties: Map<String, String>

    fun properties(init: PropertiesBuilder.() -> Unit) {
        val builder = PropertiesBuilder()
        builder.init()
        properties = builder.build()
    }
}

class PropertiesBuilder {
    private val properties = mutableMapOf<String, String>()

    infix fun String.toValue(value: String) {
        val key = this
        properties[key] = value
    }

    fun build(): Map<String, String> = properties
}

inline fun geojson(init: GeojsonBuilder.() -> Unit): FeatureCollection {
    val builder = GeojsonBuilder()
    builder.init()
    return builder.build()
}
