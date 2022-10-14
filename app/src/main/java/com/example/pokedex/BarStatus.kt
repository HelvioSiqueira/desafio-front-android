package com.example.pokedex

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import kotlin.math.round

class BarStatus @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    style: Int = 0
) : View(context, attributeSet, style) {

    private var tamStatus: Int = 0
    private var largura = 0
    private var altura = 80
    private lateinit var paint: Paint

    init {
        val styledAtts = context.obtainStyledAttributes(attributeSet, R.styleable.BarStatus)
        styledAtts.recycle()
    }

    fun setTamStatus(tamStatusEnviado: Int) {
        tamStatus = tamStatusEnviado
        invalidate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
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

        for (l in 0 until largura - 20 step largura / 15) {

            voltas++

            val alturaBarra = altura.toFloat()
            val posicaoBarra = l + 20F

            paint.color = Color.parseColor("#e7e5ea")

            if (voltas <= tamStatus) {
                paint.color = Color.parseColor("#30a7d7")
            }

            paint.strokeWidth = largura * 0.04F

            canvas?.drawLine(posicaoBarra, 0F, posicaoBarra, alturaBarra, paint)
            //canvas?.drawLine(x, y, x + 30, y, paint)

            //canvas?.drawBitmap(imagem, x + 0F, 50F, paint)

        }
    }
}