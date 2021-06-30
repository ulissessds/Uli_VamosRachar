package com.example.uli_vamosrachar

import java.text.DecimalFormat

class ContaRachada {
    var contaTotal: Double = 0.0
    var grupoNumero: Int = 1

    fun rachaConta(): String {
        if (contaTotal == 0.0)
            return "0.00"
        val df = DecimalFormat("#.00")
        return df.format(contaTotal/grupoNumero)
    }
}
