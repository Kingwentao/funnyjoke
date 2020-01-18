package com.example.funnyjoke.utils

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.example.funnyjoke.model.BottomBar
import com.example.funnyjoke.model.Destination
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

/**
 * author: created by wentaoKing
 * date: created in 2020-01-15
 * description:
 */
class AppConfig {

    companion object {

        //由于全局存在，所以可以定义成静态
        private var sDestConfig: HashMap<String, Destination>? = null
        private var sButtonBar: BottomBar ?= null

        fun getDestConfig(): HashMap<String, Destination> {

            if (sDestConfig == null) {
                val content = parseFile("destination.json")
                sDestConfig =
                    JSON.parseObject(content,
                        object : TypeReference<HashMap<String, Destination>>() {}.type)
            }
            return sDestConfig!!
        }


        fun getBottomBar(): BottomBar{

            if (sButtonBar == null){
                val content = parseFile("tabs_config.json")
                sButtonBar = JSON.parseObject(content,BottomBar::class.java)
            }
            return sButtonBar!!
        }

        /**
         * 解析文件
         */
        private fun parseFile(name: String): String {

            val assets = AppGlobals.getApplication().resources.assets
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

}