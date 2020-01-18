package fr.adefarge


data class FeatureCollection(
    val features: List<Feature>
)

data class Feature(
    val properties: Map<String, String>,
    val geometry: Geometry
)

sealed class Geometry {
    data class Point(val coordinates: Coordinates) : Geometry()
    data class Linestring(val coordinates: List<Coordinates>) : Geometry()
    data class Polygon(val rings: List<List<Coordinates>>) : Geometry()
}

data class Coordinates(
    val lat: Double,
    val lng: Double
)
