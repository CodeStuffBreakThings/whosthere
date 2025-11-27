/*
 * Copyright (c) 2018, Psikoi <https://github.com/Psikoi>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.codestuffbreakthings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import lombok.Getter;
import net.runelite.client.ui.FontManager;
import net.runelite.http.api.worlds.World;
import net.runelite.http.api.worlds.WorldRegion;
import net.runelite.http.api.worlds.WorldType;

class WhosThereTableRow extends JPanel
{

    private static final int USERNAME_COLUMN_WIDTH = 100;
    private static final int PLAYERS_COLUMN_WIDTH = 40;
    private static final int PING_COLUMN_WIDTH = 35;

    private static final Color CURRENT_WORLD = new Color(66, 227, 17);
    private static final Color DANGEROUS_WORLD = new Color(251, 62, 62);
    private static final Color BETA_WORLD = new Color(79, 145, 255);
    private static final Color MEMBERS_WORLD = new Color(210, 193, 53);
    private static final Color FREE_WORLD = new Color(200, 200, 200);
    private static final Color SEASONAL_WORLD = new Color(133, 177, 178);
    private static final Color PVP_ARENA_WORLD = new Color(144, 179, 255);
    private static final Color QUEST_SPEEDRUNNING_WORLD = new Color(94, 213, 201);
    private static final Color FRESH_START_WORLD = new Color(255, 211, 83);

    @Getter
    //private final WhosTherePlayerData whosTherePlayerData;
    private String username;

    private JLabel usernameField;

    private Color lastBackground;

    WhosThereTableRow(WhosTherePlayerData whosTherePlayerData)
    {
        //this.username = whosTherePlayerData.getUsername();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(2, 0, 2, 0));

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent mouseEvent)
            {
//                if (mouseEvent.getClickCount() == 2)
//                {
//                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent)
            {
                if (mouseEvent.getClickCount() == 2)
                {
                    setBackground(getBackground().brighter());
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent)
            {
                if (mouseEvent.getClickCount() == 2)
                {
                    setBackground(getBackground().darker());
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent)
            {
                WhosThereTableRow.this.lastBackground = getBackground();
                setBackground(getBackground().brighter());
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent)
            {
                setBackground(lastBackground);
            }
        });

        JPanel leftSide = new JPanel(new BorderLayout());
        JPanel rightSide = new JPanel(new BorderLayout());
        leftSide.setOpaque(false);
        rightSide.setOpaque(false);

        JPanel usernameField = buildUsernameField(whosTherePlayerData.getUsername());
        //usernameField.setPreferredSize(new Dimension(USERNAME_COLUMN_WIDTH, 0));
        usernameField.setOpaque(false);

//        JPanel pingField = buildPingField(ping);
//        pingField.setPreferredSize(new Dimension(PING_COLUMN_WIDTH, 0));
//        pingField.setOpaque(false);
//
//        JPanel playersField = buildPlayersField();
//        playersField.setPreferredSize(new Dimension(PLAYERS_COLUMN_WIDTH, 0));
//        playersField.setOpaque(false);
//
//        JPanel activityField = buildActivityField();
//        activityField.setBorder(new EmptyBorder(5, 5, 5, 5));
//        activityField.setOpaque(false);

       // recolour(current);

        leftSide.add(usernameField, BorderLayout.WEST);
//        leftSide.add(playersField, BorderLayout.CENTER);
//        rightSide.add(activityField, BorderLayout.CENTER);
//        rightSide.add(pingField, BorderLayout.EAST);

        add(leftSide, BorderLayout.WEST);
        add(rightSide, BorderLayout.CENTER);
    }

//    public void recolour(boolean current) {
//        playerCountField.setForeground(current ? CURRENT_WORLD : Color.WHITE);
//        pingField.setForeground(current ? CURRENT_WORLD : Color.WHITE);
//
//        if (current) {
//            activityField.setForeground(CURRENT_WORLD);
//            worldField.setForeground(CURRENT_WORLD);
//            return;
//        }
//    }


    private JPanel buildUsernameField(String username)
    {
        JPanel column = new JPanel(new BorderLayout(7, 0));
        column.setBorder(new EmptyBorder(0, 5, 0, 5));

        usernameField = new JLabel(username);

        column.add(usernameField, BorderLayout.CENTER);

        return column;
    }
}