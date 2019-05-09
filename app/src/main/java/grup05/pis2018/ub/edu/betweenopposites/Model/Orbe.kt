package grup05.pis2018.ub.edu.betweenopposites.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import java.util.*
import kotlin.random.Random.Default.nextInt

class Orbe(
    bando: Int,
    height: Float,
    width: Float,
    velocidad: Float,
    direccion: Direccion,
    posicionInicial: Posicion,
    posicion: Posicion,
    image: Bitmap?
) : Actor(height, width, velocidad, direccion, posicionInicial, posicion, image) {

    var darPuntuacion:Int=10
    var bando: Int = 0; //Esto habra que hacerlo aleatorio

    override fun mover(fps:Long) {
        if(direccion==Direccion.ABAJO){
            posicion.y+=velocidad/fps
        }
        if(direccion==Direccion.ARRIBA){
            posicion.y-=velocidad/fps
        }

        if(direccion==Direccion.IZQUIERDA){
            posicion.x-=velocidad/fps
        }
        if(direccion==Direccion.DERECHA){
            posicion.x+=velocidad/fps
        }
    }

    /**
     *
     * Método con tal de detectar si el orbe está en colision conun objeto tanto si colisiona con un muro como si colisiona con un objeto Lobo
     * Si el orbe es edel mismo lado del  lobo seguirá un patrón de movimiento de: DiagonaArribalIzquierda, DiagonalAbajoIzquierda,DigonalAbajoDerecha y DiagonalArribaDerecha
     * Esos cuatro movimiento del orbe irán cambiando cuando colisione con alún objeto muro
     *
     * Si el orbe colisiona con un objeto Lobo ,si es del bando contrario a éste le quitara una vida a éste y desaparecerá este orbe
     * Si el orbe es del mismo bando del lobo le augmentará puntos según el multiplicador que lleve en ese momento
     */
    override fun tratarColision(objeto: Objeto) {
        if (objeto is Lobo) {
            var lobo: Lobo = objeto as Lobo
            if (lobo.bando == this.bando) {
                //Le da puntos al lobo
                lobo.sumarPuntuacion(darPuntuacion)
            } else {
                //Le quita puntos al lobo
                lobo.quitarPuntuacion(darPuntuacion)
            }
        }
        if (objeto is Muro) {
            //Canviará dirección del propio orbe para que este siempre en movimiento
            canviarDireccion()
        }
    }
    fun canviarDireccion(){
        var direccionNueva:Int=0
        if(direccion==Direccion.DERECHA){
            direccionNueva=(0..1).random()
            if(direccionNueva==0){
                direccion=Direccion.ABAJO
            }
            else{
                direccion=Direccion.ARRIBA
            }

        }
        if(direccion==Direccion.IZQUIERDA){
            direccionNueva=(0..1).random()
            if(direccionNueva==0){
                direccion=Direccion.ABAJO
            }
            else{
                direccion=Direccion.ARRIBA
            }
        }
        if(direccion==Direccion.ARRIBA){
            direccionNueva=(0..1).random()
            if(direccionNueva==0){
                direccion=Direccion.IZQUIERDA
            }
            else{
                direccion=Direccion.DERECHA
            }
        }
        if(direccion==Direccion.ABAJO){
            direccionNueva=(0..1).random()
            if(direccionNueva==0){
                direccion=Direccion.IZQUIERDA
            }
            else{
                direccion=Direccion.DERECHA
            }
        }
    }

}