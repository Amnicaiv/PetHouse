package com.example.myapplication

import androidx.annotation.Nullable

class EstanciaItem(var nombre:String, @Nullable var descripcion:String?,@Nullable var tiposMascotas:String?,@Nullable var tamaños:String?,
                   var direccion:String,@Nullable var img1:Int,@Nullable var img2:Int?,@Nullable var img3:Int?,@Nullable var comprobante:Int?, @Nullable var servicioComida:Boolean?,
                   @Nullable var servicioBaño:Boolean?,@Nullable var costoNoche:Float?) {
}