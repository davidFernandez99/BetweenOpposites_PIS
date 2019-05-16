package grup05.pis2018.ub.edu.betweenopposites.Game

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.view.SurfaceView
import grup05.pis2018.ub.edu.betweenopposites.Model.*


import grup05.pis2018.ub.edu.betweenopposites.R
import grup05.pis2018.ub.edu.betweenopposites.View.FinJuegoActivity
import grup05.pis2018.ub.edu.betweenopposites.View.UnJugador


class GameView (context: Context,private val size: Point) : SurfaceView(context), Runnable {
    private val MAX_TIEMPO_VELOCIDAD: Long = 10000
    private val MAX_TIEMPO_INVISIBLE: Long = 5000
    private val MAX_TIEMPO_VULNERABLE: Long = 2000
    private val gameThread = Thread(this)
    private var playing = true
    private var paused = false
    private var conv:Long=1000
    private var tiempo:Tiempo  = Tiempo(10000,conv)
    private var tirmpoVulnerable:Tiempo=Tiempo(1000,MAX_TIEMPO_VULNERABLE)
    private var tiempoVelocidad:Tiempo=Tiempo(5000,MAX_TIEMPO_VELOCIDAD)
    private var tiempoInvisibilidad:Tiempo=Tiempo(4000,MAX_TIEMPO_INVISIBLE)
    private var segundos:Long =0
    private var canvas : Canvas = Canvas()
    private val paint : Paint = Paint()

    var bitmapBordeSimple:Bitmap=BitmapFactory.decodeResource(context.resources,R.drawable.borde_singular)
    var bitmapBordeDoble:Bitmap=BitmapFactory.decodeResource(context.resources,R.drawable.borde_doble)
    var bitmapBorde:Bitmap=BitmapFactory.decodeResource(context.resources,R.drawable.borde)
    var bitmapBordeSuperior:Bitmap=BitmapFactory.decodeResource(context.resources,R.drawable.borde_superior)
    var bitmapPausa:Bitmap=BitmapFactory.decodeResource(context.resources,R.drawable.boton_pausa)

    //Instanciamos un objeto vida para poder considerar cuantas vidas dibujar en el canvas
    var vida: Vida =Vida()
    //Cogemos la instancia del único Lobo
    var lobo:Lobo=Lobo.instance
    var comprobar_vulnerabilidad:Boolean=false
    var compobar_aumento:Boolean=false

    //Pruebas
    //Pruebas

    var bando: Actor.Bando = Actor.Bando.Blanco
    var bando2: Actor.Bando = Actor.Bando.Negro
    var orbe:Orbe= Orbe(bando,32f,32f,10f, Actor.Direccion.IZQUIERDA,Posicion(200f,200f))
    var orbe2:Orbe= Orbe(bando2,32f,32f,10f, Actor.Direccion.DERECHA,Posicion(300f,500f))
    var trampa:Trampa=Trampa(32f,32f,Posicion(500f,550f))
    var trampa2:Trampa=Trampa(32f,32f,Posicion(800f,550f))
    var suelo:Suelo?=null
    var muro:Muro?=null
    var aumentarVelocidad:AumentarVelocidad= AumentarVelocidad(32f,32f, Posicion(300f,600f))
    var puerta:Puerta = Puerta(60f,60f,Posicion(700f,310f))

    private fun prepareLevel() { // Aqui inicializaremos los objetos del juego

    }

