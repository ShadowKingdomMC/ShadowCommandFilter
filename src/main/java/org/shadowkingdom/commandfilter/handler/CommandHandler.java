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
package org.shadowkingdom.commandfilter.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import org.shadowkingdom.commandfilter.CommandFilter;
import org.shadowkingdom.commandfilter.config.Config;

/**
 * @author Matthew Hogan
 */
public class CommandHandler implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        // Ignore single slash message
        if (message.equalsIgnoreCase("/")) {
            return;
        }

        // Ignore WorldEdit
        if (message.startsWith("//")) {
            return;
        }

        // Replace all a-Z 0-9 with an empty char
        message = message.replaceAll("[a-zA-Z0-9]+", "");
        Config config = CommandFilter.get().getConfig();

        for (char item : message.toCharArray()) {

            // Do we have a special char that isn't whitelisted?
            if (!config.getCharacterWhitelist().contains(String.valueOf(item))) {

                player.sendMessage(CommandFilter.get().getLang("deny-message")
                        .replace("%char%", String.valueOf(item))
                );

                event.setCancelled(true);
                return;
            }
        }
    }
}