def call(String apiUrl) {
    echo "Chiamo endpoint REST: ${apiUrl}"

    def response = httpRequest(
        url: apiUrl,
        httpMode: 'GET',
        acceptType: 'APPLICATION_JSON',
        validResponseCodes: '200'
    )

    def objects = readJSON text: response.content


    // Variabile che conterrà la somma totale dei prezzi trovati
    Double totalCost = 0.0

    // Variabile che conterà quanti elementi hanno un campo price oppure Price
    int elementsWithPrice = 0

    // Cicla tutti gli oggetti presenti nell'array JSON
    objects.each { object ->
    
        //Recupera il campo "data" dell'oggetto corrente
        def data = object['data']

        // Controlla che "data" esista e non sia null. Alcuni oggetti dell'API hanno "data": null, quindi vanno saltati
        if (data != null && data.toString() != 'null') {

            /*Cerca il prezzo dentro data.
            Prima prova con "price" minuscolo, se non lo trova, prova con "Price" maiuscolo*/
            def price = data['price'] ?: data['Price']

            // Se il prezzo esiste, allora viene sommato
            if (price != null) {

                // Converte il prezzo in numero decimale, serve perché alcuni prezzi arrivano come numero, altri come stringa
                totalCost = totalCost + price.toString().toDouble()

                // Incrementa il contatore degli elementi che hanno un prezzo
                elementsWithPrice++

            }
        }
    }

    echo "Numero elementi con price/Price: ${elementsWithPrice}"
    echo "Total cost: ${totalCost}"

    //return totalCost
}