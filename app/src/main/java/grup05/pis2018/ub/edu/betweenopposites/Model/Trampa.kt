package grup05.pis2018.ub.edu.betweenopposites.Model

import android.graphics.Bitmap

/**
 * Los objetos Trampa de esta clase se encuantran colocados por las salas y provocan daños al Lobo,
 * haciendo que pierda un corazón.
 */
class Trampa(height: Float, width: Float, posicionInicial: Posicion,posicion:Posicion, image: Bitmap?) :
    Objeto(height, width, posicionInicial,posicion, image) {
    val DANO_EN_CORAZONES = 1
    override fun tratarColision(objeto: Objeto) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    /**
     * Quita DANO_EN_CORAZONES a la vida del Lobo, tras colisionar con el Lobo.
     */

}