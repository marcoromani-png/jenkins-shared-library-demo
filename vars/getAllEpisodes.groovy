import groovy.json.JsonSlurperClassic

def call(String startUrl) {
    //Lista vuota dove saranno messi tutti gli episodi di tutte le pagine.
    def episodes = []
    //Inizio della prima URL
    def currentUrl = startUrl
    //Continuo a chiamare l’API finché esiste una pagina successiva.
    while (currentUrl != null) {
        //echo "Chiamo API: ${currentUrl}"

        def response = httpRequest(
            httpMode: 'GET',
            url: currentUrl,
            validResponseCodes: '200',
            contentType: 'APPLICATION_JSON',
            quiet: true
        )


        def body = readJSON text: response.content, returnPojo: true

        def results = body.results

        for (episode in results) {
            episodes.add(episode)
        }

        //Prendo la prossima pagina. Se non esiste, diventa null e il ciclo finisce.
        currentUrl = body.info.next_page

        echo "Episodi raccolti finora: ${episodes.size()}"
    }

    //echo "Fine paginazione. Totale episodi raccolti: ${episodes.size()}"

    return episodes
}