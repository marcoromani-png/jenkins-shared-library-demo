def call(String apiUrl) {
    echo "Chiamo endpoint REST: ${apiUrl}"

    def response = httpRequest(
        url: apiUrl,
        httpMode: 'GET',
        acceptType: 'APPLICATION_JSON',
        validResponseCodes: '200'
    )

    echo "Status code ricevuto: ${response.status}"

    def objects = readJSON text: response.content

    def count = objects.size()

    echo "Numero oggetti presenti nell array: ${count}"

    return count
}
