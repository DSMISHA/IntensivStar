package ru.mikhailskiy.intensiv.data

object MockRepository {

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x,
                description = "the movie description",
                characters = getCharacters(),
                studio = "studio",
                genre = "genre",
                year = 1999
            )
            moviesList.add(movie)
        }

        return moviesList
    }

    fun getShows(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x,
                description = "the movie description",
                characters = getCharacters(),
                studio = "studio",
                genre = "genre",
                year = 2000
            )
            moviesList.add(movie)
        }

        return moviesList
    }


    private fun getCharacters(): List<Character>{
        val res: MutableList<Character> = ArrayList()
        for(i in 0..5){
            res.add(Character("", "Tommy Lee Jones"))
        }
        return res
    }
}
