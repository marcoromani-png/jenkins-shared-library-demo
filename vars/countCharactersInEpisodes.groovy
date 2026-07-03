def call(List episodes) {
    def characterCounter = [:]

    episodes.each { episode ->

        def episodeName = episode.name
        def characters = episode.characters ?: []

        characters.unique().each { characterUrl ->

            if (!characterCounter.containsKey(characterUrl)) {
                characterCounter[characterUrl] = [
                    count: 0,
                    episodes: []
                ]
            }

            characterCounter[characterUrl].count = characterCounter[characterUrl].count + 1
            characterCounter[characterUrl].episodes.add(episodeName)
        }
    }

    return characterCounter
}