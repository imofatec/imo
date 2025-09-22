import React from "react";
import { View, Text } from "react-native";
import { Controller } from "react-hook-form";
import InputText from "./inputText";

export default function FormInput({ control, name, label, ...rest }) {
  return (
    <Controller
      control={control}
      name={name}
      render={({ field, fieldState }) => (
        <View className="mb-4">

          <InputText
            label={label}
            value={field.value}
            onChangeText={field.onChange}
            {...rest}
            className={`border ${
              fieldState.error ? "border-red-500" : "border-transparent"
            }`}
          />

          {fieldState.error && (
            <Text className="text-red-500 mt-1 pl-1">{fieldState.error.message}</Text>
          )}

        </View>
      )}
    />
  );
}
