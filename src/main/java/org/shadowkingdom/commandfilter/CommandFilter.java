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
package org.shadowkingdom.commandfilter;

import co.uk.matthogan.jonfig.Jonfig;

import lombok.Getter;

import org.bukkit.plugin.Plugin;

import org.shadowkingdom.commandfilter.config.Config;
import org.shadowkingdom.commandfilter.config.ConfigLoader;
import org.shadowkingdom.commandfilter.config.LangConfig;
import org.shadowkingdom.commandfilter.util.Util;

/**
 * @author Matthew Hogan
 */
public class CommandFilter {

    @Getter
    private Config config;
    @Getter
    private LangConfig langConfig;

    private CommandFilter() {
        ;
    }

    private static class CommandFilterHolder {
        static final CommandFilter instance = new CommandFilter();
    }

    public static CommandFilter get() {
        return CommandFilterHolder.instance;
    }

    public String getLang(String node) {
        return this.getLang(node, true);
    }

    public String getLang(String node, boolean color) {
        String string = this.langConfig.get(node);
        return color ? Util.colorize(string) : string;
    }

    public void loadConfigs(Plugin plugin) {
        ConfigLoader configLoader = new ConfigLoader();
        Jonfig jonfig = new Jonfig(
                plugin.getDataFolder(),
                plugin.getClass(),
                plugin.getLogger()
        );

        this.config = jonfig.load(
                ("/config/config.json"),
                (configLoader::loadConfig)
        );

        this.langConfig = jonfig.load(
                ("/config/lang.json"),
                (configLoader::loadLang)
        );
    }
}