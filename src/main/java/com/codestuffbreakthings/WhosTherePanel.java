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

import com.google.common.collect.Ordering;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.EnumComposition;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.PluginPanel;
import net.runelite.http.api.worlds.World;
import net.runelite.http.api.worlds.WorldType;

class WhosTherePanel extends PluginPanel
{
    private static final Color ODD_ROW = new Color(44, 44, 44);

    private final JPanel listContainer = new JPanel();

    @Getter(AccessLevel.PACKAGE)
    private boolean active;
    private WhosThereTableHeader usernameHeader;

    private final ArrayList<WhosThereTableRow> rows = new ArrayList<>();
    private final WhosTherePlugin plugin;

    WhosTherePanel(WhosTherePlugin plugin)
    {
        this.plugin = plugin;

        setBorder(null);
        setLayout(new DynamicGridLayout(0, 1));

        JPanel headerContainer = buildHeader();

        listContainer.setLayout(new GridLayout(0, 1));

        add(headerContainer);
        add(listContainer);
    }

    @Override
    public void onActivate()
    {
        active = true;
        updateList();
    }

    @Override
    public void onDeactivate()
    {
        active = false;
    }

    void updateList()
    {
        listContainer.removeAll();

        for (int i = 0; i < rows.size(); i++)
        {
            WhosThereTableRow row = rows.get(i);
            row.setBackground(i % 2 == 0 ? ODD_ROW : ColorScheme.DARK_GRAY_COLOR);
            listContainer.add(row);
        }

        listContainer.revalidate();
        listContainer.repaint();
    }

    void populate(List<WhosTherePlayerData> playerList)
    {
        rows.clear();
        int i = 0;
        for (WhosTherePlayerData whosTherePlayerData : playerList) {
            rows.add(buildRow(whosTherePlayerData, i % 2 == 0));
            i++;
        }
        updateList();
    }

    /**
     * Builds the entire table header.
     */
    private JPanel buildHeader()
    {
        JPanel header = new JPanel(new BorderLayout());
        JPanel leftSide = new JPanel(new BorderLayout());
        JPanel rightSide = new JPanel(new BorderLayout());

        usernameHeader = new WhosThereTableHeader("Username");
        //usernameHeader.setPreferredSize(new Dimension(WORLD_COLUMN_WIDTH, 0));


        leftSide.add(usernameHeader, BorderLayout.WEST);
        //leftSide.add(playersHeader, BorderLayout.CENTER);

//        rightSide.add(activityHeader, BorderLayout.CENTER);
//        rightSide.add(pingHeader, BorderLayout.EAST);

        header.add(leftSide, BorderLayout.WEST);
        header.add(rightSide, BorderLayout.CENTER);

        return header;
    }

    /**
     * Builds a table row, that displays the world's information.
     */
    private WhosThereTableRow buildRow(WhosTherePlayerData whosTherePlayerData, boolean stripe)
    {
        WhosThereTableRow row = new WhosThereTableRow(whosTherePlayerData);
        row.setBackground(stripe ? ODD_ROW : ColorScheme.DARK_GRAY_COLOR);
        return row;
    }

}
