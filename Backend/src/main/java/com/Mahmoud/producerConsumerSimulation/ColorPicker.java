package com.Mahmoud.producerConsumerSimulation;

public class ColorPicker {


    private static final String[] colors = {

            "#FF5733", // Vivid Orange
            "#33FF57", // Lime Green
            "#3357FF", // Bright Blue
            "#FF33A1", // Hot Pink
            "#33FFF5", // Cyan
            "#FF3333", // Bright Red
            "#33FF33", // Neon Green
            "#FFC300", // Bright Yellow
            "#DAF7A6", // Light Green
            "#900C3F", // Deep Maroon
            "#C70039", // Dark Red
            "#581845", // Dark Purple
            "#900C3F", // Burgundy
            "#FF6F61", // Coral
            "#6B5B95", // Indigo
            "#88B04B", // Olive Green
            "#D65076", // Raspberry
            "#45B8AC", // Aqua Blue
            "#EFC050", // Golden Yellow
            "#5B5EA6", // Royal Blue
            "#9B2335", // Crimson
            "#DFCFBE", // Light Beige
            "#BC243C", // Rich Red
            "#98B4D4", // Light Blue
            "#55B4B0"  // Turquoise

    };

    public static String getRandomColor()
    {
        int index=(int) Math.floor(Math.round(Math.random()*25));
        if(index>24)
            index--;
        return colors[index];
    }
}
