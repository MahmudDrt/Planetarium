package com.example.news.opengl

class PlanetSelector(
    private val earthCube: Cube,
    private val moonCube: Cube,
    private val venusCube: Cube,
    private val marsCube: Cube,
    private val jupiterCube: Cube,
    private val saturnCube: Cube,
    private val mercuryCube: Cube,
    private val uranusCube: Cube,
    private val neptuneCube: Cube
) {

    private var selectedPlanetCube: Cube? = null

    init {
        // Изначально выбираем Землю
        selectedPlanetCube = earthCube
    }

    fun selectNextPlanet() {
        selectedPlanetCube = when (selectedPlanetCube) {
            earthCube -> moonCube
            moonCube -> venusCube
            venusCube -> marsCube
            marsCube -> jupiterCube
            jupiterCube -> saturnCube
            saturnCube -> mercuryCube
            mercuryCube -> uranusCube
            uranusCube -> neptuneCube
            neptuneCube -> earthCube
            else -> earthCube
        }
    }

    fun selectPreviousPlanet() {
        selectedPlanetCube = when (selectedPlanetCube) {
            earthCube -> neptuneCube
            moonCube -> earthCube
            venusCube -> moonCube
            marsCube -> venusCube
            jupiterCube -> marsCube
            saturnCube -> jupiterCube
            mercuryCube -> saturnCube
            uranusCube -> mercuryCube
            neptuneCube -> uranusCube
            else -> earthCube
        }
    }

    fun getSelectedPlanetCube(): Cube? {
        return selectedPlanetCube
    }

    fun showPlanetInfo(): String {
        return when (selectedPlanetCube) {
            earthCube -> "Земля"
            moonCube -> "Луна"
            venusCube -> "Венера"
            marsCube -> "Марс"
            jupiterCube -> "Юпитер"
            saturnCube -> "Сатурн"
            mercuryCube -> "Меркурий"
            uranusCube -> "Уран"
            neptuneCube -> "Нептун"
            else -> "Неизвестная планета"
        }
    }
}