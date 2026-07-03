import groovy.json.JsonSlurperClassic

def call(String startUrl) {
    //Lista vuota dove saranno messi tutti gli episodi di tutte le pagine.
    def allEpisodes = []
    //Inizio della prima URL
    def currentUrl = startUrl
    //Continuo a chiamare l’API finché esiste una pagina successiva.
    while (currentUrl != null  && currentUrl.toString() != 'null') {
        echo "Chiamo API: ${currentUrl}"

        def response = httpRequest(
            httpMode: 'GET',
            url: currentUrl,
            validResponseCodes: '200',
            contentType: 'APPLICATION_JSON'
        )

        def json = readJSON text: response.content,  returnPojo: true

        if (json.results != null) {
            allEpisodes.addAll(json.results)
        }
        //Prendo la prossima pagina. Se non esiste, diventa null e il ciclo finisce.
        currentUrl = json.info?.next_page

        echo "Episodi raccolti finora: ${allEpisodes.size()}"
    }

    //echo "Fine paginazione. Totale episodi raccolti: ${allEpisodes.size()}"

    return allEpisodes
}