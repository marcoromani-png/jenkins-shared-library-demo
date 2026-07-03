def call(String apiUrl) {
    echo "Chiamo endpoint REST per calcolare il costo totale: ${apiUrl}"

    def response = httpRequest(
        url: apiUrl,
        httpMode: 'GET',
        acceptType: 'APPLICATION_JSON',
        validResponseCodes: '200'
    )

    echo "Status code ricevuto: ${response.status}"

    def objects = readJSON text: response.content

    if (!(objects instanceof List)) {
        error "Il body della response non è un array JSON"
    }

    BigDecimal totalCost = 0
    int objectsWithPrice = 0
    int objectsWithoutPrice = 0

    objects.each { object ->
        def data = object.data

        if (data != null) {
            def price = data.price

            if (price == null) {
                price = data.Price
            }

            if (price != null) {
                totalCost = totalCost + new BigDecimal(price.toString())
                objectsWithPrice++
                echo "Oggetto: ${object.name} - Prezzo trovato: ${price}"
            } else {
                objectsWithoutPrice++
                echo "Oggetto: ${object.name} - Nessun prezzo trovato"
            }
        } else {
            objectsWithoutPrice++
            echo "Oggetto: ${object.name} - Campo data nullo"
        }
    }

    echo "Oggetti con prezzo: ${objectsWithPrice}"
    echo "Oggetti senza prezzo: ${objectsWithoutPrice}"
    echo "COSTO TOTALE: ${totalCost}"

    return totalCost
}