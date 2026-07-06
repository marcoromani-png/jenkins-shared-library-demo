def call(String startUrl) {

    def characterNames = [:]
    def currentUrl = startUrl

    while (currentUrl != null) {

        def response = httpRequest(
            httpMode: 'GET',
            url: currentUrl,
            validResponseCodes: '200',
            contentType: 'APPLICATION_JSON',
            quiet: true
        )

        def body = readJSON text: response.content, returnPojo: true

        def results = body.results

        for (character in results) {

            def characterUrl = "https://api.attackontitanapi.com/characters/${character.id}"
            def characterName = character.name

            characterNames[characterUrl] = characterName
        }

        currentUrl = body.info.next_page

        echo "Characters caricati finora: ${characterNames.size()}"
    }

    return characterNames
}