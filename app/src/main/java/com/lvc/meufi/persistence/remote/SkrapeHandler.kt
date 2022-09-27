package com.lvc.meufi.persistence.remote

import android.util.Log
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.toUpperCase
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.extract
import it.skrape.fetcher.extractIt
import it.skrape.fetcher.skrape
import it.skrape.selects.eachAttribute
import it.skrape.selects.eachClassName
import it.skrape.selects.eachDataAttribute
import it.skrape.selects.eachSrc
import it.skrape.selects.eachTagName
import it.skrape.selects.eachText
import it.skrape.selects.html5.table
import it.skrape.selects.html5.tbody
import it.skrape.selects.html5.td
import it.skrape.selects.html5.th
import it.skrape.selects.html5.thead
import it.skrape.selects.html5.tr
import it.skrape.selects.text

class SkrapeHandler {

     suspend fun collectData(fiiCode: String,
                             onDividendsCollected: (List<RawDividendData>) -> Unit,
                             onFail: (java.lang.Exception) -> Unit = {}) {
         try {
             collectData(fiiCode, onDividendsCollected)
         } catch (e: Exception) {
             Log.i("DATA", "Skrap data FAILED!!! $fiiCode")
             onFail(e)
         }
     }

    private suspend fun collectData(fiiCode: String, onDividendsCollected: (List<RawDividendData>) -> Unit) {
         Log.i("DATA", "Skrap data for: $fiiCode")
        val fiiCodeLower = fiiCode.toLowerCase(Locale.current)
        skrape(HttpFetcher) {
            request {
                url = "https://fiis.com.br/${fiiCodeLower}/"
            }

            extract {
                htmlDocument {
                    table {
                        withId = "last-revenues--table"
                        val headerData = thead {
                           tr {
                               findAll("th") {
                                   eachText
                               }
                           }
                        }

                        val bodyData = tbody {
                            tr {
                                findAll("td") {
                                  eachText
                                }
                            }
                        }

                        val listDividendData = ArrayList<RawDividendData>()
                        val cursor = bodyData.iterator()
                        while (cursor.hasNext()) {
                          listDividendData += RawDividendData(cursor)
                        }
                        onDividendsCollected(listDividendData)
                        Log.i("DATA", "Table data: -> $headerData content $bodyData")
                    }
                }
            }
        }
    }
}