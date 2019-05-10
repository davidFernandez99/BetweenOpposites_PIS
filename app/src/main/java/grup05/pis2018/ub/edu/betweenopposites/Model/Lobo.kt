package grup05.pis2018.ub.edu.betweenopposites.Model


/**
 *
 */
class Lobo(
    vida: Vida,
    bando: Bando,
    height: Float,
    width: Float,
    velocidad: Float,
    direccion: Direccion,
    posicionInicial: Posicion,
    posicion: Posicion
) : Actor(height, width, velocidad, direccion, posicionInicial, posicion) {
    var vida: Vida = vida
    var puntuacion: Puntuacion = Puntuacion(0)

    /**
     * Contiene la única instancia Loco con la cual debemos trabajar.
     * Es lo mismo que crear un campo estatico en Java
     * De esta forma podemos tener una única instancia para esta clase.
     */
    companion object {
        var life: Vida = Vida()
        var bando: Bando = Bando.Negro
        val instance = Lobo(life, bando, 2f, 2f, 10f, Direccion.DERECHA, Posicion(200f, 200f), Posicion(200f, 200f))
    }

    /**
     * Funcion que devuelve la instancia única de la Facade
     */

    // Bando al que pertenece el Lobo (Blanco, Negro)
    var bando: Bando = bando;

    var objetoActivable: ObjetoActivable? = null

    var multiplicador: Int = 1

    //Variable que nos dice si es visible
    var es_visible: Boolean = true

    //Variable que nos dice si esta vivo
    var esta_vivo: Boolean = true

    /**
     * TODO: ¿Lo que hace esta clase es devolver la siguiente posición del lobo donde debe ser dibujado
     *  TODO: en función de velocidad, dirección y conociendo los fps... o es mejor que de eso se encarge el gameEngine?
     */
    override fun mover(fps: Long) {
        if (direccion == Direccion.ABAJO) {
            posicion.y += velocidad / fps
        }
        if (direccion == Direccion.ARRIBA && posicion.y > velocidad / fps) {
            posicion.y -= velocidad / fps
        }

        if (direccion == Direccion.IZQUIERDA && posicion.x > velocidad / fps) {
            posicion.x -= velocidad / fps
        }
        if (direccion == Direccion.DERECHA) {
            posicion.x += velocidad / fps
        }
    }

    /**
     * TODO: Método en principio no utilizado por Lobo
     * QUIZÀ PARA LOS MUROS
     * EL lobo no deberia ser notificado nunca para detectar una colision, si fuese así seria diferente al resto de
     * objetos.
     */
    override fun tratarColision(objeto: Objeto) {
        //Este método desde la clase Lobo nunca será llamado
    }

    //MÉTODOS PROPIOS DE LA CLASE LOBO

    /**
     * Suma cierta puntuación teniendo en cuenta el multiplicador acumulado
     * TODO: DECIDIR DONDE SE ENCUANTRA LA PUNTUACION DEL JUEGO GUARDADA Y COMO PODEMOS MODIFICARLA
     */
    fun sumarPuntuacion(valorSumadpr: Int) {
        this.puntuacion.puntuacion += valorSumadpr
    }

    fun quitarPuntuacion(valorSumador: Int) {
        if (this.puntuacion.puntuacion < valorSumador) {
            this.puntuacion.puntuacion = 0
        } else {
            this.puntuacion.puntuacion -= valorSumador
        }
    }

    /**
     * Aumenta el valor de multiplicador acumulado
     */
    fun sumarMultiplicador(valorMultiplicador: Int) {
        this.multiplicador += valorMultiplicador
    }

    /**
     * Quita una vida al Lobo. En el momento que se pierden todas las vidas el valor esta_vivo=false.
     * TODO: EXTRAS En este metodo podriamos hacer cosas extras en el caso de que se la quiten, realentizar, o hacer inmortal por un tiempo corto
     */
    fun quitarVida() {
        // Si solo le queda una vida se quedarà con zero
        if (vida.numVide == 1) {
            vida.quitarVida()
            esta_vivo =
                false //Cada iteración del bucle después de comprobar las colisiones comprobaremos si el lobo esta vivo o no
        } else {
            vida.quitarVida()
        }
    }


}