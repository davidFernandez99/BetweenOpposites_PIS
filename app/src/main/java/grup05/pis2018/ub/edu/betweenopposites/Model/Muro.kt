package grup05.pis2018.ub.edu.betweenopposites.Model

import android.graphics.Bitmap


class Muro(
    height: Float,
    width: Float,
    velocidad: Float,
    direccion: Actor.Direccion,
    posicionInicial: Posicion,
    posicion:Posicion,
    image: Bitmap?
) : Objeto(height, width, posicionInicial,posicion, image) {


    /**
     * Un Actor puede colisionar con un muro. CUando lo haga depende de el objeto harà un cambio u otro.
     */
    override fun tratarColision(objeto: Objeto) {
        if (objeto is Lobo) {
            var lobo: Lobo = objeto as Lobo
            lobo.velocidad=0f
        }
    }


}