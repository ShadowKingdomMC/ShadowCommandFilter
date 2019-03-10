/*
 * MIT License
 *
 * Copyright (c) 2019 Primary Script, Inc <https://primaryscript.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Written by Matthew Hogan <matt@matthogan.co.uk> March 2019
 */
package org.shadowkingdom.commandfilter.config;

import org.json.simple.JSONObject;

import org.shadowkingdom.commandfilter.CommandFilterPlugin;
import org.shadowkingdom.commandfilter.util.JsonUtil;
import org.shadowkingdom.commandfilter.util.Util;

import java.util.HashMap;

/**
 * @author Matthew Hogan
 */
public class ConfigLoader {

    public Config loadConfig(Object json) {
        JSONObject configJson = (JSONObject) json;
        return () -> JsonUtil.getStringList(configJson.get("special-character-whitelist"));
    }

    public LangConfig loadLang(Object json) {
        JSONObject langJson = (JSONObject) json;

        return new LangConfig() {
            HashMap<String, String> key2value = new HashMap<>();

            private void populate() {
                for (Object key : langJson.keySet()) {
                    if (!(key instanceof String)) {
                        continue;
                    }

                    this.key2value.put(
                            (String) key,
                            (String) langJson.get(key)
                    );
                }
            }

            @Override
            public String get(String node) {

                if (this.key2value.isEmpty()) {
                    this.populate();
                }

                String text = this.key2value.get(node);

                if (text == null) {
                    text = "&7Text node " + node + " is undefined.";
                    CommandFilterPlugin.getPlugin().getLogger()
                            .severe("Missing text node " + node);
                }

                return Util.colorize(text);
            }
        };
    }
}