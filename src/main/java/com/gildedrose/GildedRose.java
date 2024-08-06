package com.gildedrose;

/**
 * The GildedRose class provides a method to update the quality and sell-in values of items in the inventory.
 */
class GildedRose {
    Item[] items;

    /**
     * Constructs a GildedRose instance with an array of items.
     *
     * @param items An array of Item objects.
     */
    public GildedRose(Item[] items) {
        this.items = items;
    }

    /**
     * Updates the quality and sell-in values of all items in the inventory according to specified rules.
     */
    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];

            // Handle regular items and conjured items
            if (!item.name.equals("Aged Brie") && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (item.quality > 0) {
                    if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                        // Conjured items degrade in quality twice as fast
                        if (item.name.startsWith("Conjured")) {
                            item.quality = Math.max(0, item.quality - 2);
                        } else {
                            item.quality = item.quality - 1;
                        }
                    }
                }
            } else {
                // Handle Aged Brie and Backstage passes
                if (item.quality < 50) {
                    item.quality = item.quality + 1;

                    // Backstage passes quality increases more as SellIn value decreases
                    if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (item.sellIn < 11 && item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                        if (item.sellIn < 6 && item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            }

            // Decrease SellIn value for all items except Sulfuras
            if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                item.sellIn = item.sellIn - 1;
            }

            // Handle items with expired SellIn value
            if (item.sellIn < 0) {
                if (!item.name.equals("Aged Brie")) {
                    if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (item.quality > 0) {
                            if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                                // Conjured items degrade in quality twice as fast after SellIn date
                                if (item.name.startsWith("Conjured")) {
                                    item.quality = Math.max(0, item.quality - 2);
                                } else {
                                    item.quality = item.quality - 1;
                                }
                            }
                        }
                    } else {
                        // Backstage passes quality drops to 0 after the concert
                        item.quality = 0;
                    }
                } else {
                    // Aged Brie increases in quality even after SellIn date
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                }
            }
        }
    }
}
