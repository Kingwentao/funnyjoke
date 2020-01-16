package com.example.funnyjoke.utils

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.example.funnyjoke.model.Destination
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

/**
 * author: created by wentaoKing
 * date: created in 2020-01-15
 * description: 解析
 */
object AppConfig {

    private var sDestConfig = HashMap<String, Destination>()

    fun getDestConfig(): HashMap<String, Destination> {

        if (sDestConfig == null) {
            val content = parseFile("destination.json")
            sDestConfig =
                JSON.parseObject(content, TypeReference<HashMap<String, Destination>>(){}.getType())
        }
        return sDestConfig
    }

    /**
     * 解析文件
     */
    private fun parseFile(name: String): String {

        val assets = AppGlobals.getApplication()?.resources?.assets
        var inputStream: InputStream? = null
        var bfReader: BufferedReader? = null
        val builder = StringBuilder()

        try {
            inputStream = assets?.open(name)
            bfReader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            line = bfReader.readLine()
            while (line != null) {
                builder.append(line)
                line = bfReader.readLine()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            bfReader?.close()
        }

        return builder.toString()
    }

}