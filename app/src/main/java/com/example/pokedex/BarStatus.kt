package com.example.pokedex

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlin.math.ceil
import kotlin.math.round

class BarStatus @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    style: Int = 0
) : View(context, attributeSet, style) {

    private var tamStatus = 0F
    private var largura = 0
    private var altura = 80
    private lateinit var paint: Paint
    private lateinit var detector: GestureDetector

    init {
        val styledAtts = context.obtainStyledAttributes(attributeSet, R.styleable.BarStatus)
        styledAtts.recycle()
    }

    fun setTamStatus(tamStatusEnviado: Int) {
        tamStatus = tamStatusEnviado.toFloat()
        invalidate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL

        detector = GestureDetector(context, BarStatusListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return detector.onTouchEvent(event)
    }

    inner class BarStatusListener: GestureDetector.SimpleOnGestureListener(){
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {

            if(e.action == MotionEvent.ACTION_UP){
                Toast.makeText(context, tamStatus.toString(), Toast.LENGTH_LONG).show()
            }

            return super.onSingleTapUp(e)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthPixels = resources.displayMetrics.widthPixels
        val heightPixels = resources.displayMetrics.heightPixels

        altura = if(heightPixels > widthPixels){
            round(heightPixels * 0.04).toInt()
        } else {
            round(heightPixels * 0.08).toInt()
        }

        largura = round(widthPixels * 0.6).toInt()

        setMeasuredDimension(largura, altura)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var voltas = 0

        val quantBarras = ceil(tamStatus / 15)

        for (l in 0 until largura - 20 step largura / 15) {

            voltas++

            val alturaBarra = altura.toFloat()
            val posicaoBarra = l + 20F

            paint.color = Color.parseColor("#e7e5ea")

            if (voltas <= quantBarras) {
                paint.color = Color.parseColor("#30a7d7")
            }

            paint.strokeWidth = largura * 0.04F

            canvas?.drawLine(posicaoBarra, 0F, posicaoBarra, alturaBarra, paint)

            //canvas?.drawBitmap(imagem, x + 0F, 50F, paint)

        }
    }
}