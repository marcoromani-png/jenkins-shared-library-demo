def call(List episodes) {

    // Mappa finale dove salvo i character e il loro conteggio
    def characterCounter = [:]

    // Scorro tutti gli episodi
    for (episode in episodes) {

        // Prendo il nome dell'episodio
        def episodeName = episode.name

        // Prendo la lista dei characters dell'episodio
        def characters = episode.characters

        // Controllo che la lista dei characters esista
        if (characters != null) {

            // Lista temporanea per non contare due volte lo stesso character nello stesso episodio
            def charactersAlreadyCountedInThisEpisode = []

            // Scorro tutti i characters dell'episodio
            for (character in characters) {

                // Se il character non è già stato contato in questo episodio
                if (!charactersAlreadyCountedInThisEpisode.contains(character)) {

                    // Lo segno come già contato per questo episodio
                    charactersAlreadyCountedInThisEpisode.add(character)

                    // Se il character non esiste ancora nella mappa, lo creo
                    if (!characterCounter.containsKey(character)) {

                        characterCounter[character] = [
                            count: 0,
                            episodes: []
                        ]

                    }

                    // Incremento il contatore del character
                    characterCounter[character].count = characterCounter[character].count + 1

                    // Salvo il nome dell'episodio in cui compare
                    characterCounter[character].episodes.add(episodeName)

                }
            }
        }
    }

    // Restituisco la mappa finale al Jenkinsfile
    return characterCounter
}