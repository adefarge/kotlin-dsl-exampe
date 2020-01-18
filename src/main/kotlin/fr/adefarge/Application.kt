package fr.adefarge

fun main() {
    val geojson: FeatureCollection = geojson {
        point { lat = 47.9; lng = 0.9 }

        point {
            lat = 49.5
            lng = 3.9

            properties {
                "my-property" toValue "my-value"
                "other-property" toValue "abcd"
            }
        }

        linestring {
            coordinates {
                coord { lat = 49.0; lng = 4.8 }
                coord { lat = 47.3; lng = 5.3 }
                coord { lat = 46.0; lng = 2.5 }
                coord { lat = 46.0; lng = -0.2 }
            }

            properties {
                "my-property" toValue "my-value"
            }
        }

        polygon {
            outer {
                coord { lat = 49.2; lng = 1.9 }
                coord { lat = 48.8; lng = 1.5 }
                coord { lat = 48.2; lng = 2.3 }
                coord { lat = 48.2; lng = 3.0 }
                coord { lat = 48.8; lng = 3.4 }
                coord { lat = 49.1; lng = 2.7 }
                coord { lat = 49.2; lng = 1.9 }
            }
            inner {
                coord { lat = 48.8; lng = 2.9 }
                coord { lat = 48.7; lng = 2.5 }
                coord { lat = 48.5; lng = 2.7 }
                coord { lat = 48.8; lng = 2.9 }
            }

            properties {
                "my-property" toValue "my-value"
            }
        }
    }

    println(geojson)
}
