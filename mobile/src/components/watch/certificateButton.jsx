import React from "react";
import { Pressable, Text } from "react-native";

export default function CertificateButton({ disabled, onPress }) {
  return (
    <Pressable
      onPress={onPress}
      disabled={disabled}
      className={`py-3 rounded-full mb-10 ${
        disabled ? "bg-gray-500" : "bg-green-400"
      }`}
    >
      <Text className={`text-center text-lg font-bold ${disabled ? "text-white/80" : "text-black"}`}>
        Gerar Certificado
      </Text>
    </Pressable>
  );
}
