def call(String apiUrl) {
    echo "Chiamo endpoint REST: ${apiUrl}"

    def response = httpRequest(
        url: apiUrl,
        httpMode: 'GET',
        acceptType: 'APPLICATION_JSON',
        validResponseCodes: '200'
    )

    def objects = readJSON text: response.content

    Double totalCost = 0.0
    int elementsWithPrice = 0

    objects.each { object ->
        def data = object['data']

        if (data != null && data.toString() != 'null') {
            def price = data['price'] ?: data['Price']

            if (price != null) {
                totalCost = totalCost + price.toString().toDouble()
                elementsWithPrice++
            }
        }
    }

    echo "Numero elementi con price/Price: ${elementsWithPrice}"
    echo "Total cost: ${totalCost}"

    return totalCost
}