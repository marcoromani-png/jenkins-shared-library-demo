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


    Double totalCost = 0.0
    int objectsWithPrice = 0
    int objectsWithoutPrice = 0

    objects.each { object ->

        def objectName = object['name']
        def data = object['data']

        if (data == null || data.toString() == 'null') {
            objectsWithoutPrice++
            echo "Oggetto: ${objectName} - Campo data nullo"
        } else {
            def price = null

            if (data.containsKey('price')) {
                price = data['price']
            } else if (data.containsKey('Price')) {
                price = data['Price']
            }

            if (price != null) {
                Double numericPrice = price.toString().toDouble()

                totalCost = totalCost + numericPrice
                objectsWithPrice++

                echo "Oggetto: ${objectName} - Prezzo trovato: ${numericPrice}"
            } else {
                objectsWithoutPrice++
                echo "Oggetto: ${objectName} - Nessun prezzo trovato"
            }
        }
    }

    echo "Oggetti con prezzo: ${objectsWithPrice}"
    //echo "Oggetti senza prezzo: ${objectsWithoutPrice}"
    echo "COSTO TOTALE: ${totalCost}"

    return totalCost
}