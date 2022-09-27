package com.lvc.meufi.data_scripts

import com.lvc.meufi.model.Fii
import com.lvc.meufi.model.FiiDividend
import com.lvc.meufi.model.FiiType
import com.lvc.meufi.model.MonthDayYear
import com.lvc.meufi.model.MyFii

/**
 * Data started in
 */
object KnownData {

    fun createMyFiis(): List<MyFii> {
        val myFiis = mutableListOf<MyFii>()

        myFiis += MyFii(
            fiiCode = "CPTS11",
            amount = 47,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "DEVA11",
            amount = 66,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "FLRP11",
            amount = 1,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "HCTR11",
            amount = 49,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "HGRU11",
            amount = 43,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "HLOG11",
            amount = 86,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "HSAF11",
            amount = 82,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "MGFF11",
            amount = 194,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "RBRL11",
            amount = 96,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "RBRY11",
            amount = 53,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "RRCI11",
            amount = 146,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "TRXF11",
            amount = 74,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "VISC11",
            amount = 86,
            updatedAt = MonthDayYear(8, 2022)
        )

        myFiis += MyFii(
            fiiCode = "XPML11",
            amount = 88,
            updatedAt = MonthDayYear(8, 2022)
        )

        return myFiis
    }

    fun createKnownDividends(): List<FiiDividend> {
        val fiiDividends = mutableListOf<FiiDividend>()

        fiiDividends += FiiDividend(
            fiiCode = "CPTS11",
            dividend = 1.10f,
            paymentDate = MonthDayYear(8, 2021)
        )
        fiiDividends += FiiDividend(
            fiiCode = "DEVA11",
            dividend = 1.12f,
            paymentDate = MonthDayYear(8, 2021)
        )
        fiiDividends += FiiDividend(
            fiiCode = "HCTR11",
            dividend = 1.12f,
            paymentDate = MonthDayYear(8, 2021)
        )
        fiiDividends += FiiDividend(
            fiiCode = "HGRU11",
            dividend = 0.82f,
            paymentDate = MonthDayYear(8, 2021)
        )

        fiiDividends += FiiDividend(
            fiiCode = "HLOG11",
            dividend = 0.65f,
            paymentDate = MonthDayYear(8, 2021)
        )

        fiiDividends += FiiDividend(
            fiiCode = "HSAF11",
            dividend = 1.0f,
            paymentDate = MonthDayYear(8, 2021)
        )

        fiiDividends += FiiDividend(
            fiiCode = "MGFF11",
            dividend = 0.55f,
            paymentDate = MonthDayYear(8, 2021)
        )

        fiiDividends += FiiDividend(
            fiiCode = "RBRL11",
            dividend = 0.65f,
            paymentDate = MonthDayYear(8, 2021)
        )

        fiiDividends += FiiDividend(
            fiiCode = "RBRY11",
            dividend = 1.35f,
            paymentDate = MonthDayYear(8, 2021)
        )

        fiiDividends += FiiDividend(
            fiiCode = "RRCI11",
            dividend = 1.20f,
            paymentDate = MonthDayYear(8, 2021)
        )

        fiiDividends += FiiDividend(
            fiiCode = "TRXF11",
            dividend = 0.85f,
            paymentDate = MonthDayYear(8, 2021)
        )

        fiiDividends += FiiDividend(
            fiiCode = "VISC11",
            dividend = 0.71f,
            paymentDate = MonthDayYear(8, 2021)
        )

        fiiDividends += FiiDividend(
            fiiCode = "XPML11",
            dividend = 0.70f,
            paymentDate = MonthDayYear(8, 2021)
        )

        fiiDividends += FiiDividend(
            fiiCode = "FLRP11",
            dividend = 10.00f,
            paymentDate = MonthDayYear(8, 2021)
        )

        return fiiDividends
    }

    fun createListOfFiis(): List<Fii> {
        val fiiList = mutableListOf<Fii>()
        fiiList += Fii(
            code = "CPTS11",
            name = "Capitania Securities",
            type = FiiType.PAPER
        )

        fiiList += Fii(
            code = "DEVA11",
            name = "Devant Recebiveis Imobiliarios",
            type = FiiType.PAPER
        )

        fiiList += Fii(
            code = "HCTR11",
            name = "Fundo DE Investimento Imobiliario Hectare",
            type = FiiType.PAPER
        )

        fiiList += Fii(
            code = "HGRU11",
            name = "SHG Renda Urbana-FI Imobiliario",
            type = FiiType.BRICK
        )

        fiiList += Fii(
            code = "HLOG11",
            name = "Hedge Logística",
            type = FiiType.BRICK
        )

        fiiList += Fii(
            code = "HSAF11",
            name = "HSI Ativos Financeiros",
            type = FiiType.PAPER
        )

        fiiList += Fii(
            code = "MGFF11",
            name = "",
            type = FiiType.FOUNDS_OF_FOUNDS
        )

        fiiList += Fii(
            code = "RBRL11",
            name = "",
            type = FiiType.BRICK
        )

        fiiList += Fii(
            code = "RBRY11",
            name = "",
            type = FiiType.PAPER
        )

        fiiList += Fii(
            code = "RRCI11",
            name = "",
            type = FiiType.PAPER
        )

        fiiList += Fii(
            code = "TRXF11",
            name = "",
            type = FiiType.BRICK
        )

        fiiList += Fii(
            code = "VISC11",
            name = "",
            type = FiiType.BRICK
        )

        fiiList += Fii(
            code = "XPML11",
            name = "",
            type = FiiType.BRICK
        )

        fiiList += Fii(
            code = "FLRP11",
            name = "",
            type = FiiType.BRICK
        )

        return fiiList
    }
}