def call(List episodes) {

    // variabile dove salvo i character e il loro conteggio
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
            for (characterUrl in characters) {

                // Se il character non è già stato contato in questo episodio
                if (!charactersAlreadyCountedInThisEpisode.contains(characterUrl)) {

                    charactersAlreadyCountedInThisEpisode.add(characterUrl)

                    if (!characterCounter.containsKey(characterUrl)) {

                    //echo "Recupero nome character da: ${characterUrl}"


                        def response = httpRequest(
                            httpMode: 'GET',
                            url: characterUrl,
                            validResponseCodes: '200',
                            contentType: 'APPLICATION_JSON'
                            quiet: true
                        )


                     def characterBody = readJSON text: response.content, returnPojo: true

                        def characterName = characterBody.name

                        characterCounter[characterUrl] = [
                            name: characterName,
                            count: 0,
                            episodes: []
                        ]
                }


                    // Lo segno come già contato per questo episodio
                    charactersAlreadyCountedInThisEpisode.add(characterUrl)

                    // Se il character non esiste ancora nella mappa, lo creo
                    if (!characterCounter.containsKey(characterUrl)) {

                        characterCounter[characterUrl] = [
                            count: 0,
                            episodes: []
                        ]

                    }

                    // Incremento il contatore del character
                    characterCounter[characterUrl].count = characterCounter[characterUrl].count + 1

                    // Salvo il nome dell'episodio in cui compare
                    characterCounter[characterUrl].episodes.add(episodeName)

                }
            }
        }
    }

    return characterCounter
}