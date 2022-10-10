package com.example.pokedex

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

class BarStatus @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    style: Int = 0
) : View(context, attributeSet, style) {

    private var tamanho: Int = 0
    private lateinit var paint: Paint
    private val rectF: RectF = RectF()
    private lateinit var imagem: Bitmap

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        imagem = BitmapFactory.decodeResource(resources, R.drawable.test123)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        tamanho = when(layoutParams.width){

            ViewGroup.LayoutParams.WRAP_CONTENT ->{
                (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48F, resources.displayMetrics).toInt())
            }

            ViewGroup.LayoutParams.MATCH_PARENT ->{
                Math.min(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec))
            }

            else -> layoutParams.width
        }

        setMeasuredDimension(tamanho + 810, tamanho + 20)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for(l in 0..720 step 50){

            val y = l + 0F
            val x = l + 100F

            paint.color = Color.RED
            paint.strokeWidth = 5F

            //canvas?.drawLine(0F + x, 0F, 0F + x, 100F, paint)
            //canvas?.drawLine(x, y, x + 30, y, paint)

            canvas?.drawBitmap(imagem, x + 0F, 50F, paint)

        }

    }
}