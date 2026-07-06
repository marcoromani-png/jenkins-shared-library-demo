def call(List episodes) {
    def characterCounter = [:]

// ciclo for su tutti gli epeisodi
    for (episode in episodes) {

        //recupero il nome degli episodi
        def episodeName = episode.name
        //recupero il nome dei personaggi
        def characters = episode.characters

        if (characters != null) {

            //Creo una lista temporanea per evitare di contare due volte lo stesso character nello stesso episodio.
           def charactersAlreadyCountedInThisEpisode = []

            for (character in characters) {

                if (!charactersAlreadyCountedInThisEpisode.contains(character)) {

                    charactersAlreadyCountedInThisEpisode.add(character)

                   /* if (!characterCounter.containsKey(character)) {
                        characterCounter[character] = [
                            count: 0,
                            episodes: []
                        ]
                    }*/

                    characterCounter[character].count = characterCounter[character].count + 1
                    characterCounter[character].episodes.add(episodeName)
                }
            }
        }
        return characterCounter
    }

    

