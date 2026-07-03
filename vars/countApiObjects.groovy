def call(String apiUrl) {
    echo "Chiamo endpoint REST: ${apiUrl}"

    def response = httpRequest(
        url: apiUrl,
        httpMode: 'GET',
        acceptType: 'APPLICATION_JSON',
        validResponseCodes: '200'
    )

    // Stampa nei log lo status code ricevuto dalla chiamata HTTP
    echo "Status code ricevuto: ${response.status}"

    // Converte il body della risposta JSON in un oggetto leggibile
    def objects = readJSON text: response.content

    // Conta quanti elementi ci sono nell'array 
    def count = objects.size()

    echo "Numero oggetti presenti nell array: ${count}"

    return count
}
