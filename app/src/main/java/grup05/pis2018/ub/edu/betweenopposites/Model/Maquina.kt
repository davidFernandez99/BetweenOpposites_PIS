package grup05.pis2018.ub.edu.betweenopposites.Model


/**
 * Objeto en el juego que se encuentra en las salas especiales, cuando chocamos con ella nos da la oportunidad de
 * ganar un objeto, sumadro, multiplicador, o objeto activo, tras adivinar una pregunta
 * ¿Cual crees que es tu puntuación?.
 * La parte matematica del juego te obliga a llevar la cuenta de tu puntuación para poder ganar objetos extras y
 * poder sacar mayores puntuaciones.
 * La maquina da opciones para posibles respuestas y si se acierta se obtiene la recompensa.
 */
class Maquina(height: Float, width: Float, posicionInicial: Posicion, posicion: Posicion) :
    Objeto(height, width, posicionInicial, posicion) {

    // Contiene la recompensa que se da al jugador en caso de que se adivine la respuesta correcta
    lateinit var recompensa: Objeto
    // Flag que nos dice si debe devolverse el Objeto o no. Inicialmente a "false".
    var dar_recompensa: Boolean = false
    var dar_opciones: Boolean = false
    /**
     * En el caso de que se detecte la colisión con el Lobo, se trata la colisión de forma que se dan las opciones
     * y en el caso de acierto se da el premio.
     * La colision TODO: ENTENDER COMO VA A SER EL PROCESO DE CREAR LAS OPCIONES Y ENTREGAR EL OBJETO RECOMPENSA
     */
    fun darOpciones(lobo: Lobo) {
        TODO("hacer que devuelva un array de int")
        TODO("Generar opciones con numeros aleatorios que tengan relacion con la puntuación en ese momento")
        dar_opciones = true
    }

    override fun tratarColision(objeto: Objeto) {
        if (objeto is Lobo) {
            var lobo: Lobo = objeto as Lobo
            darOpciones(lobo)
        }
    }
}