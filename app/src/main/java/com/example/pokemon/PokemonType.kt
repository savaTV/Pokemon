enum class PokemonType(val typeName: String) {
    Normal("normal"),
    Fire("fire"),
    Water("water"),
    Grass("grass"),
    Electric("electric"),
    Ice("ice"),
    Fighting("fighting"),
    Poison("poison"),
    Ground("ground"),
    Flying("flying"),
    Psychic("psychic"),
    Bug("bug"),
    Rock("rock"),
    Ghost("ghost"),
    Dragon("dragon"),
    Dark("dark"),
    Steel("steel"),
    Fairy("fairy");

    companion object {
        fun fromName(name: String): PokemonType? {
            return values().find { it.typeName == name.lowercase() }
        }
    }
}