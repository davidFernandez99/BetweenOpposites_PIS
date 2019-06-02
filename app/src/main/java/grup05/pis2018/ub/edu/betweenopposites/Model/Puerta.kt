package grup05.pis2018.ub.edu.betweenopposites.Model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import grup05.pis2018.ub.edu.betweenopposites.R


/**
 * Objeto que permite el movimiento entre salas para el Lobo.
 */
class Puerta(
    height: Float,
    width: Float,
    posicion: Posicion,
    id_nivel_destino: Int = 0,
    id_sala_destino: Int = 0,
    puerta_destino: Puerta? = null
) :
    Objeto(height, width, posicion) {

    /**
     * Una puerta te envia a un nivel y sala en concreto.
     * En el caso de que sea LA PRIMERA PUERTA DE LA PRIMERA SALA DEL NIVEL, no podemos volver atras,
     * en ese caso tanto sala_destino como nivel_destino son igual a 0.
     * Como posición de destino devolveria null al igual que si fuese la ÚLTIMA PUERTA DEL ÚLTIMO NIVEL,
     * en ese caso la sala destino se crearia como la primera pero con un identificador de nivel mas alto
     * que la cantidad de niveles que tenemos
     */

    // Nivel al que lleva la puerta
    var id_nivel_destino: Int = id_nivel_destino
    //Sala a la que lleva la puerta en ese nivel
    var id_sala_destino: Int = id_sala_destino

    /**
     * Cada puerta tiene un spawnpoint cercano donde aparece el Lobo en caso de que salga por esa puerta, este
     * spawnpoint se obtiene desde la puerta que se atraviesa gracias a puerta_destino, cogiendo su spawnpoint de forma
     * que cuando nos chocamos con una puerta el procedimiento es:
     *      1. Coger la posición de destino en la sala_destino de forma que posicion_destino = puerta_destino.spawn_point
     *      2. Definir la sala_destino como la Sala actual
     *      3. Colocar al Lobo en la posicion_destino
     */

    //Una puerta tiene otra de salida, que és el destino, y la que nos devuelve el spawnpoint de esta.
    // Serà null en el caso de que sea la primera o la última puerta del nivel.
    private var puerta_destino: Puerta? = puerta_destino
    // Posicion en la que aparece el Lobo cuando llega a esta puerta
    lateinit var spawn_point: Posicion


    /**
     * Se encarga de detectar la colision con el lobo. Lleva al Lobo a la posicion de destino
     */
    override fun tratarColision(objeto: Objeto) {
        if (objeto is Lobo) {
            var lobo:Lobo =objeto as Lobo
            lobo.velocidad=0f
            lobo.direccionChoque=lobo.direccion

            while(comprobarColision(lobo)==true){ //Si detecta una colision le hace retroceder hasta que deje de detectarla
                lobo.returnPosicion()
            }
            lobo.returnPosicion()
            lobo.direccion=Direccion.PARADO //Establece que la dirección al colisionar con el muro es PARADO
            if(this.puerta_destino==null && Facade.mapa!=1){
                Facade.acabar_juego=true
            }

            else{
                Facade.instance.traspasarPuerta(this)
            }


        }
        if(objeto is Orbe){
            var orbe: Orbe = objeto as Orbe
            orbe.direccionChoque=orbe.direccion

            while(comprobarColision(orbe)==true){ //Si detecta una colision le hace retroceder hasta que deje de detectarla
                orbe.returnPosicion()
            }
            orbe.canviarDireccionMuro() //Cambia la dirección de este orbe para que deje de colisionar y no este parado

        }
    }

    /**
     * Mètodo que pasado otra puerta pone a esta como destino.
     * En el caso de que sea la primera puerta, esta no puede volver atras, por lo tanto la puerta_destino
     * será ella misma.
     */
    fun setDestino(
        puerta_destino: Puerta? = this.puerta_destino,
        id_sala_destino: Int = this.id_sala_destino,
        id_nivel_destino: Int = this.id_nivel_destino
    ) {
        if(puerta_destino==null){
            this.puerta_destino = this
        }else{
            this.puerta_destino=puerta_destino
        }
        if(id_sala_destino==0){
            this.id_sala_destino=1
        }else{
            this.id_sala_destino=id_sala_destino
        }

        if(id_sala_destino==0){
            this.id_nivel_destino=1
        }else{
            this.id_nivel_destino=id_nivel_destino
        }

    }

    /**
     * Mètodo para coger el destino de esta puerta. Devuelve el nivel, sala y posicion de destino.
     * En el caso de que sea la primera puerta de la primera sala del nivel, no tiene destino así que devuelve un null.
     */
    fun getPosicionDestino(): Posicion? {
        if (puerta_destino != null) {
            return puerta_destino!!.spawn_point
        } else {
            return null
        }
    }

    fun comprobarColision(objeto:Objeto):Boolean{
        var colisio: Boolean = false
        if (this.posicion.x - this.width < objeto.posicion.x + objeto.width
            && this.posicion.x + this.width > objeto.posicion.x - objeto.width
            && this.posicion.y - this.height < objeto.posicion.y + objeto.height
            && this.posicion.y + this.height > objeto.posicion.y - objeto.height
        ) {

            colisio = true
        }
        //Devuelve si ha colisionado o no con ese objeto
        return colisio

    }

    fun printPuerta() {
        println("Puerta \n" +
                "POSICION: [${posicion.x_sala},${posicion.y_sala}] \n" +
                "DESTINO: nivel-> ${id_nivel_destino}    sala->${id_sala_destino}    " +
                "posicion destino-> [${getPosicionDestino()?.x_sala},${getPosicionDestino()?.y_sala}]\n" +
                "SPAWNPOINT: [${spawn_point.x_sala},${spawn_point.y_sala}]")
    }
    override fun draw(canvas: Canvas, image: Bitmap){
        canvas.drawBitmap(image,this.posicion.x-7,this.posicion.y,paint)
    }

}