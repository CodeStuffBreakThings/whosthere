/*
 *  BSD 2-Clause License
 *
 *  Copyright (c) 2020, wikiworm (Brandon Ripley)
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 *  FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.codestuffbreakthings;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
import net.runelite.client.util.QuantityFormatter;

import javax.inject.Inject;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

@Slf4j
public class WhosThereOverlay extends Overlay
{
    private int playerCount = 0;
    private final WhosThereConfig whosThereConfig;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    private WhosThereOverlay(WhosThereConfig config) {
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        this.whosThereConfig = config;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        String overlayTextLeft = "Nearby player count:";
        String overlayTextRight = Integer.toString(playerCount);
        panelComponent.getChildren().clear();

//        panelComponent.getChildren().add(TitleComponent.builder()
//                .text(titleText)
//                .color(Color.GREEN)
//                .build());

        panelComponent.setPreferredSize(new Dimension(
                graphics.getFontMetrics().stringWidth(overlayTextLeft + overlayTextRight) + 30,
                0
        ));

        panelComponent.getChildren().add(LineComponent.builder()
                .left(overlayTextLeft)
                .leftColor(Color.WHITE)
                .right(overlayTextRight)
                .rightColor(Color.YELLOW)
                .build());

        return panelComponent.render(graphics);
    }


    public void updateOverlayPlayerCount(final int newPlayerCount) {
        SwingUtilities.invokeLater(() -> playerCount = newPlayerCount);
    }


}