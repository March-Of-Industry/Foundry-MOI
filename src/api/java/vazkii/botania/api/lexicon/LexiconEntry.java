/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 6:17:06 PM (GMT)]
 */
package vazkii.botania.api.lexicon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.StatCollector;
import vazkii.botania.api.BotaniaAPI;

public class LexiconEntry implements Comparable<LexiconEntry> {

    public final String unlocalizedName;
    public final LexiconCategory category;

    private KnowledgeType type = BotaniaAPI.basicKnowledge;

    public List<LexiconPage> pages = new ArrayList<LexiconPage>();
    private boolean priority = false;

    /**
     * @param unlocalizedName The unlocalized name of this entry. This will be localized by the client display.
     */
    public LexiconEntry(String unlocalizedName, LexiconCategory category) {
        this.unlocalizedName = unlocalizedName;
        this.category = category;
    }

    /**
     * Sets this page as prioritized, as in, will appear before others in the lexicon.
     */
    public LexiconEntry setPriority() {
        priority = true;
        return this;
    }

    /**
     * Sets the Knowledge type of this entry.
     */
    public LexiconEntry setKnowledgeType(KnowledgeType type) {
        this.type = type;
        return this;
    }

    public KnowledgeType getKnowledgeType() {
        return type;
    }

    public boolean isPriority() {
        return priority;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    /**
     * Sets what pages you want this entry to have.
     */
    public LexiconEntry setLexiconPages(LexiconPage... pages) {
        this.pages.addAll(Arrays.asList(pages));

        for (int i = 0; i < this.pages.size(); i++) {
            LexiconPage page = this.pages.get(i);
            if (!page.skipRegistry) page.onPageAdded(this, i);
        }

        return this;
    }

    /**
     * Adds a page to the list of pages.
     */
    public void addPage(LexiconPage page) {
        pages.add(page);
    }

    public final String getNameForSorting() {
        return (priority ? 0 : 1) + StatCollector.translateToLocal(getUnlocalizedName());
    }

    @Override
    public int compareTo(LexiconEntry o) {
        return getNameForSorting().compareTo(o.getNameForSorting());
    }
}
