package pedrobacchini.github.com.starwarskotlinrx.model

data class Movie (val title : String, val episodeId : Int, val characters : List<Character>)

data class Character(val name : String, val gender : String) {

    override fun toString(): String {
        return "Character(name='$name', gender='$gender')"
    }
}