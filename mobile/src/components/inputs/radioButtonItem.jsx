import React from "react";
import { Pressable, View, Text } from "react-native";

export default function RadioButtonItem({ name, value, selectedValue, onPress }) {
    const isSelected = selectedValue === value;

    return (
        <Pressable
            className={`flex-row items-center py-2 px-3 mb-2 rounded-lg ${isSelected ? "bg-blue-600" : "bg-custom-secondary"}`}
            onPress={onPress}
        >
            <View className="h-4 w-4 mr-3 rounded-full border-2 border-white flex items-center justify-center">
                {isSelected && <View className="h-2 w-2 bg-white rounded-full" />}
            </View>
            <Text className="text-white">{name}</Text>
        </Pressable>
    );
}
