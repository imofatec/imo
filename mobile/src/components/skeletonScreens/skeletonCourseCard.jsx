import React from "react";
import { View, Dimensions } from "react-native";

export default function SkeletonCourseCard() {
    const { width } = Dimensions.get("window");
    const imageWidth = width / 2 - 24;
    const imageHeight = (imageWidth * 9) / 16;

    return (
        <View className="flex-1 items-center justify-center my-2 animate-pulse">

            <View style={{
                width: imageWidth,
                height: imageHeight,
            }} className={`bg-gray-700/50 rounded-lg`} />

            <View style={{
                width: imageWidth * 0.8,
                height: 16,
            }} className={`bg-gray-700/50 mt-2 rounded`} />
        </View>
    );
}
