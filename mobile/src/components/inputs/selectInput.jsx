import React, { useState } from "react";
import { View, Text } from "react-native";
import DropDownPicker from "react-native-dropdown-picker";
import { Controller } from "react-hook-form";

export default function SelectInput({ control, name, label, className = "", ...props }) {
  const [open, setOpen] = useState(false);
  const [items, setItems] = useState([
    { label: "Iniciante", value: "Iniciante" },
    { label: "Intermediário", value: "Intermediário" },
    { label: "Avançado", value: "Avançado" },
  ]);

  return (
    <Controller
      control={control}
      name={name}
      render={({ field, fieldState }) => (
        <View>
          {label && (
            <Text className="text-white text-xl font-bold mb-2">{label}</Text>
          )}

          <View
            className={`bg-[#3E3351] rounded-2xl px-3 py-1 mb-2 h-12 justify-center ${className} border ${
              fieldState.error ? "border-red-500" : "border-transparent"
            }`}
          >
            <DropDownPicker
              open={open}
              value={field.value}
              items={items}
              setOpen={setOpen}
              setValue={(callback) => {
                const value = callback(field.value);
                field.onChange(value);
              }}
              setItems={setItems}
              placeholder="Selecione um nível"
              placeholderStyle={{ color: '#aaa' }}
              style={{ backgroundColor: "transparent", borderColor: "transparent"}}
              dropDownContainerStyle={{ backgroundColor: "transparent", borderRadius: 12, backgroundColor: "#3E3351" }}
              textStyle={{color: "white"}}
              listMode="SCROLLVIEW"
              {...props}
            />
          </View>

          {fieldState.error && (
            <Text className="text-red-500 mb-2">{fieldState.error.message}</Text>
          )}
        </View>
      )}
    />
  );
}

