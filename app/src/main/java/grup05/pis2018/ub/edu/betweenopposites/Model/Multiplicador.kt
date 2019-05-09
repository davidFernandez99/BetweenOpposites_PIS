package grup05.pis2018.ub.edu.betweenopposites.Model

import android.graphics.Bitmap


class Multiplicador(valor1: Int, height: Float, width: Float, posicionInicial: Posicion,posicion:Posicion) :
    Objeto(height, width, posicionInicial,posicion) {


    //Valor que se suma a los multiplicadores almacenados por el lobo
    var valor: Int = valor1

    /**
     * Notifica al Lobo sobre la colisión pasando el valor del multiplicador
     */
    override fun tratarColision(objeto: Objeto) {

        if (objeto is Lobo) {
            var lobo: Lobo = objeto as Lobo
            lobo.sumarMultiplicador(valor)
        }
    }


}