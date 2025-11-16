import React from "react";
import { Pressable, Text, ActivityIndicator, View } from "react-native";

export default function CertificateButton({ disabled, onPress, loading }) {
  return (
    <Pressable
      onPress={onPress}
      disabled={disabled || loading}
      className={`py-3 rounded-full mb-10 ${
        disabled || loading ? "bg-gray-500" : "bg-green-400"
      }`}
    >
      <View className="flex-row items-center justify-center">
        {loading && (
          <ActivityIndicator  size="small" color={disabled ? "#fff" : "#000"} className="mr-2"/>
        )}
        <Text className={`text-center text-lg font-bold ${disabled || loading ? "text-white/80" : "text-black"}`}>
          {loading ? "Gerando..." : "Gerar Certificado"}
        </Text>
      </View>
    </Pressable>
  );
}