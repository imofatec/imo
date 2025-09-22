import React from "react";
import { TextInput, Text, View } from 'react-native';

export default function InputText({ label, className = "", ...props }) {
    return (
        <View>
            {label && (
                <Text className="text-white text-xl font-bold mb-2">{label}</Text>
            )}
            <TextInput
                placeholderTextColor="#aaa"
                className={`bg-white/20 text-white px-4 py-3 rounded-2xl ${className}`}
                {...props}
            />
        </View>
    );
}