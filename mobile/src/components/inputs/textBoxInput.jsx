import React from "react";
import { TextInput, Text, View } from 'react-native';
import { Controller } from "react-hook-form";

export default function TextBoxInput({ control, name, label, className = "", ...props }) {
    return (
        <Controller
            control={control}
            name={name}
            render={({ field, fieldState }) => (
                <View>
                    {label && (
                        <Text className="text-white text-xl font-bold mb-2">{label}</Text>
                    )}
                    <TextInput
                        placeholderTextColor="#aaa"
                        className={`text-end min-h-32 bg-white/20 text-white px-4 py-3 rounded-2xl mb-4 
                            border ${fieldState.error ? "border-red-500" : "border-transparent"
                            } ${className}`}
                        multiline={true}
                        textAlignVertical="top"
                        numberOfLines={4}
                        value={field.value}
                        onChangeText={field.onChange}
                        {...props}
                    />
                    {fieldState.error && (
                        <Text className="text-red-500 mb-2">{fieldState.error.message}</Text>
                    )}
                </View>
            )}
        />
    );
}
