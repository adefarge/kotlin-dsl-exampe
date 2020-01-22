package fr.adefarge

import kotlin.test.assertTrue

val points = listOf(
    49.0 to 4.8,
    47.3 to 5.3,
    46.0 to 2.5,
    46.0 to -0.2
)

private fun GeojsonBuilder.pointForEach(
    list: List<Pair<Double, Double>>,
    consumer: PointBuilder.(latitude: Double, longitude: Double) -> Unit
) {
    for ((lat, lng) in list) {
        point {
            consumer(lat, lng)
        }
    }
}

val geojsonWithExtension = geojson {
    pointForEach(points) { latitude, longitude ->
        lat = latitude
        lng = longitude

        properties {
            "a" toValue "b"
        }
    }
}

private fun GeojsonBuilder.pointWithName(name: String, init: PointBuilder.() -> Unit) {
    point {
        properties {
            "name" toValue name
        }
        init()
    }
}

val geojsonWithNamedPoints = geojson {

    /*
    equivalent to:
    point {
        lat = 48.9
        lng = 2.3
        properties {
            "name" toValue "Paris"
        }
    }
     */
    pointWithName("Paris") {
        lat = 48.9
        lng = 2.3
    }

    pointWithName("Bordeaux") {
        lat = 44.8
        lng = -0.6
    }
}

fun test() {
    geojsonWithNamedPoints assertHasPointWithProperties mapOf("name" to "Paris")
    geojsonWithNamedPoints assertHasPointWithProperties mapOf("name" to "Bordeaux")
}


private infix fun FeatureCollection.assertHasPointWithProperties(
    properties: Map<String, String>
) {
    val hasPointWithProperties = features.any { it.geometry is Geometry.Point && it.properties == properties }
    assertTrue(hasPointWithProperties, "No point with provided properties in the given geojson")
}