    override fun run() {

        var fps: Long = 1
        //tiempo.start()
        paint.setColor(Color.WHITE)


        while (playing) {

            // Capture the current time
            val startFrameTime = System.currentTimeMillis()

            // Update the frame
            if (!paused) {
                update(fps)
            }

            // Draw the frame
            draw()

            // Calculate the fps rate this frame
            val timeThisFrame = System.currentTimeMillis() - startFrameTime
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame
            }
        }
    }

    private fun update(fps: Long){  //Aqui actualizaremos el estado de cada objeto
        lobo.mover(fps)
        orbe.mover(fps)
        orbe2.mover(fps)
        //Move the player's wolf
        if(orbe.es_visible){
            orbe.detectarColision(lobo)

        }
        if(orbe2.es_visible){
            orbe2.detectarColision(lobo)

        }

        comprobar_vulnerabilidad=trampa.detectarColision(lobo)
        trampa2.detectarColision(lobo)
        if(comprobar_vulnerabilidad==true){
            lobo.vulnerable=false
            //tirmpoVulnerable.start()
        }
        if(tirmpoVulnerable.finish==true){
            lobo.vulnerable=true
            tirmpoVulnerable.finish=false
        }
        if(tiempo.finish==true){
            segundos++
        }
        compobar_aumento=aumentarVelocidad.detectarColision(lobo)
        if(compobar_aumento==true){
            //tiempoVelocidad.start()
        }

        if(tiempoVelocidad.finish==true){
            lobo.restarurarVelocidad()
            tirmpoVulnerable.finish=false
        }
        if(lobo.velocidad==0f && tiempoVelocidad.finish==false){
            lobo.velocidad=lobo.velocidadCambiada
        }
        puerta.detectarColision(lobo)
        if(lobo.endgame()==true){
            pause()

        }

    }

    private fun draw() {
        // Make sure our drawing surface is valid or the game will crash
        if (holder.surface.isValid) {
            // Lock the canvas ready to draw
            canvas = holder.lockCanvas()

            // Draw the background color
            canvas.drawColor(Color.argb(0, 0, 0, 0))

            //Dibujamos los suelos de la sala
            var i: Int = 0
            var j: Int = 0
            var x: Float = 0f
            var y: Float = 0f
            for (i in 0..30) {
                y = 0f
                for (j in 0..20) {
                    suelo = Suelo(32f, 32f, Posicion(x, y))
                    suelo!!.draw(canvas, context)

                    y += 64f

                }
                x += 64f
            }
            i= 0
            j= 0
            x = 0f
            y= 0f
            for(i in 0..30){
                muro=Muro(32f,32f,Posicion(x,20f))
                muro!!.draw(canvas,context)
                muro!!.detectarColision(lobo)
                muro!!.detectarColision(orbe)
                muro!!.detectarColision(orbe2)
                x+=64f
            }
            i=0
            x=0f
            for(i in 0..30){
                muro=Muro(32f,32f,Posicion(x,1024f))
                muro!!.draw(canvas,context)
                muro!!.detectarColision(lobo)
                muro!!.detectarColision(orbe)
                muro!!.detectarColision(orbe2)
                x+=64f
            }
            i=0
            x=64f
            for (i in 0..23){
                muro=Muro(32f,32f, Posicion(x,696f))
                muro!!.draw(canvas,context)
                muro!!.detectarColision(lobo)
                muro!!.detectarColision(orbe)
                muro!!.detectarColision(orbe2)
                x+=64f
            }
            i=0
            x=256f
            for(i in 0..21){
                muro=Muro(32f,32f, Posicion(x,310f))
                muro!!.draw(canvas,context)
                muro!!.detectarColision(lobo)
                muro!!.detectarColision(orbe)
                muro!!.detectarColision(orbe2)
                x+=64
            }

            i=0
            x=0f
            for(i in 0..20){
                muro=Muro(32f,32f,Posicion(0f,y))
                muro!!.draw(canvas,context)
                muro!!.detectarColision(lobo)
                muro!!.detectarColision(orbe)
                muro!!.detectarColision(orbe2)
                y+=64f
            }
            y=0f
            i=0
            for(i in 0..20){
                muro=Muro(32f,32f,Posicion(1914f,y))
                muro!!.draw(canvas,context)
                muro!!.detectarColision(lobo)
                muro!!.detectarColision(orbe)
                muro!!.detectarColision(orbe2)
                y+=64f
            }

            y=300f
            i=0
            for(i in 0..6){
                muro=Muro(32f,32f,Posicion(1596f,y))
                muro!!.draw(canvas,context)
                muro!!.detectarColision(lobo)
                muro!!.detectarColision(orbe)
                muro!!.detectarColision(orbe2)
                y+=64f
            }
            y=300f
            i=0
            for(i in 0..6){
                muro=Muro(32f,32f,Posicion(1532f,y))
                muro!!.draw(canvas,context)
                muro!!.detectarColision(lobo)
                muro!!.detectarColision(orbe)
                muro!!.detectarColision(orbe2)
                y+=64f
            }

            //Now draw the player wolf

            trampa.draw(canvas, context)
            trampa2.draw(canvas,context)
            if(orbe.es_visible){
                orbe.draw(canvas, context)
            }
            if(orbe2.es_visible){
                orbe2.draw(canvas, context)
            }
            if(aumentarVelocidad.visible==true){
                aumentarVelocidad.draw(canvas,context)
            }

            //Draw all the game objects here
            lobo.draw(canvas, context)

            puerta.draw(canvas,context)
            canvas.drawBitmap(bitmapBordeSuperior, 0f, 0f, paint)
            canvas.drawBitmap(bitmapPausa, 1920f, 0f, paint)

            canvas.drawText(segundos.toString(), 700f, 0f, paint)
            if (lobo.vida.numVide == 3) {
                vida.draw(canvas, 860f, 0f, context)
                vida.draw(canvas, 960f, 0f, context)
                vida.draw(canvas, 1060f, 0f, context)

            } else if (lobo.vida.numVide == 2) {
                vida.draw(canvas, 860f, 0f, context)
                vida.draw(canvas, 960f, 0f, context)

            } else if (lobo.vida.numVide == 1) {
                vida.draw(canvas, 860f, 0f, context)

            }


            // Draw everything to the screen
            holder.unlockCanvasAndPost(canvas)
        }

    }

    fun resume(){
        playing = true
        prepareLevel()
        gameThread.start()

    }

    fun pause(){
        playing = false

    }

    fun onDestroy() {
        gameThread.destroy()
    }

    fun onStop() {
        pause()
        gameThread.stop()

    }


}